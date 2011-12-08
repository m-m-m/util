/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.calendar;

import java.util.Date;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataEventView event}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataEntityView.CLASS_ID, title = DataEventView.CLASS_TITLE)
public interface DataEvent extends DataEventView, DataEntity {

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

}
