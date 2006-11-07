/* $Id$ */
package net.sf.mmm.content.validator.impl;

import net.sf.mmm.content.validator.api.ValidationResult;
import net.sf.mmm.content.validator.base.AbstractCompositeValueValidator;
import net.sf.mmm.content.validator.base.ValidationResultImpl;
import net.sf.mmm.nls.base.NlsMessageImpl;

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
   * @see net.sf.mmm.content.validator.base.AbstractCompositeValueValidator#getResult(net.sf.mmm.content.validator.api.ValidationResult[],
   *      int, int)
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
   * @see net.sf.mmm.content.validator.base.AbstractCompositeValueValidator#getMaximumRequiredValidChildren()
   */
  @Override
  protected int getMaximumRequiredValidChildren() {

    return 1;
  }

  /**
   * @see net.sf.mmm.content.validator.base.AbstractCompositeValueValidator#getResultIfEmpty()
   */
  @Override
  protected ValidationResult getResultIfEmpty() {

    // TODO: i18n
    return new ValidationResultImpl("OR-Validator needs at least one child validator to succeed!");
  }
}
