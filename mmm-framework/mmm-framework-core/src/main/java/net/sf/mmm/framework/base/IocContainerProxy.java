/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.IocContainerIF;
import net.sf.mmm.framework.api.MutableIocContainerIF;

/**
 * This is an implementation of the {@link IocContainerIF} interface that acts
 * as proxy on another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IocContainerProxy implements IocContainerIF {

  /** the delegate instance */
  private final IocContainerIF delegate;

  /**
   * The constructor.
   * 
   * @param proxyDelegate
   *        is the instance this proxy delegates to.
   */
  public IocContainerProxy(IocContainerIF proxyDelegate) {

    super();
    this.delegate = proxyDelegate;
  }

  /**
   * @see net.sf.mmm.framework.api.IocContainerIF#createChildContainer(java.lang.String)
   * 
   */
  public MutableIocContainerIF createChildContainer(String name) {

    return this.delegate.createChildContainer(name);
  }

  /**
   * @see net.sf.mmm.framework.api.IocContainerIF#getName()
   * 
   */
  public String getName() {

    return this.delegate.getName();
  }

  /**
   * @see net.sf.mmm.framework.api.IocContainerIF#isRunning()
   * 
   */
  public boolean isRunning() {

    return this.delegate.isRunning();
  }

}
