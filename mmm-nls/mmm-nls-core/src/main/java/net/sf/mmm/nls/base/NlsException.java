/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.io.PrintStream;
import java.io.PrintWriter;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.api.NlsThrowable;
import net.sf.mmm.nls.api.StringTranslator;

/**
 * This is an abstract base implementation of an exception with real <em>native
 * language support</em>
 * (NLS).
 * 
 * @see NlsThrowable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class NlsException extends Exception implements NlsThrowable {

  /** the internationalized message */
  private NlsMessage nlsMessage;

  /**
   * The constructor.<br>
   * 
   * @param internaitionalizedMessage
   *        is a short description of the problem. It is used for
   *        internationalization and should be in english language.
   * @param arguments
   *        are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsException(String internaitionalizedMessage, Object... arguments) {

    this(new NlsMessageImpl(internaitionalizedMessage, arguments));
  }

  /**
   * The constructor.<br>
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param internaitionalizedMessage
   *        is a short description of the problem. It is used for
   *        internationalization and should be in english language.
   * @param arguments
   *        are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    this(nested, new NlsMessageImpl(internaitionalizedMessage, arguments));
  }

  /**
   * The constructor.
   * 
   * @param internationalizedMessage
   *        is the internationalized message describing the problem briefly.
   */
  public NlsException(NlsMessage internationalizedMessage) {

    super();
    this.nlsMessage = internationalizedMessage;
  }

  /**
   * The constructor.
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage
   *        is the internationalized message describing the problem briefly.
   */
  public NlsException(Throwable nested, NlsMessage internationalizedMessage) {

    super(nested);
    this.nlsMessage = internationalizedMessage;
  }

  /**
   * This method gets the internationalized message describing the problem.
   * 
   * @return the internationalized message.
   */
  public final NlsMessage getNlsMessage() {

    return this.nlsMessage;
  }

  /**
   * @see net.sf.mmm.nls.api.NlsThrowable#printStackTrace(java.io.PrintStream,
   *      StringTranslator)
   */
  public void printStackTrace(PrintStream stream, StringTranslator nationalizer) {

    synchronized (stream) {
      stream.println(getLocalizedMessage(nationalizer));
      StackTraceElement[] trace = getStackTrace();
      for (int i = 0; i < trace.length; i++) {
        stream.println("\tat " + trace[i]);
      }

      Throwable nested = getCause();
      if (nested != null) {
        stream.println("Caused by: ");
        if (nested instanceof NlsThrowable) {
          ((NlsThrowable) nested).printStackTrace(stream, nationalizer);
        } else {
          nested.printStackTrace(stream);
        }
      }
    }
  }

  /**
   * @see net.sf.mmm.nls.api.NlsThrowable#printStackTrace(java.io.PrintWriter,
   *      net.sf.mmm.nls.api.StringTranslator)
   */
  public void printStackTrace(PrintWriter writer, StringTranslator nationalizer) {

    synchronized (writer) {
      writer.println(getLocalizedMessage(nationalizer));
      StackTraceElement[] trace = getStackTrace();
      for (int i = 0; i < trace.length; i++) {
        writer.println("\tat " + trace[i]);
      }

      Throwable nested = getCause();
      if (nested != null) {
        writer.println("Caused by: ");
        if (nested instanceof NlsThrowable) {
          ((NlsThrowable) nested).printStackTrace(writer, nationalizer);
        } else {
          nested.printStackTrace(writer);
        }
      }
    }
  }

  /**
   * @see java.lang.Throwable#getMessage()
   */
  @Override
  public String getMessage() {

    return getLocalizedMessage(null);
  }

  /**
   * @see net.sf.mmm.nls.api.NlsThrowable#getLocalizedMessage(StringTranslator)
   */
  public String getLocalizedMessage(StringTranslator nationalizer) {

    StringBuffer message = new StringBuffer();
    getLocalizedMessage(nationalizer, message);
    return message.toString();
  }

  /**
   * @see net.sf.mmm.nls.api.NlsThrowable#getLocalizedMessage(StringTranslator,
   *      java.lang.StringBuffer)
   */
  public void getLocalizedMessage(StringTranslator nationalizer, StringBuffer message) {

    getNlsMessage().getLocalizedMessage(nationalizer, message);
    /*
     * Throwable nested = getCause(); if (nested != null) { MmmThrowableIF
     * mt = null; String msg = null; if (nested instanceof MmmThrowableIF) {
     * mt = (MmmThrowableIF) nested; } else { msg =
     * nested.getLocalizedMessage(); } if ((mt != null) || (msg != null)) {
     * message.append(" ["); if (mt != null) {
     * mt.getLocalizedMessage(nationalizer, message); } else {
     * message.append(msg); } message.append("]"); } }
     */
  }

}
