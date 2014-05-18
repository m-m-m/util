/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterSplitPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtDynamicComposite;

import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * This is the abstract implementation of {@link UiWidgetAdapterSplitPanel} using GWT based on
 * {@link SplitLayoutPanel}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO: SplitLayoutPanel design is sick. Rewrite own widget...
public abstract class UiWidgetAdapterGwtSplitPanel extends
    UiWidgetAdapterGwtDynamicComposite<SplitLayoutPanel, UiWidgetRegular> implements UiWidgetAdapterSplitPanel {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtSplitPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected SplitLayoutPanel createToplevelWidget() {

    SplitLayoutPanel splitPanel = new SplitLayoutPanel();
    return splitPanel;
  }

}
