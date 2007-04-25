/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.base;

import java.io.PrintStream;
import java.io.PrintWriter;

import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.api.NlsThrowable;
import net.sf.mmm.nls.api.NlsTranslator;

/**
 * This is an abstract base implementation of a checked exception with real
 * <em>native language support</em> (NLS). <br>
 * <b>ATTENTION:</b><br>
 * Checked exceptions should be used for business errors and should only occur
 * in unexpected situations.
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
   *        internationalization and should be in English language.
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
   *        internationalization and should be in English language.
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
   * {@inheritDoc}
   */
  public void printStackTrace(PrintStream stream, NlsTranslator nationalizer) {

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
   * {@inheritDoc}
   */
  public void printStackTrace(PrintWriter writer, NlsTranslator nationalizer) {

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
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return getLocalizedMessage(null);
  }

  /**
   * {@inheritDoc}
   */
  public String getLocalizedMessage(NlsTranslator nationalizer) {

    StringBuffer message = new StringBuffer();
    getLocalizedMessage(nationalizer, message);
    return message.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void getLocalizedMessage(NlsTranslator nationalizer, StringBuffer message) {

    getNlsMessage().getLocalizedMessage(nationalizer, message);
  }

}
