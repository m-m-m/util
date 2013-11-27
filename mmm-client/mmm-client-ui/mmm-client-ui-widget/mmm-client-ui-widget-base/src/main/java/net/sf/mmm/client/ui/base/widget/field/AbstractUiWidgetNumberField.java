/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRangeField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterNumberField;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.base.ValidationFailureImpl;
import net.sf.mmm.util.value.api.Range;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the abstract base implementation of {@link UiWidgetRangeField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetNumberField<ADAPTER extends UiWidgetAdapterNumberField<VALUE>, VALUE extends Number & Comparable<VALUE>>
    extends AbstractUiWidgetTextualInputField<ADAPTER, VALUE, VALUE> implements UiWidgetRangeField<VALUE> {

  /** @see #getMinimumValue() */
  private VALUE minimumValue;

  /** @see #getMaximumValue() */
  private VALUE maximumValue;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetNumberField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.minimumValue != null) {
      adapter.setMinimumValue(this.minimumValue);
    }
    if (this.maximumValue != null) {
      adapter.setMaximumValue(this.maximumValue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMinimumValue() {

    return this.minimumValue;
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimumValue(VALUE minimum) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMinimumValue(minimum);
    }
    this.minimumValue = minimum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMaximumValue() {

    return this.maximumValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(VALUE maximum) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMaximumValue(maximum);
    }
    this.maximumValue = maximum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doValidate(ValidationState state, VALUE value) {

    if ((value != null) && ((this.minimumValue != null) || (this.maximumValue != null))) {
      Range<VALUE> range = new Range<VALUE>(this.minimumValue, this.maximumValue);
      if (!range.isContained(value)) {
        String source = getSource();
        ValidationFailure failure = new ValidationFailureImpl(ValueOutOfRangeException.MESSAGE_CODE, source,
            ValueOutOfRangeException.createMessage(value, this.minimumValue, this.maximumValue, source));
        state.onFailure(failure);
      }
    }
    super.doValidate(state, value);
  }

}
