/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.lang.reflect.Type;
import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.HashMapFactory;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorImpl;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base-implementation of the {@link PojoDescriptorBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptorBuilder extends AbstractLoggable implements
    PojoDescriptorBuilder {

  /** @see #getDescriptor(Class) */
  private final Map<GenericType<?>, PojoDescriptorImpl<?>> pojoMap;

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorBuilder() {

    this(HashMapFactory.INSTANCE);
  }

  /**
   * The constructor.
   * 
   * @param mapFactory the factory used to create the
   *        {@link #getDescriptor(Class) descriptor-cache}.
   */
  @SuppressWarnings("unchecked")
  public AbstractPojoDescriptorBuilder(MapFactory mapFactory) {

    super();
    this.pojoMap = mapFactory.create();
  }

  /**
   * This method gets the required configuration.
   * 
   * @return the {@link PojoDescriptorConfiguration}.
   */
  protected abstract PojoDescriptorConfiguration getConfiguration();

  /**
   * {@inheritDoc}
   */
  public <POJO> PojoDescriptorImpl<POJO> getDescriptor(Class<POJO> pojoClass) {

    GenericType<POJO> genericType = getConfiguration().getReflectionUtil().createGenericType(
        pojoClass);
    return getDescriptor(genericType);
  }

  /**
   * {@inheritDoc}
   */
  public PojoDescriptorImpl<?> getDescriptor(Type pojoType) {

    GenericType<?> genericType = getConfiguration().getReflectionUtil().createGenericType(pojoType);
    return getDescriptor(genericType);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <POJO> PojoDescriptorImpl<POJO> getDescriptor(GenericType<POJO> pojoType) {

    PojoDescriptorImpl<POJO> descriptor = (PojoDescriptorImpl<POJO>) this.pojoMap.get(pojoType);
    if (descriptor == null) {
      descriptor = createDescriptor(pojoType);
      this.pojoMap.put(pojoType, descriptor);
    }
    return descriptor;
  }

  /**
   * This method creates the
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor pojo descriptor}
   * for the given <code>pojoType</code>.
   * 
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder#getDescriptor(java.lang.Class)
   * 
   * @param <POJO> is the templated type of the <code>pojoType</code>.
   * @param pojoType is the {@link GenericType} reflecting the
   *        {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @return the descriptor used to get information about the properties of the
   *         according {@link net.sf.mmm.util.pojo.api.Pojo}.
   */
  protected abstract <POJO> PojoDescriptorImpl<POJO> createDescriptor(GenericType<POJO> pojoType);

}
