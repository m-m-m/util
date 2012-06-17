/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import net.sf.mmm.util.datatype.api.date.DateUnit;
import net.sf.mmm.util.datatype.api.date.WeekOfMonth;
import net.sf.mmm.util.datatype.api.date.WeekdaySet;
import net.sf.mmm.util.date.api.Recurrence;

/**
 * This is the implementation of {@link Recurrence} as simple Java Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RecurrenceBean implements Recurrence {

  /** @see #getUnit() */
  private DateUnit unit;

  /** @see #getStep() */
  private int step;

  /** @see #getWeekdays() */
  private WeekdaySet weekdays;

  /** @see #getWeekOfMonth() */
  private WeekOfMonth weekOfMonth;

  /**
   * The constructor.
   */
  public RecurrenceBean() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param unit - see {@link #getUnit()}.
   * @param step - see {@link #getStep()}.
   * @param weekdays - see {@link #getWeekdays()}.
   * @param weekOfMonth - see {@link #getWeekOfMonth()}.
   */
  public RecurrenceBean(DateUnit unit, int step, WeekdaySet weekdays, WeekOfMonth weekOfMonth) {

    super();
    this.unit = unit;
    this.step = step;
    this.weekdays = weekdays;
    this.weekOfMonth = weekOfMonth;
  }

  /**
   * {@inheritDoc}
   */
  public DateUnit getUnit() {

    return this.unit;
  }

  /**
   * @param unit is the {@link #getUnit() unit} to set.
   */
  public void setUnit(DateUnit unit) {

    this.unit = unit;
  }

  /**
   * {@inheritDoc}
   */
  public int getStep() {

    return this.step;
  }

  /**
   * @param step is the {@link #getStep() step} to set.
   */
  public void setStep(int step) {

    this.step = step;
  }

  /**
   * {@inheritDoc}
   */
  public WeekdaySet getWeekdays() {

    return this.weekdays;
  }

  /**
   * @param weekdays are the {@link #getWeekdays() weekdays} to set.
   */
  public void setWeekdays(WeekdaySet weekdays) {

    this.weekdays = weekdays;
  }

  /**
   * {@inheritDoc}
   */
  public WeekOfMonth getWeekOfMonth() {

    return this.weekOfMonth;
  }

  /**
   * @param weekOfMonth is the {@link #getWeekOfMonth() weekOfMonth} to set.
   */
  public void setWeekOfMonth(WeekOfMonth weekOfMonth) {

    this.weekOfMonth = weekOfMonth;
  }

}
