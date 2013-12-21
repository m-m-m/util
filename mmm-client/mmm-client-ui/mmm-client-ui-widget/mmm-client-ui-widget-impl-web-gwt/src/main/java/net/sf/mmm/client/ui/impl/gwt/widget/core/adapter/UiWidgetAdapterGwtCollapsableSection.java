/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterCollapsableSection;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.CollapseExpandButton;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.CollapseExpandButton.CollapseHandler;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
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

  /** The {@link CollapseExpandButton}. */
  private CollapseExpandButton collapseButton;

  /** The label for the {@link #setLabel(String) label-text}. */
  private InlineLabel label;

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
    // ClickHandler handler;
    // handler = new ClickHandler() {
    //
    // @Override
    // public void onClick(ClickEvent event) {
    //
    // setCollapsed(!UiWidgetAdapterGwtCollapsableSection.this.collapsed, false);
    // }
    // };
    // setClickEventSender(handler, this.toggleButton);
    CollapseHandler collapseHandler = new CollapseHandler() {

      @Override
      public void onCollapse(boolean collapse, boolean programmatic) {

        setCollapsed(collapse, false);
      }
    };
    this.collapseButton = new CollapseExpandButton(collapseHandler);
    // add collapse/expand button
    flowPanel.add(this.collapseButton);

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

    return this.collapseButton.isCollapsed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCollapsed(boolean collapsed) {

    if (collapsed == isCollapsed()) {
      return;
    }
    setCollapsed(collapsed, true);
  }

  /**
   * @see #setCollapsed(boolean)
   * 
   * @param collapsed - see {@link #setCollapsed(boolean)}.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()} .
   */
  public void setCollapsed(boolean collapsed, boolean programmatic) {

    EventType eventType;
    if (collapsed) {
      eventType = EventType.COLLAPSE;
    } else {
      eventType = EventType.EXPAND;
    }
    if (programmatic) {
      getEventAdapter().setProgrammaticEventType(eventType);
    }
    getEventAdapter().fireEvent(eventType);
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

    this.collapseButton.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return this.collapseButton;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return this.collapseButton;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return this.collapseButton;
  }

}
