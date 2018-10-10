/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

/**
 * This is the interface for the state of a validation.
 *
 * @see net.sf.mmm.util.validation.base.ValidationStateImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @deprecated use {@link ValidationFailure} and {@link Validatable} for simplicity. This API including
 *             {@link ValidatableObject} is considered overengineered and will be removed in the future.
 */
@Deprecated
public interface ValidationState extends ValidationCollector {

  /**
   * This method determines if the
   * {@link net.sf.mmm.util.validation.api.AbstractValidatableObject#validate(ValidationState) validation} has been
   * successful.
   *
   * @return {@code true} if no {@link ValidationFailure} has been {@link #onFailure(ValidationFailure) collected},
   *         {@code false} otherwise.
   */
  boolean isValid();

}
