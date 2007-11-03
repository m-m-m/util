/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.validator.impl;

import net.sf.mmm.util.nls.NlsMessageImpl;
import net.sf.mmm.value.validator.api.ValidationResult;
import net.sf.mmm.value.validator.base.AbstractCompositeValueValidator;
import net.sf.mmm.value.validator.base.ValidationResultImpl;

/**
 * This is the implementation of a composite validator that declares a value as
 * valid if and only if at least one child validator does.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueOrValidator extends AbstractCompositeValueValidator {

  /** uid for serialization */
  private static final long serialVersionUID = -9206244114248240571L;

  /**
   * The constructor.
   */
  public ValueOrValidator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ValidationResult getResult(ValidationResult[] details, int detailCount, int succeedCount) {

    if (succeedCount >= 1) {
      return ValidationResultImpl.VALID_RESULT;
    } else {
      // TODO: i18n
      return new ValidationResultImpl(new NlsMessageImpl(
          "At least one of the \"{0}\" error(s) must be fixed:", Integer.valueOf(detailCount)),
          details, detailCount);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int getMaximumRequiredValidChildren() {

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ValidationResult getResultIfEmpty() {

    // TODO: i18n
    return new ValidationResultImpl("OR-Validator needs at least one child validator to succeed!");
  }
}
