/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidget;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterTab;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetImageGwt;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

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
  private HTMLPanel contentPanel;

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
    this.contentPanel = new HTMLPanel("");
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
  public HTMLPanel getContentPanel() {

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
      this.image = AbstractUiWidget.getWidgetAdapter((UiWidgetImageGwt) image).getWidget();
      getWidget().insert(this.image, 0);
    }
  }
}
