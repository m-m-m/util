/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.attribute.AttributeReadModified;
import net.sf.mmm.client.ui.api.feature.UiFeatureValidation;
import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.base.handler.event.ChangeEventSender;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.SimpleValidationFailure;
import net.sf.mmm.util.validation.base.ValidationStateImpl;
import net.sf.mmm.util.validation.base.ValidatorMandatory;

/**
 * This is a base implementation of the aspect for the {@link #getValue() value} of a
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget} and all its related features.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiAspectValue<VALUE> implements UiFeatureValue<VALUE>, UiFeatureValidation<VALUE>,
    AttributeReadModified {

  /** @see #addValidator(ValueValidator) */
  private final List<ValueValidator<? super VALUE>> validatorList;

  /** @see #addChangeHandler(UiHandlerEventValueChange) */
  private ChangeEventSender<VALUE> changeEventSender;

  /** @see #getOriginalValue() */
  private VALUE originalValue;

  /** @see #isModified() */
  private boolean modified;

  /** @see #isMandatory() */
  private boolean mandatory;

  /**
   * The constructor.
   */
  public UiAspectValue() {

    super();
    this.validatorList = new LinkedList<ValueValidator<? super VALUE>>();
    this.modified = false;
    this.originalValue = null;
    this.changeEventSender = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValue() {

    try {
      return getValueOrException();
    } catch (RuntimeException e) {
      // ATTENTION: This is one of the very rare cases where we intentionally ignore an exception.
      return null;
    }
  }

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
  public final VALUE getValueOrException() throws RuntimeException {

    return doGetValue();
  }

  /**
   * This method is called from {@link #getValueOrException()}. It has to be implemented with the custom logic
   * to get the value from the view. The returned value needs to be created as new object rather than
   * modifying the {@link #getOriginalValue() original value} that has been previously set. This is required
   * to support operations such as {@link #resetValue()}. The implementation of this method has to correspond
   * with the implementation of {@link #doSetValue(Object)}.
   * 
   * @see #doSetValue(Object)
   * 
   * @return a new value reflecting the current data as specified by the end-user.
   * @throws RuntimeException if something goes wrong (e.g. validation failure).
   */
  protected abstract VALUE doGetValue() throws RuntimeException;

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getOriginalValue() {

    return this.originalValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValueForUser(VALUE value) {

    setValue(value, true);
  }

  /**
   * Implementation of {@link #setValue(Object)} and {@link #setValueForUser(Object)}.
   * 
   * @param newValue is the new {@link #getValue() value}.
   * @param forUser <code>true</code> if called from {@link #setValueForUser(Object)}, <code>false</code> if
   *        set from {@link #setValue(Object)}.
   */
  private void setValue(VALUE newValue, boolean forUser) {

    setModified(forUser);
    if (!forUser) {
      this.originalValue = newValue;
    }
    doSetValue(newValue);
    if (this.changeEventSender != null) {
      this.changeEventSender.onValueChange(this, true);
    }
  }

  /**
   * This method is called from {@link #setValue(Object)} and {@link #setValueForUser(Object)}. It has to be
   * implemented with the custom logic to set the value in the view. The implementation of this method has to
   * correspond with the implementation of {@link #doGetValue()}.
   * 
   * @see #doGetValue()
   * 
   * @param value is the value to set. Typically a composite object (e.g. java bean) so its attributes are set
   *        to {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField atomic fields}.
   */
  protected abstract void doSetValue(VALUE value);

  /**
   * {@inheritDoc}
   */
  @Override
  public final void resetValue() {

    setValue(this.originalValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isModified() {

    if (this.modified) {
      return true;
    }
    return false;
  }

  /**
   * @param modified <code>true</code> if this widget is modified (locally), <code>false</code> otherwise.
   */
  protected final void setModified(boolean modified) {

    this.modified = modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    requireChangeEventSender().addHandler(handler);
  }

  /**
   * @return the {@link ChangeEventSender}. Will be {@link #createChangeEventSender() created} on the first
   *         call.
   */
  protected final ChangeEventSender<VALUE> requireChangeEventSender() {

    if (this.changeEventSender == null) {
      this.changeEventSender = createChangeEventSender();
    }
    return this.changeEventSender;
  }

  /**
   * @return a new instance of {@link ChangeEventSender}. Should be connected with the underlying native
   *         widget where applicable.
   */
  protected abstract ChangeEventSender<VALUE> createChangeEventSender();

  /**
   * @return the {@link ChangeEventSender} or <code>null</code> if NOT yet created.
   */
  protected final ChangeEventSender<VALUE> getChangeEventSender() {

    return this.changeEventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    if (this.changeEventSender != null) {
      return this.changeEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMandatory() {

    return this.mandatory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addValidator(ValueValidator<? super VALUE> validator) {

    NlsNullPointerException.checkNotNull(ValueValidator.class, validator);
    this.validatorList.add(validator);
    if (validator instanceof ValidatorMandatory) {
      setMandatory(true);
      // ensure that super call was not omitted if overridden...
      assert this.mandatory;
    }
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
   * This method updates the {@link #isMandatory()} flag. You may override it to update the UI to reflect this
   * change.
   * 
   * @param mandatory is the new value of {@link #isMandatory()}.
   */
  protected void setMandatory(boolean mandatory) {

    this.mandatory = mandatory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeValidator(ValueValidator<? super VALUE> validator) {

    NlsNullPointerException.checkNotNull(ValueValidator.class, validator);
    boolean removed = this.validatorList.remove(validator);
    if (removed) {
      boolean newMandatory = false;
      for (ValueValidator<? super VALUE> currentValidator : this.validatorList) {
        if (currentValidator instanceof ValidatorMandatory) {
          newMandatory = true;
          break;
        }
      }
      if (newMandatory != this.mandatory) {
        setMandatory(newMandatory);
        // if mandatory has changed due to removal of validator, it can only change to false
        assert !this.mandatory;
      }
    }
    return removed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean validate(ValidationState state) {

    ValidationState validationState = state;
    if (validationState == null) {
      validationState = new ValidationStateImpl();
    }
    doValidate(validationState);
    return validationState.isValid();
  }

  /**
   * This method is called from {@link #validate(ValidationState)} and performs the actual validation of this
   * object.
   * 
   * @param state is the {@link ValidationState}. Never <code>null</code>.
   */
  protected void doValidate(ValidationState state) {

    VALUE currentValue;
    try {
      currentValue = getValueOrException();
      for (ValueValidator<? super VALUE> validator : this.validatorList) {
        ValidationFailure failure = validator.validate(currentValue, getSource());
        if (failure != null) {
          state.onFailure(failure);
        }
      }
    } catch (RuntimeException e) {
      ValidationFailure failure = createValidationFailure(e);
      if (failure != null) {
        state.onFailure(failure);
      }
    }
  }

  /**
   * This method converts an exception from {@link #getValueOrException()} to a {@link ValidationFailure}.
   * 
   * @param error is the exception.
   * @return the {@link ValidationFailure}.
   */
  protected ValidationFailure createValidationFailure(Throwable error) {

    return new SimpleValidationFailure(error.getClass().getSimpleName(), getSource(), error.getLocalizedMessage());
  }

  /**
   * @return the source for validation failures.
   */
  protected abstract String getSource();

}
