/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component;

import javax.annotation.PostConstruct;

/**
 * This is the abstract base class for a component that needs a strict
 * {@link #initialize() initialization}.<br>
 * It therefore offers the method {@link #requireNotInitilized()} that can be
 * called from {@link javax.annotation.Resource injection-setters} so nothing
 * can be re-injected after {@link #initialize() initialization}. Additionally
 * there is {@link #requireInitilized()} that can be called from functional
 * methods of the component to ensure that the component has been
 * {@link #initialize() initialized}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractInitializableComponent {

  /** @see #initialize() */
  private boolean initialized;

  /**
   * The constructor.
   */
  public AbstractInitializableComponent() {

    super();
  }

  /**
   * This method gets the status of the {@link #initialize() initialization}.
   * 
   * @return <code>true</code> if this component has already been
   *         {@link #initialize() initialized}, <code>false</code> otherwise.
   */
  protected boolean isInitialized() {

    return this.initialized;
  }

  /**
   * This method checks that this component has NOT yet been
   * {@link #initialize() initialized}.
   * 
   * @throws AlreadyInitializedException if this component has already been
   *         initialized.
   */
  protected void requireNotInitilized() throws AlreadyInitializedException {

    if (this.initialized) {
      throw new AlreadyInitializedException();
    }
  }

  /**
   * This method checks that this component has already been
   * {@link #initialize() initialized}.
   * 
   * @throws NotInitializedException if this component has NOT been initialized
   *         yet.
   */
  protected void requireInitilized() throws NotInitializedException {

    if (!this.initialized) {
      throw new NotInitializedException();
    }
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public final void initialize() {

    if (!this.initialized) {
      doInitialize();
    }
    this.initialized = true;
  }

  /**
   * This method performs the actual initialization. It has to be implemented by
   * the component.
   */
  protected abstract void doInitialize();

}
