/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.field;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteModified;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteValidationFailure;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetLabel;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;
import net.sf.mmm.ui.toolkit.base.handler.event.ChangeEventSender;
import net.sf.mmm.ui.toolkit.base.handler.event.FocusEventSender;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetRegularAtomic;
import net.sf.mmm.ui.toolkit.base.widget.UiAspectValue;
import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is the abstract base implementation of {@link UiWidgetField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getWidgetAdapter() adapter} value.
 */
public abstract class AbstractUiWidgetField<ADAPTER extends UiWidgetAdapterField<?, VALUE, ADAPTER_VALUE>, VALUE, ADAPTER_VALUE>
    extends AbstractUiWidgetRegularAtomic<ADAPTER> implements UiWidgetField<VALUE>, AttributeWriteModified,
    AttributeWriteValidationFailure {

  /** The instance of {@link ValueAspect} to avoid redundancy and due to lack of multi-inheritance. */
  private final ValueAspect aspectValue;

  /** @see #addFocusHandler(UiHandlerEventFocus) */
  private FocusEventSender focusEventSender;

  /** @see #isModified() */
  private boolean modified;

  /** @see #getValidationFailure() */
  private String validationFailure;

  /** @see #getLabel() */
  private String label;

  /** @see #getLabelWidget() */
  private UiWidgetLabel labelWidget;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetField(AbstractUiWidgetFactory<?> factory) {

    super(factory);
    this.aspectValue = new ValueAspect();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    ChangeEventSender<VALUE> changeEventSender = this.aspectValue.getSender();
    if (changeEventSender != null) {
      adapter.setChangeEventSender(this, changeEventSender);
    }
    if (this.aspectValue.value != null) {
      adapter.setValue(convertFromValue(this.aspectValue.value));
    }
    if (this.focusEventSender != null) {
      adapter.setFocusEventSender(this, this.focusEventSender);
    }
    if (this.validationFailure != null) {
      adapter.setValidationFailure(this.validationFailure);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    this.aspectValue.addChangeHandler(handler);
  }

  /**
   * @return the {@link ValueAspect}.
   */
  protected ValueAspect getAspectValue() {

    return this.aspectValue;
  }

  // /**
  // * @return the {@link ChangeEventSender} or <code>null</code> if NOT yet created.
  // */
  // protected final ChangeEventSender<VALUE> getChangeEventSender() {
  //
  // return this.changeEventSender;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    return this.aspectValue.removeChangeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValue(VALUE value) {

    this.aspectValue.setValue(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueForUser(VALUE userValue) {

    this.aspectValue.setValueForUser(userValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void resetValue() {

    this.aspectValue.resetValue();
  }

  /**
   * This method converts the given <code>adapterValue</code> from the {@link #getWidgetAdapter() widget
   * adapter} to the type of this {@link #getValue() value}. The default implementation simply performs a
   * cast. You have to override this method if {@literal <VALUE>} and {@literal <WIDGET_VALUE>} differ.
   * 
   * @param adapterValue is the value to convert. Will NOT be null.
   * @return the converted value.
   */
  @SuppressWarnings("unchecked")
  protected VALUE convertToValue(ADAPTER_VALUE adapterValue) {

    return (VALUE) adapterValue;
  }

  /**
   * This method gets the result of {@link #convertFromValue(Object)} for the value <code>null</code>. It is
   * called for <code>null</code> values instead of {@link #convertFromValue(Object)} to prevent
   * {@link NullPointerException}s. The default is <code>null</code> - override to change.
   * 
   * @return the value representation of <code>null</code> for the {@link #getWidgetAdapter() widget adapter}.
   */
  protected ADAPTER_VALUE getNullValue() {

    return null;
  }

  /**
   * This method converts the given <code>widgetValue</code> to the type used by the
   * {@link #getWidgetAdapter() widget adapter}. The default implementation simply performs a cast. You have
   * to override this method if {@literal <VALUE>} and {@literal <WIDGET_VALUE>} differ.
   * 
   * @param widgetValue is the value to convert. Will NOT be <code>null</code> (See {@link #getNullValue()}).
   * @return the converted value.
   */
  @SuppressWarnings("unchecked")
  protected ADAPTER_VALUE convertFromValue(VALUE widgetValue) {

    return (ADAPTER_VALUE) widgetValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValue() {

    return this.aspectValue.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueOrException() throws RuntimeException {

    return this.aspectValue.getValueOrException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getOriginalValue() {

    return this.aspectValue.getOriginalValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender == null) {
      this.focusEventSender = new FocusEventSender(this, getFactory());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setFocusEventSender(this, this.focusEventSender);
      }
    }
    this.focusEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    if (hasWidgetAdapter()) {
      // if (this.focusEventSender != null) {
      // this.focusEventSender.setProgrammatic();
      // }
      getWidgetAdapter().setFocused(focused);
    } else if (this.focusEventSender != null) {
      this.focusEventSender.onFocusChange(this, true, !focused);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender != null) {
      return this.focusEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    if (this.focusEventSender != null) {
      return this.focusEventSender.isFocused();
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isModifiedLocal() {

    return this.modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModified(boolean modified) {

    this.modified = modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValidationFailure() {

    return this.validationFailure;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidationFailure(String validationFailure) {

    this.validationFailure = validationFailure;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setValidationFailure(validationFailure);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    if (this.labelWidget != null) {
      this.labelWidget.setLabel(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiWidgetLabel getLabelWidget() {

    if (this.labelWidget == null) {
      this.labelWidget = createLabelWidget();
      if (this.label != null) {
        this.labelWidget.setLabel(this.label);
      }
    }
    return this.labelWidget;
  }

  /**
   * @return the label widget.
   */
  private UiWidgetLabel createLabelWidget() {

    // TODO: This actually needs to happen via the WidgetAdapter!
    return getFactory().create(UiWidgetLabel.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addValidator(ValueValidator<? super VALUE> validator) {

    this.aspectValue.addValidator(validator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeValidator(ValueValidator<? super VALUE> validator) {

    return this.aspectValue.removeValidator(validator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addValidatorMandatory() {

    this.aspectValue.addValidatorMandatory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMandatory() {

    return this.aspectValue.isMandatory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void validateLocal(ValidationState state) {

    super.validateLocal(state);
    this.aspectValue.validate(state);
  }

  /**
   * This inner class exists to avoid redundancy and due to lack of multi-inheritance.
   */
  private class ValueAspect extends UiAspectValue<VALUE> {

    /** @see #getValue() */
    private VALUE value;

    /**
     * The constructor.
     */
    public ValueAspect() {

      super();
    }

    /**
     * @return The value of {@link #getChangeEventSender()} so it can be accessed by outer class.
     */
    protected ChangeEventSender<VALUE> getSender() {

      return getChangeEventSender();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected VALUE doGetValue() throws RuntimeException {

      if (hasWidgetAdapter()) {
        ADAPTER_VALUE adapterValue = getWidgetAdapter().getValue();
        if (adapterValue == null) {
          return null;
        }
        return convertToValue(adapterValue);
      }
      return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doSetValue(VALUE newValue) {

      this.value = newValue;
      if (hasWidgetAdapter()) {
        ADAPTER_VALUE adapterValue;
        if (newValue == null) {
          adapterValue = getNullValue();
        } else {
          adapterValue = convertFromValue(newValue);
        }
        getWidgetAdapter().setValue(adapterValue);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ChangeEventSender<VALUE> createChangeEventSender() {

      ChangeEventSender<VALUE> changeEventSender = new ChangeEventSender<VALUE>(AbstractUiWidgetField.this,
          getFactory());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setChangeEventSender(AbstractUiWidgetField.this, changeEventSender);
      }
      return changeEventSender;
    }

    /**
     * @return the source for validation failures.
     */
    @Override
    protected String getSource() {

      String source = getLabel();
      if (source == null) {
        source = getId();
        // may still be null, but then no reasonable source is available...
      }
      return source;
    }

  }

}
