/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import net.sf.mmm.util.date.api.DateUnit;
import net.sf.mmm.util.date.api.Recurrence;
import net.sf.mmm.util.date.api.WeekOfMonth;
import net.sf.mmm.util.date.api.WeekdaySet;

/**
 * This is the implementation of {@link Recurrence} as simple Java Bean.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RecurrenceBean implements Recurrence {

  private DateUnit unit;

  private int step;

  private WeekdaySet weekdays;

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

  @Override
  public DateUnit getUnit() {

    return this.unit;
  }

  /**
   * @param unit is the {@link #getUnit() unit} to set.
   */
  public void setUnit(DateUnit unit) {

    this.unit = unit;
  }

  @Override
  public int getStep() {

    return this.step;
  }

  /**
   * @param step is the {@link #getStep() step} to set.
   */
  public void setStep(int step) {

    this.step = step;
  }

  @Override
  public WeekdaySet getWeekdays() {

    return this.weekdays;
  }

  /**
   * @param weekdays are the {@link #getWeekdays() weekdays} to set.
   */
  public void setWeekdays(WeekdaySet weekdays) {

    this.weekdays = weekdays;
  }

  @Override
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
