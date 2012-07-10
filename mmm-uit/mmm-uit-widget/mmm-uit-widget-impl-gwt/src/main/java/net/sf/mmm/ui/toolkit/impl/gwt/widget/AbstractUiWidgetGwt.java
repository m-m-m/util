/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.UiHandlerObserver;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getActiveWidget()}.
 */
public abstract class AbstractUiWidgetGwt<WIDGET extends UIObject> extends AbstractUiWidget<Widget> implements
    AttributeWriteHandlerObserver {

  /** The name of the <code>id</code> attribute. */
  private static final String HTML_ATTRIBUTE_ID = "id";

  /** @see #getHandlerObserver() */
  private UiHandlerObserver handlerObserver;

  /**
   * The constructor.
   * 
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   */
  public AbstractUiWidgetGwt(Widget toplevelWidget) {

    super(toplevelWidget);
  }

  /**
   * This method gets the active widget. If not overridden, this is the same as the
   * {@link #getToplevelWidget() toplevel widget}. However for a complex
   * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} it might be necessary to have this
   * {@link #getActiveWidget() active widget} embedded in a composite parent widget that is then returned by
   * {@link #getToplevelWidget()}.<br/>
   * Operations like {@link #setVisible(boolean)} are performed on {@link #getToplevelWidget()} while
   * {@link #setEnabled(boolean)} has to be performed on {@link #getActiveWidget()}.
   * 
   * @return the active widget.
   */
  protected WIDGET getActiveWidget() {

    return (WIDGET) getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetId(String newId) {

    if (newId == null) {
      DOM.removeElementAttribute(getToplevelWidget().getElement(), HTML_ATTRIBUTE_ID);
    } else {
      DOM.setElementAttribute(getToplevelWidget().getElement(), HTML_ATTRIBUTE_ID, newId);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetVisible(boolean newVisible) {

    getToplevelWidget().setVisible(newVisible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetTooltip(String newTooltip) {

    getToplevelWidget().setTitle(newTooltip);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStyles() {

    return getToplevelWidget().getStyleName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStyles(String styles) {

    getToplevelWidget().setStyleName(styles);
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
