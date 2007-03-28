/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.validator.impl;

import net.sf.mmm.value.validator.api.ValidationResult;
import net.sf.mmm.value.validator.base.AbstractValueValidator;
import net.sf.mmm.value.validator.base.ValidationResultImpl;

/**
 * This is an implementation of the ValueValidatorIF interface that checks if a
 * value has a specific type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueTypeValidator extends AbstractValueValidator {

  /** uid for serialization */
  private static final long serialVersionUID = 8221282496616883367L;

  /** the required value type */
  private Class type;

  /**
   * The constructor.
   * 
   * TODO: accept Type instead of Class
   * 
   * @param valueType
   *        is the required value type.
   */
  public ValueTypeValidator(Class valueType) {

    super();
    this.type = valueType;
  }

  /**
   * {@inheritDoc}
   */
  public ValidationResult validate(Object value) {

    if ((value == null) || (value.getClass().equals(this.type))) {
      return ValidationResultImpl.VALID_RESULT;
    } else {
      // TODO: i18n
      return new ValidationResultImpl("The value must have the type " + this.type + "!");
      // "The value must have the type {0}!",
      // new Object[]{this.type});
    }
  }
}
