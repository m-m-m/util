/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter.jackson;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.lang.api.EnumProvider;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * This is a factory bean to {@link #create() create} an instance of {@link ObjectMapper} for JSON mapping
 * with {@link net.sf.mmm.util.lang.api.Datatype} support via {@link DatatypeDescriptorManager}.
 *
 * @see DatatypeDescriptorManager
 * @see DatatypeJsonSerializer
 * @see DatatypeJsonDeserializer
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeObjectMapperFactory extends AbstractLoggableComponent {

  /** @see #setDatatypeDescriptorManager(DatatypeDescriptorManager) */
  private DatatypeDescriptorManager datatypeDescriptorManager;

  /** @see #setDatatypeDetector(DatatypeDetector) */
  private DatatypeDetector datatypeDetector;

  /** @see #setEnumProvider(EnumProvider) */
  private EnumProvider enumProvider;

  /**
   * The constructor.
   */
  public DatatypeObjectMapperFactory() {

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

  /**
   * @return an instance of {@link ObjectMapper} configured to support all
   *         {@link net.sf.mmm.util.lang.api.Datatype}s supported by {@link DatatypeDescriptorManager}.
   */
  public ObjectMapper create() {

    ObjectMapper mapper = new ObjectMapper();

    SimpleModule module = new SimpleModule("DatatypeAdapter", new Version(6, 0, 0, null, "net.sf.mmm", "mmm-util-core"));

    DatatypeJsonSerializers serializers = new DatatypeJsonSerializers();
    serializers.setDatatypeDescriptorManager(this.datatypeDescriptorManager);
    serializers.setDatatypeDetector(this.datatypeDetector);
    module.setSerializers(serializers);
    DatatypeJsonDeserializers deserializers = new DatatypeJsonDeserializers();
    deserializers.setDatatypeDescriptorManager(this.datatypeDescriptorManager);
    deserializers.setDatatypeDetector(this.datatypeDetector);
    deserializers.setEnumProvider(this.enumProvider);
    module.setDeserializers(deserializers);
    mapper.registerModule(module);
    return mapper;
  }

}
