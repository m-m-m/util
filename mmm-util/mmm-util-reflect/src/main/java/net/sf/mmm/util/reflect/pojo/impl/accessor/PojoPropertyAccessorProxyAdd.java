/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArg}
 * interface for {@link PojoPropertyAccessorOneArgMode#ADD adding} an element
 * using the getter from another accessor returning an array or
 * {@link java.util.Collection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxyAdd extends
    AbstractPojoPropertyAccessorProxyAdapterComponentType implements PojoPropertyAccessorOneArg {

  /** The according setter to use if array has to be resized. */
  private final PojoPropertyAccessorOneArg containerSetAccessor;

  /**
   * The constructor.
   * 
   * @param containerGetAccessor is the accessor delegate that gets an array, or
   *        list property.
   * @param containerSetAccessor is the accessor that sets the array, or
   *        {@link java.util.Collection} property. May be <code>null</code> if
   *        NOT available.
   */
  public PojoPropertyAccessorProxyAdd(PojoPropertyAccessorNonArg containerGetAccessor,
      PojoPropertyAccessorOneArg containerSetAccessor) {

    super(containerGetAccessor);
    this.containerSetAccessor = containerSetAccessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.ADD;
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
  public Object invoke(Object pojoInstance, Object argument) throws IllegalAccessException,
      InvocationTargetException {

    Object arrayOrCollection = getDelegate().invoke(pojoInstance);
    Object arrayCopy = getCollectionUtil().add(arrayOrCollection, argument);
    if ((arrayCopy != arrayOrCollection) && (this.containerSetAccessor != null)) {
      // we will NOT create this proxy if the setter is missing for an array
      // type getter.
      this.containerSetAccessor.invoke(pojoInstance, arrayCopy);
    }
    return null;
  }

}
