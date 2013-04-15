/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterSection;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;

import com.google.gwt.user.client.ui.Label;

/**
 * This is the implementation of {@link UiWidgetAdapterSection} using GWT based on {@link Label}. We simply
 * and implicitly change the primary style to make it a section instead of a label.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtSection extends UiWidgetAdapterGwtWidget<Label> implements UiWidgetAdapterSection {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtSection() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Label createToplevelWidget() {

    return new Label();
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
  public void setEnabled(boolean enabled) {

    // nothing to do
  }

}
