/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetAtomicClickable;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.ClickEventSenderGwt;

import com.google.gwt.user.client.ui.FocusWidget;

/**
 * This is the implementation of {@link UiWidgetAtomicClickable} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAtomicClickableGwt<WIDGET extends FocusWidget> extends UiWidgetAtomicGwt<WIDGET>
    implements UiWidgetAtomicClickable {

  /** @see #addClickHandler(UiHandlerEventClick) */
  private ClickEventSenderGwt clickEventSender;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetAtomicClickableGwt(WIDGET widget) {

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
  public void addClickHandler(UiHandlerEventClick handler) {

    if (this.clickEventSender == null) {
      this.clickEventSender = new ClickEventSenderGwt(this);
      getWidget().addClickHandler(this.clickEventSender);
    }
    this.clickEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeClickHandler(UiHandlerEventClick handler) {

    if (this.clickEventSender != null) {
      return this.clickEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void click() {

    if (this.clickEventSender != null) {
      this.clickEventSender.onClick(null, true);
    }
  }

}
