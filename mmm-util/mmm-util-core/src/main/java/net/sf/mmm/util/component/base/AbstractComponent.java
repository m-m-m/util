/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

import javax.annotation.PostConstruct;

/**
 * This is the abstract base class for a component that needs
 * {@link #initialize() initialization}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractComponent {

  /** @see #getInitializationState() */
  private final InitializationState initializationState;

  /**
   * The constructor.
   */
  public AbstractComponent() {

    super();
    this.initializationState = new InitializationState();
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public final void initialize() {

    if (this.initializationState.setInitializing()) {
      doInitialize();
      this.initializationState.setInitialized();
    }
  }

  /**
   * This method performs the actual {@link #initialize() initialization}. It
   * is called when {@link #initialize()} is invoked for the first time.<br>
   * <b>ATTENTION:</b><br>
   * When you override this method from a sub-class you need to do a
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
