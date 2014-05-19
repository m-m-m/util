/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the implementation of {@link UiDataBinding} for no value ({@link Void}).
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingNone implements UiDataBinding<Void> {

  /** @see #getInstance() */
  private static final UiDataBindingNone INSTANCE = new UiDataBindingNone();

  /**
   * The constructor.
   */
  public UiDataBindingNone() {

    super();
  }

  /**
   * @return the singleton instance of this class.
   */
  public static UiDataBinding<Void> getInstance() {

    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void getValueAndValidate(ValidationState state) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void getValueDirect(Void template, ValidationState state) throws RuntimeException {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChangeHandler(UiHandlerEventValueChange<Void> handler) {

    // nothing to do - value can never change...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChangeHandler(UiHandlerEventValueChange<Void> handler) {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Void value) {

    setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueForUser(Void value) {

    setValue(value, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Void value, boolean forUser) {

    if (value != null) {
      throw new IllegalStateException();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resetValue() {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void getValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void getValueOrException(Void template) throws RuntimeException {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void getOriginalValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void getRecentValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addValidatorMandatory() {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMandatory() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addValidator(ValueValidator<? super Void> validator) {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeValidator(ValueValidator<? super Void> validator) {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(ValidationState state) {

    // TODO hohwille bug if composite widget with <Void>
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean doValidate(ValidationState state, Void value) {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> void bind(TypedProperty<P> property, UiWidgetWithValue<P> widget) {

    throw new NlsUnsupportedOperationException("bind");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property) {

    return createAndBind(property, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property, String label) {

    throw new NlsUnsupportedOperationException("createAndBind");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PropertyAccessor<Void, ?> createPropertyAccessor(String property) {

    throw new NlsUnsupportedOperationException("createPropertyAccessor");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> PropertyAccessor<Void, P> createPropertyAccessor(TypedProperty<P> property) {

    throw new NlsUnsupportedOperationException("createPropertyAccessor");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModified(boolean modified) {

    if (modified) {
      throw new IllegalStateException();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isModified() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValidity() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidity(Boolean valid) {

    // validity is also set to FALSE if child widgets are invalid...
    // if (Boolean.FALSE.equals(valid)) {
    // throw new NlsIllegalStateException();
    // }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel(TypedProperty<?> property) {

    return property.getTitle();
  }

}
