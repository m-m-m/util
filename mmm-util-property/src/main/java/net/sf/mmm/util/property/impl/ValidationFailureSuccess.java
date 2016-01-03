/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.Locale;
import java.util.UUID;

import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * Implementation of {@link ValidationFailure} for success.
 *
 * @author hohwille
 * @since 7.1.0
 */
final class ValidationFailureSuccess implements ValidationFailure {

  private static final long serialVersionUID = 1L;

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
