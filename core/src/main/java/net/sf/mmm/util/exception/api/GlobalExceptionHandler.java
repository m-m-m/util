/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface used as fallback to {@link #handleErrors(Object, Throwable...) handle errors} that can not be
 * handled in a specific way by generic components. It allows to write portable code that can delegate error handling to
 * this component that allows to exchange its implementation and therefore the handling strategy.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
@ComponentSpecification
public interface GlobalExceptionHandler {

  /**
   * This method handles one or multiple errors that occurred in a generic component that can not handle them in a
   * specific way. <br>
   * In a typical server application you may like to log the errors while in a client application you might want to show
   * a popup that displays the error.
   *
   * @param context is an Object with information about the context when the error occurred. Its
   *        {@link Object#toString() string representation} should be human readable and give additional hints to track
   *        down the error. E.g. the source or parameters of an operation where the error occurred. This parameter may
   *        also be {@code null} if no context information is available.
   * @param errors are the errors that have been catched. Has to contain at least one {@link Throwable}.
   */
  void handleErrors(Object context, Throwable... errors);

}
