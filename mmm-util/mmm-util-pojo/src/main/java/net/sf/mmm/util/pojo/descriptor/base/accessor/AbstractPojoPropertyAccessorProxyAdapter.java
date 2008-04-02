/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorConfiguration;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor}
 * that acts as proxy to a {@link #getDelegate() delegate} allowing to add new
 * ways to access a property.<br>
 * E.g. if the {@link #getDelegate() delegate} is a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}
 * that returns an array or a {@link java.util.List} then this adapter may
 * expose it as
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed getter}
 * or
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET_SIZE size}
 * accessor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessorProxyAdapter extends
    AbstractPojoPropertyAccessorProxy {

  /** @see #getDelegate() */
  private final PojoPropertyAccessorNonArg containerGetAccessor;

  /** @see #getConfiguration() */
  private final PojoDescriptorConfiguration configuration;

  /**
   * The constructor.
   * 
   * @param configuration is the configuration to use.
   * @param containerGetAccessor is the accessor delegate that gets an array,
   *        map or collection property.
   */
  public AbstractPojoPropertyAccessorProxyAdapter(PojoDescriptorConfiguration configuration,
      PojoPropertyAccessorNonArg containerGetAccessor) {

    super();
    this.containerGetAccessor = containerGetAccessor;
    this.configuration = configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final PojoPropertyAccessorNonArg getDelegate() {

    return this.containerGetAccessor;
  }

  /**
   * @return the configuration
   */
  protected PojoDescriptorConfiguration getConfiguration() {

    return this.configuration;
  }

}
