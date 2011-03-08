/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.gwt.view;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.base.gwt.AbstractUiFactoryGwt;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link UiElement} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiElement extends AbstractUiNodeGwt implements UiElement {

  /** @see #getTooltip() */
  private String tooltip;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiElement(AbstractUiFactoryGwt uiFactory) {

    super(uiFactory);
    this.tooltip = "";
  }

  /**
   * Sets the internal {@link #getTooltip() tooltip}. Requires according to sick
   * design of {@link Widget#setTitle(String)} in GWT + SmartGWT.
   * 
   * @param newTooltip is the {@link #getTooltip() tooltip}.
   */
  protected void doSetTooltip(String newTooltip) {

    this.tooltip = newTooltip;
  }

  /**
   * {@inheritDoc}
   */
  public void setTooltip(String tooltip) {

    this.tooltip = tooltip;
    getNativeUiObject().setTitle(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  public String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(int width, int height) {

    Widget widget = getNativeUiObject();
    widget.setWidth(Integer.toString(width));
    widget.setHeight(Integer.toString(height));
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public int getWidth() {

    // TODO
    return getNativeUiObject().getOffsetHeight();
  }

  /**
   * {@inheritDoc}
   */
  public int getHeight() {

    // TODO
    return getNativeUiObject().getOffsetWidth();
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredWidth() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getPreferredHeight() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public void dispose() {

    getNativeUiObject().removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDisposed() {

    // TODO Auto-generated method stub
    return false;
  }

}
