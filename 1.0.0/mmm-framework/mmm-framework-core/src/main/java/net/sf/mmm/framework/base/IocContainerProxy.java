/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.IocContainer;
import net.sf.mmm.framework.api.MutableIocContainer;

/**
 * This is an implementation of the {@link IocContainer} interface that acts as
 * proxy on another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IocContainerProxy implements IocContainer {

  /** the delegate instance */
  private final IocContainer delegate;

  /**
   * The constructor.
   * 
   * @param proxyDelegate is the instance this proxy delegates to.
   */
  public IocContainerProxy(IocContainer proxyDelegate) {

    super();
    this.delegate = proxyDelegate;
  }

  /**
   * {@inheritDoc}
   */
  public MutableIocContainer createChildContainer(String name) {

    return this.delegate.createChildContainer(name);
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.delegate.getName();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isRunning() {

    return this.delegate.isRunning();
  }

}
