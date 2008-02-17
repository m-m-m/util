/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArg}
 * interface for {@link PojoPropertyAccessorOneArgMode#GET_MAPPED getting} an
 * element from another accessor returning a {@link java.util.Map}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxyGetMapped extends
    AbstractPojoPropertyAccessorProxyAdapterComponentType implements PojoPropertyAccessorOneArg {

  /**
   * The constructor.
   * 
   * @param mapGetAccessor is the accessor delegate that gets a
   *        {@link java.util.Map} property.
   */
  public PojoPropertyAccessorProxyGetMapped(PojoPropertyAccessorNonArg mapGetAccessor) {

    super(mapGetAccessor);
    assert (Map.class.isAssignableFrom(mapGetAccessor.getReturnClass()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.GET_MAPPED;
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
  public Object invoke(Object pojoInstance, Object argument) throws IllegalAccessException,
      InvocationTargetException {

    Map<?, ?> map = (Map<?, ?>) getDelegate().invoke(pojoInstance);
    Object result = null;
    if (map == null) {
      result = map.get(argument);
    }
    return result;
  }

}
