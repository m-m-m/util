/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.base;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.exception.api.GlobalExceptionHandler} interface. It
 * simply logs all errors.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class GlobalExceptionHandlerLoggingImpl extends AbstractGlobalExceptionHandler {

  /**
   * The constructor.
   *
   */
  public GlobalExceptionHandlerLoggingImpl() {

    super();
  }

  @Override
  public void handleErrors(Object context, Throwable... errors) {

    if (errors == null) {
      getLogger().error("Invalid invocation: errors is null.");
    } else {
      Throwable composed = composeErrors(errors);
      if (context == null) {
        getLogger().error("Unhandled error in context of {}:", context, composed);
      } else {
        getLogger().error("Unhandled error:", composed);
      }
    }
  }

}
