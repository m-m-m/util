/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.api;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * This the base class for all runtime exceptions of the project.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractNlsRuntimeException extends RuntimeException implements NlsThrowable {

  /** the internationalized message */
  private NlsMessage nlsMessage;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage the internationalized message describing
   *        the problem briefly.
   */
  public AbstractNlsRuntimeException(NlsMessage internationalizedMessage) {

    super();
    this.nlsMessage = internationalizedMessage;
  }

  /**
   * The constructor.
   * 
   * @param nested is the throwable that caused this exception.
   * @param internationalizedMessage the internationalized message describing
   *        the problem briefly.
   */
  public AbstractNlsRuntimeException(Throwable nested, NlsMessage internationalizedMessage) {

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
  @Override
  public void printStackTrace(PrintStream s) {

    printStackTrace(s, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(PrintWriter s) {

    printStackTrace(s, null);
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
    Throwable nested = getCause();
    if (nested != null) {
      NlsThrowable mt = null;
      String msg = null;
      if (nested instanceof NlsThrowable) {
        mt = (NlsThrowable) nested;
      } else {
        msg = nested.getLocalizedMessage();
      }
      if ((mt != null) || (msg != null)) {
        message.append(" [");
        if (mt != null) {
          mt.getLocalizedMessage(nationalizer, message);
        } else {
          message.append(msg);
        }
        message.append("]");
      }
    }
  }

}
