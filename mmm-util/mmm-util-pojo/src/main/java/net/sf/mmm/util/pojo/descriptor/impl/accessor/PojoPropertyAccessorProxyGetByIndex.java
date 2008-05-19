/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxy;
import net.sf.mmm.util.reflect.api.ReflectionException;

/**
 * This is an implementation of the {@link PojoPropertyAccessorNonArg} acting as
 * as a {@link PojoPropertyAccessorNonArgMode#GET getter} that
 * {@link #getDelegate() delegates} to a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed-getter}
 * using a fixed index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxyGetByIndex extends AbstractPojoPropertyAccessorProxy
    implements PojoPropertyAccessorNonArg {

  /** @see #getDelegate() */
  private final PojoPropertyAccessorIndexedNonArg delegate;

  /** @see #invoke(Object) */
  private final int index;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param index is the index to use.
   */
  public PojoPropertyAccessorProxyGetByIndex(PojoPropertyAccessorIndexedNonArg delegate, int index) {

    super();
    this.delegate = delegate;
    this.index = index;
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
  protected PojoPropertyAccessorIndexedNonArg getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) throws ReflectionException {

    return this.delegate.invoke(pojoInstance, this.index);
  }

}
