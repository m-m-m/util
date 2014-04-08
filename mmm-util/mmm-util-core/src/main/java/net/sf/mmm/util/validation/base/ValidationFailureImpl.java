/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is an implementation of {@link net.sf.mmm.util.validation.api.ValidationFailure} that properly
 * supports {@link #getMessage(Locale) internationalization} using {@link NlsMessage}. However it is not
 * properly serializable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValidationFailureImpl extends AbstractValidationFailure {

  /** UID for serialization. */
  private static final long serialVersionUID = -7710863486871741334L;

  /** @see #getMessage() */
  private NlsMessage message;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ValidationFailureImpl() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link NlsMessage} for {@link #getMessage(Locale)}.
   */
  public ValidationFailureImpl(String code, Object source, NlsMessage message) {

    super(code, source);
    this.message = message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return this.message.getLocalizedMessage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage(Locale locale) {

    return this.message.getLocalizedMessage(locale);
  }

}
