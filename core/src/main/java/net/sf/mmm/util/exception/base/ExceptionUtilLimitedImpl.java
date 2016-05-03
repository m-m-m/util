/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.base;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.ExceptionUtilLimited;
import net.sf.mmm.util.exception.api.TechnicalErrorUserException;
import net.sf.mmm.util.security.api.SecurityErrorUserException;

/**
 * This is the default implementation of {@link ExceptionUtilLimited}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ExceptionUtilLimitedImpl extends AbstractLoggableComponent implements ExceptionUtilLimited {

  /**
   * The constructor.
   */
  public ExceptionUtilLimitedImpl() {

    super();
  }

  @Override
  public String getStacktrace(Throwable exception) {

    StringWriter writer = new StringWriter();
    PrintWriter printWriter = new PrintWriter(writer);
    exception.printStackTrace(printWriter);
    printWriter.flush();
    return writer.toString();
  }

  @Override
  public Throwable convertForUser(Throwable exception) {

    if (exception instanceof SecurityException) {
      return new SecurityErrorUserException(exception);
    }
    // } else if (exception instanceof ValidationException) {
    // return new ValidationErrorUserException(exception);
    return TechnicalErrorUserException.getOrCreateUserException(exception);
  }
}
