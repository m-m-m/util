/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetComposite;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetGwt<WIDGET extends UIObject> extends AbstractUiWidget<WIDGET> {

  /** The name of the <code>id</code> attribute. */
  private static final String HTML_ATTRIBUTE_ID = "id";

  /**
   * The constructor.
   */
  public UiWidgetGwt() {

    super();
  }

  /**
   * This method sets the {@link #getParent() parent} of the given <code>widget</code>.
   * 
   * @param widget is the widget where to set the {@link #getParent() parent}.
   * @param newParent is the new {@link #getParent() parent}.
   */
  protected void setParent(UiWidgetGwt<?> widget, UiWidgetComposite<?> newParent) {

    widget.setParent(newParent);
  }

  /**
   * This method invokes {@link #removeFromParent()} on the given <code>widget</code>.
   * 
   * @param widget is the widget that should be removed from its {@link #getParent() parent}.
   */
  protected void removeFromParent(UiWidgetGwt<?> widget) {

    widget.removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void removeFromParent() {

    super.removeFromParent();
    // TODO Widget vs. UIObject
    ((Widget) getWidget()).removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetId(String newId) {

    if (newId == null) {
      DOM.removeElementAttribute(getWidget().getElement(), HTML_ATTRIBUTE_ID);
    } else {
      DOM.setElementAttribute(getWidget().getElement(), HTML_ATTRIBUTE_ID, newId);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetVisible(boolean newVisible) {

    getWidget().setVisible(newVisible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetTooltip(String newTooltip) {

    getWidget().setTitle(newTooltip);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyles() {

    return getWidget().getStyleName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    getWidget().setStyleName(styles);
  }

}
