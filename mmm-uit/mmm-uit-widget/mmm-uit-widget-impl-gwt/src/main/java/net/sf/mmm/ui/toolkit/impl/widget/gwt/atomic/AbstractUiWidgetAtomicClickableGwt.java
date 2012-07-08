/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.widget.gwt.atomic;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.UiHandlerClick;
import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetAtomicClickable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusWidget;

/**
 * This is the implementation of {@link UiWidgetAtomicClickable} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class AbstractUiWidgetAtomicClickableGwt<WIDGET extends FocusWidget> extends
    AbstractUiWidgetAtomicGwt<WIDGET> implements UiWidgetAtomicClickable {

  /** @see #addClickHandler(UiHandlerClick) */
  private List<UiHandlerClick> clickHandlers;

  /** @see #getHandlerObserver() */
  private UiFeatureHandlerObserver handlerObserver;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public AbstractUiWidgetAtomicClickableGwt(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    getWidget().setEnabled(newEnabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addClickHandler(UiHandlerClick handler) {

    if (this.clickHandlers == null) {
      this.clickHandlers = new LinkedList<UiHandlerClick>();
      getWidget().addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {

          doClick(false);
        }
      });
    }
    this.clickHandlers.add(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeClickHandler(UiHandlerClick handler) {

    if (this.clickHandlers != null) {
      return this.clickHandlers.remove(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void click() {

    doClick(true);
  }

  /**
   * @see #click()
   * 
   * @param programmatic - see {@link UiHandlerClick#onClick(boolean)}.
   */
  protected void doClick(boolean programmatic) {

    if (this.handlerObserver != null) {
      this.handlerObserver.beforeHandler(UiHandlerClick.class);
    }
    if (this.clickHandlers != null) {
      for (UiHandlerClick handler : this.clickHandlers) {
        handler.onClick(programmatic);
      }
    }
    if (this.handlerObserver != null) {
      this.handlerObserver.afterHandler(UiHandlerClick.class);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureHandlerObserver getHandlerObserver() {

    return this.handlerObserver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHandlerObserver(UiFeatureHandlerObserver handlerObserver) {

    this.handlerObserver = handlerObserver;
  }

}
