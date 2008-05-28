/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoProperty;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxyGetByIndex;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxyGetByKey;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxySetByIndex;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorProxySetByKey;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor} interface.
 * 
 * @param <POJO> is the templated type of the {@link #getPojoClass() POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorImpl<POJO> extends AbstractPojoDescriptor<POJO> {

  /** @see #getPropertyDescriptor(String) */
  private final Map<String, PojoPropertyDescriptorImpl> propertyMap;

  /** @see #getPropertyDescriptor(String) */
  private final Collection<PojoPropertyDescriptorImpl> properties;

  /**
   * The constructor.
   * 
   * @param pojoType is the {@link #getPojoType() pojo-type}.
   */
  public PojoDescriptorImpl(GenericType<POJO> pojoType) {

    super(pojoType);
    // we do NOT want MapFactory here: no need for cache or to be thread-safe
    this.propertyMap = new HashMap<String, PojoPropertyDescriptorImpl>();
    this.properties = Collections.unmodifiableCollection(this.propertyMap.values());
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyDescriptorImpl getPropertyDescriptor(String propertyName) {

    return this.propertyMap.get(propertyName);
  }

  /**
   * This method gets the {@link PojoPropertyDescriptorImpl descriptor}s of all
   * properties of the according {@link #getPojoClass() pojo}.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptorImpl property descriptor}s
   */
  @Override
  public Collection<PojoPropertyDescriptorImpl> getPropertyDescriptors() {

    return this.properties;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String property,
      PojoPropertyAccessorMode<ACCESSOR> mode, boolean required) {

    PojoProperty pojoProperty = new PojoProperty(property);
    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(pojoProperty.getName());
    if (descriptor == null) {
      if (required) {
        throw new PojoPropertyNotFoundException(getPojoClass(), pojoProperty.getName());
      } else {
        return null;
      }
    }
    ACCESSOR accessor = descriptor.getAccessor(mode);
    if (pojoProperty.getIndex() != null) {
      if (mode == PojoPropertyAccessorNonArgMode.GET) {
        PojoPropertyAccessorIndexedNonArg indexedGetAccessor = descriptor
            .getAccessor(PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED);
        if (indexedGetAccessor != null) {
          accessor = (ACCESSOR) new PojoPropertyAccessorProxyGetByIndex(indexedGetAccessor,
              pojoProperty.getIndex().intValue());
        }
      } else if (mode == PojoPropertyAccessorOneArgMode.SET) {
        PojoPropertyAccessorIndexedOneArg indexedSetAccessor = descriptor
            .getAccessor(PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED);
        if (indexedSetAccessor != null) {
          accessor = (ACCESSOR) new PojoPropertyAccessorProxySetByIndex(indexedSetAccessor,
              pojoProperty.getIndex().intValue());
        }
      }
    } else if (pojoProperty.getKey() != null) {
      if (mode == PojoPropertyAccessorNonArgMode.GET) {
        PojoPropertyAccessorOneArg mappedGetAccessor = descriptor
            .getAccessor(PojoPropertyAccessorOneArgMode.GET_MAPPED);
        if (mappedGetAccessor != null) {
          accessor = (ACCESSOR) new PojoPropertyAccessorProxyGetByKey(mappedGetAccessor,
              pojoProperty.getKey());
        }
      } else if (mode == PojoPropertyAccessorOneArgMode.SET) {
        PojoPropertyAccessorTwoArg mappedSetAccessor = descriptor
            .getAccessor(PojoPropertyAccessorTwoArgMode.SET_MAPPED);
        if (mappedSetAccessor != null) {
          accessor = (ACCESSOR) new PojoPropertyAccessorProxySetByKey(mappedSetAccessor,
              pojoProperty.getKey());
        }
      }
    }
    if ((accessor == null) && required) {
      throw new PojoPropertyNotFoundException(getPojoClass(), property, mode);
    }
    return accessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyDescriptorImpl getOrCreatePropertyDescriptor(String propertyName) {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      descriptor = new PojoPropertyDescriptorImpl(propertyName);
      this.propertyMap.put(propertyName, descriptor);
    }
    return descriptor;
  }

}
