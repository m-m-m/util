/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.reflect.ReflectionException;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxy;

/**
 * This is an implementation of the {@link PojoPropertyAccessorOneArg} acting as
 * as a {@link PojoPropertyAccessorOneArgMode#SET setter} that
 * {@link #getDelegate() delegates} to a
 * {@link net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED indexed-setter}
 * using a fixed index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxySetByIndex extends AbstractPojoPropertyAccessorProxy
    implements PojoPropertyAccessorOneArg {

  /** @see #getDelegate() */
  private final PojoPropertyAccessorIndexedOneArg delegate;

  /** @see #invoke(Object, Object) */
  private final int index;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param index is the index to use.
   */
  public PojoPropertyAccessorProxySetByIndex(PojoPropertyAccessorIndexedOneArg delegate, int index) {

    super();
    this.delegate = delegate;
    this.index = index;
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
  protected PojoPropertyAccessorIndexedOneArg getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, Object argument) throws ReflectionException {

    return this.delegate.invoke(pojoInstance, this.index, argument);
  }

}
