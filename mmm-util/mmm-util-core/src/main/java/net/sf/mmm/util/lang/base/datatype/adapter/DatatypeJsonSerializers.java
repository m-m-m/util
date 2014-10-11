/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import javax.inject.Inject;

import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeDetector;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

/**
 * This class extends {@link SimpleSerializers} in order to create serializers dynamically.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeJsonSerializers extends SimpleSerializers {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #setDatatypeDescriptorManager(DatatypeDescriptorManager) */
  private DatatypeDescriptorManager datatypeDescriptorManager;

  /** @see #setDatatypeDetector(DatatypeDetector) */
  private DatatypeDetector datatypeDetector;

  /** @see #findSerializer(SerializationConfig, JavaType, BeanDescription) */
  private DatatypeJsonSerializer<?> datatypeJsonSerializer;

  /**
   * The constructor.
   */
  public DatatypeJsonSerializers() {

    super();
  }

  /**
   * @param datatypeDescriptorManager is the {@link DatatypeDescriptorManager} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDescriptorManager(DatatypeDescriptorManager datatypeDescriptorManager) {

    this.datatypeDescriptorManager = datatypeDescriptorManager;
    this.datatypeJsonSerializer = new DatatypeJsonSerializer<>(this.datatypeDescriptorManager);
  }

  /**
   * @param datatypeDetector is the {@link DatatypeDetector} to {@link Inject}.
   */
  @Inject
  public void setDatatypeDetector(DatatypeDetector datatypeDetector) {

    this.datatypeDetector = datatypeDetector;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {

    JsonSerializer<?> serializer = super.findSerializer(config, type, beanDesc);
    if (serializer == null) {
      @SuppressWarnings("rawtypes")
      Class datatype = type.getRawClass();
      if (this.datatypeDetector.isDatatype(datatype)) {
        if (!this.datatypeDetector.isJavaStandardDatatype(datatype)) {
          serializer = this.datatypeJsonSerializer;
        }
      }
    }
    return serializer;
  }

}
