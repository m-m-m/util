/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

import javax.annotation.PostConstruct;

/**
 * This is the abstract base class for a {@link net.sf.mmm.util.component.api.ComponentSpecification
 * component} that needs {@link #initialize() initialization}. We strongly recommend that component
 * implementations should be {@link javax.inject.Singleton stateless} and therefore thread-safe. It provides
 * an {@link #getInitializationState() initialization state} that helps to prevent accidental coding mistakes.
 * E.g. you can do this:
 * 
 * <pre>
 * public class MyComponentImpl extends {@link AbstractComponent} {
 *
 *  private OtherComponent otherComponent;
 *
 *  public void setOtherComponent(OtherComponent otherComponent) {
 *    {@link #getInitializationState()}.{@link InitializationState#requireNotInitilized() requireNotInitilized()};
 *    this.otherComponent = otherComponent;
 *  }
 *
 *  public void {@link #doInitialize()} {
 *    if (this.otherCompoent == null) {
 *      setOtherComponent(new OtherComponentDefault());
 *    }
 *  }
 *
 *  public void doSomething() {
 *    {@link #getInitializationState()}.{@link InitializationState#requireInitilized() requireInitilized()};
 *    // ... do something ...
 *  }
 *
 * }
 * </pre>
 * 
 * In advance to this class, we recommend that you extend {@link AbstractLoggableComponent}.
 * 
 * @see AbstractLoggableComponent
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
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
   * This method initializes this class. It has to be called after construction and injection is completed.
   */
  @PostConstruct
  public final void initialize() {

    if (this.initializationState.setInitializing()) {
      doInitialize();
      this.initializationState.setInitialized();
      doInitialized();
    }
  }

  /**
   * This method performs the actual {@link #initialize() initialization}. It is called when
   * {@link #initialize()} is invoked for the first time. <br>
   * <b>ATTENTION:</b><br>
   * When you override this method from a sub-class you need to do a
   * <code>super.{@link #doInitialize()}</code>.
   */
  protected void doInitialize() {

    // nothing by default, override to extend
  }

  /**
   * This method is invoked at the end of the actual {@link #initialize() initialization}. It is called when
   * {@link #initialize()} is invoked for the first time after {@link #doInitialize()} is completed and
   * {@link #getInitializationState() initialization-state} has changed to
   * {@link InitializationState#isInitialized() initialized}. <br>
   * <b>ATTENTION:</b><br>
   * When you override this method from a sub-class you need to do a
   * <code>super.{@link #doInitialized()}</code>.
   */
  protected void doInitialized() {

    // nothing by default, override to extend
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
