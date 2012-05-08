/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.concurrent.api;

/**
 * This is the interface for some object that represents a task that can be {@link #stop() stopped}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Stoppable {

  /**
   * This method stops the task.
   */
  void stop();

}
