/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorProxy;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getting} a
 * {@link Field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxySetIndexed extends AbstractPojoPropertyAccessorProxy
    implements PojoPropertyAccessorIndexedOneArg {

  /** @see #PojoPropertyAccessorProxySetIndexed(PojoPropertyAccessorNonArg) */
  private final PojoPropertyAccessorNonArg containerGetAccessor;

  /**
   * The constructor.
   * 
   * @param containerGetAccessor is the accessor delegate that gets an array,
   *        map or collection property.
   */
  public PojoPropertyAccessorProxySetIndexed(PojoPropertyAccessorNonArg containerGetAccessor) {

    super();
    this.containerGetAccessor = containerGetAccessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoPropertyAccessorNonArg getDelegate() {

    return this.containerGetAccessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getPropertyType() {

    return getPropertyClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getPropertyClass() {

    return void.class;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, int index, Object item) throws IllegalAccessException,
      InvocationTargetException {

    Object arrayOrList = this.containerGetAccessor.invoke(pojoInstance);
    getCollectionUtil().set(arrayOrList, index, item);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedOneArgMode getMode() {

    return PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED;
  }

}
