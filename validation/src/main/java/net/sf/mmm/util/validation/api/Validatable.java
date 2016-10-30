/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

/**
 * This is the abstract interface for an object, that can be {@link #validate() validated}.<br>
 * <b>NOTE:</b> It is a simpler and more convenient API than {@link AbstractValidatableObject} and
 * {@link ValidatableObject}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.4.0
 */
public abstract interface Validatable {

  /**
   * This method performs the actual validation.
   *
   * @see ValueValidator#validate(Object)
   *
   * @return {@link ValidationFailure} the validation failure.
   */
  ValidationFailure validate();

}
