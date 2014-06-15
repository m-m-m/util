/*
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
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
package java.time.chrono;

import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.calendrical.ChronoField;
import java.time.calendrical.DateTimeAccessor;
import java.time.calendrical.DateTimeValueRange;
import java.time.jdk8.Jdk7Methods;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The ISO calendar system.
 * <p>
 * This chronology defines the rules of the ISO calendar system. This calendar system is based on the ISO-8601
 * standard, which is the <i>de facto</i> world calendar.
 * <p>
 * The fields are defined as follows:
 * <p>
 * <ul>
 * <li>era - There are two eras, 'Current Era' (CE) and 'Before Current Era' (BCE).
 * <li>year-of-era - The year-of-era is the same as the proleptic-year for the current CE era. For the BCE era
 * before the ISO epoch the year increases from 1 upwards as time goes backwards.
 * <li>proleptic-year - The proleptic year is the same as the year-of-era for the current era. For the
 * previous era, years have zero, then negative values.
 * <li>month-of-year - There are 12 months in an ISO year, numbered from 1 to 12.
 * <li>day-of-month - There are between 28 and 31 days in each of the ISO month, numbered from 1 to 31. Months
 * 4, 6, 9 and 11 have 30 days, Months 1, 3, 5, 7, 8, 10 and 12 have 31 days. Month 2 has 28 days, or 29 in a
 * leap year.
 * <li>day-of-year - There are 365 days in a standard ISO year and 366 in a leap year. The days are numbered
 * from 1 to 365 or 1 to 366.
 * <li>leap-year - Leap years occur every 4 years, except where the year is divisble by 100 and not divisble
 * by 400.
 * </ul>
 * <p>
 * 
 * <h4>Implementation notes</h4> This class is immutable and thread-safe.
 */
public final class ISOChrono extends Chrono<ISOChrono> implements Serializable {

  /**
   * Singleton instance of the ISO chronology.
   */
  public static final ISOChrono INSTANCE = new ISOChrono();

  /**
   * The singleton instance for the era BCE - 'Before Current Era'. The 'ISO' part of the name emphasizes that
   * this differs from the BCE era in the Gregorian calendar system. This has the numeric value of {@code 0}.
   */
  public static final Era<ISOChrono> ERA_BCE = ISOEra.BCE;

  /**
   * The singleton instance for the era CE - 'Current Era'. The 'ISO' part of the name emphasizes that this
   * differs from the CE era in the Gregorian calendar system. This has the numeric value of {@code 1}.
   */
  public static final Era<ISOChrono> ERA_CE = ISOEra.CE;

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = -1440403870442975015L;

  /**
   * Restricted constructor.
   */
  private ISOChrono() {

  }

  /**
   * Resolve singleton.
   * 
   * @return the singleton instance, not null
   */
  private Object readResolve() {

    return INSTANCE;
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the ID of the chronology - 'ISO'.
   * <p>
   * The ID uniquely identifies the {@code Chrono}. It can be used to lookup the {@code Chrono} using
   * {@link #of(String)}.
   * 
   * @return the chronology ID - 'ISO'
   * @see #getCalendarType()
   */
  @Override
  public String getId() {

    return "ISO";
  }

  /**
   * Gets the calendar type of the underlying calendar system - 'iso8601'.
   * <p>
   * The calendar type is an identifier defined by the <em>Unicode Locale Data Markup Language (LDML)</em>
   * specification. It can be used to lookup the {@code Chrono} using {@link #of(String)}. It can also be used
   * as part of a locale, accessible via {@link Locale#getUnicodeLocaleType(String)} with the key 'ca'.
   * 
   * @return the calendar system type - 'iso8601'
   * @see #getId()
   */
  @Override
  public String getCalendarType() {

    return "iso8601";
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an ISO local date from the era, year-of-era, month-of-year and day-of-month fields.
   * 
   * @param era the ISO era, not null
   * @param yearOfEra the ISO year-of-era
   * @param month the ISO month-of-year
   * @param dayOfMonth the ISO day-of-month
   * @return the ISO local date, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate date(Era<ISOChrono> era, int yearOfEra, int month, int dayOfMonth) {

    return date(prolepticYear(era, yearOfEra), month, dayOfMonth);
  }

  /**
   * Obtains an ISO local date from the proleptic-year, month-of-year and day-of-month fields.
   * <p>
   * This is equivalent to {@link LocalDate#of(int, int, int)}.
   * 
   * @param prolepticYear the ISO proleptic-year
   * @param month the ISO month-of-year
   * @param dayOfMonth the ISO day-of-month
   * @return the ISO local date, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate date(int prolepticYear, int month, int dayOfMonth) {

    return LocalDate.of(prolepticYear, month, dayOfMonth);
  }

  /**
   * Obtains an ISO local date from the era, year-of-era and day-of-year fields.
   * 
   * @param era the ISO era, not null
   * @param yearOfEra the ISO year-of-era
   * @param dayOfYear the ISO day-of-year
   * @return the ISO local date, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate dateYearDay(Era<ISOChrono> era, int yearOfEra, int dayOfYear) {

    return dateYearDay(prolepticYear(era, yearOfEra), dayOfYear);
  }

  /**
   * Obtains an ISO local date from the proleptic-year and day-of-year fields.
   * <p>
   * This is equivalent to {@link LocalDate#ofYearDay(int, int)}.
   * 
   * @param prolepticYear the ISO proleptic-year
   * @param dayOfYear the ISO day-of-year
   * @return the ISO local date, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate dateYearDay(int prolepticYear, int dayOfYear) {

    return LocalDate.ofYearDay(prolepticYear, dayOfYear);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an ISO local date from another date-time object.
   * <p>
   * This is equivalent to {@link LocalDate#from(DateTimeAccessor)}.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the ISO local date, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate date(DateTimeAccessor dateTime) {

    return LocalDate.from(dateTime);
  }

  /**
   * Obtains an ISO local date-time from another date-time object.
   * <p>
   * This is equivalent to {@link LocalDateTime#from(DateTimeAccessor)}.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the ISO local date-time, not null
   * @throws DateTimeException if unable to create the date-time
   */
  @Override
  // override with covariant return type
  public LocalDateTime localDateTime(DateTimeAccessor dateTime) {

    return LocalDateTime.from(dateTime);
  }

  /**
   * Obtains an ISO zoned date-time from another date-time object.
   * <p>
   * This is equivalent to {@link ZonedDateTime#from(DateTimeAccessor)}.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the ISO zoned date-time, not null
   * @throws DateTimeException if unable to create the date-time
   */
  @Override
  // override with covariant return type
  public ZonedDateTime zonedDateTime(DateTimeAccessor dateTime) {

    return ZonedDateTime.from(dateTime);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains the current ISO local date from the system clock in the default time-zone.
   * <p>
   * This will query the {@link Clock#systemDefaultZone() system clock} in the default time-zone to obtain the
   * current date.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current ISO local date using the system clock and default time-zone, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate dateNow() {

    return dateNow(Clock.systemDefaultZone());
  }

  /**
   * Obtains the current ISO local date from the system clock in the specified time-zone.
   * <p>
   * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date. Specifying the
   * time-zone avoids dependence on the default time-zone.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current ISO local date using the system clock, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate dateNow(ZoneId zone) {

    return dateNow(Clock.system(zone));
  }

  /**
   * Obtains the current ISO local date from the specified clock.
   * <p>
   * This will query the specified clock to obtain the current date - today. Using this method allows the use
   * of an alternate clock for testing. The alternate clock may be introduced using {@link Clock dependency
   * injection}.
   * 
   * @param clock the clock to use, not null
   * @return the current ISO local date, not null
   * @throws DateTimeException if unable to create the date
   */
  @Override
  // override with covariant return type
  public LocalDate dateNow(Clock clock) {

    Jdk7Methods.Objects_requireNonNull(clock, "clock");
    return date(LocalDate.now(clock));
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
   * @param prolepticYear the ISO proleptic year to check
   * @return true if the year is leap, false otherwise
   */
  @Override
  public boolean isLeapYear(long prolepticYear) {

    return ((prolepticYear & 3) == 0) && ((prolepticYear % 100) != 0 || (prolepticYear % 400) == 0);
  }

  @Override
  public int prolepticYear(Era<ISOChrono> era, int yearOfEra) {

    if (era instanceof ISOEra == false) {
      throw new DateTimeException("Era must be ISOEra");
    }
    return (era == ISOEra.CE ? yearOfEra : 1 - yearOfEra);
  }

  @Override
  public Era<ISOChrono> eraOf(int eraValue) {

    return ISOEra.of(eraValue);
  }

  @Override
  public List<Era<ISOChrono>> eras() {

    return Arrays.<Era<ISOChrono>> asList(ISOEra.values());
  }

  // -----------------------------------------------------------------------
  @Override
  public DateTimeValueRange range(ChronoField field) {

    return field.range();
  }

}
