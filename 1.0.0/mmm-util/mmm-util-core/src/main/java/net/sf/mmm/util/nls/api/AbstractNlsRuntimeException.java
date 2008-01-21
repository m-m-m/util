/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * This is an abstract base implementation of an unchecked exception with real
 * <em>native language support</em> (NLS). <br>
 * <b>ATTENTION:</b><br>
 * Please prefer extending {@link net.sf.mmm.util.nls.NlsRuntimeException}
 * instead of this class.<br>
 * <b>INFORMATION:</b><br>
 * Unchecked exceptions should be used for technical errors and should only
 * occur in unexpected situations.
 * 
 * @see NlsThrowable
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

    AbstractNlsException.printStackTrace(this, locale, resolver, buffer);
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

    StringBuffer message = new StringBuffer();
    getLocalizedMessage(null, resolver, message);
    return message.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    AbstractNlsException.getLocalizedMessage(this, locale, resolver, buffer);
  }

  /**
   * {@inheritDoc}
   */
  public NlsMessage toNlsMessage() {

    return getNlsMessage();
  }

}
