/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for a collection of utility functions that help with to deal with {@link Recurrence}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface RecurrenceUtil {

  /**
   * This method determines the next occurrence (as {@link Date}) after the given <code>date</code> based on
   * the given {@link Recurrence}.
   * 
   * @see #getNextDate(Calendar, Recurrence)
   * 
   * @param date is the {@link Date} of the current occurrence.
   * @param recurrence is the {@link Recurrence} specifying the rules how an event or appointment recurs.
   * @return the next recurrence after <code>date</code> based on <code>recurrence</code>.
   */
  Date getNextDate(Date date, Recurrence recurrence);

  /**
   * This method determines the next occurrence (as {@link Calendar}) after the given <code>calendar</code>
   * based on the given {@link Recurrence}.
   * 
   * @see #getNextDate(Date, Recurrence)
   * 
   * @param calendar is the {@link Calendar} of the current occurrence.
   * @param recurrence is the {@link Recurrence} specifying the rules how an event or appointment recurs.
   * @return the next recurrence after <code>calendar</code> based on <code>recurrence</code>.
   */
  Calendar getNextDate(Calendar calendar, Recurrence recurrence);

}
