/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import net.sf.mmm.util.StringUtil;

/**
 * This is an abstract base implementation of a checked exception with real
 * <em>native language support</em> (NLS). <br>
 * <b>ATTENTION:</b><br>
 * Please prefer extending {@link net.sf.mmm.util.nls.NlsException} instead of
 * this class.<br>
 * <b>INFORMATION:</b><br>
 * Checked exceptions should be used for business errors and should only occur
 * in unexpected situations.
 * 
 * @see NlsThrowable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractNlsException extends Exception implements NlsThrowable {

  /** the internationalized message */
  private NlsMessage nlsMessage;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is the internationalized message describing
   *        the problem briefly.
   */
  public AbstractNlsException(NlsMessage internationalizedMessage) {

    super();
    this.nlsMessage = internationalizedMessage;
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is the internationalized message describing
   *        the problem briefly.
   */
  public AbstractNlsException(Throwable nested, NlsMessage internationalizedMessage) {

    super(nested);
    this.nlsMessage = internationalizedMessage;
  }

  /**
   * {@inheritDoc}
   */
  public final NlsMessage getNlsMessage() {

    return this.nlsMessage;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(PrintStream s) {

    printStackTrace(Locale.getDefault(), null, s);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(PrintWriter s) {

    printStackTrace(Locale.getDefault(), null, s);
  }

  /**
   * {@inheritDoc}
   */
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
  static void printStackTrace(NlsThrowable throwable, Locale locale, NlsTemplateResolver resolver,
      Appendable buffer) {

    try {
      synchronized (buffer) {
        throwable.getLocalizedMessage(locale, resolver, buffer);
        buffer.append(StringUtil.LINE_SEPARATOR);
        StackTraceElement[] trace = throwable.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
          buffer.append("\tat ");
          buffer.append(trace[i].toString());
          buffer.append(StringUtil.LINE_SEPARATOR);
        }

        Throwable nested = throwable.getCause();
        if (nested != null) {
          buffer.append("Caused by: ");
          buffer.append(StringUtil.LINE_SEPARATOR);
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
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return getNlsMessage().getLocalizedMessage();
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage(Locale locale) {

    return getLocalizedMessage(locale, null);
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver) {

    StringBuffer buffer = new StringBuffer();
    getLocalizedMessage(locale, resolver, buffer);
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    getNlsMessage().getLocalizedMessage(locale, resolver, buffer);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage toNlsMessage() {

    return getNlsMessage();
  }

}
