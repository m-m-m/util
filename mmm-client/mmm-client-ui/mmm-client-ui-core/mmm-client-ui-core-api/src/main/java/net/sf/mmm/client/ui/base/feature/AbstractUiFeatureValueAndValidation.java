/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.feature;

import net.sf.mmm.client.ui.api.feature.UiFeatureValueAndValidation;
import net.sf.mmm.util.exception.api.NlsThrowable;
import net.sf.mmm.util.exception.api.TechnicalErrorUserException;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationFailureImpl;
import net.sf.mmm.util.validation.base.ValidationStateImpl;
import net.sf.mmm.util.validation.base.ValidatorMandatory;

import org.slf4j.Logger;

/**
 * This is the abstract base implementation of {@link UiFeatureValueAndValidation}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiFeatureValueAndValidation<VALUE> implements UiFeatureValueAndValidation<VALUE> {

  /**
   * The constructor.
   */
  public AbstractUiFeatureValueAndValidation() {

    super();
  }

  /**
   * @return a {@link Logger} instance.
   */
  protected abstract Logger getLogger();

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValue(VALUE value) {

    setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValueForUser(VALUE value) {

    setValue(value, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValue() {

    try {
      return getValueOrException(null);
    } catch (RuntimeException e) {
      handleGetValueError(e, null);
      return null;
    }
  }

  /**
   * This method handles a {@link RuntimeException} that occurred in
   * {@link #getValueDirect(Object, ValidationState)}.
   *
   * @param e is the {@link RuntimeException} to handle.
   * @param state is the {@link ValidationState} or <code>null</code> if no validation is performed.
   */
  protected void handleGetValueError(RuntimeException e, ValidationState state) {

    if (state == null) {
      // ATTENTION: This is one of the very rare cases where we intentionally consume an exception.
      getLogger().debug("Error in getValue() at " + toString(), e);
    } else {
      ValidationFailure failure = createValidationFailure(e);
      if (failure != null) {
        state.onFailure(failure);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueOrException(VALUE template) throws RuntimeException {

    return getValueDirect(template, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValueAndValidate(ValidationState state) {

    clearValidity();
    ValidationState validationState = state;
    if (validationState == null) {
      validationState = new ValidationStateImpl();
    }
    VALUE result = getValueDirect(null, validationState);
    if (validationState.isValid()) {
      if ((result == null) && (state == null)) {
        getLogger().error("null has been returned as valid value at " + toString());
      }
      return result;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void resetValue() {

    setValue(getOriginalValue());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addValidatorMandatory() {

    if (isMandatory()) {
      return;
    }
    addValidator(new ValidatorMandatory());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean validate(ValidationState state) {

    clearValidity();
    ValidationState validationState = state;
    if (validationState == null) {
      validationState = new ValidationStateImpl();
    }

    int failureCount = validationState.getFailureCount();
    getValueDirect(null, validationState);
    return (validationState.getFailureCount() == failureCount);
  }

  /**
   * This method may clear potential state information from previous validations.
   */
  protected void clearValidity() {

    // nothing by default
  }

  /**
   * This method converts an exception from {@link #getValueOrException(Object)} to a
   * {@link ValidationFailure}.
   *
   * @param error is the exception.
   * @return the {@link ValidationFailure}.
   */
  protected ValidationFailure createValidationFailure(Throwable error) {

    ValidationFailure failure;
    if (error instanceof NlsThrowable) {
      NlsThrowable nlsThrowable = (NlsThrowable) error;
      failure = new ValidationFailureImpl(nlsThrowable.getCode(), getSource(), ((NlsThrowable) error).getNlsMessage());
    } else {
      failure = new ValidationFailureImpl(TechnicalErrorUserException.CODE, getSource(), error.getMessage());
    }
    return failure;
  }

  /**
   * @return the source for validation failures.
   */
  protected abstract String getSource();

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    // default implementation
    return getSource() + "[" + getClass().getSimpleName() + "]";
  }

}
