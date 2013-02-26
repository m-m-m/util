/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.calendar;

import java.util.Date;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.date.api.Recurrence;

/**
 * This is the view interface for a {@link DataEntity} that represents an event. An event is something that
 * happens at a {@link #getInitialStartDate() specific point in time}, e.g. a birthday or an
 * {@link DataAppointment appointment}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEvent.CLASS_ID, title = DataEvent.CLASS_TITLE)
public interface DataEvent extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_EVENT;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataEvent";

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

  /**
   * This method gets the detailed description of this event.
   * 
   * @return the detailed description.
   */
  String getDescription();

  /**
   * This method gets the {@link Recurrence} of this event.
   * 
   * @return the {@link Recurrence}.
   */
  Recurrence getRecurrence();

  /**
   * This method sets the {@link #getInitialStartDate() initial start date}.
   * 
   * @param initialStartDate is the initial start date to set.
   */
  void setInitialStartDate(Date initialStartDate);

  /**
   * This method sets the {@link #getDescription() description}.
   * 
   * @param description is the description to set.
   */
  void setDescription(String description);

  /**
   * @param recurrence is the {@link #getRecurrence() recurrence} to set.
   */
  void setRecurrence(Recurrence recurrence);

}
