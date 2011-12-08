/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.calendar;

import java.util.Date;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;

/**
 * This is the view interface for a {@link DataEntityView} that represents an
 * event. An event is something that happens at a {@link #getInitialStartDate()
 * specific point in time}, e.g. a birthday or an {@link DataAppointmentView
 * appointment}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityView.CLASS_ID, title = DataEventView.CLASS_TITLE)
public interface DataEventView extends DataEntityView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_EVENT;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
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
   * TODO: the recurrence
   * 
   * @return
   */
  String getRecurrence();

}
