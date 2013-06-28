/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the abstract base class for a {@link HttpServlet} that needs a {@link #getLogger() logger}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractLoggableHttpServlet extends HttpServlet {

  /** UID for serialization. */
  private static final long serialVersionUID = 1054107399519894261L;

  /** @see #getLogger() */
  private final Logger logger;

  /**
   * The constructor.
   */
  public AbstractLoggableHttpServlet() {

    super();
    this.logger = LoggerFactory.getLogger(getClass());
  }

  /**
   * The constructor.
   * 
   * @param logger is the {@link #getLogger() logger}.
   */
  public AbstractLoggableHttpServlet(Logger logger) {

    super();
    this.logger = logger;
  }

  /**
   * This method gets the {@link Logger logger} for this object.<br>
   * This method is NOT public because the logger should only be used by the component itself and NOT
   * externally.
   * 
   * @return the logger to be used by this object.
   */
  protected Logger getLogger() {

    return this.logger;
  }

}
