/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base.accessor;

import net.sf.mmm.util.reflect.CollectionUtil;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor} that
 * acts as proxy to a {@link #getDelegate() delegate} allowing to add new ways
 * to access a property.<br>
 * E.g. if the {@link #getDelegate() delegate} is a
 * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}
 * that returns an array or a {@link java.util.List} then this adapter may
 * expose it as
 * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed getter}
 * or
 * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode#SIZE size}
 * accessor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessorProxyAdapter extends
    AbstractPojoPropertyAccessorProxy {

  /** @see #getDelegate() */
  private final PojoPropertyAccessorNonArg containerGetAccessor;

  /**
   * The constructor.
   * 
   * @param containerGetAccessor is the accessor delegate that gets an array,
   *        map or collection property.
   */
  public AbstractPojoPropertyAccessorProxyAdapter(PojoPropertyAccessorNonArg containerGetAccessor) {

    super();
    this.containerGetAccessor = containerGetAccessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final PojoPropertyAccessorNonArg getDelegate() {

    return this.containerGetAccessor;
  }

  /**
   * This method gets the {@link CollectionUtil} instance.
   * 
   * @see CollectionUtil#getInstance()
   * 
   * @return the collection util.
   */
  protected CollectionUtil getCollectionUtil() {

    // TODO: get from PojoBuilder
    return CollectionUtil.getInstance();
  }

  /**
   * This method gets the {@link ReflectionUtil} instance.
   * 
   * @see ReflectionUtil#getInstance()
   * 
   * @return the {@link ReflectionUtil}.
   */
  protected ReflectionUtil getReflectionUtil() {

    return ReflectionUtil.getInstance();
  }

}
