/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter;

import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuItemClickable;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwt;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * This is the implementation of {@link UiWidgetAdapterMenuItemClickable} using GWT based on {@link MenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMenuItem extends UiWidgetAdapterGwtMenuItemBase implements
    UiWidgetAdapterMenuItemClickable, Command {

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

    EventAdapterGwt eventAdapter = getEventAdapter();
    if (eventAdapter != null) {
      eventAdapter.onClick(null);
    }
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
  public boolean setFocused() {

    // Not supported by GWT...
    return false;
  }
}
