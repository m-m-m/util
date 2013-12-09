/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Calendar;

import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * The enum contains the units of a date. These are {@link #DAY}, {@link #WEEK}, {@link #MONTH}, and
 * {@link #YEAR}.
 * 
 * @see java.util.concurrent.TimeUnit
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum DateUnit implements SimpleDatatype<String> {

  /** The day of the month starting with 1. */
  DAY("D", "day", Calendar.DAY_OF_MONTH),

  /** The week (of the year) starting with 1. */
  WEEK("W", "week", Calendar.WEEK_OF_YEAR),

  /**
   * The month of the year starting with 1 (NOT 0 like {@link Calendar} does).
   * 
   * @see Month
   */
  MONTH("M", "month", Calendar.MONTH),

  /** The year. */
  YEAR("Y", "year", Calendar.YEAR);

  /** @see #getValue() */
  private final String value;

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
  private DateUnit(String value, String title, int calendarId) {

    this.value = value;
    this.title = title;
    this.calendarId = calendarId;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.title;
  }

  /**
   * This method delegates to {@link Calendar#get(int)}. However, it will return the month in a natural way
   * from 1 to 12. Java {@link Calendar} returns 0 for January instead of 1 and so forth what is the cause of
   * many mistakes.
   * 
   * @param calendar is the {@link Calendar}.
   * @return the requested calendar field for this {@link DateUnit}.
   */
  public int getCalendarField(Calendar calendar) {

    NlsNullPointerException.checkNotNull(Calendar.class, calendar);
    int result = calendar.get(getCalendarId());
    if (this == MONTH) {
      result++;
    }
    return result;
  }

  /**
   * This method delegates to {@link Calendar#set(int, int)}. However, it will treat the month in a natural
   * way from 1 to 12. Java {@link Calendar} expects 0 for January instead of 1 and so forth what is the cause
   * of many mistakes.
   * 
   * @param calendar is the {@link Calendar}.
   * @param unitValue is the value to set.
   */
  public void setCalendarField(Calendar calendar, int unitValue) {

    NlsNullPointerException.checkNotNull(Calendar.class, calendar);
    int normalizedValue = unitValue;
    if (this == MONTH) {
      normalizedValue--;
    }
    calendar.set(getCalendarId(), normalizedValue);
  }

  /**
   * This method gets the ID constant for {@link java.util.Calendar} operations such as
   * {@link java.util.Calendar#get(int)}.
   * 
   * @return the calendar ID.
   */
  public int getCalendarId() {

    return this.calendarId;
  }

  /**
   * This method gets the {@link DateUnit} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested {@link DateUnit}.
   * @return the requested {@link DateUnit} or <code>null</code> if no such {@link DateUnit} exists.
   */
  public static DateUnit fromValue(String value) {

    for (DateUnit instance : values()) {
      if (instance.value.equals(value)) {
        return instance;
      }
    }
    return null;
  }

}
