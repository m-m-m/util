/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.uuid.api.UuidAccess;

/**
 * This is an abstract base implementation of a checked exception with real <em>native language support</em>
 * (NLS). <br>
 * <b>ATTENTION:</b><br>
 * Checked exceptions are discouraged and should be avoided. Use {@link NlsRuntimeException} instead.
 *
 * @see NlsThrowable
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NlsException extends Exception implements NlsThrowable, Cloneable {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** the internationalized message */
  private final NlsMessage nlsMessage;

  /** @see #getUuid() */
  private final UUID uuid;

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public NlsException(NlsMessage message) {

    super();
    this.nlsMessage = message;
    this.uuid = createUuid();
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public NlsException(Throwable nested, NlsMessage message) {

    super(nested);
    this.nlsMessage = message;
    if ((nested != null) && (nested instanceof NlsThrowable)) {
      this.uuid = ((NlsThrowable) nested).getUuid();
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
  protected NlsException(NlsException copySource, ExceptionTruncation truncation) {

    super(null, truncation.isRemoveCause() ? null : copySource.getCause(), !truncation.isRemoveSuppressed(),
        !truncation.isRemoveStacktrace());
    this.nlsMessage = copySource.nlsMessage;
    this.uuid = copySource.uuid;
    if (!truncation.isRemoveStacktrace()) {
      setStackTrace(copySource.getStackTrace());
    }
    if (!truncation.isRemoveSuppressed()) {
      for (Throwable suppressed : copySource.getSuppressed()) {
        addSuppressed(suppressed);
      }
    }
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

    NlsRuntimeException.printStackTrace(this, locale, buffer);
  }

  /**
   * @see #createCopy(ExceptionTruncation)
   *
   * @param truncation the {@link ExceptionTruncation} settings.
   * @return the (truncated) copy.
   */
  protected NlsRuntimeException createCopyViaClone(ExceptionTruncation truncation) {

    try {
      NlsRuntimeException copy = (NlsRuntimeException) clone();
      ThrowableHelper.removeDetails(copy, truncation);
      return copy;
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   *
   * This default implementation is using {@link #createCopyViaClone(ExceptionTruncation) clone} to create a
   * copy and truncate it as configured. However, a proper implementation would use the appropriate
   * {@link #NlsException(NlsException, ExceptionTruncation) copy constructor} instead.
   */
  @Override
  public NlsRuntimeException createCopy(ExceptionTruncation truncation) {

    return createCopyViaClone(truncation);
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
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    // checked exceptions should be avoided at all. However, if they are used they should represent business
    // exceptions (user failures).
    return false;
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
