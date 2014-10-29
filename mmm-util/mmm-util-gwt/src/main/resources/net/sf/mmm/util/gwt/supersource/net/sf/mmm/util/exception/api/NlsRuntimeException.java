/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.gwt.api.JavaScriptUtil;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.uuid.api.UuidAccess;

/**
 * This is an abstract base implementation of {@link NlsThrowable} based on {@link RuntimeException}. For
 * further details see {@link NlsThrowable}. For an example read the {@link net.sf.mmm.util.exception.api
 * package javadoc} or see the source code of the derived exceptions.
 *
 * @see NlsThrowable
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NlsRuntimeException extends RuntimeException implements NlsThrowable, Cloneable {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** the internationalized message */
  private NlsMessage nlsMessage;

  /** @see #getUuid() */
  private UUID uuid;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected NlsRuntimeException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public NlsRuntimeException(NlsMessage message) {

    super();
    this.nlsMessage = message;
    this.uuid = createUuid();
  }

  /**
   * The constructor. <br>
   * <b>ATTENTION:</b><br>
   * Please use {@link NlsRuntimeException#NlsRuntimeException(NlsMessage)} in advance to this constructor to
   * get best NLS/I18N.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link NlsMessage#getInternationalizedMessage() internationalization} and should be in English
   *        language.
   */
  public NlsRuntimeException(String internationalizedMessage) {

    this(null, internationalizedMessage);
  }

  /**
   * The constructor. <br>
   * <b>ATTENTION:</b><br>
   * Please use {@link NlsRuntimeException#NlsRuntimeException(Throwable, NlsMessage)} in advance to this
   * constructor to get best NLS/I18N.
   *
   * @param cause is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link NlsMessage#getInternationalizedMessage() internationalization} and should be in English
   *        language.
   */
  public NlsRuntimeException(Throwable cause, String internationalizedMessage) {

    this(cause, NlsAccess.getFactory().create(internationalizedMessage));
  }

  /**
   * The constructor.
   *
   * @param cause is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public NlsRuntimeException(Throwable cause, NlsMessage message) {

    super(cause);
    this.nlsMessage = message;
    if ((cause != null) && (cause instanceof NlsThrowable)) {
      this.uuid = ((NlsThrowable) cause).getUuid();
    } else {
      this.uuid = createUuid();
    }
  }

  /**
   * The copy constructor.
   *
   * @param copySource is the exception to copy.
   * @param truncation is the {@link ExceptionTruncation} to configure potential truncations.
   */
  protected NlsRuntimeException(NlsRuntimeException copySource, ExceptionTruncation truncation) {

    super();
    this.nlsMessage = copySource.nlsMessage;
    this.uuid = copySource.uuid;
  }

  /**
   * This method creates a new {@link UUID}.
   *
   * @return the new {@link UUID} or <code>null</code> to turn this feature off.
   */
  protected UUID createUuid() {

    return UuidAccess.getFactory().createUuid();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UUID getUuid() {

    return this.uuid;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final NlsMessage getNlsMessage() {

    return this.nlsMessage;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(Locale locale, Appendable buffer) {

    printStackTrace(this, locale, buffer);
  }

  /**
   * @see NlsThrowable#printStackTrace(Locale, Appendable)
   *
   * @param throwable is the {@link NlsThrowable} to print.
   * @param locale is the {@link Locale} to translate to.
   * @param buffer is where to write the stack trace to.
   */
  static void printStackTrace(NlsThrowable throwable, Locale locale, Appendable buffer) {

    try {
      synchronized (buffer) {
        buffer.append(throwable.getClass().getName());
        buffer.append(": ");
        throwable.getLocalizedMessage(locale, buffer);
        buffer.append(StringUtil.LINE_SEPARATOR);
        UUID uuid = throwable.getUuid();
        if (uuid != null) {
          buffer.append(uuid.toString());
          buffer.append(StringUtil.LINE_SEPARATOR);
        }
        StackTraceElement[] trace = throwable.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
          buffer.append("\tat ");
          buffer.append(trace[i].toString());
          buffer.append(StringUtil.LINE_SEPARATOR);
        }
        for (Throwable suppressed : ((Throwable) throwable).getSuppressed()) {
          buffer.append("Suppressed: ");
          buffer.append(StringUtil.LINE_SEPARATOR);
          printStackTraceCause(suppressed, locale, buffer);
        }

        Throwable cause = throwable.getCause();
        if (cause != null) {
          buffer.append("Caused by: ");
          buffer.append(StringUtil.LINE_SEPARATOR);
          printStackTraceCause(cause, locale, buffer);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * @see NlsThrowable#printStackTrace(Locale, Appendable)
   *
   * @param nested is the {@link Throwable} to print.
   * @param locale is the {@link Locale} to translate to.
   * @param buffer is where to write the stack trace to.
   * @throws IOException if caused by <code>buffer</code>.
   */
  private static void printStackTraceCause(Throwable nested, Locale locale, Appendable buffer) throws IOException {

    if (nested instanceof NlsThrowable) {
      ((NlsThrowable) nested).printStackTrace(locale, buffer);
    } else {
      for (StackTraceElement element : nested.getStackTrace()) {
        buffer.append("\tat ");
        buffer.append(element.toString());
        buffer.append(StringUtil.LINE_SEPARATOR);
      }
      Throwable cause = nested.getCause();
      if (cause != null) {
        buffer.append("Caused by: ");
        buffer.append(StringUtil.LINE_SEPARATOR);
        printStackTraceCause(cause, locale, buffer);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return getNlsMessage().getMessage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocalizedMessage() {

    return getNlsMessage().getLocalizedMessage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocalizedMessage(Locale locale) {

    return getNlsMessage().getLocalizedMessage(locale);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void getLocalizedMessage(Locale locale, Appendable appendable) {

    getNlsMessage().getLocalizedMessage(locale, appendable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessage toNlsMessage() {

    return getNlsMessage();
  }

  /**
   * @see net.sf.mmm.util.nls.api.NlsBundleFactory#createBundle(Class)
   *
   * @param <BUNDLE> is the generic type of the requested {@link NlsBundle}.
   * @param bundleInterface is the {@link NlsBundle} interface.
   * @return the {@link NlsBundle} instance.
   */
  protected static <BUNDLE extends NlsBundle> BUNDLE createBundle(Class<BUNDLE> bundleInterface) {

    return NlsAccess.getBundleFactory().createBundle(bundleInterface);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    // override to change...
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isForUser() {

    return !isTechnical();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return getClass().getSimpleName();
  }

  /**
   * @see #createCopy(ExceptionTruncation)
   *
   * @param truncation the {@link ExceptionTruncation} settings.
   * @return the (truncated) copy.
   */
  protected NlsRuntimeException createCopyViaClone(ExceptionTruncation truncation) {

    NlsRuntimeException copy = (NlsRuntimeException) JavaScriptUtil.getInstance().clone(this);
    // ThrowableHelper.removeDetails(copy, truncation);
    return copy;
  }

  /**
   * {@inheritDoc}
   *
   * This default implementation is using {@link #createCopyViaClone(ExceptionTruncation) clone} to create a
   * copy and truncate it as configured. However, a proper implementation would use the appropriate
   * {@link #NlsRuntimeException(NlsRuntimeException, ExceptionTruncation) copy constructor} instead.
   */
  @Override
  public NlsRuntimeException createCopy(ExceptionTruncation truncation) {

    return createCopyViaClone(truncation);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    // We intentionally use the system locale here to prevent mixed languages in log-files...
    return toString(Locale.getDefault(), null).toString();
  }

  /**
   * {@inheritDoc}
   */
  public String toString(Locale locale) {

    return toString(locale, null).toString();
  }

  /**
   * {@inheritDoc}
   */
  public Appendable toString(Locale locale, Appendable appendable) {

    Appendable buffer = appendable;
    if (buffer == null) {
      buffer = new StringBuilder(32);
    }
    try {
      Class<?> myClass = getClass();
      buffer.append(myClass.getName());
      buffer.append(": ");
      String code = getCode();
      if (!myClass.getSimpleName().equals(code)) {
        buffer.append(code);
        buffer.append(": ");
      }
      buffer.append(getLocalizedMessage(locale));
      if (this.uuid != null) {
        buffer.append(StringUtil.LINE_SEPARATOR);
        buffer.append(this.uuid.toString());
      }
      return buffer;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }
}
