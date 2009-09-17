/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArg;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxy;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is an implementation of the {@link PojoPropertyAccessorOneArg} acting as
 * as a {@link PojoPropertyAccessorOneArgMode#SET setter} that
 * {@link #getDelegate() delegates} to a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode#SET_MAPPED mapped-setter}
 * using a fixed key.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxySetByKey extends AbstractPojoPropertyAccessorProxy implements
    PojoPropertyAccessorOneArg {

  /** @see #getDelegate() */
  private final PojoPropertyAccessorTwoArg delegate;

  /** @see #invoke(Object, Object) */
  private final Object key;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param key is the mapped key to use.
   */
  public PojoPropertyAccessorProxySetByKey(PojoPropertyAccessorTwoArg delegate, Object key) {

    super();
    this.delegate = delegate;
    this.key = key;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.SET;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoPropertyAccessorTwoArg getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, Object argument) throws ReflectionException {

    return this.delegate.invoke(pojoInstance, this.key, argument);
  }

}
