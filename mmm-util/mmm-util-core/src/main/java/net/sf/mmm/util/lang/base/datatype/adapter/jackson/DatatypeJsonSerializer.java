/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jackson;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.util.lang.api.DatatypeDescriptor;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * This is a generic {@link JsonSerializer} based on {@link DatatypeDescriptorManager} so every
 * {@link net.sf.mmm.util.lang.api.Datatype} supported by {@link DatatypeDescriptorManager} can be serialized
 * to JSON.
 *
 * @see DatatypeJsonDeserializer
 *
 * @param <T> is the generic type of the {@link net.sf.mmm.util.lang.api.Datatype} to serialize.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeJsonSerializer<T> extends JsonSerializer<T> {

  /** @see #setDatatypeDescriptorManager(DatatypeDescriptorManager) */
  private DatatypeDescriptorManager datatypeDescriptorManager;

  /**
   * The constructor.
   *
   */
  public DatatypeJsonSerializer() {

    super();
  }

  /**
   * The constructor.
   *
   * @param datatypeDescriptorManager is the {@link DatatypeDescriptorManager} instance to use.
   */
  public DatatypeJsonSerializer(DatatypeDescriptorManager datatypeDescriptorManager) {

    super();
    this.datatypeDescriptorManager = datatypeDescriptorManager;
  }

  /**
   * @param datatypeDescriptorManager is the {@link DatatypeDescriptorManager} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDescriptorManager(DatatypeDescriptorManager datatypeDescriptorManager) {

    this.datatypeDescriptorManager = datatypeDescriptorManager;
  }

  /**
   * Generic and recursive implementation of {@link #serialize(Object, JsonGenerator, SerializerProvider)}.
   *
   * @param <V> is the generic type of <code>value</code>.
   * @param value is the value to serialize.
   * @param jgen is the {@link JsonGenerator}.
   * @param provider is the {@link SerializerProvider}.
   * @throws IOException in case of an error writing the serialized data.
   */
  public <V> void serializeGeneric(V value, JsonGenerator jgen, SerializerProvider provider) throws IOException {

    if (value == null) {
      return;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    Class<V> type = (Class) value.getClass();
    DatatypeDescriptor<V> descriptor = this.datatypeDescriptorManager.getDatatypeDescriptor(type);
    List<DatatypeSegmentDescriptor<V, ?>> segmentList = descriptor.getSegmentDescriptors();

    int segmentCount = segmentList.size();
    if (segmentCount == 0) {
      jgen.writeObject(value);
    } else if (segmentCount == 1) {
      Object segment = descriptor.getSegment(value, 0);
      // TODO: avoid infinity loop?
      serializeGeneric(segment, jgen, provider);
    } else {
      jgen.writeStartObject();
      for (DatatypeSegmentDescriptor<V, ?> segment : segmentList) {
        Object segmentValue = segment.getSegment(value);
        if (segmentValue == null) {
          assert (segment.isOptional());
        } else {
          jgen.writeFieldName(segment.getName());
          serializeGeneric(segmentValue, jgen, provider);
        }
      }
      jgen.writeEndObject();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
      JsonProcessingException {

    serializeGeneric(value, jgen, provider);
  }

}
