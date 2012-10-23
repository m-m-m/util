/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.busy;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a component that manages the {@link #isBusy() busy indication} of a client
 * application. If an asynchronous client is {@link #isBusy() busy} for a while this should be indicated to
 * the user. This is what this component is all about.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface BusyManager {

  /**
   * This method determines if the application client is currently <em>busy</em>. Busy means the client is
   * waiting for a remote invocation service result or performing client-side longer lasting logic (sorting
   * long lists, complex rendering, etc.). The logic what happens is abstracted by the interface. Typically
   * the user gets visual feedback (e.g. an animated wait image or progress-bar) and can not interact with the
   * client (e.g. press additional buttons).<br/>
   * If the client is NOT busy, it is considered as <em>idle</em> and the user can interact normally with the
   * client.
   * 
   * @return <code>true</code> if busy, <code>false</code> otherwise.
   */
  boolean isBusy();

  /**
   * This method starts a {@link BusySession} that has to be terminated by {@link BusySession#stop()}. If the
   * application client is idle (NOT {@link #isBusy() busy}), it will switch to {@link #isBusy() busy}.
   * Otherwise this is considered as a nested {@link BusySession}. Only after the initial {@link BusySession}
   * have been {@link BusySession#stop() stopped}, the application will switch from {@link #isBusy() busy}
   * back to idle mode.
   * 
   * @param id is an {@link BusySession#getId() identifier} for the source or the reason for the client being
   *        busy.
   * @return the {@link BusySession} used to {@link BusySession#stop() stop} the session.
   */
  BusySession start(String id);

}
