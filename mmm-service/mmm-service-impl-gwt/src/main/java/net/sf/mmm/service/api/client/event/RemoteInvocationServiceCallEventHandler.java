/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * This is the {@link EventHandler} for {@link RemoteInvocationServiceEvent}s. It can be used for additional
 * logging, performance measurement, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceCallEventHandler extends EventHandler {

  /**
   * This method is called whenever a {@link RemoteInvocationServiceCallEvent} is send.
   * 
   * @see RemoteInvocationServiceCallEvent#getState()
   * 
   * @param event is the {@link RemoteInvocationServiceCallEvent}.
   */
  void onServiceEvent(RemoteInvocationServiceCallEvent event);

}
