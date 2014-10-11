/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import javax.inject.Inject;

import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.api.EnumProvider;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.google.gwt.i18n.server.Type.EnumType;

/**
 * This class extends {@link SimpleDeserializers} in order to create a {@link DatatypeJsonDeserializer}
 * dynamically as requested.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeJsonDeserializers extends SimpleDeserializers {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #setDatatypeDescriptorManager(DatatypeDescriptorManager) */
  private DatatypeDescriptorManager datatypeDescriptorManager;

  /** @see #setDatatypeDetector(DatatypeDetector) */
  private DatatypeDetector datatypeDetector;

  /** @see #setEnumProvider */
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
    EnumJsonDeserializer enumDeserializer = new EnumJsonDeserializer(this.datatypeDescriptorManager, this.enumProvider);
    addDeserializer(Enum.class, enumDeserializer);
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

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc)
      throws JsonMappingException {

    JsonDeserializer<?> deserializer = super.findBeanDeserializer(type, config, beanDesc);
    if (deserializer == null) {
      @SuppressWarnings("rawtypes")
      Class datatype = type.getRawClass();
      if (this.datatypeDetector.isDatatype(datatype)) {
        if ((datatype.isEnum() || EnumType.class.isAssignableFrom(datatype))) {
          deserializer = new EnumTypeJsonDeserializer<>(datatype, this.enumProvider);
        } else {
          // construction is cheap, addDeserializer uses a Map that is not thread-safe...
          deserializer = new DatatypeJsonDeserializer<>(datatype, this.datatypeDescriptorManager);
        }
      }
    }
    return deserializer;
  }
}
