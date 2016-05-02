/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

/**
 * This is the abstract interface for an object, that can be {@link #validate(ValidationState) validated}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface AbstractValidatableObject {

  /**
   * This method performs the actual validation.
   * 
   * @param state is the {@link ValidationState}. Shall initially be created as new instance and passed on in
   *        case of recursive validations. May also be {@code null} and will then be created internally
   *        (in case you do not need more feedback than a boolean result).
   * @return {@code true} if the validation of this object has been successful, {@code false}
   *         otherwise (if there are validation failures).
   */
  boolean validate(ValidationState state);

}
