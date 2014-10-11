/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import java.io.IOException;

import javax.inject.Inject;

import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.EnumProvider;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;

/**
 * This is a generic deserializer for {@link Enum}s with additional support via
 * {@link DatatypeDescriptorManager}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@SuppressWarnings("rawtypes")
public class EnumJsonDeserializer extends JsonDeserializer<Enum> implements ContextualDeserializer {

  /** @see #setDatatypeDescriptorManager(DatatypeDescriptorManager) */
  private DatatypeDescriptorManager datatypeDescriptorManager;

  /** @see #setEnumProvider(EnumProvider) */
  private EnumProvider enumProvider;

  /**
   * The constructor.
   */
  public EnumJsonDeserializer() {

    super();
  }

  /**
   * The constructor.
   *
   * @param datatypeDescriptorManager the {@link DatatypeDescriptorManager}.
   * @param enumProvider is the {@link EnumProvider}.
   */
  public EnumJsonDeserializer(DatatypeDescriptorManager datatypeDescriptorManager, EnumProvider enumProvider) {

    super();
    this.datatypeDescriptorManager = datatypeDescriptorManager;
    this.enumProvider = enumProvider;
  }

  /**
   * @param datatypeDescriptorManager is the {@link DatatypeDescriptorManager} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDescriptorManager(DatatypeDescriptorManager datatypeDescriptorManager) {

    this.datatypeDescriptorManager = datatypeDescriptorManager;
  }

  /**
   * @param enumProvider is the {@link EnumProvider} to {@link Inject}.
   */
  @Inject
  public void setEnumProvider(EnumProvider enumProvider) {

    this.enumProvider = enumProvider;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Enum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    DeserializerFactory factory = ctxt.getFactory();
    throw new UnsupportedOperationException("Invalid usage of ContextualDeserializer!");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
      throws JsonMappingException {

    if (property == null) {
      // fallback if jackson fails to provide type (if enum is root object)
      return this;
    }
    return new EnumTypeJsonDeserializer<>(property.getType().getRawClass(), this.enumProvider);
  }

}
