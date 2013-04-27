/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.base.handler.event.CollapseEventSender;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetClickable;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterCollapsableSection;

/**
 * This is the abstract base implementation of {@link UiWidgetCollapsableSection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetCollapsableSection<ADAPTER extends UiWidgetAdapterCollapsableSection> extends
    AbstractUiWidgetClickable<ADAPTER> implements UiWidgetCollapsableSection {

  /** @see #isCollapsed() */
  private boolean collapsed;

  /** @see #addCollapseHandler(UiHandlerEventCollapse) */
  private CollapseEventSender collapseEventSender;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetCollapsableSection(UiContext context) {

    super(context);
    setPrimaryStyle(PRIMARY_STYLE);
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
    if (this.collapseEventSender != null) {
      adapter.setCollapseEventSender(this, this.collapseEventSender);
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

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setCollapsed(collapsed);
    } else {
      if (this.collapsed == collapsed) {
        return;
      }
      if (this.collapseEventSender != null) {
        this.collapseEventSender.onCollapseOrExpand(this, collapsed, true);
      }
    }
    this.collapsed = collapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addCollapseHandler(UiHandlerEventCollapse handler) {

    if (this.collapseEventSender == null) {
      this.collapseEventSender = new CollapseEventSender(this, getObserverSource());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setCollapseEventSender(this, this.collapseEventSender);
      }
    }
    this.collapseEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeCollapseHandle(UiHandlerEventCollapse handler) {

    if (this.collapseEventSender == null) {
      return false;
    }
    return this.collapseEventSender.removeHandler(handler);
  }

}
