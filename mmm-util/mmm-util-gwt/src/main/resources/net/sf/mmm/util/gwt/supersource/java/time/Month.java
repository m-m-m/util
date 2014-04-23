/*
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package java.time;

import static java.time.calendrical.ChronoField.MONTH_OF_YEAR;

import java.time.calendrical.ChronoField;
import java.time.calendrical.DateTime;
import java.time.calendrical.DateTime.WithAdjuster;
import java.time.calendrical.DateTimeAccessor;
import java.time.calendrical.DateTimeField;
import java.time.calendrical.DateTimeValueRange;
import java.time.chrono.Chrono;
import java.time.chrono.ISOChrono;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * A month-of-year, such as 'July'.
 * <p>
 * {@code Month} is an enum representing the 12 months of the year - January, February, March, April, May,
 * June, July, August, September, October, November and December.
 * <p>
 * In addition to the textual enum name, each month-of-year has an {@code int} value. The {@code int} value
 * follows normal usage and the ISO-8601 standard, from 1 (January) to 12 (December). It is recommended that
 * applications use the enum rather than the {@code int} value to ensure code clarity.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code Month}. Use
 * {@code getValue()} instead.</b>
 * <p>
 * This enum represents a common concept that is found in many calendar systems. As such, this enum may be
 * used by any calendar system that has the month-of-year concept defined exactly equivalent to the ISO
 * calendar system.
 * 
 * <h4>Implementation notes</h4> This is an immutable and thread-safe enum.
 */
public enum Month implements DateTimeAccessor, WithAdjuster {

  /**
   * The singleton instance for the month of January with 31 days. This has the numeric value of {@code 1}.
   */
  JANUARY,
  /**
   * The singleton instance for the month of February with 28 days, or 29 in a leap year. This has the numeric
   * value of {@code 2}.
   */
  FEBRUARY,
  /**
   * The singleton instance for the month of March with 31 days. This has the numeric value of {@code 3}.
   */
  MARCH,
  /**
   * The singleton instance for the month of April with 30 days. This has the numeric value of {@code 4}.
   */
  APRIL,
  /**
   * The singleton instance for the month of May with 31 days. This has the numeric value of {@code 5}.
   */
  MAY,
  /**
   * The singleton instance for the month of June with 30 days. This has the numeric value of {@code 6}.
   */
  JUNE,
  /**
   * The singleton instance for the month of July with 31 days. This has the numeric value of {@code 7}.
   */
  JULY,
  /**
   * The singleton instance for the month of August with 31 days. This has the numeric value of {@code 8}.
   */
  AUGUST,
  /**
   * The singleton instance for the month of September with 30 days. This has the numeric value of {@code 9}.
   */
  SEPTEMBER,
  /**
   * The singleton instance for the month of October with 31 days. This has the numeric value of {@code 10}.
   */
  OCTOBER,
  /**
   * The singleton instance for the month of November with 30 days. This has the numeric value of {@code 11}.
   */
  NOVEMBER,
  /**
   * The singleton instance for the month of December with 31 days. This has the numeric value of {@code 12}.
   */
  DECEMBER;

  /**
   * Private cache of all the constants.
   */
  private static final Month[] ENUMS = Month.values();

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Month} from an {@code int} value.
   * <p>
   * {@code Month} is an enum representing the 12 months of the year. This factory allows the enum to be
   * obtained from the {@code int} value. The {@code int} value follows the ISO-8601 standard, from 1
   * (January) to 12 (December).
   * 
   * @param month the month-of-year to represent, from 1 (January) to 12 (December)
   * @return the month-of-year, not null
   * @throws DateTimeException if the month-of-year is invalid
   */
  public static Month of(int month) {

    if (month < 1 || month > 12) {
      throw new DateTimeException("Invalid value for MonthOfYear: " + month);
    }
    return ENUMS[month - 1];
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Month} from a date-time object.
   * <p>
   * A {@code DateTimeAccessor} represents some form of date and time information. This factory converts the
   * arbitrary date-time object to an instance of {@code Month}.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the month-of-year, not null
   * @throws DateTimeException if unable to convert to a {@code Month}
   */
  public static Month from(DateTimeAccessor dateTime) {

    if (dateTime instanceof Month) {
      return (Month) dateTime;
    }
    return of(dateTime.get(MONTH_OF_YEAR));
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the month-of-year {@code int} value.
   * <p>
   * The values are numbered following the ISO-8601 standard, from 1 (January) to 12 (December).
   * 
   * @return the month-of-year, from 1 (January) to 12 (December)
   */
  public int getValue() {

    return ordinal() + 1;
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the textual representation, such as 'Jan' or 'December'.
   * <p>
   * This returns the textual name used to identify the month-of-year. The parameters control the length of
   * the returned text and the locale.
   * <p>
   * If no textual mapping is found then the {@link #getValue() numeric value} is returned.
   * 
   * @param style the length of the text required, not null
   * @param locale the locale to use, not null
   * @return the text value of the month-of-year, not null
   */
  public String getText(TextStyle style, Locale locale) {

    if (style == null) {
      throw new NullPointerException("style");
    }
    if (locale == null) {
      throw new NullPointerException("locale");
    }
    // TODO
    return toString();
  }

  // -----------------------------------------------------------------------
  @Override
  public boolean isSupported(DateTimeField field) {

    if (field instanceof ChronoField) {
      return field == MONTH_OF_YEAR;
    }
    return field != null && field.doIsSupported(this);
  }

  @Override
  public DateTimeValueRange range(DateTimeField field) {

    if (field == MONTH_OF_YEAR) {
      return field.range();
    } else if (field instanceof ChronoField) {
      throw new DateTimeException("Unsupported field: " + field.getName());
    }
    return field.doRange(this);
  }

  @Override
  public int get(DateTimeField field) {

    if (field == MONTH_OF_YEAR) {
      return getValue();
    }
    return range(field).checkValidIntValue(getLong(field), field);
  }

  @Override
  public long getLong(DateTimeField field) {

    if (field == MONTH_OF_YEAR) {
      return getValue();
    } else if (field instanceof ChronoField) {
      throw new DateTimeException("Unsupported field: " + field.getName());
    }
    return field.doGet(this);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns the month that is the specified number of quarters after this one.
   * <p>
   * The calculation rolls around the end of the year from December to January. The specified period may be
   * negative.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param months the months to add, positive or negative
   * @return the resulting month, not null
   */
  public Month plus(long months) {

    int amount = (int) (months % 12);
    return values()[(ordinal() + (amount + 12)) % 12];
  }

  /**
   * Returns the month that is the specified number of months before this one.
   * <p>
   * The calculation rolls around the start of the year from January to December. The specified period may be
   * negative.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param months the months to subtract, positive or negative
   * @return the resulting month, not null
   */
  public Month minus(long months) {

    return plus(-(months % 12));
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the length of this month in days.
   * <p>
   * This takes a flag to determine whether to return the length for a leap year or not.
   * <p>
   * February has 28 days in a standard year and 29 days in a leap year. April, June, September and November
   * have 30 days. All other months have 31 days.
   * 
   * @param leapYear true if the length is required for a leap year
   * @return the length of this month in days, from 28 to 31
   */
  public int length(boolean leapYear) {

    switch (this) {
      case FEBRUARY:
        return (leapYear ? 29 : 28);
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
   * Gets the minimum length of this month in days.
   * <p>
   * February has a minimum length of 28 days. April, June, September and November have 30 days. All other
   * months have 31 days.
   * 
   * @return the minimum length of this month in days, from 28 to 31
   */
  public int minLength() {

    switch (this) {
      case FEBRUARY:
        return 28;
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
   * Gets the maximum length of this month in days.
   * <p>
   * February has a maximum length of 29 days. April, June, September and November have 30 days. All other
   * months have 31 days.
   * 
   * @return the maximum length of this month in days, from 29 to 31
   */
  public int maxLength() {

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

  // -----------------------------------------------------------------------
  /**
   * Gets the day-of-year for the first day of this month.
   * <p>
   * This returns the day-of-year that this month begins on, using the leap year flag to determine the length
   * of February.
   * 
   * @param leapYear true if the length is required for a leap year
   * @return the last day of this month, from 1 to 335
   */
  public int firstDayOfYear(boolean leapYear) {

    int leap = leapYear ? 1 : 0;
    switch (this) {
      case JANUARY:
        return 1;
      case FEBRUARY:
        return 32;
      case MARCH:
        return 60 + leap;
      case APRIL:
        return 91 + leap;
      case MAY:
        return 121 + leap;
      case JUNE:
        return 152 + leap;
      case JULY:
        return 182 + leap;
      case AUGUST:
        return 213 + leap;
      case SEPTEMBER:
        return 244 + leap;
      case OCTOBER:
        return 274 + leap;
      case NOVEMBER:
        return 305 + leap;
      case DECEMBER:
      default :
        return 335 + leap;
    }
  }

  // -----------------------------------------------------------------------
  @SuppressWarnings("unchecked")
  @Override
  public <R> R query(Query<R> query) {

    if (query == Query.CHRONO) {
      return (R) ISOChrono.INSTANCE;
    }
    return query.doQuery(this);
  }

  /**
   * Implementation of the strategy to make an adjustment to the specified date-time object.
   * <p>
   * This method is not intended to be called by application code directly. Applications should use the
   * {@code with(WithAdjuster)} method on the date-time object to make the adjustment passing this as the
   * argument.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * <h4>Implementation notes</h4>
   * Adjusts the specified date-time to have the value of this month. The date-time object must use the ISO
   * calendar system. The adjustment is equivalent to using {@link DateTime#with(DateTimeField, long)} passing
   * {@code MONTH_OF_YEAR} as the field.
   * 
   * @param dateTime the target object to be adjusted, not null
   * @return the adjusted object, not null
   */
  @Override
  public DateTime doWithAdjustment(DateTime dateTime) {

    if (Chrono.from(dateTime).equals(ISOChrono.INSTANCE) == false) {
      throw new DateTimeException("Adjustment only supported on ISO date-time");
    }
    return dateTime.with(MONTH_OF_YEAR, getValue());
  }

}
