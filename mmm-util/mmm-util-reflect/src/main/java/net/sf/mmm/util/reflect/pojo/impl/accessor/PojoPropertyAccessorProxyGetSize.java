/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorProxyAdapter;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getting} the size of
 * an array, {@link java.util.List} or {@link java.util.Map} from another
 * accessor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxyGetSize extends AbstractPojoPropertyAccessorProxyAdapter
    implements PojoPropertyAccessorNonArg {

  /**
   * The constructor.
   * 
   * @param containerGetAccessor is the accessor delegate that gets an array,
   *        map or collection property.
   */
  public PojoPropertyAccessorProxyGetSize(PojoPropertyAccessorNonArg containerGetAccessor) {

    super(containerGetAccessor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.SIZE;
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
  public Object invoke(Object pojoInstance) throws IllegalAccessException,
      InvocationTargetException {

    Object arrayMapOrCollection = getDelegate().invoke(pojoInstance);
    int size;
    if (arrayMapOrCollection == null) {
      size = 0;
    } else {
      size = getCollectionUtil().getSize(arrayMapOrCollection);
    }
    return Integer.valueOf(size);
  }

}
