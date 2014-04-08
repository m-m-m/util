/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.exception.api.ExceptionTruncation;
import net.sf.mmm.util.uuid.api.UuidAccess;

/**
 * This is an abstract base implementation of an unchecked exception with real
 * <em>native language support</em> (NLS). <br>
 * <b>ATTENTION:</b><br>
 * Please prefer extending {@link net.sf.mmm.util.nls.api.NlsRuntimeException} instead of this class.<br>
 * <b>INFORMATION:</b><br>
 * Unchecked exceptions should be used for technical errors and should only occur in unexpected situations.
 *
 * @see NlsThrowable
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsRuntimeException extends RuntimeException implements NlsThrowable, Cloneable {

  /** UID for serialization. */
  private static final long serialVersionUID = -7838850701154079724L;

  /** the internationalized message */
  private NlsMessage nlsMessage;

  /** @see #getUuid() */
  private UUID uuid;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AbstractNlsRuntimeException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public AbstractNlsRuntimeException(NlsMessage message) {

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
  public AbstractNlsRuntimeException(Throwable nested, NlsMessage message) {

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
  protected AbstractNlsRuntimeException(AbstractNlsRuntimeException copySource, ExceptionTruncation truncation) {

    // super(null, truncation.isRemoveCause() ? null : copySource.getCause(),
    // !truncation.isRemoveSuppressed(),
    // !truncation.isRemoveStacktrace());
    super(null, truncation.isRemoveCause() ? null : copySource.getCause());
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
  public void printStackTrace(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    AbstractNlsException.printStackTrace(this, locale, resolver, buffer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(Locale locale, Appendable buffer) {

    printStackTrace(locale, null, buffer);
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

    return getLocalizedMessage(locale, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver) {

    StringBuffer message = new StringBuffer();
    getLocalizedMessage(locale, resolver, message);
    return message.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    getNlsMessage().getLocalizedMessage(locale, resolver, buffer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessage toNlsMessage() {

    return getNlsMessage();
  }

  /**
   * @see NlsBundleFactory#createBundle(Class)
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
   * @see #createCopy(ExceptionTruncation)
   *
   * @param truncation the {@link ExceptionTruncation} settings.
   * @return the (truncated) copy.
   */
  protected AbstractNlsRuntimeException createCopyViaClone(ExceptionTruncation truncation) {

    // try {
    AbstractNlsRuntimeException copy = null; // (AbstractNlsRuntimeException) clone();
    ThrowableHelper.removeDetails(copy, truncation);
    return copy;
    // } catch (CloneNotSupportedException e) {
    // throw new IllegalStateException(e);
    // }
  }

  /**
   * {@inheritDoc}
   *
   * This default implementation is using {@link #createCopyViaClone(ExceptionTruncation) clone} to create a
   * copy and truncate it as configured. However, a proper implementation would use the appropriate
   * {@link #AbstractNlsRuntimeException(AbstractNlsRuntimeException, ExceptionTruncation) copy constructor}
   * instead.
   */
  @Override
  public AbstractNlsRuntimeException createCopy(ExceptionTruncation truncation) {

    return createCopyViaClone(truncation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String result = super.toString();
    if (this.uuid != null) {
      result = result + AbstractNlsException.LINE_SEPARATOR + this.uuid.toString();
    }
    return result;
  }
}
