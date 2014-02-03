/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterSection;
import net.sf.mmm.client.ui.gwt.widgets.Section;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;

/**
 * This is the implementation of {@link UiWidgetAdapterSection} using GWT based on {@link Section}. The
 * primary style and according CSS makes it a section instead of a label.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtSection extends UiWidgetAdapterGwtWidget<Section> implements UiWidgetAdapterSection {

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
  protected Section createToplevelWidget() {

    Section result = new Section();
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getToplevelWidget().getLabel().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // nothing to do
  }

}
