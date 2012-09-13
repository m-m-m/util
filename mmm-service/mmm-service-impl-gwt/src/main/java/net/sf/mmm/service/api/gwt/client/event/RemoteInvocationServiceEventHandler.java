/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.gwt.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * This is the {@link EventHandler} for {@link RemoteInvocationServiceEvent}s. It can be used for additional
 * logging, performance measurement, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationServiceEventHandler extends EventHandler {

  /**
   * This method is called whenever a {@link RemoteInvocationServiceEvent} is send.
   * 
   * @see RemoteInvocationServiceEvent#getState()
   * 
   * @param event is the {@link RemoteInvocationServiceEvent}.
   */
  void onServiceEvent(RemoteInvocationServiceEvent event);

}
