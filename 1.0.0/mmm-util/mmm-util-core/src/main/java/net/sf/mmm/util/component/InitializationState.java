/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class represents the state of an initialization.<br>
 * It therefore offers the method {@link #requireNotInitilized()} that can be
 * called before initialization e.g. from
 * {@link javax.annotation.Resource injection-setters} so nothing can be
 * re-injected after {@link #setInitialized() initialization}. Additionally
 * there is {@link #requireInitilized()} that can be called after initialization
 * e.g. from functional methods of the component to ensure that the component
 * has been {@link #setInitialized() initialized}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class InitializationState {

  /** @see #isInitialized() */
  private AtomicBoolean initialized;

  /**
   * The constructor.
   */
  public InitializationState() {

    super();
    this.initialized = new AtomicBoolean();
  }

  /**
   * This method atomically sets the state to
   * {@link #isInitialized() initialized}.
   * 
   * @return <code>true</code> if the state was NOT
   *         {@link #isInitialized() initialized} and is now
   *         {@link #isInitialized() initialized}, <code>false</code> if the
   *         state is already {@link #isInitialized() initialized}.
   */
  public boolean setInitialized() {

    return this.initialized.compareAndSet(false, true);
  }

  /**
   * This method gets the status of the {@link #setInitialized() initialization}.
   * 
   * @return <code>true</code> if this component has been
   *         {@link #setInitialized() initialized}, <code>false</code>
   *         otherwise.
   */
  public boolean isInitialized() {

    return this.initialized.get();
  }

  /**
   * This method checks that this state has already been
   * {@link #setInitialized() initialized}.
   * 
   * @throws NotInitializedException if this state has NOT been initialized yet.
   */
  public void requireInitilized() throws NotInitializedException {

    if (!this.initialized.get()) {
      throw new NotInitializedException();
    }
  }

  /**
   * This method checks that this state has NOT yet been
   * {@link #setInitialized() initialized}.
   * 
   * @throws AlreadyInitializedException if this state has already been
   *         initialized.
   */
  public void requireNotInitilized() throws AlreadyInitializedException {

    if (this.initialized.get()) {
      throw new AlreadyInitializedException();
    }
  }

}
