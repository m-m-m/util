/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;

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

  /** The according setter to use if array has to be resized. */
  private final PojoPropertyAccessorOneArg containerSetAccessor;

  private int maximumListGrowth;

  private int maximumArrayGrowth;

  /**
   * The constructor.
   * 
   * @param containerGetAccessor is the accessor delegate that gets an array, or
   *        {@link java.util.List} property.
   * @param containerSetAccessor is the accessor that sets the array, or
   *        {@link java.util.List} property. May be <code>null</code> if NOT
   *        available.
   */
  public PojoPropertyAccessorProxySetIndexed(PojoPropertyAccessorNonArg containerGetAccessor,
      PojoPropertyAccessorOneArg containerSetAccessor) {

    super(containerGetAccessor);
    this.containerSetAccessor = containerSetAccessor;
    this.maximumListGrowth = 64;
    this.maximumArrayGrowth = 64;
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
    Object arrayCopy = getCollectionUtil().set(arrayOrList, index, item, this.maximumListGrowth,
        this.maximumArrayGrowth);
    if ((arrayCopy != arrayOrList) && (this.containerSetAccessor != null)) {
      this.containerSetAccessor.invoke(pojoInstance, arrayCopy);
    }
    return null;
  }

}
