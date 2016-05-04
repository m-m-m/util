/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.base;

import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

import net.sf.mmm.util.exception.api.BusinessErrorUserException;
import net.sf.mmm.util.exception.api.ExceptionTruncation;
import net.sf.mmm.util.exception.api.ExceptionUtil;
import net.sf.mmm.util.exception.api.GenericSerializableException;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.exception.api.NlsThrowable;
import net.sf.mmm.util.exception.api.TechnicalErrorUserException;
import net.sf.mmm.util.lang.api.EnvironmentDetector;
import net.sf.mmm.util.session.api.UserSessionAccess;
import net.sf.mmm.util.uuid.api.UuidAccess;

/**
 * This is the default implementation of {@link ExceptionUtil}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ExceptionUtilImpl extends ExceptionUtilLimitedImpl implements ExceptionUtil {

  private  static ExceptionUtil instance;

  private  EnvironmentDetector environmentDetector;

  private  boolean enforceSerializableForClient;

  /**
   * The constructor.
   */
  public ExceptionUtilImpl() {

    super();
    this.enforceSerializableForClient = false;
  }

  /**
   * This method gets the singleton instance of this {@link ExceptionUtil}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static ExceptionUtil getInstance() {

    if (instance == null) {
      synchronized (ExceptionUtilImpl.class) {
        if (instance == null) {
          instance = new ExceptionUtilImpl();
        }
      }
    }
    return instance;
  }

  /**
   * @return {@code true} if {@link #convertForClient(Throwable)} shall
   *         {@link #convertForSerialization(Throwable, ExceptionTruncation) enforce serializable exceptions},
   *         {@code false} otherwise.
   */
  public boolean isEnforceSerializableForClient() {

    return this.enforceSerializableForClient;
  }

  /**
   * @param enforceSerializableForClient is the new value of {@link #isEnforceSerializableForClient()}.
   */
  public void setEnforceSerializableForClient(boolean enforceSerializableForClient) {

    getInitializationState().requireNotInitilized();
    this.enforceSerializableForClient = enforceSerializableForClient;
  }

  @Override
  public Throwable convertForSerialization(Throwable exception, ExceptionTruncation truncation) {

    String originalExceptionName = exception.getClass().getName();
    Throwable cause = null;
    if (!truncation.isRemoveCause()) {
      cause = convertForSerialization(cause, truncation);
    }
    StackTraceElement[] stacktrace = NO_STACKTRACE;
    if (!truncation.isRemoveStacktrace()) {
      stacktrace = exception.getStackTrace();
    }

    String message;
    boolean technical;
    String code;
    UUID uuid;
    if (exception instanceof NlsThrowable) {
      NlsThrowable nlsThrowable = (NlsThrowable) exception;
      uuid = nlsThrowable.getUuid();
      code = nlsThrowable.getCode();
      technical = nlsThrowable.isTechnical();
      Locale locale = UserSessionAccess.getUserLocale();
      message = nlsThrowable.getLocalizedMessage(locale);
    } else {
      message = exception.getMessage();
      technical = true;
      code = exception.getClass().getSimpleName();
      uuid = UuidAccess.getFactory().createUuid();
    }
    GenericSerializableException serializableException = new GenericSerializableException(cause, message,
        originalExceptionName, stacktrace, technical, code, uuid);
    if (!truncation.isRemoveSuppressed()) {
      for (Throwable suppressed : exception.getSuppressed()) {
        serializableException.addSuppressed(convertForSerialization(suppressed, truncation));
      }
    }
    return serializableException;
  }

  @Override
  public Throwable convertForUser(Throwable exception, ExceptionTruncation truncation) {

    Throwable result = convertForUser(exception);
    if (truncation.isRetainAll()) {
      return result;
    }
    NlsThrowable nlsThrowable;
    if (result instanceof NlsThrowable) {
      nlsThrowable = (NlsThrowable) result;
    } else {
      // should not happen, but it is a fallback for strange overrides of convertForUser(Throwable)
      nlsThrowable = TechnicalErrorUserException.getOrCreateUserException(result);
    }
    return nlsThrowable.createCopy(truncation);
  }

  @Override
  public Throwable convertForClient(Throwable exception) {

    ExceptionTruncation truncation = getTruncationForClient();
    if (isEnforceSerializableForClient()) {
      return convertForSerialization(convertForUser(exception), truncation);
    } else {
      return convertForUser(exception, truncation);
    }
  }

  /**
   * @see #getTruncationForClient()
   *
   * @param exception is the {@link Throwable} to obfuscate.
   * @return the obfuscated exception.
   */
  protected Throwable obfuscateException(Throwable exception) {

    NlsRuntimeException obfuscatedException = null;
    if (exception instanceof NlsThrowable) {
      NlsThrowable nlsThrowable = (NlsThrowable) exception;
      if (nlsThrowable.isForUser()) {
        obfuscatedException = new BusinessErrorUserException(nlsThrowable.getNlsMessage());
      }
    }
    if (obfuscatedException == null) {
      obfuscatedException = new TechnicalErrorUserException(null);
    }
    return obfuscatedException;
  }

  /**
   * @return {@code true} if {@link #obfuscateException(Throwable)} shall remove the
   *         {@link Throwable#getStackTrace() stacktrace} of the resulting exception.
   */
  protected boolean isRemoveStacktrace() {

    return true;
  }

  /**
   * @return {@code true} if exceptions shall be obfuscated, {@code false} otherwise.
   */
  protected ExceptionTruncation getTruncationForClient() {

    if (this.environmentDetector.isEnvironmentCloseToProduction()) {
      return ExceptionTruncation.REMOVE_ALL;
    } else {
      return ExceptionTruncation.REMOVE_NONE;
    }
  }

  /**
   * @return the {@link EnvironmentDetector} instance.
   */
  public EnvironmentDetector getEnvironmentDetector() {

    return this.environmentDetector;
  }

  /**
   * @param environmentDetector is the {@link EnvironmentDetector} to {@link Inject}.
   */
  @Inject
  public void setEnvironmentDetector(EnvironmentDetector environmentDetector) {

    this.environmentDetector = environmentDetector;
  }

}
