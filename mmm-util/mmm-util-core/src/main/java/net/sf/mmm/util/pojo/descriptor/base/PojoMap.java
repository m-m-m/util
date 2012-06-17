/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.collection.base.AbstractSimpleMap;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;

/**
 * This is represents a given {@link net.sf.mmm.util.pojo.api.Pojo} as {@link java.util.Map} where the key is
 * a {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor#getName() property-name} given
 * as String.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
public class PojoMap extends AbstractSimpleMap<String, Object> {

  /** @see #get(Object) */
  @SuppressWarnings("rawtypes")
  private final PojoDescriptor pojoDescriptor;

  /** @see #get(Object) */
  private final Object pojo;

  /** @see #keySet() */
  private Set<String> keySet;

  /**
   * The constructor.
   * 
   * @param pojoDescriptorBuilder is the {@link PojoDescriptorBuilder} to use.
   * @param pojo the {@link net.sf.mmm.util.pojo.api.Pojo} to represent as {@link java.util.Map}.
   */
  public PojoMap(PojoDescriptorBuilder pojoDescriptorBuilder, Object pojo) {

    super();
    this.pojoDescriptor = pojoDescriptorBuilder.getDescriptor(pojo.getClass());
    this.pojo = pojo;
    this.keySet = null;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Object get(Object key) {

    String pojoPath = (String) key;
    return this.pojoDescriptor.getProperty(this.pojo, pojoPath);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> keySet() {

    if (this.keySet == null) {
      Set<String> keys = new HashSet<String>();
      PojoDescriptor<?> descriptor = this.pojoDescriptor;
      for (PojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
        if (propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET) != null) {
          keys.add(propertyDescriptor.getName());
        }
      }
      this.keySet = Collections.unmodifiableSet(keys);
    }
    return this.keySet;
  }
}
