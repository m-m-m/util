/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.gwt.view;

import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.base.gwt.AbstractUiFactoryGwt;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link UiElement} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiElementGwt extends AbstractUiNodeGwt implements UiElement {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public AbstractUiElementGwt(AbstractUiFactoryGwt uiFactory) {

    super(uiFactory);
  }

  /**
   * Sets the internal {@link #getTooltip() tooltip}. Requires according to sick design of
   * {@link Widget#setTitle(String)} in GWT + SmartGWT.
   * 
   * @param newTooltip is the {@link #getTooltip() tooltip}.
   */
  protected void doSetTooltip(String newTooltip) {

    getNativeUiObject().setTitle(newTooltip);
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
  @Override
  public void setParent(UiNode newParent) {

    super.setParent(newParent);
    if (newParent == null) {
      getNativeUiObject().removeFromParent();
    }
  }

}
