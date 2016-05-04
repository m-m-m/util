/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jackson;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;

import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.api.EnumProvider;

/**
 * This class extends {@link SimpleDeserializers} in order to create a {@link DatatypeJsonDeserializer} dynamically as
 * requested.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeJsonDeserializers extends SimpleDeserializers {

  private static final long serialVersionUID = 1L;

  private DatatypeDescriptorManager datatypeDescriptorManager;

  private DatatypeDetector datatypeDetector;

  private EnumProvider enumProvider;

  /**
   * The constructor.
   */
  public DatatypeJsonDeserializers() {

    super();
  }

  /**
   * @param datatypeDescriptorManager is the {@link DatatypeDescriptorManager} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDescriptorManager(DatatypeDescriptorManager datatypeDescriptorManager) {

    this.datatypeDescriptorManager = datatypeDescriptorManager;
  }

  /**
   * @param datatypeDetector is the {@link DatatypeDetector} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDetector(DatatypeDetector datatypeDetector) {

    this.datatypeDetector = datatypeDetector;
  }

  /**
   * @param enumProvider is the {@link EnumProvider} to {@link Inject}.
   */
  @Inject
  public void setEnumProvider(EnumProvider enumProvider) {

    this.enumProvider = enumProvider;
  }

  @Override
  public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config,
      BeanDescription beanDesc) throws JsonMappingException {

    return new EnumTypeJsonDeserializer<>(type, this.enumProvider);
  }

  @SuppressWarnings("unchecked")
  @Override
  public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config,
      BeanDescription beanDesc) throws JsonMappingException {

    JsonDeserializer<?> deserializer = super.findBeanDeserializer(type, config, beanDesc);
    if (deserializer == null) {
      @SuppressWarnings("rawtypes")
      Class datatype = type.getRawClass();
      if (this.datatypeDetector.isDatatype(datatype)) {
        if (!this.datatypeDetector.isJavaStandardDatatype(datatype)) {
          // construction is cheap, addDeserializer uses a Map that is not thread-safe...
          deserializer = new DatatypeJsonDeserializer<>(datatype, this.datatypeDescriptorManager);
        }
      }
    }
    return deserializer;
  }
}
