/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterButtonPanel;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.ButtonPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterButtonPanel} using GWT based on
 * {@link ButtonPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtButtonPanel extends UiWidgetAdapterGwtPanel<ButtonPanel> implements
    UiWidgetAdapterButtonPanel {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtButtonPanel() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   */
  public UiWidgetAdapterGwtButtonPanel(ButtonPanel toplevelWidget) {

    super(toplevelWidget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ButtonPanel createToplevelWidget() {

    return new ButtonPanel();
  }

}
