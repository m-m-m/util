/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetSingleCompositeGwt;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetTab} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTabGwt extends UiWidgetSingleCompositeGwt<HorizontalPanel, UiWidgetRegular> implements UiWidgetTab {

  /** @see #getTitle() */
  private Label label;

  /** @see #getContentPanel() */
  private HTMLPanel contentPanel;

  /**
   * The constructor.
   */
  public UiWidgetTabGwt() {

    super();
    getWidget().setStyleName("gwt-TabLayoutPanelTab");
    this.label = new Label();
    getWidget().add(this.label);
    this.contentPanel = new HTMLPanel("");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HorizontalPanel createWidget() {

    return new HorizontalPanel();
  }

  /**
   * @return the widget for the {@link #getChild() child}.
   */
  public HTMLPanel getContentPanel() {

    return this.contentPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.label.getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String newTitle) {

    this.label.setText(newTitle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage image) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetChild(UiWidgetRegular oldChild, UiWidgetRegular newChild) {

    super.doSetChild(oldChild, newChild);
    if (oldChild != null) {
      this.contentPanel.remove(0);
    }
    @SuppressWarnings("unchecked")
    UiWidgetGwt<? extends Widget> childWidget = (UiWidgetGwt<? extends Widget>) newChild;
    this.contentPanel.add(childWidget.getWidget());
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetTab> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTab.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetTab create() {

      return new UiWidgetTabGwt();
    }

  }

}
