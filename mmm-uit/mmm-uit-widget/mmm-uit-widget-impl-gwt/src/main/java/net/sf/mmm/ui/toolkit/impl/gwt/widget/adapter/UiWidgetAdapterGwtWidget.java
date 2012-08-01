/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter} using GWT
 * based on {@link Widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAdapterGwtWidget<WIDGET extends Widget> extends UiWidgetAdapterGwt<WIDGET> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtWidget() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void removeFromParent() {

    getWidget().removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    super.dispose();
    getWidget().removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClickEventSender(final UiFeatureClick source, final UiHandlerEventClick clickEventSender) {

    ClickHandler handler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        clickEventSender.onClick(source, false);
      }
    };
    // HandlerRegistration registration =
    getWidget().addDomHandler(handler, ClickEvent.getType());
  }

}
