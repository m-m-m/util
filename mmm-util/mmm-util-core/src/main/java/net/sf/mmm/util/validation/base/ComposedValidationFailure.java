/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Locale;

import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is an implementation of {@link ValidationFailure} that represents the composition of other
 * {@link ValidationFailure}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ComposedValidationFailure extends AbstractValidationFailure {

  /** UID for serialization. */
  private static final long serialVersionUID = -5191509274230075436L;

  /** @see #getFailure(int) */
  private ValidationFailure[] failures;

  /**
   * The constructor for de-serialization.
   */
  protected ComposedValidationFailure() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param code is the {@link #getCode() code}.
   * @param source is the {@link #getSource() source}.
   * @param failures are the individual {@link ValidationFailure}s.
   */
  public ComposedValidationFailure(String code, String source, ValidationFailure[] failures) {

    super(code, source);
    this.failures = failures;
  }

  /**
   * @return the line separator (e.g. {@link net.sf.mmm.util.lang.api.StringUtil#LINE_SEPARATOR_LF}).
   */
  protected String getSeparator() {

    return "\n";
  }

  /**
   * @see #getFailure(int)
   * @see java.util.Collection#size()
   * 
   * @return the number of {@link #getFailure(int) failures}.
   */
  public int getFailureCount() {

    return this.failures.length;
  }

  /**
   * Gets the {@link ValidationFailure} at the given <code>index</code>.
   * 
   * @see java.util.List#get(int)
   * 
   * @param index is the index of the {@link ValidationFailure} to get.
   * @return the requested {@link ValidationFailure}.
   */
  public ValidationFailure getFailure(int index) {

    return this.failures[index];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDetails() {

    StringBuilder buffer = new StringBuilder();
    for (ValidationFailure failure : this.failures) {
      String details = failure.getDetails();
      if (details != null) {
        if (buffer.length() > 0) {
          buffer.append(getSeparator());
        }
        buffer.append(details);
      }
    }
    if (buffer.length() == 0) {
      return null;
    }
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {

    return getMessage(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage(Locale locale) {

    StringBuilder buffer = new StringBuilder();
    for (ValidationFailure failure : this.failures) {
      if (buffer.length() > 0) {
        buffer.append(getSeparator());
      }
      String message;
      if (locale == null) {
        message = failure.getMessage();
      } else {
        message = failure.getMessage(locale);
      }
      buffer.append(message);
    }
    return buffer.toString();
  }

}
