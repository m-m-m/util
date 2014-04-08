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
  private String message;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected SimpleValidationFailure() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param message is the {@link #getMessage() message}.
   */
  public SimpleValidationFailure(String code, Object source, String message) {

    super(code, source);
    this.message = message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return this.message;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage(Locale locale) {

    return this.message;
  }

}
