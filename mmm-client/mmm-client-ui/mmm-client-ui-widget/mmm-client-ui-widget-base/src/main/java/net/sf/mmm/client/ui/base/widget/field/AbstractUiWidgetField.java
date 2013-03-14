/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.handler.event.ChangeEventSender;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
import net.sf.mmm.client.ui.base.widget.core.AbstractUiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationStateMessageCollector;

/**
 * This is the abstract base implementation of {@link UiWidgetField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getWidgetAdapter() adapter} value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetField<ADAPTER extends UiWidgetAdapterField<VALUE, ADAPTER_VALUE>, VALUE, ADAPTER_VALUE>
    extends AbstractUiWidgetActive<ADAPTER, VALUE> implements UiWidgetField<VALUE> {

  /** @see #getValidationFailure() */
  private String validationFailure;

  /** @see #getFieldLabel() */
  private String fieldLabel;

  /** @see #getFieldLabelWidget() */
  private AbstractUiWidgetLabel<?> fieldLabelWidget;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetField(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    ChangeEventSender<VALUE> changeEventSender = getChangeEventSender();
    if (changeEventSender != null) {
      adapter.setChangeEventSender(this, changeEventSender);
    }
    VALUE value = getValue();
    if (value != null) {
      adapter.setValue(convertFromValue(value));
    }
    if (this.validationFailure != null) {
      adapter.setValidationFailure(this.validationFailure);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE doGetValue(VALUE template, ValidationState state) throws RuntimeException {

    // here we ignore template, as we assume that field widgets only use Datatypes for <VALUE>...
    VALUE value;
    if (hasWidgetAdapter()) {
      ADAPTER_VALUE adapterValue = getWidgetAdapter().getValue();
      if (adapterValue == null) {
        return null;
      }
      value = convertToValue(adapterValue);
    } else {
      value = getOriginalValue();
    }
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE newValue, boolean forUser) {

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
  public void clearMessagesLocal() {

    super.clearMessagesLocal();
    setValidationFailure("");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFieldLabel() {

    return this.fieldLabel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFieldLabel(String label) {

    this.fieldLabel = label;
    if (this.fieldLabelWidget != null) {
      this.fieldLabelWidget.setLabel(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiWidgetLabel getFieldLabelWidget() {

    if (this.fieldLabelWidget == null) {
      this.fieldLabelWidget = getWidgetAdapter().createLabel(getContext());
      this.fieldLabelWidget.setLabelledWidget(this);
      if (this.fieldLabel != null) {
        this.fieldLabelWidget.setLabel(this.fieldLabel);
      }
    }
    return this.fieldLabelWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    super.setId(newId);
    if (this.fieldLabelWidget == null) {
      this.fieldLabelWidget.setLabelledWidget(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSource() {

    if (this.fieldLabelWidget != null) {
      String label = this.fieldLabelWidget.getLabel();
      if (label != null) {
        return label;
      }
    }
    if (this.fieldLabel != null) {
      return this.fieldLabel;
    }
    return super.getSource();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ChangeEventSender<VALUE> createChangeEventSender() {

    ChangeEventSender<VALUE> changeEventSender = super.createChangeEventSender();
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setChangeEventSender(this, changeEventSender);
    }
    return changeEventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doValidate(ValidationState state, VALUE value) {

    ValidationStateMessageCollector messageCollector = new ValidationStateMessageCollector(state);
    super.doValidate(messageCollector, value);
    String failureMessages = messageCollector.getFailureMessages();
    setValidationFailure(failureMessages);
  }

}
