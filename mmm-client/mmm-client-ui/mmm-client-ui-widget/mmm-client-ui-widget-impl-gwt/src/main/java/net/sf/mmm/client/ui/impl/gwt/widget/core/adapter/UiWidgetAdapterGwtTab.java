/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetReal;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterTab;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetImageGwt;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of {@link UiWidgetAdapterTab} using GWT based on {@link HorizontalPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTab extends UiWidgetAdapterGwtWidget<HorizontalPanel> implements
    UiWidgetAdapterTab<HorizontalPanel> {

  /** @see #setLabel(String) */
  private Label label;

  /** @see #getContentPanel() */
  private VerticalPanel contentPanel;

  /** @see #setImage(UiWidgetImage) */
  private Image image;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTab() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HorizontalPanel createWidget() {

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.setStyleName("gwt-TabLayoutPanelTab");
    this.label = new Label();
    horizontalPanel.add(this.label);
    this.contentPanel = new VerticalPanel();
    this.contentPanel.setWidth("100%");
    this.contentPanel.setHeight("100%");
    return horizontalPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label.setText(label);
  }

  /**
   * @return the contentPanel where the {@link #setChild(UiWidgetRegular) child} is attached.
   */
  public VerticalPanel getContentPanel() {

    return this.contentPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(UiWidgetRegular child) {

    // if (this.contentPanel.getWidgetCount() > 0) {
    // this.contentPanel.remove(0);
    // }
    this.contentPanel.add(getWidget(child));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage image) {

    // if (this.image != null) {
    // getWidget().remove(this.image);
    // }
    if (image != null) {
      this.image = AbstractUiWidgetReal.getWidgetAdapter((UiWidgetImageGwt) image).getWidget();
      getWidget().insert(this.image, 0);
    }
  }
}
