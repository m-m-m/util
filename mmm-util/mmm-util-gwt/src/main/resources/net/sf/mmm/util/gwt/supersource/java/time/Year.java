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

import static java.time.calendrical.ChronoField.ERA;
import static java.time.calendrical.ChronoField.YEAR;
import static java.time.calendrical.ChronoField.YEAR_OF_ERA;

import java.io.Serializable;
import java.time.calendrical.ChronoField;
import java.time.calendrical.ChronoUnit;
import java.time.calendrical.DateTime;
import java.time.calendrical.DateTime.WithAdjuster;
import java.time.calendrical.DateTimeAccessor;
import java.time.calendrical.DateTimeField;
import java.time.calendrical.DateTimeValueRange;
import java.time.calendrical.PeriodUnit;
import java.time.chrono.Chrono;
import java.time.chrono.ISOChrono;
import java.time.format.DateTimeParseException;
import java.time.jdk8.DefaultInterfaceDateTimeAccessor;
import java.time.jdk8.Jdk8Methods;

/**
 * A year in the ISO-8601 calendar system, such as {@code 2007}.
 * <p>
 * {@code Year} is an immutable date-time object that represents a year. Any field that can be derived from a
 * year can be obtained.
 * <p>
 * <b>Note that years in the ISO chronology only align with years in the Gregorian-Julian system for modern
 * years. Parts of Russia did not switch to the modern Gregorian/ISO rules until 1920. As such, historical
 * years must be treated with caution.</b>
 * <p>
 * This class does not store or represent a month, day, time or time-zone. For example, the value "2007" can
 * be stored in a {@code Year}.
 * <p>
 * Years represented by this class follow the ISO-8601 standard and use the proleptic numbering system. Year 1
 * is preceded by year 0, then by year -1.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today in most of the world. It is
 * equivalent to the proleptic Gregorian calendar system, in which todays's rules for leap years are applied
 * for all time. For most applications written today, the ISO-8601 rules are entirely suitable. Any
 * application that uses historical dates should consider using {@code HistoricDate}.
 * 
 * <h4>Implementation notes</h4> This class is immutable and thread-safe.
 */
public final class Year extends DefaultInterfaceDateTimeAccessor implements DateTime, WithAdjuster, Comparable<Year>,
    Serializable {

  // TODO: remove constants?
  /**
   * The minimum supported year, -999,999,999.
   */
  public static final int MIN_YEAR = LocalDate.MIN_YEAR;

  /**
   * The maximum supported year, 999,999,999.
   */
  public static final int MAX_YEAR = LocalDate.MAX_YEAR;

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = -23038383694477807L;

  /**
   * The year being represented.
   */
  private final int year;

  // -----------------------------------------------------------------------
  /**
   * Obtains the current year from the system clock in the default time-zone.
   * <p>
   * This will query the {@link Clock#systemDefaultZone() system clock} in the default time-zone to obtain the
   * current year.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current year using the system clock and default time-zone, not null
   */
  public static Year now() {

    return now(Clock.systemDefaultZone());
  }

  /**
   * Obtains the current year from the system clock in the specified time-zone.
   * <p>
   * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current year. Specifying the
   * time-zone avoids dependence on the default time-zone.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current year using the system clock, not null
   */
  public static Year now(ZoneId zone) {

    return now(Clock.system(zone));
  }

  /**
   * Obtains the current year from the specified clock.
   * <p>
   * This will query the specified clock to obtain the current year. Using this method allows the use of an
   * alternate clock for testing. The alternate clock may be introduced using {@link Clock dependency
   * injection}.
   * 
   * @param clock the clock to use, not null
   * @return the current year, not null
   */
  public static Year now(Clock clock) {

    final LocalDate now = LocalDate.now(clock); // called once
    return Year.of(now.getYear());
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Year}.
   * <p>
   * This method accepts a year value from the proleptic ISO calendar system.
   * <p>
   * The year 2AD/CE is represented by 2.<br />
   * The year 1AD/CE is represented by 1.<br />
   * The year 1BC/BCE is represented by 0.<br />
   * The year 2BC/BCE is represented by -1.<br />
   * 
   * @param isoYear the ISO proleptic year to represent, from MIN_YEAR to MAX_YEAR
   * @return the year, not null
   * @throws DateTimeException if the field is invalid
   */
  public static Year of(int isoYear) {

    YEAR.checkValidValue(isoYear);
    return new Year(isoYear);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Year} from a date-time object.
   * <p>
   * A {@code DateTimeAccessor} represents some form of date and time information. This factory converts the
   * arbitrary date-time object to an instance of {@code Year}.
   * <p>
   * The conversion extracts the {@link ChronoField#YEAR year} field. The extraction is only permitted if the
   * date-time has an ISO chronology.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the year, not null
   * @throws DateTimeException if unable to convert to a {@code Year}
   */
  public static Year from(DateTimeAccessor dateTime) {

    if (dateTime instanceof Year) {
      return (Year) dateTime;
    }
    if (ISOChrono.INSTANCE.equals(Chrono.from(dateTime)) == false) {
      dateTime = LocalDate.from(dateTime);
    }
    return of(dateTime.get(YEAR));
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code Year} from a text string such as {@code 2007}.
   * <p>
   * The string must represent a valid year. Years outside the range 0000 to 9999 must be prefixed by the plus
   * or minus symbol.
   * 
   * @param text the text to parse such as "2007", not null
   * @return the parsed year, not null
   * @throws DateTimeParseException if the text cannot be parsed
   */
  public static Year parse(CharSequence text) {

    Throwable cause = null;
    try {
      if (text.length() >= 4) {
        return new Year(Integer.parseInt(text.toString()));
      }
    } catch (NumberFormatException e) {
      cause = e;
    }
    throw new DateTimeParseException("Expected format yyyy", text, 0, cause);
  }

  // -------------------------------------------------------------------------
  /**
   * Checks if the year is a leap year, according to the ISO proleptic calendar system rules.
   * <p>
   * This method applies the current rules for leap years across the whole time-line. In general, a year is a
   * leap year if it is divisible by four without remainder. However, years divisible by 100, are not leap
   * years, with the exception of years divisible by 400 which are.
   * <p>
   * For example, 1904 is a leap year it is divisible by 4. 1900 was not a leap year as it is divisible by
   * 100, however 2000 was a leap year as it is divisible by 400.
   * <p>
   * The calculation is proleptic - applying the same rules into the far future and far past. This is
   * historically inaccurate, but is correct for the ISO-8601 standard.
   * 
   * @param year the year to check
   * @return true if the year is leap, false otherwise
   */
  public static boolean isLeap(long year) {

    return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
  }

  // -----------------------------------------------------------------------
  /**
   * Constructor.
   * 
   * @param year the year to represent
   */
  private Year(int year) {

    this.year = year;
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the year value.
   * <p>
   * The year returned by this method is proleptic as per {@code get(YEAR)}.
   * 
   * @return the year, from MIN_YEAR to MAX_YEAR
   */
  public int getValue() {

    return this.year;
  }

  // -----------------------------------------------------------------------
  @Override
  public boolean isSupported(DateTimeField field) {

    if (field instanceof ChronoField) {
      return field == YEAR || field == YEAR_OF_ERA || field == ERA;
    }
    return field != null && field.doIsSupported(this);
  }

  @Override
  public DateTimeValueRange range(DateTimeField field) {

    if (field == YEAR_OF_ERA) {
      return (this.year <= 0 ? DateTimeValueRange.of(1, MAX_YEAR + 1) : DateTimeValueRange.of(1, MAX_YEAR));
    }
    return super.range(field);
  }

  @Override
  public long getLong(DateTimeField field) {

    if (field instanceof ChronoField) {
      switch ((ChronoField) field) {
        case YEAR_OF_ERA:
          return (this.year < 1 ? 1 - this.year : this.year);
        case YEAR:
          return this.year;
        case ERA:
          return (this.year < 1 ? 0 : 1);
      }
      throw new DateTimeException("Unsupported field: " + field.getName());
    }
    return field.doGet(this);
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if the year is a leap year, according to the ISO proleptic calendar system rules.
   * <p>
   * This method applies the current rules for leap years across the whole time-line. In general, a year is a
   * leap year if it is divisible by four without remainder. However, years divisible by 100, are not leap
   * years, with the exception of years divisible by 400 which are.
   * <p>
   * For example, 1904 is a leap year it is divisible by 4. 1900 was not a leap year as it is divisible by
   * 100, however 2000 was a leap year as it is divisible by 400.
   * <p>
   * The calculation is proleptic - applying the same rules into the far future and far past. This is
   * historically inaccurate, but is correct for the ISO-8601 standard.
   * 
   * @return true if the year is leap, false otherwise
   */
  public boolean isLeap() {

    return Year.isLeap(this.year);
  }

  /**
   * Gets the length of this year in days.
   * 
   * @return the length of this year in days, 365 or 366
   */
  public int length() {

    return isLeap() ? 366 : 365;
  }

  // -----------------------------------------------------------------------
  /**
   * Returns an adjusted year based on this year.
   * <p>
   * This adjusts the year according to the rules of the specified adjuster.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return a {@code LocalDate} based on this date with the adjustment made, not null
   * @throws DateTimeException if the adjustment cannot be made
   */
  @Override
  public Year with(WithAdjuster adjuster) {

    if (adjuster instanceof Year) {
      return (Year) adjuster;
    }
    return (Year) adjuster.doWithAdjustment(this);
  }

  @Override
  public Year with(DateTimeField field, long newValue) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      f.checkValidValue(newValue);
      switch (f) {
        case YEAR_OF_ERA:
          return Year.of((int) (this.year < 1 ? 1 - newValue : newValue));
        case YEAR:
          return Year.of((int) newValue);
        case ERA:
          return (getLong(ERA) == newValue ? this : Year.of(1 - this.year));
      }
      throw new DateTimeException("Unsupported field: " + field.getName());
    }
    return field.doWith(this, newValue);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this year with the specified period added.
   * <p>
   * This method returns a new year based on this year with the specified period added. The adjuster is
   * typically {@link Period} but may be any other type implementing the
   * {@link java.time.calendrical.DateTime.PlusAdjuster} interface. The calculation is delegated to the
   * specified adjuster, which typically calls back to {@link #plus(long, PeriodUnit)}.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return a {@code Year} based on this year with the addition made, not null
   * @throws DateTimeException if the addition cannot be made
   * @throws ArithmeticException if numeric overflow occurs
   */
  @Override
  public Year plus(PlusAdjuster adjuster) {

    return (Year) adjuster.doPlusAdjustment(this);
  }

  @Override
  public Year plus(long amountToAdd, PeriodUnit unit) {

    if (unit instanceof ChronoUnit) {
      switch ((ChronoUnit) unit) {
        case YEARS:
          return plusYears(amountToAdd);
        case DECADES:
          return plusYears(Jdk8Methods.safeMultiply(amountToAdd, 10));
        case CENTURIES:
          return plusYears(Jdk8Methods.safeMultiply(amountToAdd, 100));
        case MILLENNIA:
          return plusYears(Jdk8Methods.safeMultiply(amountToAdd, 1000));
        case ERAS:
          return with(ERA, Jdk8Methods.safeAdd(getLong(ERA), amountToAdd));
      }
      throw new DateTimeException("Unsupported unit: " + unit.getName());
    }
    return unit.doPlus(this, amountToAdd);
  }

  /**
   * Returns a copy of this year with the specified number of years added.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param yearsToAdd the years to add, may be negative
   * @return a {@code Year} based on this year with the period added, not null
   * @throws DateTimeException if the result exceeds the supported year range
   */
  public Year plusYears(long yearsToAdd) {

    if (yearsToAdd == 0) {
      return this;
    }
    return of(YEAR.checkValidIntValue(this.year + yearsToAdd)); // overflow safe
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this year with the specified period subtracted.
   * <p>
   * This method returns a new year based on this year with the specified period subtracted. The adjuster is
   * typically {@link Period} but may be any other type implementing the
   * {@link java.time.calendrical.DateTime.MinusAdjuster} interface. The calculation is delegated to the
   * specified adjuster, which typically calls back to {@link #minus(long, PeriodUnit)}.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return a {@code Year} based on this year with the subtraction made, not null
   * @throws DateTimeException if the subtraction cannot be made
   * @throws ArithmeticException if numeric overflow occurs
   */
  @Override
  public Year minus(MinusAdjuster adjuster) {

    return (Year) adjuster.doMinusAdjustment(this);
  }

  @Override
  public Year minus(long amountToSubtract, PeriodUnit unit) {

    return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract,
        unit));
  }

  /**
   * Returns a copy of this year with the specified number of years subtracted.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param yearsToSubtract the years to subtract, may be negative
   * @return a {@code Year} based on this year with the period subtracted, not null
   * @throws DateTimeException if the result exceeds the supported year range
   */
  public Year minusYears(long yearsToSubtract) {

    return (yearsToSubtract == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if the month-day is valid for this year.
   * <p>
   * This method checks whether this year and the input month and day form a valid date.
   * 
   * @param monthDay the month-day to validate, null returns false
   * @return true if the month and day are valid for this year
   */
  public boolean isValidMonthDay(MonthDay monthDay) {

    return monthDay != null && monthDay.isValidYear(this.year);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a year-month formed from this year at the specified month.
   * <p>
   * This method merges {@code this} and the specified month to form an instance of {@code YearMonth}. This
   * method can be used as part of a chain to produce a date:
   * 
   * <pre>
     * LocalDate date = year.atMonth(month).atDay(day);
     * </pre>
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param month the month-of-year to use, not null
   * @return the year-month formed from this year and the specified month, not null
   */
  public YearMonth atMonth(Month month) {

    return YearMonth.of(this.year, month);
  }

  /**
   * Returns a year-month formed from this year at the specified month.
   * <p>
   * This method merges {@code this} and the specified month to form an instance of {@code YearMonth}. This
   * method can be used as part of a chain to produce a date:
   * 
   * <pre>
     * LocalDate date = year.atMonth(month).atDay(day);
     * </pre>
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param month the month-of-year to use, from 1 (January) to 12 (December)
   * @return the year-month formed from this year and the specified month, not null
   */
  public YearMonth atMonth(int month) {

    return YearMonth.of(this.year, month);
  }

  /**
   * Returns a date formed from this year at the specified month-day.
   * <p>
   * This merges the two objects - {@code this} and the specified day - to form an instance of
   * {@code LocalDate}.
   * 
   * <pre>
     * LocalDate date = year.atMonthDay(monthDay);
     * </pre>
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param monthDay the month-day to use, not null
   * @return the local date formed from this year and the specified month-day, not null
   * @throws DateTimeException if the month-day is February 29th and this is not a leap year
   */
  public LocalDate atMonthDay(MonthDay monthDay) {

    return LocalDate.of(this.year, monthDay.getMonth(), monthDay.getDayOfMonth());
  }

  /**
   * Returns a date formed from this year at the specified day-of-year.
   * <p>
   * This merges the two objects - {@code this} and the specified day - to form an instance of
   * {@code LocalDate}.
   * 
   * <pre>
     * LocalDate date = year.atDay(dayOfYear);
     * </pre>
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param dayOfYear the day-of-year to use, not null
   * @return the local date formed from this year and the specified date of year, not null
   * @throws DateTimeException if the day of year is 366 and this is not a leap year
   */
  public LocalDate atDay(int dayOfYear) {

    return LocalDate.ofYearDay(this.year, dayOfYear);
  }

  // -----------------------------------------------------------------------
  @SuppressWarnings("unchecked")
  @Override
  public <R> R query(Query<R> query) {

    if (query == Query.CHRONO) {
      return (R) ISOChrono.INSTANCE;
    }
    return super.query(query);
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
   * Adjusts the specified date-time to have the value of this year. The date-time object must use the ISO
   * calendar system. The adjustment is equivalent to using {@link DateTime#with(DateTimeField, long)} passing
   * {@code YEAR} as the field.
   * 
   * @param dateTime the target object to be adjusted, not null
   * @return the adjusted object, not null
   */
  @Override
  public DateTime doWithAdjustment(DateTime dateTime) {

    if (Chrono.from(dateTime).equals(ISOChrono.INSTANCE) == false) {
      throw new DateTimeException("Adjustment only supported on ISO date-time");
    }
    return dateTime.with(YEAR, this.year);
  }

  @Override
  public long periodUntil(DateTime endDateTime, PeriodUnit unit) {

    if (endDateTime instanceof Year == false) {
      throw new DateTimeException("Unable to calculate period between objects of two different types");
    }
    Year end = (Year) endDateTime;
    if (unit instanceof ChronoUnit) {
      long yearsUntil = ((long) end.year) - this.year; // no overflow
      switch ((ChronoUnit) unit) {
        case YEARS:
          return yearsUntil;
        case DECADES:
          return yearsUntil / 10;
        case CENTURIES:
          return yearsUntil / 100;
        case MILLENNIA:
          return yearsUntil / 1000;
        case ERAS:
          return end.getLong(ERA) - getLong(ERA);
      }
      throw new DateTimeException("Unsupported unit: " + unit.getName());
    }
    return unit.between(this, endDateTime).getAmount();
  }

  // -----------------------------------------------------------------------
  /**
   * Compares this year to another year.
   * <p>
   * The comparison is based on the value of the year. It is "consistent with equals", as defined by
   * {@link Comparable}.
   * 
   * @param other the other year to compare to, not null
   * @return the comparator value, negative if less, positive if greater
   */
  @Override
  public int compareTo(Year other) {

    return this.year - other.year;
  }

  /**
   * Is this year after the specified year.
   * 
   * @param other the other year to compare to, not null
   * @return true if this is after the specified year
   */
  public boolean isAfter(Year other) {

    return this.year > other.year;
  }

  /**
   * Is this year before the specified year.
   * 
   * @param other the other year to compare to, not null
   * @return true if this point is before the specified year
   */
  public boolean isBefore(Year other) {

    return this.year < other.year;
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if this year is equal to another year.
   * <p>
   * The comparison is based on the time-line position of the years.
   * 
   * @param obj the object to check, null returns false
   * @return true if this is equal to the other year
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj instanceof Year) {
      return this.year == ((Year) obj).year;
    }
    return false;
  }

  /**
   * A hash code for this year.
   * 
   * @return a suitable hash code
   */
  @Override
  public int hashCode() {

    return this.year;
  }

  // -----------------------------------------------------------------------
  /**
   * Outputs this year as a {@code String}.
   * 
   * @return a string representation of this year, not null
   */
  @Override
  public String toString() {

    return Integer.toString(this.year);
  }

}
