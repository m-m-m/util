/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * Placeholder implementation of {@link ValidationFailure} representing success used as internal representation where
 * {@code null} is not suitable.
 *
 * @author hohwille
 * @since 7.1.0
 */
public final class ValidationFailureSuccess implements ValidationFailure {

  private static final long serialVersionUID = 1L;

  /** The singleton instance. */
  public static ValidationFailureSuccess INSTANCE = new ValidationFailureSuccess();

  /**
   * The constructor.
   */
  private ValidationFailureSuccess() {
    super();
  }

  @Override
  public String getDetails() {

    return null;
  }

  @Override
  public String getType() {

    return null;
  }

  @Override
  public UUID getUuid() {

    return null;
  }

  @Override
  public String getSource() {

    return null;
  }

  @Override
  public String getMessage() {

    return null;
  }

  @Override
  public String getMessage(Locale locale) {

    return null;
  }

  @Override
  public String getCode() {

    return null;
  }

}
