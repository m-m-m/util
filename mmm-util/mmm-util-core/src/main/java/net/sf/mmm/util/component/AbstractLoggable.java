/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the abstract base class for a component that needs a
 * {@link #getLogger() logger}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractLoggable {

  /** @see #getInitializationState() */
  private final InitializationState initializationState;

  /** @see #getLogger() */
  private Log logger;

  /**
   * The constructor.
   */
  public AbstractLoggable() {

    super();
    this.initializationState = new InitializationState();
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

    this.initializationState.requireNotInitilized();
    this.logger = logger;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public final void initialize() {

    if (this.initializationState.setInitialized()) {
      if (this.logger == null) {
        // new Jdk14Logger(getClass()));
        // even more ugly...
        this.logger = LogFactory.getLog(getClass());
      }
      doInitialize();
    }
  }

  /**
   * This method performs the actual {@link #initialize() initialization}. It
   * is called when {@link #initialize()} is invoked for the first time.<br>
   * <b>ATTENTION:</b><br>
   * When you override this method you need to do a
   * <code>super.{@link #doInitialize()}</code>.
   */
  protected void doInitialize() {

  }

  /**
   * This method gets the {@link InitializationState} of this component.
   * 
   * @see #initialize()
   * @see InitializationState#requireInitilized()
   * @see InitializationState#requireNotInitilized()
   * 
   * @return the initializationState
   */
  protected final InitializationState getInitializationState() {

    return this.initializationState;
  }

}
