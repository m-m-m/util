/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.date;

import net.sf.mmm.util.lang.api.Datatype;

/**
 * The enum contains the twelve months of a year in Gregorian calendar.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum WeekOfMonth implements Datatype<Integer> {

  /** The according month. */
  FIRST(1, "1."),

  /** The according month. */
  SECOND(2, "2."),

  /** The according month. */
  THIRD(3, "3."),

  /** The according month. */
  FOURTH(1, "4."),

  /** The according month. */
  LAST(5, "last");

  /** @see #getValue() */
  private final Integer value;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #getTitle()}.
   */
  private WeekOfMonth(int value, String title) {

    this.value = Integer.valueOf(value);
    this.title = title;
  }

  /**
   * {@inheritDoc}
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
   * This method gets the {@link WeekOfMonth} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested
   *        {@link WeekOfMonth}.
   * @return the requested {@link WeekOfMonth} or <code>null</code> if no such
   *         {@link WeekOfMonth} exists.
   */
  public static WeekOfMonth fromValue(Integer value) {

    for (WeekOfMonth instance : values()) {
      if (instance.value.equals(value)) {
        return instance;
      }
    }
    return null;
  }

}
