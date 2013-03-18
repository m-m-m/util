/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter;

import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuItem;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * This is the implementation of {@link UiWidgetAdapterMenuItem} using GWT based on {@link MenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMenuItem extends UiWidgetAdapterGwtMenuItemBase implements UiWidgetAdapterMenuItem,
    Command {

  /** @see #setClickEventSender(UiFeatureClick, UiHandlerEventClick) */
  private UiFeatureClick source;

  /** @see #setClickEventSender(UiFeatureClick, UiHandlerEventClick) */
  private UiHandlerEventClick clickEventSender;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMenuItem() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MenuItem createToplevelWidget() {

    return new MenuItem("", this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute() {

    if (this.clickEventSender != null) {
      this.clickEventSender.onClick(this.source, false);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClickEventSender(UiFeatureClick eventSource, UiHandlerEventClick eventSender) {

    this.source = eventSource;
    this.clickEventSender = eventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    Element element = getToplevelWidget().getElement();
    if (accessKey == ACCESS_KEY_NONE) {
      element.removeAttribute(HTML_ATTRIBUTE_ACCESS_KEY);
    } else {
      element.setAttribute(HTML_ATTRIBUTE_ACCESS_KEY, String.valueOf(accessKey));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocusEventSender(UiFeatureFocus focusSource, UiHandlerEventFocus focusSender) {

    // Not supported by GWT...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    // Not supported by GWT...
  }
}
