/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.adapter;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.lang.api.DatatypeDescriptorManager;
import net.sf.mmm.util.lang.api.DatatypeDetector;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

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

  private ReflectionUtil reflectionUtil;

  private DatatypeDescriptorManager datatypeDescriptorManager;

  private DatatypeDetector datatypeDetector;

  /**
   * The constructor.
   */
  public DatatypeObjectMapperFactory() {

    super();
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    this.reflectionUtil = reflectionUtil;
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

  public ObjectMapper create() {

    ObjectMapper mapper = new ObjectMapper();

    SimpleModule module = new SimpleModule("DatatypeAdapter", new Version(6, 0, 0, null, "net.sf.mmm", "mmm-util-core"));

    Collection<Class<?>> datatypes = findAllDatatypes();

    for (Class<?> datatype : datatypes) {
      registerDatatype(module, datatype);
    }
    mapper.registerModule(module);
    return mapper;
  }

  /**
   * Registers generic {@link DatatypeJsonSerializer serializer} and {@link DatatypeJsonDeserializer
   * deserializer} for the given <code>datatype</code>.
   *
   * @param <T> is the generic type of <code>datatype</code>.
   * @param module is the {@link SimpleModule} where to register.
   * @param datatype is the {@link Class} reflecting the {@link net.sf.mmm.util.lang.api.Datatype} to support.
   */
  private <T> void registerDatatype(SimpleModule module, Class<T> datatype) {

    module.addSerializer(datatype, new DatatypeJsonSerializer<>(this.datatypeDescriptorManager));
    module.addDeserializer(datatype, new DatatypeJsonDeserializer<>(datatype, this.datatypeDescriptorManager));
  }

  /**
   * @return a {@link Collection} with all the {@link Class}es reflecting the
   *         {@link net.sf.mmm.util.lang.api.Datatype}s that should be supported.
   */
  private Collection<Class<?>> findAllDatatypes() {

    Filter<String> nameFilter = new Filter<String>() {

      @Override
      public boolean accept(String value) {

        if (value.startsWith("java.")) {
          return false;
        }
        if (value.startsWith("org.springframework.")) {
          return false;
        }
        if (value.startsWith("org.hibernate.")) {
          return false;
        }
        if (value.startsWith("org.apache.cxf.")) {
          return false;
        }
        if (value.startsWith("net.sf.mmm.util.pojo.")) {
          return false;
        }
        if (value.contains("$")) {
          return false;
        }
        return true;
      }
    };
    Set<String> classNames = this.reflectionUtil.findClassNames("", true, nameFilter);
    Filter<Class<?>> classFilter = new Filter<Class<?>>() {

      @Override
      public boolean accept(Class<?> value) {

        if (value.isInterface()) {
          return false;
        }
        if (!value.isEnum() && Modifier.isAbstract(value.getModifiers())) {
          return false;
        }
        if (!DatatypeObjectMapperFactory.this.datatypeDetector.isDatatype(value)) {
          return false;
        }
        getLogger().debug("Detected datatype {}", value);
        return true;
      }
    };
    Set<Class<?>> classes = this.reflectionUtil.loadClasses(classNames, classFilter);
    return classes;
  }
}
