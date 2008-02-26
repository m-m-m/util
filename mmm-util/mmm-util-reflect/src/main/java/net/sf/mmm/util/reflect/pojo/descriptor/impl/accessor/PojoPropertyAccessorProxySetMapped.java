/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl.accessor;

import java.lang.reflect.Type;
import java.util.Map;

import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorTwoArg}
 * interface for {@link PojoPropertyAccessorTwoArgMode#SET_MAPPED setting} an
 * element in a {@link java.util.Map} returned from another accessor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxySetMapped extends
    AbstractPojoPropertyAccessorProxyAdapterComponentType implements PojoPropertyAccessorTwoArg {

  /**
   * The constructor.
   * 
   * @param mapGetAccessor is the accessor delegate that gets a
   *        {@link java.util.Map} property.
   */
  public PojoPropertyAccessorProxySetMapped(PojoPropertyAccessorNonArg mapGetAccessor) {

    super(mapGetAccessor);
    assert (Map.class.isAssignableFrom(mapGetAccessor.getReturnClass()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorTwoArgMode getMode() {

    return PojoPropertyAccessorTwoArgMode.SET_MAPPED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getReturnType() {

    return getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return getPropertyClass();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Object invoke(Object pojoInstance, Object key, Object value) {

    Map map = (Map) getDelegate().invoke(pojoInstance);
    Object result = null;
    if (map == null) {
      result = map.put(key, value);
    }
    return result;
  }

}
