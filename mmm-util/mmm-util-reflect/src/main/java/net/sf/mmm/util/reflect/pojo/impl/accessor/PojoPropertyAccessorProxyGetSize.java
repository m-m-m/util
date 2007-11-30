/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.CollectionUtil;
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
public class PojoPropertyAccessorProxyGetSize extends AbstractPojoPropertyAccessorProxy implements
    PojoPropertyAccessorNonArg {

  /** @see #PojoPropertyAccessorProxyGetSize(PojoPropertyAccessorNonArg) */
  private final PojoPropertyAccessorNonArg containerGetAccessor;

  /**
   * The constructor.
   * 
   * @param containerGetAccessor is the accessor delegate that gets an array,
   *        map or collection property.
   */
  public PojoPropertyAccessorProxyGetSize(PojoPropertyAccessorNonArg containerGetAccessor) {

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

    return int.class;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) throws IllegalAccessException,
      InvocationTargetException {

    Object arrayMapOrCollection = this.containerGetAccessor.invoke(pojoInstance);
    int size;
    if (arrayMapOrCollection == null) {
      size = 0;
    } else {
      size = CollectionUtil.INSTANCE.getSize(arrayMapOrCollection);
    }
    return Integer.valueOf(size);
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.SIZE;
  }

}
