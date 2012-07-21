/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.UiHandlerObserver;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetComposite;
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
public abstract class UiWidgetGwt<WIDGET extends UIObject> extends AbstractUiWidget<WIDGET> implements
    AttributeWriteHandlerObserver {

  /** The name of the <code>id</code> attribute. */
  private static final String HTML_ATTRIBUTE_ID = "id";

  /** @see #getHandlerObserver() */
  private UiHandlerObserver handlerObserver;

  /** @see #getParent() */
  private UiWidgetCompositeGwt<?, ?> parent;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetGwt(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetComposite<?> getParent() {

    return this.parent;
  }

  /**
   * This method sets the {@link #getParent() parent} of the given <code>widget</code>.
   * 
   * @param widget is the widget where to set the {@link #getParent() parent}.
   * @param newParent is the new {@link #getParent() parent}.
   */
  protected void setParent(UiWidgetGwt<?> widget, UiWidgetCompositeGwt<?, ?> newParent) {

    widget.parent = newParent;
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

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiHandlerObserver getHandlerObserver() {

    return this.handlerObserver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setHandlerObserver(UiHandlerObserver handlerObserver) {

    this.handlerObserver = handlerObserver;
  }

}
