/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is an implementation of {@link net.sf.mmm.util.validation.api.ValidationFailure} that properly
 * supports {@link #getMessage(Locale) internationalization} using {@link NlsMessage}. However it is not
 * properly serializable.
 * 
 * @author hohwille
 * @since 2.0.2
 */
public class ValidationFailureImpl extends AbstractValidationFailure {

  /** UID for serialization. */
  private static final long serialVersionUID = -7710863486871741334L;

  /** @see #getMessage() */
  private final NlsMessage message;

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link NlsMessage} for {@link #getMessage(Locale)}.
   */
  public ValidationFailureImpl(String code, String source, NlsMessage message) {

    super(code, source);
    this.message = message;
  }

  /**
   * {@inheritDoc}
   */
  public String getMessage() {

    return this.message.getLocalizedMessage();
  }

  /**
   * {@inheritDoc}
   */
  public String getMessage(Locale locale) {

    return this.message.getLocalizedMessage(locale);
  }

}
