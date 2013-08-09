/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.feature;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This class extends {@link AbstractUiFeatureValueAndValidation} with the validators.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiFeatureValueAndValidationWithValidators<VALUE> extends
    AbstractUiFeatureValueAndValidation<VALUE> {

  /** @see #addValidator(ValueValidator) */
  private List<ValueValidator<? super VALUE>> validatorList;

  /** @see #isMandatory() */
  private boolean mandatory;

  /**
   * The constructor.
   */
  public AbstractUiFeatureValueAndValidationWithValidators() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isMandatory() {

    return this.mandatory;
  }

  /**
   * @param mandatory is the mandatory to set
   */
  protected void setMandatory(boolean mandatory) {

    this.mandatory = mandatory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addValidator(ValueValidator<? super VALUE> validator) {

    NlsNullPointerException.checkNotNull(ValueValidator.class, validator);
    if (isMandatory(validator)) {
      setMandatory(true);
    }
    ensureValidatorList();
    this.validatorList.add(validator);
  }

  /**
   * Determines the given <code>validator</code> is mandatory.
   * 
   * @param validator the given {@link ValueValidator}.
   * @return <code>true</code> if the given <code>validator</code> is {@link AbstractValidator#isMandatory()
   *         mandatory}.
   */
  protected boolean isMandatory(ValueValidator<?> validator) {

    AbstractValidator<?> abstractValidator = (AbstractValidator<?>) validator;
    return abstractValidator.isMandatory();
  }

  /**
   * Ensures that the validator {@link List} is already created.
   */
  private void ensureValidatorList() {

    if (this.validatorList == null) {
      this.validatorList = new LinkedList<ValueValidator<? super VALUE>>();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeValidator(ValueValidator<? super VALUE> validator) {

    NlsNullPointerException.checkNotNull(ValueValidator.class, validator);
    boolean removed = false;
    if (this.validatorList != null) {
      removed = this.validatorList.remove(validator);
    }
    if (removed && isMandatory(validator)) {
      if (this.mandatory) {
        setMandatory(false);
      } else {
        getLogger().warn("Internal error: removed manadatory validator but mandatory flag was not set.");
      }
    }
    return removed;
  }

  /**
   * This method performs the actual validation using the
   * {@link #addValidator(net.sf.mmm.util.validation.api.ValueValidator) registered} validators. It is
   * (indirectly) called from {@link #validate(ValidationState)}.
   * 
   * @param state is the {@link ValidationState}. Must NOT be <code>null</code>.
   * @param value is the {@link #getValue() current value} of this object that has already be determined.
   * @return <code>true</code> if the validation was successful, <code>false</code> otherwise (if there are
   *         validation failures).
   */
  public final boolean doValidate(ValidationState state, VALUE value) {

    boolean success = true;
    if (this.validatorList != null) {
      for (ValueValidator<? super VALUE> validator : this.validatorList) {
        try {
          ValidationFailure failure = validator.validate(value, getSource());
          if (failure != null) {
            success = false;
            state.onFailure(failure);
          }
        } catch (RuntimeException e) {
          success = false;
          getLogger().error("Error in validator '" + validator + "' at " + toString(), e);
          handleGetValueError(e, state);
        }
      }
    }
    return success;
  }

}
