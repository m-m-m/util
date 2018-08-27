/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.base;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.ComposedException;
import net.sf.mmm.util.exception.api.GlobalExceptionHandler;

/**
 * This is the abstract base implementation of the {@link GlobalExceptionHandler} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public abstract class AbstractGlobalExceptionHandler extends AbstractLoggableComponent
    implements GlobalExceptionHandler {

  /**
   * The constructor.
   *
   */
  public AbstractGlobalExceptionHandler() {

    super();
  }

  /**
   * Returns a single exception containing all information of the given {@code errors}.
   *
   * @param errors are the errors to compose.
   * @return a single exception containing all errors.
   */
  protected Throwable composeErrors(Throwable... errors) {

    if (errors == null) {
      return new NullPointerException("errors");
    } else if (errors.length == 1) {
      return errors[0];
    } else {
      return new ComposedException(errors);
    }
  }
}
