/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.component.api.NotInitializedException;

/**
 * This class represents the state of an initialization.<br>
 * It therefore offers the method {@link #requireNotInitilized()} that can be called before initialization
 * e.g. from {@link javax.inject.Inject injection-setters} so nothing can be re-injected after
 * {@link #setInitializing() initialization}. Additionally there is {@link #requireInitilized()} that can be
 * called after initialization e.g. from functional methods of the component to ensure that the component has
 * been {@link #setInitializing() initialized}.
 * 
 * @see javax.annotation.PostConstruct
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class InitializationState {

  /** The initial {@link #state}. */
  private static final int STATE_UNINITIALIZED = 0;

  /** The {@link #state} during {@link #setInitializing() initialization}. */
  private static final int STATE_INITIALIZING = 1;

  /**
   * The {@link #state} if {@link #setInitialized() initialization} has completed.
   */
  private static final int STATE_INITIALIZED = 2;

  /** This field holds the atomic state of this object. */
  private int state;

  /**
   * The constructor.
   */
  public InitializationState() {

    super();
    this.state = STATE_UNINITIALIZED;
  }

  /**
   * This method sets the state to <em>initializing</em>. This should be done to start the initialization in a
   * thread-safe and atomic way. After the initialization has completed, the method {@link #setInitialized()}
   * should be invoked.
   * 
   * @return <code>true</code> if the state was NOT {@link #isInitialized() initialized} and is now
   *         {@link #isInitialized() initialized}, <code>false</code> if the state is already
   *         {@link #isInitialized() initialized}.
   */
  public boolean setInitializing() {

    boolean result = (this.state == STATE_UNINITIALIZED);
    this.state = STATE_INITIALIZING;
    return result;
  }

  /**
   * This method sets the state to {@link #isInitialized() initialized}.<br>
   * <b>ATTENTION:</b><br>
   * You need to call {@link #setInitializing()} before you invoke this method!
   */
  public void setInitialized() {

    boolean okay = (this.state == STATE_INITIALIZING);
    this.state = STATE_INITIALIZED;
    if (!okay) {
      if (isInitialized()) {
        throw new AlreadyInitializedException();
      } else {
        throw new IllegalStateException("You need to call setInitializing() before!");
      }
    }
  }

  /**
   * This method gets the status of the {@link #setInitialized() initialization} .
   * 
   * @return <code>true</code> if this component has been {@link #setInitializing() initialized},
   *         <code>false</code> otherwise.
   */
  public boolean isInitialized() {

    return (this.state == STATE_INITIALIZED);
  }

  /**
   * This method checks that this state has already been {@link #setInitialized() initialized}.
   * 
   * @throws NotInitializedException if this state has NOT been initialized yet.
   */
  public void requireInitilized() throws NotInitializedException {

    if (!isInitialized()) {
      throw new NotInitializedException();
    }
  }

  /**
   * This method checks that this state has NOT yet been {@link #setInitialized() initialized}.
   * 
   * @throws AlreadyInitializedException if this state has already been initialized.
   */
  public void requireNotInitilized() throws AlreadyInitializedException {

    if (isInitialized()) {
      throw new AlreadyInitializedException();
    }
  }

}
