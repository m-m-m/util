/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxy;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is an implementation of the {@link PojoPropertyAccessorNonArg} acting as
 * as a {@link PojoPropertyAccessorNonArgMode#GET getter} that
 * {@link #getDelegate() delegates} to a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode#GET_MAPPED
 * mapped-getter} using a fixed key.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorProxyGetByKey extends AbstractPojoPropertyAccessorProxy implements
    PojoPropertyAccessorNonArg {

  /** @see #getDelegate() */
  private final PojoPropertyAccessorOneArg delegate;

  /** @see #invoke(Object) */
  private final Object key;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param key is the mapped key to use.
   */
  public PojoPropertyAccessorProxyGetByKey(PojoPropertyAccessorOneArg delegate, Object key) {

    super();
    this.delegate = delegate;
    this.key = key;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoPropertyAccessorOneArg getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) throws ReflectionException {

    return this.delegate.invoke(pojoInstance, this.key);
  }

}
