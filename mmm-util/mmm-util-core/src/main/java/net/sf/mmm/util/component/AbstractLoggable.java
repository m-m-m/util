/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the abstract base class for a component that needs a
 * {@link #getLogger() logger}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractLoggable extends AbstractComponent {

  /** @see #getLogger() */
  private Log logger;

  /**
   * The constructor.
   */
  public AbstractLoggable() {

    super();
  }

  /**
   * This method gets the {@link Log logger} for this component. It should only
   * be invoked after the component has been {@link #initialize() initialized}.<br>
   * This method is NOT public because the logger should only be used by the
   * component itself and NOT externally.
   * 
   * @return the logger to be used by this component.
   * @throws NotInitializedException if this component has NOT yet been
   *         {@link #initialize() initialized}.
   */
  protected Log getLogger() throws NotInitializedException {

    if (this.logger == null) {
      throw new NotInitializedException();
    }
    return this.logger;
  }

  /**
   * This method allows to inject the {@link #getLogger() logger} instance for
   * this component. This is an optional operation that has to be invoked before
   * the component has been {@link #initialize() initialized}.<br>
   * 
   * @param logger is the {@link #getLogger() logger} to set.
   * @throws AlreadyInitializedException if this component has already been
   *         {@link #initialize() initialized}.
   */
  @Resource
  public void setLogger(Log logger) throws AlreadyInitializedException {

    getInitializationState().requireNotInitilized();
    this.logger = logger;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (this.logger == null) {
      // new Jdk14Logger(getClass()));
      // even more ugly...
      this.logger = LogFactory.getLog(getClass());
    }
  }

}
