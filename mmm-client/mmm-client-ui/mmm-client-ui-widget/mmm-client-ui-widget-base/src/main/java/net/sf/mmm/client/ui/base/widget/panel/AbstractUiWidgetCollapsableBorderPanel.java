/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventCollapse;
import net.sf.mmm.client.ui.api.event.UiEventExpand;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetCollapsableBorderPanel;
import net.sf.mmm.client.ui.base.dynamic.VisibilityFlagModifier;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterCollapsableBorderPanel;

/**
 * This is the abstract base implementation of {@link UiWidgetCollapsableBorderPanel}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetCollapsableBorderPanel<ADAPTER extends UiWidgetAdapterCollapsableBorderPanel>
    extends AbstractUiWidgetAbstractBorderPanel<ADAPTER> implements UiWidgetCollapsableBorderPanel {

  /** @see #isCollapsed() */
  private boolean collapsed;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetCollapsableBorderPanel(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.collapsed = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.collapsed) {
      adapter.setCollapsed(this.collapsed);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCollapsed() {

    if (hasWidgetAdapter()) {
      return getWidgetAdapter().isCollapsed();
    }
    return this.collapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapsed(boolean collapsed) {

    setCollapsed(collapsed, true, false);
  }

  /**
   * @see #setCollapsed(boolean)
   * 
   * @param collapsedFlag - see {@link #setCollapsed(boolean)}.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   * @param fromAdapter - <code>true</code> if this method is called from the {@link #getWidgetAdapter()
   *        widget adapter}, <code>false</code> if called from this
   *        {@link net.sf.mmm.client.ui.api.widget.UiWidget}.
   */
  public void setCollapsed(boolean collapsedFlag, boolean programmatic, boolean fromAdapter) {

    if ((collapsedFlag == isCollapsed()) && !fromAdapter) {
      return;
    }
    UiEvent event;
    if (collapsedFlag) {
      event = new UiEventCollapse(this, programmatic);
    } else {
      event = new UiEventExpand(this, programmatic);
    }
    this.collapsed = collapsedFlag;
    fireEvent(event);
    if (hasWidgetAdapter() && !fromAdapter) {
      getWidgetAdapter().setCollapsed(collapsedFlag);
    }
    UiWidgetRegular child = getChild();
    if (child != null) {
      boolean visible = !collapsedFlag;
      child.getVisibleFlag().setFlag(visible, VisibilityFlagModifier.MODIFIER_COLLAPSE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCollapseHandler(UiHandlerEventCollapse handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeCollapseHandle(UiHandlerEventCollapse handler) {

    return removeEventHandler(handler);
  }

}
