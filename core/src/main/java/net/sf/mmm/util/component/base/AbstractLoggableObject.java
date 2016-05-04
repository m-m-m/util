/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the abstract base class for any object that is no {@link AbstractComponent component} but needs a
 * {@link #getLogger() logger}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractLoggableObject {

  private final Logger logger;

  /**
   * The constructor.
   */
  public AbstractLoggableObject() {

    super();
    this.logger = LoggerFactory.getLogger(getClass());
  }

  /**
   * The constructor.
   *
   * @param logger is the {@link #getLogger() logger}.
   */
  public AbstractLoggableObject(Logger logger) {

    super();
    this.logger = logger;
  }

  /**
   * This method gets the {@link Logger logger} for this object. <br>
   * This method is NOT public because the logger should only be used by the component itself and NOT externally.
   *
   * @return the logger to be used by this object.
   */
  protected Logger getLogger() {

    return this.logger;
  }

}
