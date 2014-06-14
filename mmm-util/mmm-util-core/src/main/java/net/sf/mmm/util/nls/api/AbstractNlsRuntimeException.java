/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;

import net.sf.mmm.util.exception.api.ExceptionTruncation;

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
 * @deprecated use {@link net.sf.mmm.util.exception.api.NlsRuntimeException} instead.
 */
@Deprecated
public abstract class AbstractNlsRuntimeException extends net.sf.mmm.util.exception.api.NlsRuntimeException
    implements NlsThrowable {

  /** UID for serialization. */
  private static final long serialVersionUID = -7838850701154079724L;

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

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   */
  public AbstractNlsRuntimeException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * The copy constructor.
   *
   * @param copySource is the exception to copy.
   * @param truncation is the {@link ExceptionTruncation} to configure potential truncations.
   */
  protected AbstractNlsRuntimeException(AbstractNlsRuntimeException copySource, ExceptionTruncation truncation) {

    super(copySource, truncation);
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
  public String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver) {

    StringBuffer message = new StringBuffer();
    getLocalizedMessage(locale, resolver, message);
    return message.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printStackTrace(Locale locale, NlsTemplateResolver resolver, Appendable buffer) {

    AbstractNlsException.printStackTrace(this, locale, resolver, buffer);
  }

}
