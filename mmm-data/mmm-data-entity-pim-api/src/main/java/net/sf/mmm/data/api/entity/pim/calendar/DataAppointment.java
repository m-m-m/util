/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.calendar;

import java.util.Date;

import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataAppointmentView appointment}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAppointmentView.CLASS_ID, title = DataAppointmentView.CLASS_TITLE)
public interface DataAppointment extends DataAppointmentView, DataEvent {

  /**
   * This method sets the {@link #getLocation() location}.
   * 
   * @param location is the location to set.
   */
  void setLocation(String location);

  /**
   * This method sets the {@link #getInitialEndDate() initial end date}.
   * 
   * @param initialEndDate is the initial end date to set.
   */
  void setInitialEndDate(Date initialEndDate);

}
