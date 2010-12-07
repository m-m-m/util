/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.AccessibleObject;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract implementation of the {@link PojoPropertyAccessor}
 * interface that delegates to another {@link PojoPropertyAccessor accessor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPropertyAccessorProxy extends AbstractPojoPropertyAccessor {

  /**
   * The constructor.
   */
  public AbstractPojoPropertyAccessorProxy() {

    super();
  }

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} this proxy
   * delegates to.
   * 
   * @return the delegate.
   */
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
  public Class<?> getReturnClass() {

    return getDelegate().getReturnClass();
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getReturnType() {

    return getDelegate().getReturnType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getPropertyClass() {

    return getDelegate().getPropertyClass();
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getPropertyType() {

    return getDelegate().getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return getDelegate().getName();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorMode<? extends PojoPropertyAccessor> getMode() {

    return getDelegate().getMode();
  }

}
