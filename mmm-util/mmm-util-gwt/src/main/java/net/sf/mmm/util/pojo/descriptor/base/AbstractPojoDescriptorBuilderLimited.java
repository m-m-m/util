/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorImpl;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.base.ReflectionUtilLimitedImpl;

import com.google.gwt.core.shared.GWT;

/**
 * This is the abstract base-implementation of the {@link PojoDescriptorBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoDescriptorBuilderLimited extends AbstractLoggableComponent implements
    PojoDescriptorBuilder {

  /** @see #getInstance() */
  private static final PojoDescriptorBuilder INSTANCE = GWT.create(PojoDescriptorBuilder.class);

  /** @see #getDescriptor(Class) */
  private final Map<Class<?>, PojoDescriptorImpl<?>> pojoMap;

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorBuilderLimited() {

    super();
    this.pojoMap = new HashMap<Class<?>, PojoDescriptorImpl<?>>();
  }

  /**
   * @return the instance
   */
  public static PojoDescriptorBuilder getInstance() {

    return INSTANCE;
  }

  /**
   * This method gets the instance of {@link ReflectionUtilLimited}.
   * 
   * @return the {@link ReflectionUtilLimited}.
   */
  protected ReflectionUtilLimited getReflectionUtil() {

    return ReflectionUtilLimitedImpl.getInstance();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <POJO> PojoDescriptorImpl<POJO> getDescriptor(Class<POJO> pojoClass) {

    PojoDescriptorImpl<POJO> descriptor = (PojoDescriptorImpl<POJO>) this.pojoMap.get(pojoClass);
    if (descriptor == null) {
      descriptor = createDescriptor(pojoClass);
      this.pojoMap.put(pojoClass, descriptor);
    }
    return descriptor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoDescriptorImpl<?> getDescriptor(Type pojoType) {

    GenericType<?> genericType = getReflectionUtil().createGenericType(pojoType);
    return getDescriptor(genericType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <POJO> PojoDescriptorImpl<POJO> getDescriptor(GenericType<POJO> pojoType) {

    return getDescriptor(pojoType.getRetrievalClass());
  }

  /**
   * This method creates the {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor pojo descriptor} for
   * the given <code>pojoType</code>.
   * 
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder#getDescriptor(java.lang.Class)
   * 
   * @param <POJO> is the templated type of the <code>pojoType</code>.
   * @param pojoType is the {@link GenericType} reflecting the {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @return the descriptor used to get information about the properties of the according
   *         {@link net.sf.mmm.util.pojo.api.Pojo}.
   */
  public <POJO> PojoDescriptorImpl<POJO> createDescriptor(Class<POJO> pojoType) {

    throw new NlsUnsupportedOperationException("createDescriptor(" + pojoType.getName() + ")");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> pojo2Map(Object pojo) {

    return new PojoMap(this, pojo);
  }

}
