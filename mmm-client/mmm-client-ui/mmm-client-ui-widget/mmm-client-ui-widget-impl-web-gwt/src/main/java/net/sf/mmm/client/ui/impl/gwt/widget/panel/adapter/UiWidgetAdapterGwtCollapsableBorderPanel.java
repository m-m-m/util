/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterCollapsableBorderPanel;
import net.sf.mmm.client.ui.gwt.widgets.CollapseExpandButton;
import net.sf.mmm.client.ui.gwt.widgets.CollapseExpandButton.CollapseHandler;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetCollapsableBorderPanelGwt;

/**
 * This is the implementation of {@link UiWidgetAdapterCollapsableBorderPanel} using GWT based on
 * {@link net.sf.mmm.client.ui.gwt.widgets.BorderPanel} and {@link CollapseExpandButton}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtCollapsableBorderPanel extends UiWidgetAdapterGwtBorderPanel implements
    UiWidgetAdapterCollapsableBorderPanel, CollapseHandler {

  /** The {@link CollapseExpandButton} for user interaction. */
  private final CollapseExpandButton collapseExpandButton;

  /**
   * The constructor.
   * 
   */
  public UiWidgetAdapterGwtCollapsableBorderPanel() {

    super();
    this.collapseExpandButton = new CollapseExpandButton(this);
    getToplevelWidget().getLegend().insert(this.collapseExpandButton, 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    super.setEnabled(enabled);
    this.collapseExpandButton.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setFocused() {

    this.collapseExpandButton.setFocus(true);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCollapsed() {

    return this.collapseExpandButton.isCollapsed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapsed(boolean collapsed) {

    this.collapseExpandButton.setCollapsed(collapsed);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    this.collapseExpandButton.setAccessKey(accessKey);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCollapse(boolean collapse, boolean programmatic) {

    ((UiWidgetCollapsableBorderPanelGwt) getUiWidget()).setCollapsed(collapse, programmatic, true);
  }
}
