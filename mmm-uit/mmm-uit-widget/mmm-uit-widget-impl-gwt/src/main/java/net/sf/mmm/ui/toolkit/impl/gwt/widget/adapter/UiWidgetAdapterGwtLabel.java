/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterWithLabel;

import com.google.gwt.user.client.ui.Label;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterWithLabel}
 * using GWT based on {@link Label}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtLabel extends UiWidgetAdapterGwtWidget<Label> implements UiWidgetAdapterWithLabel<Label> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtLabel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Label createWidget() {

    return new Label();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getWidget().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // nothing to do
  }

}
