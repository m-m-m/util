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
package java.time.chrono;

import static java.time.calendrical.ChronoField.EPOCH_DAY;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.calendrical.ChronoField;
import java.time.calendrical.ChronoUnit;
import java.time.calendrical.DateTime;
import java.time.calendrical.DateTime.WithAdjuster;
import java.time.calendrical.DateTimeField;
import java.time.calendrical.DateTimeValueRange;
import java.time.calendrical.PeriodUnit;
import java.time.jdk8.DefaultInterfaceChronoLocalDateTime;
import java.time.jdk8.Jdk7Methods;
import java.time.jdk8.Jdk8Methods;

/**
 * A date-time without a time-zone for the calendar neutral API.
 * <p>
 * {@code ChronoLocalDateTime} is an immutable date-time object that represents a date-time, often viewed as
 * year-month-day-hour-minute-second. This object can also access other fields such as day-of-year,
 * day-of-week and week-of-year.
 * <p>
 * This class stores all date and time fields, to a precision of nanoseconds. It does not store or represent a
 * time-zone. For example, the value "2nd October 2007 at 13:45.30.123456789" can be stored in an
 * {@code ChronoLocalDateTime}.
 * 
 * <h4>Implementation notes</h4> This class is immutable and thread-safe.
 * 
 * @param <C> the chronology of this date
 */
final class ChronoDateTimeImpl<C extends Chrono<C>> extends DefaultInterfaceChronoLocalDateTime<C> implements
    ChronoLocalDateTime<C>, DateTime, WithAdjuster, Serializable {

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 4556003607393004514L;

  /**
   * Hours per minute.
   */
  private static final int HOURS_PER_DAY = 24;

  /**
   * Minutes per hour.
   */
  private static final int MINUTES_PER_HOUR = 60;

  /**
   * Minutes per day.
   */
  private static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

  /**
   * Seconds per minute.
   */
  private static final int SECONDS_PER_MINUTE = 60;

  /**
   * Seconds per hour.
   */
  private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

  /**
   * Seconds per day.
   */
  private static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

  /**
   * Milliseconds per day.
   */
  private static final long MILLIS_PER_DAY = SECONDS_PER_DAY * 1000L;

  /**
   * Microseconds per day.
   */
  private static final long MICROS_PER_DAY = SECONDS_PER_DAY * 1000000L;

  /**
   * Nanos per second.
   */
  private static final long NANOS_PER_SECOND = 1000000000L;

  /**
   * Nanos per minute.
   */
  private static final long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;

  /**
   * Nanos per hour.
   */
  private static final long NANOS_PER_HOUR = NANOS_PER_MINUTE * MINUTES_PER_HOUR;

  /**
   * Nanos per day.
   */
  private static final long NANOS_PER_DAY = NANOS_PER_HOUR * HOURS_PER_DAY;

  /**
   * The date part.
   */
  private final ChronoLocalDate<C> date;

  /**
   * The time part.
   */
  private final LocalTime time;

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code ChronoLocalDateTime} from a date and time.
   * 
   * @param date the local date, not null
   * @param time the local time, not null
   * @return the local date-time, not null
   */
  static <R extends Chrono<R>> ChronoDateTimeImpl<R> of(ChronoLocalDate<R> date, LocalTime time) {

    return new ChronoDateTimeImpl<R>(date, time);
  }

  /**
   * Constructor.
   * 
   * @param date the date part of the date-time, not null
   * @param time the time part of the date-time, not null
   */
  private ChronoDateTimeImpl(ChronoLocalDate<C> date, LocalTime time) {

    Jdk7Methods.Objects_requireNonNull(date, "date");
    Jdk7Methods.Objects_requireNonNull(time, "time");
    this.date = date;
    this.time = time;
  }

  /**
   * Returns a copy of this date-time with the new date and time, checking to see if a new object is in fact
   * required.
   * 
   * @param newDate the date of the new date-time, not null
   * @param newTime the time of the new date-time, not null
   * @return the date-time, not null
   */
  private ChronoDateTimeImpl<C> with(DateTime newDate, LocalTime newTime) {

    if (this.date == newDate && this.time == newTime) {
      return this;
    }
    // Validate that the new DateTime is a ChronoLocalDate (and not something else)
    ChronoLocalDate<C> cd = this.date.getChrono().ensureChronoLocalDate(newDate);
    return new ChronoDateTimeImpl<C>(cd, newTime);
  }

  // -----------------------------------------------------------------------
  @Override
  public ChronoLocalDate<C> getDate() {

    return this.date;
  }

  @Override
  public LocalTime getTime() {

    return this.time;
  }

  // -----------------------------------------------------------------------
  @Override
  public boolean isSupported(DateTimeField field) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      return f.isDateField() || f.isTimeField();
    }
    return field != null && field.doIsSupported(this);
  }

  @Override
  public DateTimeValueRange range(DateTimeField field) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      return (f.isTimeField() ? this.time.range(field) : this.date.range(field));
    }
    return field.doRange(this);
  }

  @Override
  public int get(DateTimeField field) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      return (f.isTimeField() ? this.time.get(field) : this.date.get(field));
    }
    return range(field).checkValidIntValue(getLong(field), field);
  }

  @Override
  public long getLong(DateTimeField field) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      return (f.isTimeField() ? this.time.getLong(field) : this.date.getLong(field));
    }
    return field.doGet(this);
  }

  // -----------------------------------------------------------------------
  @SuppressWarnings("unchecked")
  @Override
  public ChronoDateTimeImpl<C> with(WithAdjuster adjuster) {

    if (adjuster instanceof ChronoLocalDate) {
      // The Chrono is checked in with(date,time)
      return with((ChronoLocalDate<C>) adjuster, this.time);
    } else if (adjuster instanceof LocalTime) {
      return with(this.date, (LocalTime) adjuster);
    } else if (adjuster instanceof ChronoDateTimeImpl) {
      return this.date.getChrono().ensureChronoLocalDateTime((ChronoDateTimeImpl<?>) adjuster);
    }
    return this.date.getChrono().ensureChronoLocalDateTime(adjuster.doWithAdjustment(this));
  }

  @Override
  public ChronoDateTimeImpl<C> with(DateTimeField field, long newValue) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      if (f.isTimeField()) {
        return with(this.date, this.time.with(field, newValue));
      } else {
        return with(this.date.with(field, newValue), this.time);
      }
    }
    return this.date.getChrono().ensureChronoLocalDateTime(field.doWith(this, newValue));
  }

  // -----------------------------------------------------------------------
  @Override
  public ChronoDateTimeImpl<C> plus(long amountToAdd, PeriodUnit unit) {

    if (unit instanceof ChronoUnit) {
      ChronoUnit f = (ChronoUnit) unit;
      switch (f) {
        case NANOS:
          return plusNanos(amountToAdd);
        case MICROS:
          return plusDays(amountToAdd / MICROS_PER_DAY).plusNanos((amountToAdd % MICROS_PER_DAY) * 1000);
        case MILLIS:
          return plusDays(amountToAdd / MILLIS_PER_DAY).plusNanos((amountToAdd % MILLIS_PER_DAY) * 1000000);
        case SECONDS:
          return plusSeconds(amountToAdd);
        case MINUTES:
          return plusMinutes(amountToAdd);
        case HOURS:
          return plusHours(amountToAdd);
        case HALF_DAYS:
          return plusDays(amountToAdd / 256).plusHours((amountToAdd % 256) * 12); // no overflow (256 is
                                                                                  // multiple of 2)
      }
      return with(this.date.plus(amountToAdd, unit), this.time);
    }
    return this.date.getChrono().ensureChronoLocalDateTime(unit.doPlus(this, amountToAdd));
  }

  private ChronoDateTimeImpl<C> plusDays(long days) {

    return with(this.date.plus(days, ChronoUnit.DAYS), this.time);
  }

  private ChronoDateTimeImpl<C> plusHours(long hours) {

    return plusWithOverflow(this.date, hours, 0, 0, 0);
  }

  private ChronoDateTimeImpl<C> plusMinutes(long minutes) {

    return plusWithOverflow(this.date, 0, minutes, 0, 0);
  }

  ChronoDateTimeImpl<C> plusSeconds(long seconds) {

    return plusWithOverflow(this.date, 0, 0, seconds, 0);
  }

  private ChronoDateTimeImpl<C> plusNanos(long nanos) {

    return plusWithOverflow(this.date, 0, 0, 0, nanos);
  }

  // -----------------------------------------------------------------------
  private ChronoDateTimeImpl<C> plusWithOverflow(ChronoLocalDate<C> newDate, long hours, long minutes, long seconds,
      long nanos) {

    // 9223372036854775808 long, 2147483648 int
    if ((hours | minutes | seconds | nanos) == 0) {
      return with(newDate, this.time);
    }
    long totDays = nanos / NANOS_PER_DAY + // max/24*60*60*1B
        seconds / SECONDS_PER_DAY + // max/24*60*60
        minutes / MINUTES_PER_DAY + // max/24*60
        hours / HOURS_PER_DAY; // max/24
    long totNanos = nanos % NANOS_PER_DAY + // max 86400000000000
        (seconds % SECONDS_PER_DAY) * NANOS_PER_SECOND + // max 86400000000000
        (minutes % MINUTES_PER_DAY) * NANOS_PER_MINUTE + // max 86400000000000
        (hours % HOURS_PER_DAY) * NANOS_PER_HOUR; // max 86400000000000
    long curNoD = this.time.toNanoOfDay(); // max 86400000000000
    totNanos = totNanos + curNoD; // total 432000000000000
    totDays += Jdk8Methods.floorDiv(totNanos, NANOS_PER_DAY);
    long newNoD = Jdk8Methods.floorMod(totNanos, NANOS_PER_DAY);
    LocalTime newTime = (newNoD == curNoD ? this.time : LocalTime.ofNanoOfDay(newNoD));
    return with(newDate.plus(totDays, ChronoUnit.DAYS), newTime);
  }

  // -----------------------------------------------------------------------
  @Override
  public ChronoZonedDateTime<C> atZone(ZoneId zoneId) {

    return ChronoZonedDateTimeImpl.ofBest(this, zoneId, null);
  }

  // -----------------------------------------------------------------------
  @Override
  public long periodUntil(DateTime endDateTime, PeriodUnit unit) {

    if (endDateTime instanceof ChronoLocalDateTime == false) {
      throw new DateTimeException("Unable to calculate period between objects of two different types");
    }
    @SuppressWarnings("unchecked")
    ChronoLocalDateTime<C> end = (ChronoLocalDateTime<C>) endDateTime;
    if (getDate().getChrono().equals(end.getDate().getChrono()) == false) {
      throw new DateTimeException("Unable to calculate period between two different chronologies");
    }
    if (unit instanceof ChronoUnit) {
      ChronoUnit f = (ChronoUnit) unit;
      if (f.isTimeUnit()) {
        long amount = end.getLong(EPOCH_DAY) - this.date.getLong(EPOCH_DAY);
        switch (f) {
          case NANOS:
            amount = Jdk8Methods.safeMultiply(amount, NANOS_PER_DAY);
            break;
          case MICROS:
            amount = Jdk8Methods.safeMultiply(amount, MICROS_PER_DAY);
            break;
          case MILLIS:
            amount = Jdk8Methods.safeMultiply(amount, MILLIS_PER_DAY);
            break;
          case SECONDS:
            amount = Jdk8Methods.safeMultiply(amount, SECONDS_PER_DAY);
            break;
          case MINUTES:
            amount = Jdk8Methods.safeMultiply(amount, MINUTES_PER_DAY);
            break;
          case HOURS:
            amount = Jdk8Methods.safeMultiply(amount, HOURS_PER_DAY);
            break;
          case HALF_DAYS:
            amount = Jdk8Methods.safeMultiply(amount, 2);
            break;
        }
        return Jdk8Methods.safeAdd(amount, this.time.periodUntil(end.getTime(), unit));
      }
      ChronoLocalDate<C> endDate = end.getDate();
      if (end.getTime().isBefore(this.time)) {
        endDate = endDate.minus(1, ChronoUnit.DAYS);
      }
      return this.date.periodUntil(endDate, unit);
    }
    return unit.between(this, endDateTime).getAmount();
  }

}
