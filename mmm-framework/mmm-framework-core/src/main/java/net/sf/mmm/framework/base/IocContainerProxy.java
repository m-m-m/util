/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.IocContainer;
import net.sf.mmm.framework.api.MutableIocContainer;

/**
 * This is an implementation of the {@link IocContainer} interface that acts
 * as proxy on another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IocContainerProxy implements IocContainer {

  /** the delegate instance */
  private final IocContainer delegate;

  /**
   * The constructor.
   * 
   * @param proxyDelegate
   *        is the instance this proxy delegates to.
   */
  public IocContainerProxy(IocContainer proxyDelegate) {

    super();
    this.delegate = proxyDelegate;
  }

  /**
   * @see net.sf.mmm.framework.api.IocContainer#createChildContainer(java.lang.String)
   * 
   */
  public MutableIocContainer createChildContainer(String name) {

    return this.delegate.createChildContainer(name);
  }

  /**
   * @see net.sf.mmm.framework.api.IocContainer#getName()
   * 
   */
  public String getName() {

    return this.delegate.getName();
  }

  /**
   * @see net.sf.mmm.framework.api.IocContainer#isRunning()
   * 
   */
  public boolean isRunning() {

    return this.delegate.isRunning();
  }

}
