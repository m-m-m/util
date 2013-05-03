/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler;

import net.sf.mmm.client.ui.api.common.UiEvent;

/**
 * This is the interface for an observer, that gets notified {@link #beforeHandler(UiEvent) before} and
 * {@link #afterHandler(UiEvent) after} one or multiple {@link UiHandler}(s) get invoked.
 * 
 * @see net.sf.mmm.client.ui.api.attribute.AttributeWriteEventObserver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiEventObserver {

  /**
   * This method is called before the {@link UiHandler}(s) are invoked.
   * 
   * @param event is the event that is going to be send to all registered
   *        {@link net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent event listeners}.
   */
  void beforeHandler(UiEvent event);

  /**
   * This method is called after the {@link UiHandler}(s) are invoked.
   * 
   * @param event is the event that has been send to all registered
   *        {@link net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent event listeners}.
   */
  void afterHandler(UiEvent event);

}
