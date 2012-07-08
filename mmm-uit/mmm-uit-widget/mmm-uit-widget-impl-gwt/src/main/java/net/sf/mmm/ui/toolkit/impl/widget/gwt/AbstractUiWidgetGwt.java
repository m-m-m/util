/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.widget.gwt;

import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class AbstractUiWidgetGwt<WIDGET extends UIObject> extends AbstractUiWidget<WIDGET> {

  /** The name of the <code>id</code> attribute. */
  private static final String HTML_ATTRIBUTE_ID = "id";

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public AbstractUiWidgetGwt(WIDGET widget) {

    super(widget);
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
