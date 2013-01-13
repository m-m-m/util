/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Date;

import net.sf.mmm.util.date.api.attribute.AttributeReadWeekday;

/**
 * This is the interface for an advanced {@link SimpleDate date}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AdvancedDate extends SimpleDate, AttributeReadWeekday, Comparable<SimpleDate> {

  /**
   * @return this object converted as {@link Date}.
   */
  Date asDate();

  /**
   * This method creates a new {@link AdvancedDate} representing this {@link AdvancedDate date} added by the
   * given {@link Duration}.
   * 
   * @param duration is the {@link Duration} to add.
   * @param absoluteMillis - <code>true</code> if the {@link Duration#getValue() absolute milliseconds} shall
   *        be added, <code>false</code> if the individual fields ({@link Duration#getDays() days},
   *        {@link Duration#getHours() hours}, etc.) should be {@link java.util.Calendar#add(int, int) added}.
   *        This may lead to different results due to daylight savings and other date effects.
   * @return the result of this {@link AdvancedDate} added by the given {@link Duration}.
   */
  AdvancedDate add(Duration duration, boolean absoluteMillis);

  /**
   * This method creates a new {@link AdvancedDate} representing this date subtracted by the given
   * {@link Duration}.
   * 
   * @param duration is the {@link Duration} to subtract.
   * @param absoluteMillis - <code>true</code> if the {@link Duration#getValue() absolute milliseconds} shall
   *        be added, <code>false</code> if the individual fields ({@link Duration#getDays() days},
   *        {@link Duration#getHours() hours}, etc.) should be {@link java.util.Calendar#add(int, int) added}.
   *        This may lead to different results due to daylight savings and other date effects.
   * @return the result of this {@link AdvancedDate} subtracted by the given {@link Duration}.
   */
  AdvancedDate subtract(Duration duration, boolean absoluteMillis);

  /**
   * This method calculates the {@link Duration} from this {@link AdvancedDate} to the given
   * <code>{@link AdvancedDate date}</code>.
   * 
   * @param date is the {@link AdvancedDate} to subtract.
   * @param absoluteMillis - <code>true</code> if the {@link Duration} shall represent the absolute difference
   *        in milliseconds, <code>false</code> if the individual fields ({@link Duration#getDays() days},
   *        {@link Duration#getHours() hours}, etc.) should be subtracted. This may lead to different results
   *        due to daylight savings and other date effects.
   * @return the {@link Duration} from this {@link AdvancedDate} to the given <code>date</code>.
   */
  Duration subtract(SimpleDate date, boolean absoluteMillis);

  /**
   * This method determines if this {@link AdvancedDate} is before the given <code>date</code>.
   * 
   * @param date is the {@link SimpleDate date} to compare.
   * @return <code>true</code> if <code>{@link #compareTo(SimpleDate) compareTo}(date)</code> is less than
   *         zero.
   */
  boolean isBefore(SimpleDate date);

  /**
   * This method determines if this {@link AdvancedDate} is after the given <code>date</code>.
   * 
   * @param date is the {@link SimpleDate date} to compare.
   * @return <code>true</code> if <code>{@link #compareTo(SimpleDate) compareTo}(date)</code> is greater than
   *         zero.
   */
  boolean isAfter(SimpleDate date);

}
