/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Date;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * The enum contains the twelve months of a year in Gregorian calendar.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Month implements SimpleDatatype<Integer> {

  /** The according month. */
  JANUARY(1, "January"),

  /** The according month. */
  FEBRUARY(2, "February"),

  /** The according month. */
  MARCH(3, "March"),

  /** The according month. */
  APRIL(4, "April"),

  /** The according month. */
  MAY(5, "May"),

  /** The according month. */
  JUNE(6, "June"),

  /** The according month. */
  JULY(7, "July"),

  /** The according month. */
  AUGUST(8, "August"),

  /** The according month. */
  SEPTEMBER(9, "September"),

  /** The according month. */
  OCTOBER(10, "October"),

  /** The according month. */
  NOVEMBER(11, "November"),

  /** The according month. */
  DECEMBER(12, "December");

  private  final Integer value;

  private  final String title;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #toString()}.
   */
  private Month(int value, String title) {

    this.value = Integer.valueOf(value);
    this.title = title;
  }

  /**
   * {@inheritDoc}
   * 
   * <b>ATTENTION:</b><br>
   * Unlike java {@link java.util.Calendar} this will return the natural representation of a {@link Month} in
   * the range from {@code 1-12}.
   */
  @Override
  public Integer getValue() {

    return this.value;
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
   * This method gets the {@link DateUnit} for the given {@code value}.
   * 
   * @param value is the {@link #getValue() value} of the requested {@link DateUnit}.
   * @return the requested {@link DateUnit} or {@code null} if no such {@link DateUnit} exists.
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
   * This method gets the {@link Month} from the given {@code date}.
   * 
   * @param date is the {@link Date}.
   * @return the {@link Month} of the given {@link Date}.
   */
  @SuppressWarnings("deprecation")
  public static Month fromDate(Date date) {

    NlsNullPointerException.checkNotNull(Date.class, date);
    return fromValue(Integer.valueOf(date.getMonth() + 1));
  }

  @Override
  public String toString() {

    return this.title;
  }

}
