/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Collection;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This is an implementation of {@link ConstraintValidator} for {@link Mandatory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MandatoryValidator implements ConstraintValidator<Mandatory, Object> {

  /**
   * The constructor.
   */
  public MandatoryValidator() {

    super();
  }

  @Override
  public void initialize(Mandatory constraintAnnotation) {

    // nothing to do...
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {

    if (value == null) {
      return false;
    }
    if (value instanceof String) {
      return (((String) value).length() > 0);
    }
    if (value instanceof Collection) {
      return !((Collection<?>) value).isEmpty();
    }
    if (value instanceof Map) {
      return !((Map<?, ?>) value).isEmpty();
    }
    return true;
  }
}
