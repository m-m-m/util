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

import static java.time.calendrical.ChronoField.EPOCH_MONTH;
import static java.time.calendrical.ChronoField.ERA;
import static java.time.calendrical.ChronoField.MONTH_OF_YEAR;
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
import java.time.jdk8.Jdk7Methods;
import java.time.jdk8.Jdk8Methods;

/**
 * A year-month in the ISO-8601 calendar system, such as {@code 2007-12}.
 * <p>
 * {@code YearMonth} is an immutable date-time object that represents the combination of a year and month. Any
 * field that can be derived from a year and month, such as quarter-of-year, can be obtained.
 * <p>
 * This class does not store or represent a day, time or time-zone. For example, the value "October 2007" can
 * be stored in a {@code YearMonth}.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today in most of the world. It is
 * equivalent to the proleptic Gregorian calendar system, in which todays's rules for leap years are applied
 * for all time. For most applications written today, the ISO-8601 rules are entirely suitable. Any
 * application that uses historical dates should consider using {@code HistoricDate}.
 * 
 * <h4>Implementation notes</h4> This class is immutable and thread-safe.
 */
public final class YearMonth extends DefaultInterfaceDateTimeAccessor implements DateTime, WithAdjuster,
    Comparable<YearMonth>, Serializable {

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 4183400860270640070L;

  /**
   * The year.
   */
  private final int year;

  /**
   * The month-of-year, not null.
   */
  private final int month;

  // -----------------------------------------------------------------------
  /**
   * Obtains the current year-month from the system clock in the default time-zone.
   * <p>
   * This will query the {@link Clock#systemDefaultZone() system clock} in the default time-zone to obtain the
   * current year-month. The zone and offset will be set based on the time-zone in the clock.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current year-month using the system clock and default time-zone, not null
   */
  public static YearMonth now() {

    return now(Clock.systemDefaultZone());
  }

  /**
   * Obtains the current year-month from the system clock in the specified time-zone.
   * <p>
   * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current year-month.
   * Specifying the time-zone avoids dependence on the default time-zone.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current year-month using the system clock, not null
   */
  public static YearMonth now(ZoneId zone) {

    return now(Clock.system(zone));
  }

  /**
   * Obtains the current year-month from the specified clock.
   * <p>
   * This will query the specified clock to obtain the current year-month. Using this method allows the use of
   * an alternate clock for testing. The alternate clock may be introduced using {@link Clock dependency
   * injection}.
   * 
   * @param clock the clock to use, not null
   * @return the current year-month, not null
   */
  public static YearMonth now(Clock clock) {

    final LocalDate now = LocalDate.now(clock); // called once
    return YearMonth.of(now.getYear(), now.getMonth());
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code YearMonth} from a year and month.
   * 
   * @param year the year to represent, from MIN_YEAR to MAX_YEAR
   * @param month the month-of-year to represent, not null
   * @return the year-month, not null
   * @throws DateTimeException if the year value is invalid
   */
  public static YearMonth of(int year, Month month) {

    Jdk7Methods.Objects_requireNonNull(month, "month");
    return of(year, month.getValue());
  }

  /**
   * Obtains an instance of {@code YearMonth} from a year and month.
   * 
   * @param year the year to represent, from MIN_YEAR to MAX_YEAR
   * @param month the month-of-year to represent, from 1 (January) to 12 (December)
   * @return the year-month, not null
   * @throws DateTimeException if either field value is invalid
   */
  public static YearMonth of(int year, int month) {

    YEAR.checkValidValue(year);
    MONTH_OF_YEAR.checkValidValue(month);
    return new YearMonth(year, month);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code YearMonth} from a date-time object.
   * <p>
   * A {@code DateTimeAccessor} represents some form of date and time information. This factory converts the
   * arbitrary date-time object to an instance of {@code YearMonth}.
   * <p>
   * The conversion extracts the {@link ChronoField#YEAR year} and {@link ChronoField#MONTH_OF_YEAR
   * month-of-year} fields. The extraction is only permitted if the date-time has an ISO chronology.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the year-month, not null
   * @throws DateTimeException if unable to convert to a {@code YearMonth}
   */
  public static YearMonth from(DateTimeAccessor dateTime) {

    if (dateTime instanceof YearMonth) {
      return (YearMonth) dateTime;
    }
    if (ISOChrono.INSTANCE.equals(Chrono.from(dateTime)) == false) {
      dateTime = LocalDate.from(dateTime);
    }
    return of(dateTime.get(YEAR), dateTime.get(MONTH_OF_YEAR));
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code YearMonth} from a text string such as {@code 2007-12}.
   * <p>
   * The string must represent a valid year-month. The format must be {@code yyyy-MM}. Years outside the range
   * 0000 to 9999 must be prefixed by the plus or minus symbol.
   * 
   * @param text the text to parse such as "2007-12", not null
   * @return the parsed year-month, not null
   * @throws DateTimeParseException if the text cannot be parsed
   */
  public static YearMonth parse(CharSequence text) {

    int length = text.length();
    int errorIndex = 0;
    Throwable cause = null;
    try {
      // "yyyy-MM".length() == 7
      if (length >= 7) {
        String string = text.toString();
        int index = string.indexOf('-', 4);
        if (index >= 4) {
          int yearValue = Integer.parseInt(string.substring(0, index));
          errorIndex = index + 1;
          int monthValue = Integer.parseInt(string.substring(index + 1, length));
          if ((monthValue > 0) && (monthValue <= 12)) {
            return new YearMonth(yearValue, monthValue);
          }
        }
      }
    } catch (NumberFormatException e) {
      cause = e;
    }
    throw new DateTimeParseException("Expected format yyyy-MM", text, errorIndex, cause);
  }

  // -----------------------------------------------------------------------
  /**
   * Constructor.
   * 
   * @param year the year to represent, validated from MIN_YEAR to MAX_YEAR
   * @param month the month-of-year to represent, validated from 1 (January) to 12 (December)
   */
  private YearMonth(int year, int month) {

    this.year = year;
    this.month = month;
  }

  /**
   * Returns a copy of this year-month with the new year and month, checking to see if a new object is in fact
   * required.
   * 
   * @param newYear the year to represent, validated from MIN_YEAR to MAX_YEAR
   * @param newMonth the month-of-year to represent, validated not null
   * @return the year-month, not null
   */
  private YearMonth with(int newYear, int newMonth) {

    if (this.year == newYear && this.month == newMonth) {
      return this;
    }
    return new YearMonth(newYear, newMonth);
  }

  // -----------------------------------------------------------------------
  @Override
  public boolean isSupported(DateTimeField field) {

    if (field instanceof ChronoField) {
      return field == YEAR || field == MONTH_OF_YEAR || field == EPOCH_MONTH || field == YEAR_OF_ERA || field == ERA;
    }
    return field != null && field.doIsSupported(this);
  }

  @Override
  public DateTimeValueRange range(DateTimeField field) {

    if (field == YEAR_OF_ERA) {
      return (getYear() <= 0 ? DateTimeValueRange.of(1, LocalDate.MAX_YEAR + 1) : DateTimeValueRange.of(1,
          LocalDate.MAX_YEAR));
    }
    return super.range(field);
  }

  @Override
  public long getLong(DateTimeField field) {

    if (field instanceof ChronoField) {
      switch ((ChronoField) field) {
        case MONTH_OF_YEAR:
          return this.month;
        case EPOCH_MONTH:
          return getEpochMonth();
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

  private long getEpochMonth() {

    return ((this.year - 1970) * 12L) + (this.month - 1);
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the year field.
   * <p>
   * This method returns the primitive {@code int} value for the year.
   * <p>
   * The year returned by this method is proleptic as per {@code get(YEAR)}.
   * 
   * @return the year, from MIN_YEAR to MAX_YEAR
   */
  public int getYear() {

    return this.year;
  }

  /**
   * Gets the month-of-year field using the {@code Month} enum.
   * <p>
   * This method returns the enum {@link Month} for the month. This avoids confusion as to what {@code int}
   * values mean. If you need access to the primitive {@code int} value then the enum provides the
   * {@link Month#getValue() int value}.
   * 
   * @return the month-of-year, not null
   */
  public Month getMonth() {

    return Month.of(this.month);
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
  public boolean isLeapYear() {

    return ISOChrono.INSTANCE.isLeapYear(this.year);
  }

  /**
   * Returns the length of the month, taking account of the year.
   * <p>
   * This returns the length of the month in days. For example, a date in January would return 31.
   * 
   * @return the length of the month in days, from 28 to 31
   */
  public int lengthOfMonth() {

    return getMonth().length(isLeapYear());
  }

  /**
   * Returns the length of the year.
   * <p>
   * This returns the length of the year in days, either 365 or 366.
   * 
   * @return 366 if the year is leap, 365 otherwise
   */
  public int lengthOfYear() {

    return (isLeapYear() ? 366 : 365);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns an adjusted year-month based on this year-month.
   * <p>
   * This adjusts the year-month according to the rules of the specified adjuster. A simple adjuster might
   * simply set the one of the fields, such as the year field. A more complex adjuster might set the
   * year-month to the next month that Halley's comet will pass the Earth.
   * <p>
   * In addition, all principal classes implement the {@link WithAdjuster} interface, including this one. For
   * example, {@link Month} implements the adjuster interface. As such, this code will compile and run:
   * 
   * <pre>
     *  yearMonth.with(month);
     * </pre>
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return a {@code YearMonth} based on this year-month with the adjustment made, not null
   * @throws DateTimeException if the adjustment cannot be made
   */
  @Override
  public YearMonth with(WithAdjuster adjuster) {

    if (adjuster instanceof YearMonth) {
      return (YearMonth) adjuster;
    }
    return (YearMonth) adjuster.doWithAdjustment(this);
  }

  @Override
  public YearMonth with(DateTimeField field, long newValue) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      f.checkValidValue(newValue);
      switch (f) {
        case MONTH_OF_YEAR:
          return withMonth((int) newValue);
        case EPOCH_MONTH:
          return plusMonths(newValue - getLong(EPOCH_MONTH));
        case YEAR_OF_ERA:
          return withYear((int) (this.year < 1 ? 1 - newValue : newValue));
        case YEAR:
          return withYear((int) newValue);
        case ERA:
          return (getLong(ERA) == newValue ? this : withYear(1 - this.year));
      }
      throw new DateTimeException("Unsupported field: " + field.getName());
    }
    return field.doWith(this, newValue);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this {@code YearMonth} with the year altered.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param year the year to set in the returned year-month, from MIN_YEAR to MAX_YEAR
   * @return a {@code YearMonth} based on this year-month with the requested year, not null
   * @throws DateTimeException if the year value is invalid
   */
  public YearMonth withYear(int year) {

    YEAR.checkValidValue(year);
    return with(year, this.month);
  }

  /**
   * Returns a copy of this {@code YearMonth} with the month-of-year altered.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param month the month-of-year to set in the returned year-month, from 1 (January) to 12 (December)
   * @return a {@code YearMonth} based on this year-month with the requested month, not null
   * @throws DateTimeException if the month-of-year value is invalid
   */
  public YearMonth withMonth(int month) {

    MONTH_OF_YEAR.checkValidValue(month);
    return with(this.year, month);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this year-month with the specified period added.
   * <p>
   * This method returns a new year-month based on this year-month with the specified period added. The
   * adjuster is typically {@link Period} but may be any other type implementing the
   * {@link java.time.calendrical.DateTime.PlusAdjuster} interface. The calculation is delegated to the
   * specified adjuster, which typically calls back to {@link #plus(long, PeriodUnit)}.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return a {@code YearMonth} based on this year-month with the addition made, not null
   * @throws DateTimeException if the addition cannot be made
   * @throws ArithmeticException if numeric overflow occurs
   */
  @Override
  public YearMonth plus(PlusAdjuster adjuster) {

    return (YearMonth) adjuster.doPlusAdjustment(this);
  }

  @Override
  public YearMonth plus(long amountToAdd, PeriodUnit unit) {

    if (unit instanceof ChronoUnit) {
      switch ((ChronoUnit) unit) {
        case MONTHS:
          return plusMonths(amountToAdd);
        case QUARTER_YEARS:
          return plusYears(amountToAdd / 256).plusMonths((amountToAdd % 256) * 3); // no overflow (256 is
                                                                                   // multiple of 4)
        case HALF_YEARS:
          return plusYears(amountToAdd / 256).plusMonths((amountToAdd % 256) * 6); // no overflow (256 is
                                                                                   // multiple of 2)
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
   * Returns a copy of this year-month with the specified period in years added.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param yearsToAdd the years to add, may be negative
   * @return a {@code YearMonth} based on this year-month with the years added, not null
   * @throws DateTimeException if the result exceeds the supported range
   */
  public YearMonth plusYears(long yearsToAdd) {

    if (yearsToAdd == 0) {
      return this;
    }
    int newYear = YEAR.checkValidIntValue(this.year + yearsToAdd); // safe overflow
    return with(newYear, this.month);
  }

  /**
   * Returns a copy of this year-month with the specified period in months added.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param monthsToAdd the months to add, may be negative
   * @return a {@code YearMonth} based on this year-month with the months added, not null
   * @throws DateTimeException if the result exceeds the supported range
   */
  public YearMonth plusMonths(long monthsToAdd) {

    if (monthsToAdd == 0) {
      return this;
    }
    long monthCount = this.year * 12L + (this.month - 1);
    long calcMonths = monthCount + monthsToAdd; // safe overflow
    int newYear = YEAR.checkValidIntValue(Jdk8Methods.floorDiv(calcMonths, 12));
    int newMonth = Jdk8Methods.floorMod(calcMonths, 12) + 1;
    return with(newYear, newMonth);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this year-month with the specified period subtracted.
   * <p>
   * This method returns a new year-month based on this year-month with the specified period subtracted. The
   * adjuster is typically {@link Period} but may be any other type implementing the
   * {@link java.time.calendrical.DateTime.MinusAdjuster} interface. The calculation is delegated to the
   * specified adjuster, which typically calls back to {@link #minus(long, PeriodUnit)}.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return a {@code YearMonth} based on this year-month with the subtraction made, not null
   * @throws DateTimeException if the subtraction cannot be made
   * @throws ArithmeticException if numeric overflow occurs
   */
  @Override
  public YearMonth minus(MinusAdjuster adjuster) {

    return (YearMonth) adjuster.doMinusAdjustment(this);
  }

  @Override
  public YearMonth minus(long amountToSubtract, PeriodUnit unit) {

    return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract,
        unit));
  }

  /**
   * Returns a copy of this year-month with the specified period in years subtracted.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param yearsToSubtract the years to subtract, may be negative
   * @return a {@code YearMonth} based on this year-month with the years subtracted, not null
   * @throws DateTimeException if the result exceeds the supported range
   */
  public YearMonth minusYears(long yearsToSubtract) {

    return (yearsToSubtract == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
  }

  /**
   * Returns a copy of this year-month with the specified period in months subtracted.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param monthsToSubtract the months to subtract, may be negative
   * @return a {@code YearMonth} based on this year-month with the months subtracted, not null
   * @throws DateTimeException if the result exceeds the supported range
   */
  public YearMonth minusMonths(long monthsToSubtract) {

    return (monthsToSubtract == Long.MIN_VALUE
        ? plusMonths(Long.MAX_VALUE).plusMonths(1)
        : plusMonths(-monthsToSubtract));
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if the day-of-month is valid for this year-month.
   * <p>
   * This method checks whether this year and month and the input day form a valid date.
   * 
   * @param dayOfMonth the day-of-month to validate, from 1 to 31, invalid value returns false
   * @return true if the day is valid for this year-month
   */
  public boolean isValidDay(int dayOfMonth) {

    return dayOfMonth >= 1 && dayOfMonth <= lengthOfMonth();
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a date formed from this year-month at the specified day-of-month.
   * <p>
   * This method merges {@code this} and the specified day to form an instance of {@code LocalDate}. This
   * method can be used as part of a chain to produce a date:
   * 
   * <pre>
     * LocalDate date = year.atMonth(month).atDay(day);
     * </pre>
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param dayOfMonth the day-of-month to use, from 1 to 31
   * @return the date formed from this year-month and the specified day, not null
   * @throws DateTimeException when the day is invalid for the year-month
   * @see #isValidDay(int)
   */
  public LocalDate atDay(int dayOfMonth) {

    return LocalDate.of(this.year, this.month, dayOfMonth);
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
   * Adjusts the specified date-time to have the value of this year-month. The date-time object must use the
   * ISO calendar system. The adjustment is equivalent to using {@link DateTime#with(DateTimeField, long)}
   * passing {@code EPOCH_MONTH} as the field.
   * 
   * @param dateTime the target object to be adjusted, not null
   * @return the adjusted object, not null
   */
  @Override
  public DateTime doWithAdjustment(DateTime dateTime) {

    if (Chrono.from(dateTime).equals(ISOChrono.INSTANCE) == false) {
      throw new DateTimeException("Adjustment only supported on ISO date-time");
    }
    return dateTime.with(EPOCH_MONTH, getEpochMonth());
  }

  @Override
  public long periodUntil(DateTime endDateTime, PeriodUnit unit) {

    if (endDateTime instanceof YearMonth == false) {
      throw new DateTimeException("Unable to calculate period between objects of two different types");
    }
    YearMonth end = (YearMonth) endDateTime;
    if (unit instanceof ChronoUnit) {
      long monthsUntil = end.getEpochMonth() - getEpochMonth(); // no overflow
      switch ((ChronoUnit) unit) {
        case MONTHS:
          return monthsUntil;
        case QUARTER_YEARS:
          return monthsUntil / 3;
        case HALF_YEARS:
          return monthsUntil / 6;
        case YEARS:
          return monthsUntil / 12;
        case DECADES:
          return monthsUntil / 120;
        case CENTURIES:
          return monthsUntil / 1200;
        case MILLENNIA:
          return monthsUntil / 12000;
        case ERAS:
          return end.getLong(ERA) - getLong(ERA);
      }
      throw new DateTimeException("Unsupported unit: " + unit.getName());
    }
    return unit.between(this, endDateTime).getAmount();
  }

  // -----------------------------------------------------------------------
  /**
   * Compares this year-month to another year-month.
   * <p>
   * The comparison is based first on the value of the year, then on the value of the month. It is
   * "consistent with equals", as defined by {@link Comparable}.
   * 
   * @param other the other year-month to compare to, not null
   * @return the comparator value, negative if less, positive if greater
   */
  @Override
  public int compareTo(YearMonth other) {

    int cmp = (this.year - other.year);
    if (cmp == 0) {
      cmp = (this.month - other.month);
    }
    return cmp;
  }

  /**
   * Is this year-month after the specified year-month.
   * 
   * @param other the other year-month to compare to, not null
   * @return true if this is after the specified year-month
   */
  public boolean isAfter(YearMonth other) {

    return compareTo(other) > 0;
  }

  /**
   * Is this year-month before the specified year-month.
   * 
   * @param other the other year-month to compare to, not null
   * @return true if this point is before the specified year-month
   */
  public boolean isBefore(YearMonth other) {

    return compareTo(other) < 0;
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if this year-month is equal to another year-month.
   * <p>
   * The comparison is based on the time-line position of the year-months.
   * 
   * @param obj the object to check, null returns false
   * @return true if this is equal to the other year-month
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj instanceof YearMonth) {
      YearMonth other = (YearMonth) obj;
      return this.year == other.year && this.month == other.month;
    }
    return false;
  }

  /**
   * A hash code for this year-month.
   * 
   * @return a suitable hash code
   */
  @Override
  public int hashCode() {

    return this.year ^ (this.month << 27);
  }

  // -----------------------------------------------------------------------
  /**
   * Outputs this year-month as a {@code String}, such as {@code 2007-12}.
   * <p>
   * The output will be in the format {@code yyyy-MM}:
   * 
   * @return a string representation of this year-month, not null
   */
  @Override
  public String toString() {

    int absYear = Math.abs(this.year);
    StringBuilder buf = new StringBuilder(9);
    if (absYear < 1000) {
      if (this.year < 0) {
        buf.append(this.year - 10000).deleteCharAt(1);
      } else {
        buf.append(this.year + 10000).deleteCharAt(0);
      }
    } else {
      buf.append(this.year);
    }
    return buf.append(this.month < 10 ? "-0" : "-").append(this.month).toString();
  }

}
