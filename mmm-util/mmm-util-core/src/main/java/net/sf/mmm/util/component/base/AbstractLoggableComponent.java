/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the abstract base class for a component that needs a {@link #getLogger() logger}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0 (renamed, 1.0.1)
 */
public abstract class AbstractLoggableComponent extends AbstractComponent {

  /** @see #getLogger() */
  private final Logger logger;

  /**
   * The constructor.
   */
  public AbstractLoggableComponent() {

    super();
    // ugly static...
    this.logger = createLogger();
  }

  /**
   * This method creates the {@link #getLogger() logger} for this component.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is called from the constructor. It is not final to allow tests to replace the logger with a
   * mock. You should never override this method unless you are exactly aware about what you are doing!
   * 
   * @return the {@link Logger} for this class.
   */
  protected Logger createLogger() {

    return LoggerFactory.getLogger(getClass());
  }

  /**
   * This method gets the {@link Logger logger} for this component.<br>
   * This method is NOT public because the logger should only be used by the component itself and NOT
   * externally.
   * 
   * @return the {@link Logger} to be used by this component.
   */
  protected Logger getLogger() {

    return this.logger;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    this.logger.trace("Initializing: {}", getClass().getName());
  }

}
