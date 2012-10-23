/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.busy;

import net.sf.mmm.util.concurrent.api.Stoppable;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This is the interface for a {@link BusyManager#start(String) busy session}. It represents a session during
 * which the application client is {@link BusyManager#isBusy() busy}. After the logic making the client busy
 * is performed this {@link BusySession} has to be {@link #stop() stopped}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface BusySession extends AttributeReadId<String>, Stoppable {

  /**
   * {@inheritDoc}
   * 
   * @see BusyManager#start(String)
   */
  String getId();

  /**
   * This method stops this busy session. It should be called exactly once. Additional invocations of this
   * method on the same {@link BusySession} may log a warning but have no further effect. If the top-level
   * {@link BusySession} is stopped, the application client changes from {@link BusyManager#isBusy() busy}
   * back to idle.
   */
  void stop();

  /**
   * @return <code>true</code> if this {@link BusySession} has already been {@link #stop() stopped}.
   */
  boolean isStopped();

}
