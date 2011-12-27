/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.date;

import java.util.Calendar;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * The enum contains the twelve months of a year in Gregorian calendar.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Month implements Datatype<Integer> {

  /** The according month. */
  JANUARY(1, "January", Calendar.JANUARY),

  /** The according month. */
  FEBRUARY(2, "February", Calendar.FEBRUARY),

  /** The according month. */
  MARCH(3, "March", Calendar.MARCH),

  /** The according month. */
  APRIL(4, "April", Calendar.APRIL),

  /** The according month. */
  MAY(5, "May", Calendar.MAY),

  /** The according month. */
  JUNE(6, "June", Calendar.JUNE),

  /** The according month. */
  JULY(7, "July", Calendar.JULY),

  /** The according month. */
  AUGUST(8, "August", Calendar.AUGUST),

  /** The according month. */
  SEPTEMBER(9, "September", Calendar.SEPTEMBER),

  /** The according month. */
  OCTOBER(10, "October", Calendar.OCTOBER),

  /** The according month. */
  NOVEMBER(11, "November", Calendar.NOVEMBER),

  /** The according month. */
  DECEMBER(12, "December", Calendar.DECEMBER);

  /** @see #getValue() */
  private final Integer value;

  /** @see #getTitle() */
  private final String title;

  /** @see #getCalendarId() */
  private final int calendarId;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #getTitle()}.
   * @param calendarId - see {@link #getCalendarId()}.
   */
  private Month(int value, String title, int calendarId) {

    this.value = Integer.valueOf(value);
    this.title = title;
    this.calendarId = calendarId;
  }

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br/>
   * Unlike java {@link Calendar} this will return the natural representation of
   * a {@link Month} in the range from <code>1-12</code>.
   * 
   * @see #getCalendarId()
   */
  public Integer getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * This method {@link Calendar#set(int, int) sets} this {@link Month} in the
   * given <code>calendar</code>.
   * 
   * @param calendar is the {@link Calendar}.
   */
  public void setMonth(Calendar calendar) {

    NlsNullPointerException.checkNotNull(Calendar.class, calendar);
    calendar.set(Calendar.MONTH, getCalendarId());
  }

  /**
   * This method gets the ID constant of {@link java.util.Calendar} representing
   * this {@link Month}.
   * 
   * @return the calendar ID.
   */
  public int getCalendarId() {

    return this.calendarId;
  }

  /**
   * This method gets the maximum number of days in this {@link Month}.
   * 
   * @return the maximum number of days (29, 30, or 31).
   */
  public int getMaxDays() {

    switch (this) {
      case FEBRUARY:
        return 29;
      case APRIL:
      case JUNE:
      case SEPTEMBER:
      case NOVEMBER:
        return 30;
      default :
        return 31;
    }
  }

  /**
   * This method gets the minimum number of days in this {@link Month}.
   * 
   * @return the minimum number of days (28, 30, or 31).
   */
  public int getMinDays() {

    if (this == FEBRUARY) {
      return 28;
    } else {
      return getMaxDays();
    }
  }

  /**
   * This method gets the {@link DateUnit} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested
   *        {@link DateUnit}.
   * @return the requested {@link DateUnit} or <code>null</code> if no such
   *         {@link DateUnit} exists.
   */
  public static Month fromValue(Integer value) {

    for (Month instance : values()) {
      if (instance.value.equals(value)) {
        return instance;
      }
    }
    return null;
  }

  /**
   * This method gets the {@link Month} from the given <code>calendar</code>.
   * 
   * @param calendar is the {@link Calendar}.
   * @return the {@link Month} representing the {@link Calendar#MONTH month} of
   *         the given {@link Calendar}.
   */
  public static Month fromCalendar(Calendar calendar) {

    NlsNullPointerException.checkNotNull(Calendar.class, calendar);
    int result = calendar.get(Calendar.MONTH);
    for (Month month : values()) {
      if (month.getCalendarId() == result) {
        return month;
      }
    }
    throw new IllegalCaseException(Integer.toString(result));
  }

}
