/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorIndexedOneArg}
 * interface for
 * {@link PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED setting} an indexed
 * property using the getter from another accessor returning an array or
 * {@link java.util.List}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxySetIndexed extends
    AbstractPojoPropertyAccessorProxyAdapterComponentType implements
    PojoPropertyAccessorIndexedOneArg {

  /**
   * The constructor.
   * 
   * @param containerGetAccessor is the accessor delegate that gets an array, or
   *        list property.
   */
  public PojoPropertyAccessorProxySetIndexed(PojoPropertyAccessorNonArg containerGetAccessor) {

    super(containerGetAccessor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorIndexedOneArgMode getMode() {

    return PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getPropertyType() {

    return getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getPropertyClass() {

    return getPropertyClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getReturnType() {

    return getReturnClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return void.class;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, int index, Object item) throws IllegalAccessException,
      InvocationTargetException {

    Object arrayOrList = getDelegate().invoke(pojoInstance);
    getCollectionUtil().set(arrayOrList, index, item);
    return null;
  }

}
