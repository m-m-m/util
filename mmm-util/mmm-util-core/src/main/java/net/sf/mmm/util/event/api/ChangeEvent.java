/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.api;

/**
 * A {@link ChangeEvent} is an {@link Event} that informs about something that
 * has been {@link ChangeType#ADD added}, {@link ChangeType#UPDATE
 * updated} or {@link ChangeType#REMOVE removed}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface ChangeEvent extends Event {

  /**
   * This method gets the type of this event.
   * 
   * @return the type of the change.
   */
  ChangeType getType();

}
