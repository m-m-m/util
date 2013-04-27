/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterCollapsableSection;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * This is the implementation of {@link UiWidgetAdapterCollapsableSection} using GWT based on
 * {@link FlowPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtCollapsableSection extends UiWidgetAdapterGwtWidgetActive<FlowPanel> implements
    UiWidgetAdapterCollapsableSection {

  /** The Label-Text to collapse. */
  private static final String LABEL_COLLAPSE = "\u2212";

  /** The Label-Text to collapse. */
  private static final String LABEL_EXPAND = "+";

  /** The hyperlink with the expand/collapse icon. */
  private Button toggleButton;

  /** The label for the {@link #setLabel(String) label-text}. */
  private InlineLabel label;

  /** @see #setEnabled(boolean) */
  private boolean collapsed;

  /** @see #setCollapseEventSender(UiFeatureCollapse, UiHandlerEventCollapse) */
  private UiHandlerEventCollapse collapseEventSender;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtCollapsableSection() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FlowPanel createToplevelWidget() {

    FlowPanel flowPanel = new FlowPanel();
    this.collapsed = false;
    this.toggleButton = new Button(LABEL_COLLAPSE);
    this.toggleButton.setStylePrimaryName("MiniButton");
    this.toggleButton.setTitle(getBundle().tooltipCollapse().getLocalizedMessage());
    ClickHandler handler;
    handler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        setCollapsed(!UiWidgetAdapterGwtCollapsableSection.this.collapsed, false);
      }
    };
    setClickEventSender(handler, this.toggleButton);
    // add collapse/expand button
    flowPanel.add(this.toggleButton);

    this.label = new InlineLabel();
    this.label.setStylePrimaryName("SectionLabel");
    flowPanel.add(this.label);
    return flowPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCollapsed() {

    return this.collapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapsed(boolean collapsed) {

    setCollapsed(collapsed, true);
  }

  /**
   * @see #setCollapsed(boolean)
   * 
   * @param newCollapsed - see {@link #setCollapsed(boolean)}.
   * @param programmatic - see
   *        {@link UiHandlerEventCollapse#onCollapseOrExpand(UiFeatureCollapse, boolean, boolean)}.
   */
  public void setCollapsed(boolean newCollapsed, boolean programmatic) {

    // if (this.collapsed == collapsed) {
    // return;
    // }

    if (newCollapsed) {
      this.toggleButton.setText(LABEL_EXPAND);
      this.toggleButton.setTitle(getBundle().tooltipExpand().getLocalizedMessage());
    } else {
      this.toggleButton.setText(LABEL_COLLAPSE);
      this.toggleButton.setTitle(getBundle().tooltipCollapse().getLocalizedMessage());
    }
    this.collapseEventSender.onCollapseOrExpand((UiWidgetCollapsableSection) getUiWidget(), newCollapsed, programmatic);
    this.collapsed = newCollapsed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapseEventSender(UiFeatureCollapse eventSource, UiHandlerEventCollapse eventSender) {

    this.collapseEventSender = eventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label.setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    this.toggleButton.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return this.toggleButton;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return this.toggleButton;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return this.toggleButton;
  }

}
