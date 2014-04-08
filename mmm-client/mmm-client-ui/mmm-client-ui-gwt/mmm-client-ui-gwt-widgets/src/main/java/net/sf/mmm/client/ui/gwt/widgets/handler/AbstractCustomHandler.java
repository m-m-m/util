/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.handler;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract interface for a custom handler such as {@link AbstractDragAndDropHandler}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AbstractCustomHandler {

  /**
   * This method registers this custom handler in the given {@link Widget}. It will also ensure that the
   * {@link Widget} is properly configured to send the required events (e.g. via
   * {@link Widget#sinkEvents(int) sends}).
   * 
   * @param widget is the {@link Widget} where to register this handler.
   * @param collector is the {@link HandlerRegistrationCollector} where to collect the
   *        {@link com.google.gwt.event.shared.HandlerRegistration}(s) of the registered handlers. May be
   *        <code>null</code> to ignore all registrations.
   */
  void register(Widget widget, HandlerRegistrationCollector collector);

}
