/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.date;

import java.util.Calendar;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * The enum contains the seven days of a week in Gregorian calendar.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Weekday implements Datatype<String> {

  /** The according weekday. */
  MONDAY("Mo", "Monday", Calendar.MONDAY),

  /** The according weekday. */
  TUESDAY("Tu", "Tuesday", Calendar.TUESDAY),

  /** The according weekday. */
  WEDNESDAY("We", "Wednesday", Calendar.WEDNESDAY),

  /** The according weekday. */
  THURSDAY("Th", "Thursday", Calendar.THURSDAY),

  /** The according weekday. */
  FRIDAY("Fr", "Friday", Calendar.FRIDAY),

  /** The according weekday. */
  SATURDAY("Sa", "Saturday", Calendar.SATURDAY),

  /** The according weekday. */
  SUNDAY("Su", "Sunday", Calendar.SUNDAY);

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
  private Weekday(String value, String title, int calendarId) {

    this.value = value;
    this.title = title;
    this.calendarId = calendarId;
  }

  /**
   * This method gets the next {@link Weekday} for this {@link Weekday}.
   * 
   * @return the {@link Weekday} one day after this {@link Weekday}.
   */
  public Weekday getNext() {

    switch (this) {
      case MONDAY:
        return TUESDAY;
      case TUESDAY:
        return WEDNESDAY;
      case WEDNESDAY:
        return THURSDAY;
      case THURSDAY:
        return FRIDAY;
      case FRIDAY:
        return SATURDAY;
      case SATURDAY:
        return SUNDAY;
      case SUNDAY:
        return MONDAY;
      default :
        throw new IllegalCaseException(Weekday.class, this);
    }
  }

  /**
   * This method gets the previous {@link Weekday} for this {@link Weekday}.
   * 
   * @return the {@link Weekday} one day before this {@link Weekday}.
   */
  public Weekday getPrevious() {

    switch (this) {
      case MONDAY:
        return SUNDAY;
      case TUESDAY:
        return MONDAY;
      case WEDNESDAY:
        return TUESDAY;
      case THURSDAY:
        return WEDNESDAY;
      case FRIDAY:
        return THURSDAY;
      case SATURDAY:
        return FRIDAY;
      case SUNDAY:
        return SATURDAY;
      default :
        throw new IllegalCaseException(Weekday.class, this);
    }
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
   * This method {@link Calendar#set(int, int) sets} this {@link Weekday} in the
   * given <code>calendar</code>.
   * 
   * @param calendar is the {@link Calendar}.
   */
  public void setWeekday(Calendar calendar) {

    NlsNullPointerException.checkNotNull(Calendar.class, calendar);
    calendar.set(Calendar.DAY_OF_WEEK, getCalendarId());
  }

  /**
   * This method gets the ID constant of {@link java.util.Calendar} representing
   * this {@link Weekday}. This is the Christian representation where
   * {@link #SUNDAY} is the first day of the week represented with 1. The enum
   * constants of {@link Weekday} are ordered such that {@link #MONDAY} comes
   * first, so you can use {@link #ordinal()} for other representation. Please
   * note that {@link #ordinal()} is <code>0</code> based so you might want to
   * increment the result.
   * 
   * @return the calendar ID.
   */
  public int getCalendarId() {

    return this.calendarId;
  }

  /**
   * This method returns the {@link Weekday} resulting if the given number of
   * <code>days</code> are elapsed after the current {@link Weekday} (this). The
   * {@link Weekday} will wrap so {@link #add(int) add(7)} will return the
   * {@link Weekday} itself (this) just like {@link #add(int) add(0)} or e.g.
   * {@link #add(int) add(-14)}.
   * 
   * @param days are the number of days to add. May be negative to subtract or
   *        zero (<code>0</code>) for no change.
   * @return the resulting {@link Weekday}.
   */
  public Weekday add(int days) {

    int sum = (ordinal() + days) % 7;
    for (Weekday weekday : values()) {
      if (weekday.ordinal() == sum) {
        return weekday;
      }
    }
    throw new IllegalCaseException(this.title + "+" + days);
  }

  /**
   * This method gets the {@link Weekday} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested
   *        {@link Weekday}.
   * @return the requested {@link Weekday} or <code>null</code> if no such
   *         {@link Weekday} exists.
   */
  public static Weekday fromValue(String value) {

    for (Weekday instance : values()) {
      if (instance.value.equals(value)) {
        return instance;
      }
    }
    return null;
  }

  /**
   * This method gets the {@link Weekday} from the given <code>calendar</code>.
   * 
   * @param calendar is the {@link Calendar}.
   * @return the {@link Weekday} representing the {@link Calendar#DAY_OF_WEEK
   *         weekday} of the given {@link Calendar}.
   */
  public static Weekday fromCalendar(Calendar calendar) {

    NlsNullPointerException.checkNotNull(Calendar.class, calendar);
    int result = calendar.get(Calendar.DAY_OF_WEEK);
    for (Weekday weekday : values()) {
      if (weekday.getCalendarId() == result) {
        return weekday;
      }
    }
    throw new IllegalCaseException(Integer.toString(result));
  }

  /**
   * This method gets the {@link Weekday} from the given <code>calendar</code>.
   * 
   * @param calendarId is the {@link #getCalendarId() calendar ID}.
   * @return the {@link Weekday} representing the {@link #getCalendarId()
   *         calendar ID} or <code>null</code> if no such {@link Weekday} exists
   *         (illegal <code>calendarId</code>).
   */
  public static Weekday fromCalendarId(int calendarId) {

    for (Weekday weekday : values()) {
      if (weekday.getCalendarId() == calendarId) {
        return weekday;
      }
    }
    return null;
  }

}
