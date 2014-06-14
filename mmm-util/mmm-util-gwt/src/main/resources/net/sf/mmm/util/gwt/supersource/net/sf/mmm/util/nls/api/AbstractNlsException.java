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

/**
 * A GWT compatible copy of {@link net.sf.mmm.util.nls.api.AbstractNlsException}.
 *
 * @see NlsThrowable
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated use {@link net.sf.mmm.util.exception.api.NlsException} instead.
 */
@Deprecated
public abstract class AbstractNlsException extends net.sf.mmm.util.exception.api.NlsException implements
    NlsThrowable {

  /** UID for serialization. */
  private static final long serialVersionUID = -9077132842682462106L;

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public AbstractNlsException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public AbstractNlsException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * The copy constructor.
   *
   * @param copySource is the exception to copy.
   * @param truncation is the {@link ExceptionTruncation} to configure potential truncations.
   */
  protected AbstractNlsException(AbstractNlsException copySource, ExceptionTruncation truncation) {

    super(copySource, truncation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    printStackTrace(this, locale, resolver, buffer);
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
          printStackTraceNested(suppressed, locale, resolver, buffer);
        }

        Throwable nested = throwable.getCause();
        if (nested != null) {
          buffer.append("Caused by: ");
          buffer.append(StringUtil.LINE_SEPARATOR);
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
      for (StackTraceElement element : nested.getStackTrace()) {
        buffer.append("\tat ");
        buffer.append(element.toString());
        buffer.append(StringUtil.LINE_SEPARATOR);
      }
      Throwable cause = nested.getCause();
      if (cause != null) {
        buffer.append("Caused by: ");
        buffer.append(StringUtil.LINE_SEPARATOR);
      }
    }
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

}