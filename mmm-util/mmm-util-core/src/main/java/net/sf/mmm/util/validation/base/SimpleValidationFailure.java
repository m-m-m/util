/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Locale;

/**
 * This is a simple implementation of {@link AbstractValidationFailure} that does NOT (properly) support
 * {@link #getMessage(Locale) internationalization}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class SimpleValidationFailure extends AbstractValidationFailure {

  /** UID for serialization. */
  private static final long serialVersionUID = -6721660142697209055L;

  /** @see #getMessage() */
  private final String message;

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   */
  public SimpleValidationFailure(String code, String source, String message) {

    super(code, source);
    this.message = message;
  }

  /**
   * {@inheritDoc}
   */
  public String getMessage() {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  public String getMessage(Locale locale) {

    return this.message;
  }

}
