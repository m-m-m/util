/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterCheckboxField;
import net.sf.mmm.client.ui.gwt.widgets.CheckBoxWithChangeHandlers;

import com.google.gwt.user.client.TakesValue;

/**
 * This is the implementation of {@link UiWidgetAdapterCheckboxField} using GWT based on
 * {@link CheckBoxWithChangeHandlers}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtCheckboxField extends
    UiWidgetAdapterGwtFieldFocusWidget<CheckBoxWithChangeHandlers, Boolean, Boolean> implements
    UiWidgetAdapterCheckboxField {

  /** @see #getWidgetAsTakesValueString() */
  private ValueAdapter valueAdapter;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtCheckboxField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CheckBoxWithChangeHandlers createActiveWidget() {

    return new CheckBoxWithChangeHandlers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    getActiveWidget().setText(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<String> getWidgetAsTakesValueString() {

    if (this.valueAdapter == null) {
      this.valueAdapter = new ValueAdapter();
    }
    return this.valueAdapter;
  }

  /**
   * Adapter from the active widget to {@link TakesValue} {@literal <String>}.
   */
  protected class ValueAdapter implements TakesValue<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {

      return getActiveWidget().getValue().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value) {

      Boolean booleanValue = Boolean.valueOf(value);
      getActiveWidget().setValue(booleanValue);
    }

  }

}
