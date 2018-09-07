/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the default implementation of the {@link net.sf.mmm.util.exception.api.GlobalExceptionHandler} interface. It
 * simply logs all errors.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class GlobalExceptionHandlerLoggingImpl extends AbstractGlobalExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandlerLoggingImpl.class);

  /**
   * The constructor.
   */
  public GlobalExceptionHandlerLoggingImpl() {

    super();
  }

  @Override
  public void handleErrors(Object context, Throwable... errors) {

    if (errors == null) {
      LOG.error("Invalid invocation: errors is null.");
    } else {
      Throwable composed = composeErrors(errors);
      if (context == null) {
        LOG.error("Unhandled error in context of {}:", context, composed);
      } else {
        LOG.error("Unhandled error:", composed);
      }
    }
  }

}
