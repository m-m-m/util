/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

/**
 * This is the interface for a collection of utility functions to deal with exceptions ({@link Throwable}s).
 * It is especially useful for {@link #convertForClient(Throwable) converting exceptions at application
 * barriers}, e.g. to prevent violating the OWASP principle <em>sensitive data exposure</em>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 * @deprecated will be removed
 */
@Deprecated
public interface ExceptionUtil extends ExceptionUtilLimited {

  /** The empty stacktrace. */
  StackTraceElement[] NO_STACKTRACE = new StackTraceElement[0];

  /**
   * Converts the given {@code exception} so it is ensured to be serializable.
   *
   * @see net.sf.mmm.util.exception.api.GenericSerializableException
   * @see net.sf.mmm.util.exception.api.NlsThrowable#createCopy(ExceptionTruncation)
   *
   * @param exception is the {@link Throwable} to convert.
   * @param truncation the {@link ExceptionTruncation} to configure if details shall be removed. E.g.
   *        {@link ExceptionTruncation#REMOVE_ALL}.
   * @return a serializable variant of the given {@code exception}. Guaranteed to implement
   *         {@link net.sf.mmm.util.exception.api.NlsThrowable}. By default an instance of
   *         {@link net.sf.mmm.util.exception.api.GenericSerializableException}.
   */
  Throwable convertForSerialization(Throwable exception, ExceptionTruncation truncation);

  /**
   * Converts the given {@code exception} for end-users with potential truncation.
   *
   * @see #convertForUser(Throwable)
   *
   * @param exception is the exception to wrap.
   * @param truncation the {@link ExceptionTruncation} to configure if details shall be removed. E.g.
   *        {@link ExceptionTruncation#REMOVE_ALL}.
   * @return the converted exception. Will be an instance of
   *         {@link net.sf.mmm.util.exception.api.NlsThrowable}.
   */
  Throwable convertForUser(Throwable exception, ExceptionTruncation truncation);

  /**
   * Converts the given {@code exception} for the <em>client</em>. With client we mean any kind of system that
   * calls the current application via a remote interface. This can be a user-interface client, a different
   * server application, or the like. If the exception would be send to the client as is, then all exception
   * classes have to be available on the client side for de-serialization and all error details are exposed to
   * the client violating the OWASP principle <em>sensitive data exposure</em>. <br>
   * The suggested implementation should behave as following:
   * <ul>
   * <li>Details are removed according to the current {@link net.sf.mmm.util.lang.api.EnvironmentDetector
   * environment}. In {@link net.sf.mmm.util.lang.api.EnvironmentDetector#isDevelopmentEnvironment()
   * development environment} and {@link net.sf.mmm.util.lang.api.EnvironmentDetector#ENVIRONMENT_TYPE_TEST
   * test environment} the details should be retained to support debugging. However, in
   * {@link net.sf.mmm.util.lang.api.EnvironmentDetector#isEnvironmentCloseToProduction() environments close
   * to production}, the details shall be removed.</li>
   * <li>Exceptions are {@link #convertForUser(Throwable) converted for the end-user} so that confusion with
   * technical details and exposure of internals in messages is avoided.</li>
   * <li>Potentially exceptions are {@link #convertForSerialization(Throwable, ExceptionTruncation) converted
   * for serialization}.</li>
   * </ul>
   * <br>
   * <b>ATTENTION:</b><br>
   * It is not the task of this utility to log exceptions. Instead this shall be done before this method is
   * invoked. To make use of the features of {@link net.sf.mmm.util.exception.api.NlsThrowable} such as the
   * {@link net.sf.mmm.util.exception.api.NlsThrowable#getUuid() UUID} that is logged and transferred to the
   * client, you should call {@link #convertForUser(Throwable)} in advance.
   *
   * @param exception is the {@link Throwable} that has been catched at an application boundary.
   * @return the converted {@link Throwable}.
   */
  Throwable convertForClient(Throwable exception);

}
