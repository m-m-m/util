/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is a limited subset of {@link ExceptionUtil} that is GWT compatible.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 * @deprecated will be removed
 */
@Deprecated
@ComponentSpecification
public interface ExceptionUtilLimited {

  /**
   * Gets the {@link Throwable#printStackTrace(java.io.PrintWriter) complete stacktrace} of the given
   * {@code exception} as {@link String}. Ensured to work also in limited environments such as GWT.
   *
   * @param exception is the {@link Throwable} to get the stacktrace from.
   * @return the {@link Throwable#printStackTrace(java.io.PrintWriter) complete stacktrace} of the given
   *         {@code exception}.
   */
  String getStacktrace(Throwable exception);

  /**
   * Converts the given {@code exception} for end-users. Technical exceptions are converted to
   * {@link net.sf.mmm.util.exception.api.TechnicalErrorUserException}.
   *
   * @see net.sf.mmm.util.exception.api.TechnicalErrorUserException#getOrCreateUserException(Throwable)
   * @see net.sf.mmm.util.exception.api.NlsThrowable#isForUser()
   * @see net.sf.mmm.util.exception.api.TechnicalErrorUserException
   *
   * @param exception is the exception to wrap.
   * @return the converted exception. Will be an instance of
   *         {@link net.sf.mmm.util.exception.api.NlsThrowable}.
   */
  Throwable convertForUser(Throwable exception);

}
