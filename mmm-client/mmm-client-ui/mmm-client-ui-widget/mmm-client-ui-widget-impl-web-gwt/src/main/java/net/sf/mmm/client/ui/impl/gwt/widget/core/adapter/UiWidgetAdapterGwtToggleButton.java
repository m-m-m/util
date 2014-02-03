/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterToggleButton;
import net.sf.mmm.client.ui.gwt.widgets.SimpleToggleButton;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtClickable;

/**
 * This is the implementation of {@link UiWidgetAdapterToggleButton} using GWT based on
 * {@link SimpleToggleButton}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtToggleButton extends UiWidgetAdapterGwtClickable<SimpleToggleButton> implements
    UiWidgetAdapterToggleButton {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtToggleButton() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected SimpleToggleButton createToplevelWidget() {

    return new SimpleToggleButton();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getToplevelWidget().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Boolean value) {

    getToplevelWidget().setValue(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValue() {

    return getToplevelWidget().getValue();
  }

}
