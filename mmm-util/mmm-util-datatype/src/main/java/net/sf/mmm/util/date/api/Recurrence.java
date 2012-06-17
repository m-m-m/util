/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import net.sf.mmm.util.datatype.api.date.DateUnit;
import net.sf.mmm.util.datatype.api.date.WeekOfMonth;
import net.sf.mmm.util.datatype.api.date.WeekdaySet;

/**
 * This is the interface for a datatype representing the recurrence of an event
 * or appointment.<br/>
 * In addition to the method specification here are some examples to get started
 * easily:
 * <table border="1">
 * <tr>
 * <th>example</th>
 * <th>{@link #getUnit()}</th>
 * <th>{@link #getStep()}</th>
 * <th>{@link #getWeekdays()}</th>
 * <th>{@link #getWeekOfMonth()}</th>
 * </tr>
 * <tr>
 * <td>Every day</td>
 * <td>{@link DateUnit#DAY}</td>
 * <td>1</td>
 * <td>{@link WeekdaySet#ALL}</td>
 * <td>*</td>
 * </tr>
 * <tr>
 * <td>Every 3 days</td>
 * <td>{@link DateUnit#DAY}</td>
 * <td>3</td>
 * <td>{@link WeekdaySet#ALL}</td>
 * <td>*</td>
 * </tr>
 * <tr>
 * <td>Every working day (Mo-Fr)</td>
 * <td>{@link DateUnit#DAY}</td>
 * <td>1</td>
 * <td>{@link WeekdaySet#WORKDAYS}</td>
 * <td>*</td>
 * </tr>
 * <tr>
 * <td>Every 2 weeks</td>
 * <td>{@link DateUnit#WEEK}</td>
 * <td>2</td>
 * <td>{@link WeekdaySet#ALL}</td>
 * <td>*</td>
 * </tr>
 * <tr>
 * <td>Every month on fixed day</td>
 * <td>{@link DateUnit#MONTH}</td>
 * <td>1</td>
 * <td>{@link WeekdaySet#ALL}</td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>Every month on the first Monday</td>
 * <td>{@link DateUnit#MONTH}</td>
 * <td>1</td>
 * <td>&#123;{@link net.sf.mmm.util.datatype.api.date.Weekday#MONDAY}&#125;</td>
 * <td>{@link WeekOfMonth#FIRST}</td>
 * </tr>
 * <tr>
 * <td>Every month on the last working day (Mo-Fr)</td>
 * <td>{@link DateUnit#MONTH}</td>
 * <td>1</td>
 * <td>{@link WeekdaySet#WORKDAYS}</td>
 * <td>{@link WeekOfMonth#LAST}</td>
 * </tr>
 * <tr>
 * <td>Every year on fixed day</td>
 * <td>{@link DateUnit#YEAR}</td>
 * <td>1</td>
 * <td>{@link WeekdaySet#ALL}</td>
 * <td>null</td>
 * </tr>
 * <tr>
 * <td>Every two years on the second Wednesday of a fixed month</td>
 * <td>{@link DateUnit#YEAR}</td>
 * <td>2</td>
 * <td>&#123;{@link net.sf.mmm.util.datatype.api.date.Weekday#WEDNESDAY}&#125;</td>
 * <td>{@link WeekOfMonth#SECOND}</td>
 * </tr>
 * </table>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Recurrence {

  /**
   * This method gets the {@link DateUnit} of this {@link Recurrence}.
   * 
   * @see #getStep()
   * 
   * @return the {@link DateUnit}.
   */
  DateUnit getUnit();

  /**
   * This method gets the step of this {@link Recurrence}. The
   * {@link Recurrence} will happen every <code>{@link #getStep() step}</code>
   * {@link #getUnit() units} (e.g. every day or every 2 weeks). The returned
   * value has to be greater or equal to <code>1</code>.
   * 
   * @return the step.
   */
  int getStep();

  /**
   * This method gets the {@link WeekdaySet} containing the
   * {@link net.sf.mmm.util.datatype.api.date.Weekday}s of the recurrence. This
   * is used as a filter to match only for particular
   * {@link net.sf.mmm.util.datatype.api.date.Weekday}s. The default is
   * {@link WeekdaySet#ALL}. This method is ignored for {@link DateUnit#WEEK
   * weekly} {@link Recurrence} as it would NOT make any sense.<br/>
   * <b>ATTENTION:</b><br/>
   * If {@link #getUnit() unit} is {@link DateUnit#DAY daily} recurrences that
   * do not
   * {@link WeekdaySet#contains(net.sf.mmm.util.datatype.api.date.Weekday)
   * match} will be omitted, while otherwise the closest match will be used. If
   * it is {@link DateUnit#MONTH monthly} or {@link DateUnit#YEAR yearly} it
   * will recurre on the <em>first</em> match of the weekdays according to the
   * value of {@link #getWeekOfMonth()}.
   * 
   * @return the {@link WeekdaySet} or <code>null</code>.
   */
  WeekdaySet getWeekdays();

  /**
   * This method gets the {@link WeekOfMonth} of a {@link DateUnit#MONTH
   * monthly} or {@link DateUnit#YEAR yearly} recurrence. By default (if this
   * method returns <code>null</code> and {@link #getWeekdays()} returns
   * {@link WeekdaySet#ALL}) such recurrence always takes place on a fixed day
   * of the month based on the initial occurrence. However, to specify a
   * {@link DateUnit#MONTH monthly} or {@link DateUnit#YEAR yearly}
   * {@link Recurrence} taking place on a specific {@link #getWeekdays() day of
   * the week} this method can return a value identifying the week within the
   * month (e.g. "on <b>first</b> Friday every month", or
   * "on <b>last</b> weekday every month"). This method is ignored if this
   * {@link #getUnit() is} NOT a {@link DateUnit#MONTH monthly} or
   * {@link DateUnit#YEAR yearly} {@link Recurrence}.
   * 
   * @return the {@link WeekOfMonth} or <code>null</code>.
   */
  WeekOfMonth getWeekOfMonth();

}
