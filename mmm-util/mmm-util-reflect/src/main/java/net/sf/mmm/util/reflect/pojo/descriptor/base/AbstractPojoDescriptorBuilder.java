/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.base;

import java.util.Map;

import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.component.AbstractLoggable;
import net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.pojo.descriptor.impl.PojoDescriptorImpl;

/**
 * This is the abstract base-implementation of the {@link PojoDescriptorBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptorBuilder extends AbstractLoggable implements
    PojoDescriptorBuilder {

  /** @see #getDescriptor(Class) */
  private final Map<Class<?>, PojoDescriptorImpl<?>> pojoMap;

  /**
   * The constructor.
   */
  public AbstractPojoDescriptorBuilder() {

    this(MapFactory.INSTANCE_HASH_MAP);
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
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <POJO> PojoDescriptorImpl<POJO> getDescriptor(Class<POJO> pojoType) {

    PojoDescriptorImpl<POJO> descriptor = (PojoDescriptorImpl<POJO>) this.pojoMap.get(pojoType);
    if (descriptor == null) {
      descriptor = createDescriptor(pojoType);
      this.pojoMap.put(pojoType, descriptor);
    }
    return descriptor;
  }

  /**
   * This method creates the
   * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptor pojo descriptor}
   * for the given <code>pojoType</code>.
   * 
   * @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptorBuilder#getDescriptor(java.lang.Class)
   * 
   * @param <POJO> is the templated type of the <code>pojoType</code>.
   * @param pojoType is the type reflecting the POJO.
   * @return the descriptor used to get information about the properties of the
   *         given <code>pojoType</code>.
   */
  protected abstract <POJO> PojoDescriptorImpl<POJO> createDescriptor(Class<POJO> pojoType);

}
