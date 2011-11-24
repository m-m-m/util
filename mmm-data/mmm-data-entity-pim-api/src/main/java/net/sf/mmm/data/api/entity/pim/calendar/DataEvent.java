/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.calendar;

import java.util.Date;

import net.sf.mmm.data.api.entity.DataEntity;

/**
 * This is the interface for a {@link DataEntity} that represents an event. An
 * event is something that happens at a {@link #getInitialStartDate() specific
 * point in time}, e.g. a birthday or an {@link DataAppointment appointment}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataEvent extends DataEntity {

  /**
   * This method gets the start date when this event will initially take place.
   * 
   * @see #getNextStartDate()
   * 
   * @return the initial start date.
   */
  Date getInitialStartDate();

  /**
   * This method gets the start date of the next occurrence of this event.
   * 
   * @return the next start date.
   */
  Date getNextStartDate();

  String getDescription();

}
