/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterCollapsableSection;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Label;

/**
 * This is the implementation of {@link UiWidgetAdapterCollapsableSection} using GWT based on
 * {@link FlowPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtCollapsableSection extends UiWidgetAdapterGwtWidgetActive<FlowPanel> implements
    UiWidgetAdapterCollapsableSection {

  /** The hyperlink with the expand/collapse icon. */
  private Anchor anchor;

  /** The label for the {@link #setLabel(String) label-text}. */
  private Label label;

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
    this.anchor = new Anchor();
    // add collapse/expand icon
    flowPanel.add(this.anchor);

    this.label = new Label();
    this.label.setStylePrimaryName("SectionLabel");
    flowPanel.add(this.label);
    return flowPanel;
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

    this.anchor.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return this.anchor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return this.anchor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return this.anchor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Element getImageParentElement() {

    return this.anchor.getElement();
  }

}
