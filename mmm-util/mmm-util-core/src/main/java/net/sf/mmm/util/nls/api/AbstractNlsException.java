/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.exception.api.ExceptionTruncation;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.uuid.api.UuidAccess;

/**
 * This is an abstract base implementation of a checked exception with real <em>native language support</em>
 * (NLS). <br>
 * <b>ATTENTION:</b><br>
 * Please prefer extending {@link net.sf.mmm.util.nls.api.NlsException} instead of this class.<br>
 * <b>INFORMATION:</b><br>
 * Checked exceptions should be used for business errors and should only occur in unexpected situations.
 *
 * @see NlsThrowable
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsException extends Exception implements NlsThrowable, Cloneable {

  /** UID for serialization. */
  private static final long serialVersionUID = -9077132842682462106L;

  /**
   * The line separator used by
   * {@link #printStackTrace(NlsThrowable, Locale, NlsTemplateResolver, Appendable)}.
   */
  // BEGIN_GWT: IF_GWT_IS_SERVER
  static final String LINE_SEPARATOR = System.getProperty(StringUtil.SYSTEM_PROPERTY_LINE_SEPARATOR);

  // ELSE_IF_GWT_IS_CLIENT
  // static final String LINE_SEPARATOR = "\n";
  // :END_GWT

  /** the internationalized message */
  private final NlsMessage nlsMessage;

  /** @see #getUuid() */
  private final UUID uuid;

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public AbstractNlsException(NlsMessage message) {

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
  public AbstractNlsException(Throwable nested, NlsMessage message) {

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
  protected AbstractNlsException(AbstractNlsException copySource, ExceptionTruncation truncation) {

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

    printStackTrace(locale, null, buffer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    printStackTrace(this, locale, resolver, buffer);
  }

  /**
   * @see #createCopy(ExceptionTruncation)
   *
   * @param truncation the {@link ExceptionTruncation} settings.
   * @return the (truncated) copy.
   */
  protected AbstractNlsRuntimeException createCopyViaClone(ExceptionTruncation truncation) {

    try {
      AbstractNlsRuntimeException copy = (AbstractNlsRuntimeException) clone();
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
   * {@link #AbstractNlsException(AbstractNlsException, ExceptionTruncation) copy constructor} instead.
   */
  @Override
  public AbstractNlsRuntimeException createCopy(ExceptionTruncation truncation) {

    return createCopyViaClone(truncation);
  }

  /**
   * @see NlsThrowable#printStackTrace(Locale, NlsTemplateResolver, Appendable)
   *
   * @param throwable is the throwable to print.
   * @param locale is the locale to translate to.
   * @param resolver translates the original message.
   * @param buffer is where to write the stack trace to.
   */
  static void printStackTrace(NlsThrowable throwable, Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    try {
      synchronized (buffer) {
        buffer.append(throwable.getClass().getName());
        buffer.append(": ");
        throwable.getLocalizedMessage(locale, resolver, buffer);
        buffer.append(LINE_SEPARATOR);
        UUID uuid = throwable.getUuid();
        if (uuid != null) {
          buffer.append(uuid.toString());
          buffer.append(LINE_SEPARATOR);
        }
        StackTraceElement[] trace = throwable.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
          buffer.append("\tat ");
          buffer.append(trace[i].toString());
          buffer.append(LINE_SEPARATOR);
        }
        for (Throwable suppressed : ((Throwable) throwable).getSuppressed()) {
          buffer.append("Suppressed: ");
          buffer.append(LINE_SEPARATOR);
          printStackTraceNested(suppressed, locale, resolver, buffer);
        }

        Throwable nested = throwable.getCause();
        if (nested != null) {
          buffer.append("Caused by: ");
          buffer.append(LINE_SEPARATOR);
          printStackTraceNested(nested, locale, resolver, buffer);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * @see NlsThrowable#printStackTrace(Locale, NlsTemplateResolver, Appendable)
   *
   * @param nested is the throwable to print.
   * @param locale is the locale to translate to.
   * @param resolver translates the original message.
   * @param buffer is where to write the stack trace to.
   * @throws IOException if caused by <code>buffer</code>.
   */
  private static void printStackTraceNested(Throwable nested, Locale locale, NlsTemplateResolver resolver,
      Appendable buffer) throws IOException {

    if (nested instanceof NlsThrowable) {
      ((NlsThrowable) nested).printStackTrace(locale, resolver, buffer);
    } else {
      if (buffer instanceof PrintStream) {
        nested.printStackTrace((PrintStream) buffer);
      } else if (buffer instanceof PrintWriter) {
        nested.printStackTrace((PrintWriter) buffer);
      } else {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        nested.printStackTrace(printWriter);
        printWriter.flush();
        buffer.append(writer.toString());
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

    return getLocalizedMessage(locale, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver) {

    StringBuffer buffer = new StringBuffer();
    getLocalizedMessage(locale, resolver, buffer);
    return buffer.toString();
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
  public String toString() {

    String result = super.toString();
    if (this.uuid != null) {
      result = result + LINE_SEPARATOR + this.uuid.toString();
    }
    return result;
  }
}
