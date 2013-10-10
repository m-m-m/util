/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterCheckboxField;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.CheckBoxWithChangeHandlers;

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

}
