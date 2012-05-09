/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.uuid.api.UuidAccess;

/**
 * A GWT compatible copy of {@link net.sf.mmm.util.nls.api.AbstractNlsException}.
 * 
 * @see NlsThrowable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsException extends Exception implements NlsThrowable {

  /** UID for serialization. */
  private static final long serialVersionUID = -9077132842682462106L;

  /** @see #getSuppressed() */
  protected static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];

  /**
   * The line separator used by
   * {@link #printStackTrace(NlsThrowable, Locale, NlsTemplateResolver, Appendable)}.
   */
  static final String LINE_SEPARATOR = "\n";

  /** the internationalized message */
  private final NlsMessage nlsMessage;

  /** @see #getUuid() */
  private final UUID uuid;

  /** @see #getSuppressed() */
  private List<Throwable> suppressedList;

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
  public final UUID getUuid() {

    return this.uuid;
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
  public void printStackTrace(Locale locale, Appendable buffer) {

    printStackTrace(locale, null, buffer);
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
        for (Throwable suppressed : throwable.getSuppressed()) {
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

  /**
   * {@inheritDoc}
   */
  public void addSuppressed(Throwable suppressed) {

    // only available since Java 1.7
    // super.addSuppressed(suppressed);
    if ((suppressed == null) || (suppressed == this)) {
      // prevent non-sense...
      return;
    }
    if (this.suppressedList == null) {
      this.suppressedList = new ArrayList<Throwable>();
    }
    this.suppressedList.add(suppressed);
  }

  /**
   * {@inheritDoc}
   */
  public Throwable[] getSuppressed() {

    // only available since Java 1.7
    // return super.getSuppressed();
    if (this.suppressedList == null) {
      return EMPTY_THROWABLE_ARRAY;
    } else {
      return this.suppressedList.toArray(new Throwable[this.suppressedList.size()]);
    }
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
