/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;

/**
 * This is the abstract implementation of the {@link PojoPropertyAccessor}
 * interface that delegates to another {@link PojoPropertyAccessor accessor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessorProxy implements PojoPropertyAccessor {

  /**
   * The constructor.
   */
  public AbstractPojoPropertyAccessorProxy() {

    super();
  }

  protected abstract PojoPropertyAccessor getDelegate();

  /**
   * {@inheritDoc}
   */
  public AccessibleObject getAccessibleObject() {

    return getDelegate().getAccessibleObject();
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getDeclaringClass() {

    return getDelegate().getDeclaringClass();
  }

  /**
   * {@inheritDoc}
   */
  public int getModifiers() {

    return getDelegate().getModifiers();
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getPropertyClass() {

    return getDelegate().getPropertyClass();
  }

  /**
   * {@inheritDoc}
   */
  public Type getPropertyComponentType() {

    return getDelegate().getPropertyComponentType();
  }

  /**
   * {@inheritDoc}
   */
  public Type getPropertyType() {

    return getDelegate().getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return getDelegate().getName();
  }

}
