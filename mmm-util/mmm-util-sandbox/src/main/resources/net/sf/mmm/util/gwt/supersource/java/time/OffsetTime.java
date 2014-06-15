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

import static java.time.LocalTime.NANOS_PER_HOUR;
import static java.time.LocalTime.NANOS_PER_MINUTE;
import static java.time.LocalTime.NANOS_PER_SECOND;
import static java.time.LocalTime.SECONDS_PER_DAY;
import static java.time.calendrical.ChronoField.NANO_OF_DAY;
import static java.time.calendrical.ChronoField.OFFSET_SECONDS;
import static java.time.calendrical.ChronoUnit.NANOS;

import java.io.Serializable;
import java.time.calendrical.ChronoField;
import java.time.calendrical.ChronoUnit;
import java.time.calendrical.DateTime;
import java.time.calendrical.DateTime.WithAdjuster;
import java.time.calendrical.DateTimeAccessor;
import java.time.calendrical.DateTimeField;
import java.time.calendrical.DateTimeValueRange;
import java.time.calendrical.PeriodUnit;
import java.time.format.DateTimeParseException;
import java.time.jdk8.DefaultInterfaceDateTimeAccessor;
import java.time.jdk8.Jdk7Methods;
import java.time.zone.ZoneRules;

/**
 * A time with an offset from UTC/Greenwich in the ISO-8601 calendar system, such as {@code 10:15:30+01:00}.
 * <p>
 * {@code OffsetTime} is an immutable date-time object that represents a time, often viewed as
 * hour-minute-second-offset. This class stores all time fields, to a precision of nanoseconds, as well as a
 * zone offset. For example, the value "13:45.30.123456789+02:00" can be stored in an {@code OffsetTime}.
 * 
 * <h4>Implementation notes</h4> This class is immutable and thread-safe.
 */
public final class OffsetTime extends DefaultInterfaceDateTimeAccessor implements DateTime, WithAdjuster,
    Comparable<OffsetTime>, Serializable {

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 7264499704384272492L;

  /**
   * The local date-time.
   */
  private final LocalTime time;

  /**
   * The offset from UTC/Greenwich.
   */
  private final ZoneOffset offset;

  // -----------------------------------------------------------------------
  /**
   * Obtains the current time from the system clock in the default time-zone.
   * <p>
   * This will query the {@link Clock#systemDefaultZone() system clock} in the default time-zone to obtain the
   * current time. The offset will be calculated from the time-zone in the clock.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current time using the system clock, not null
   */
  public static OffsetTime now() {

    return now(Clock.systemDefaultZone());
  }

  /**
   * Obtains the current time from the system clock in the specified time-zone.
   * <p>
   * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current time. Specifying the
   * time-zone avoids dependence on the default time-zone. The offset will be calculated from the specified
   * time-zone.
   * <p>
   * Using this method will prevent the ability to use an alternate clock for testing because the clock is
   * hard-coded.
   * 
   * @return the current time using the system clock, not null
   */
  public static OffsetTime now(ZoneId zone) {

    return now(Clock.system(zone));
  }

  /**
   * Obtains the current time from the specified clock.
   * <p>
   * This will query the specified clock to obtain the current time. The offset will be calculated from the
   * time-zone in the clock.
   * <p>
   * Using this method allows the use of an alternate clock for testing. The alternate clock may be introduced
   * using {@link Clock dependency injection}.
   * 
   * @param clock the clock to use, not null
   * @return the current time, not null
   */
  public static OffsetTime now(Clock clock) {

    Jdk7Methods.Objects_requireNonNull(clock, "clock");
    final Instant now = clock.instant(); // called once
    return ofInstant(now, clock.getZone().getRules().getOffset(now));
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code OffsetTime} from an hour and minute.
   * <p>
   * The second and nanosecond fields will be set to zero by this factory method.
   * 
   * @param hour the hour-of-day to represent, from 0 to 23
   * @param minute the minute-of-hour to represent, from 0 to 59
   * @param offset the zone offset, not null
   * @return the offset time, not null
   * @throws DateTimeException if the value of any field is out of range
   */
  public static OffsetTime of(int hour, int minute, ZoneOffset offset) {

    LocalTime time = LocalTime.of(hour, minute);
    return new OffsetTime(time, offset);
  }

  /**
   * Obtains an instance of {@code OffsetTime} from an hour, minute and second.
   * <p>
   * The second field will be set to zero by this factory method.
   * 
   * @param hour the hour-of-day to represent, from 0 to 23
   * @param minute the minute-of-hour to represent, from 0 to 59
   * @param second the second-of-minute to represent, from 0 to 59
   * @param offset the zone offset, not null
   * @return the offset time, not null
   * @throws DateTimeException if the value of any field is out of range
   */
  public static OffsetTime of(int hour, int minute, int second, ZoneOffset offset) {

    LocalTime time = LocalTime.of(hour, minute, second);
    return new OffsetTime(time, offset);
  }

  /**
   * Obtains an instance of {@code OffsetTime} from an hour, minute, second and nanosecond.
   * 
   * @param hour the hour-of-day to represent, from 0 to 23
   * @param minute the minute-of-hour to represent, from 0 to 59
   * @param second the second-of-minute to represent, from 0 to 59
   * @param nanoOfSecond the nano-of-second to represent, from 0 to 999,999,999
   * @param offset the zone offset, not null
   * @return the offset time, not null
   * @throws DateTimeException if the value of any field is out of range
   */
  public static OffsetTime of(int hour, int minute, int second, int nanoOfSecond, ZoneOffset offset) {

    LocalTime time = LocalTime.of(hour, minute, second, nanoOfSecond);
    return new OffsetTime(time, offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code OffsetTime} from a local time and an offset.
   * 
   * @param time the local time, not null
   * @param offset the zone offset, not null
   * @return the offset time, not null
   */
  public static OffsetTime of(LocalTime time, ZoneOffset offset) {

    return new OffsetTime(time, offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code OffsetTime} from an {@code Instant} and zone ID.
   * <p>
   * This creates an offset time with the same instant as that specified. Finding the offset from
   * UTC/Greenwich is simple as there is only one valid offset for each instant.
   * <p>
   * The date component of the instant is dropped during the conversion. This means that the conversion can
   * never fail due to the instant being out of the valid range of dates.
   * 
   * @param instant the instant to create the time from, not null
   * @param zone the time-zone, which may be an offset, not null
   * @return the offset time, not null
   */
  public static OffsetTime ofInstant(Instant instant, ZoneId zone) {

    Jdk7Methods.Objects_requireNonNull(instant, "instant");
    Jdk7Methods.Objects_requireNonNull(zone, "zone");
    ZoneRules rules = zone.getRules();
    ZoneOffset offset = rules.getOffset(instant);
    long secsOfDay = instant.getEpochSecond() % SECONDS_PER_DAY;
    secsOfDay = (secsOfDay + offset.getTotalSeconds()) % SECONDS_PER_DAY;
    if (secsOfDay < 0) {
      secsOfDay += SECONDS_PER_DAY;
    }
    LocalTime time = LocalTime.ofSecondOfDay(secsOfDay, instant.getNano());
    return new OffsetTime(time, offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code OffsetTime} from a date-time object.
   * <p>
   * A {@code DateTimeAccessor} represents some form of date and time information. This factory converts the
   * arbitrary date-time object to an instance of {@code OffsetTime}.
   * 
   * @param dateTime the date-time object to convert, not null
   * @return the offset time, not null
   * @throws DateTimeException if unable to convert to an {@code OffsetTime}
   */
  public static OffsetTime from(DateTimeAccessor dateTime) {

    if (dateTime instanceof OffsetTime) {
      return (OffsetTime) dateTime;
    }
    LocalTime time = LocalTime.from(dateTime);
    ZoneOffset offset = ZoneOffset.from(dateTime);
    return new OffsetTime(time, offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Obtains an instance of {@code OffsetTime} from a text string such as {@code 10:15:30+01:00}.
   * <p>
   * The string must represent a valid time and is parsed using
   * {@link java.time.format.DateTimeFormatters#isoOffsetTime()}.
   * 
   * @param text the text to parse such as "10:15:30+01:00", not null
   * @return the parsed local time, not null
   * @throws DateTimeParseException if the text cannot be parsed
   */
  public static OffsetTime parse(CharSequence text) {

    int length = text.length();
    Throwable cause = null;
    // "HH:mmZ".length() == 6, "HH:mm:ss.SSSSSSSSSXXXXX".length() == 23
    if ((length >= 6) && (length <= 30)) {
      int zoneStartIndex = 5;
      while (!isZoneStartCharacter(text.charAt(zoneStartIndex))) {
        zoneStartIndex++;
        if (zoneStartIndex >= length) {
          zoneStartIndex = -1;
          break;
        }
      }
      if (zoneStartIndex > 0) {
        LocalTime localTime = LocalTime.parse(text.subSequence(0, zoneStartIndex));
        ZoneOffset zoneOffset;
        try {
          zoneOffset = ZoneOffset.of(text.subSequence(zoneStartIndex, length).toString());
          return new OffsetTime(localTime, zoneOffset);
        } catch (DateTimeException e) {
          cause = e;
        }
      }
    }
    throw new DateTimeParseException("Expected format HH:mm:ss.SSSSSSSSSXXXXX", text, 0, cause);
  }

  /**
   * @param c is the character to check.
   * @return <code>true</code> if <code>c</code> indicates the start of a zone (such as "Z", "+02", "-01:30",
   *         "Europe/Berlin"), <code>false</code> otherwise (if the character is legal for date/time in the
   *         form "yyyy-MM-ddTHH:mm:ss.SSSSSSSSS").
   */
  static boolean isZoneStartCharacter(char c) {

    if ((c == ':') || (c == '.') || (c >= '0') && (c <= '9')) {
      // character of date/time
      return false;
    }
    return true;
  }

  // -----------------------------------------------------------------------
  /**
   * Constructor.
   * 
   * @param time the local time, not null
   * @param offset the zone offset, not null
   */
  private OffsetTime(LocalTime time, ZoneOffset offset) {

    this.time = Jdk7Methods.Objects_requireNonNull(time, "time");
    this.offset = Jdk7Methods.Objects_requireNonNull(offset, "offset");
  }

  /**
   * Returns a new time based on this one, returning {@code this} where possible.
   * 
   * @param time the time to create with, not null
   * @param offset the zone offset to create with, not null
   */
  private OffsetTime with(LocalTime time, ZoneOffset offset) {

    if (this.time == time && this.offset.equals(offset)) {
      return this;
    }
    return new OffsetTime(time, offset);
  }

  // -----------------------------------------------------------------------
  @Override
  public boolean isSupported(DateTimeField field) {

    if (field instanceof ChronoField) {
      return ((ChronoField) field).isTimeField() || field == OFFSET_SECONDS;
    }
    return field != null && field.doIsSupported(this);
  }

  @Override
  public DateTimeValueRange range(DateTimeField field) {

    if (field instanceof ChronoField) {
      if (field == OFFSET_SECONDS) {
        return field.range();
      }
      return this.time.range(field);
    }
    return field.doRange(this);
  }

  @Override
  public long getLong(DateTimeField field) {

    if (field instanceof ChronoField) {
      if (field == OFFSET_SECONDS) {
        return getOffset().getTotalSeconds();
      }
      return this.time.getLong(field);
    }
    return field.doGet(this);
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the zone offset, such as '+01:00'.
   * <p>
   * This is the offset of the local time from UTC/Greenwich.
   * 
   * @return the zone offset, not null
   */
  public ZoneOffset getOffset() {

    return this.offset;
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified offset ensuring that the result has the same
   * local time.
   * <p>
   * This method returns an object with the same {@code LocalTime} and the specified {@code ZoneOffset}. No
   * calculation is needed or performed. For example, if this time represents {@code 10:30+02:00} and the
   * offset specified is {@code +03:00}, then this method will return {@code 10:30+03:00}.
   * <p>
   * To take into account the difference between the offsets, and adjust the time fields, use
   * {@link #withOffsetSameInstant}.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param offset the zone offset to change to, not null
   * @return an {@code OffsetTime} based on this time with the requested offset, not null
   */
  public OffsetTime withOffsetSameLocal(ZoneOffset offset) {

    return offset != null && offset.equals(this.offset) ? this : new OffsetTime(this.time, offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified offset ensuring that the result is at the
   * same instant on an implied day.
   * <p>
   * This method returns an object with the specified {@code ZoneOffset} and a {@code LocalTime} adjusted by
   * the difference between the two offsets. This will result in the old and new objects representing the same
   * instant an an implied day. This is useful for finding the local time in a different offset. For example,
   * if this time represents {@code 10:30+02:00} and the offset specified is {@code +03:00}, then this method
   * will return {@code 11:30+03:00}.
   * <p>
   * To change the offset without adjusting the local time use {@link #withOffsetSameLocal}.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param offset the zone offset to change to, not null
   * @return an {@code OffsetTime} based on this time with the requested offset, not null
   */
  public OffsetTime withOffsetSameInstant(ZoneOffset offset) {

    if (offset.equals(this.offset)) {
      return this;
    }
    int difference = offset.getTotalSeconds() - this.offset.getTotalSeconds();
    LocalTime adjusted = this.time.plusSeconds(difference);
    return new OffsetTime(adjusted, offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the {@code LocalTime} part of this date-time.
   * <p>
   * This returns a {@code LocalTime} with the same hour, minute, second and nanosecond as this date-time.
   * 
   * @return the time part of this date-time, not null
   */
  public LocalTime getTime() {

    return this.time;
  }

  // -----------------------------------------------------------------------
  /**
   * Gets the hour-of-day field.
   * 
   * @return the hour-of-day, from 0 to 23
   */
  public int getHour() {

    return this.time.getHour();
  }

  /**
   * Gets the minute-of-hour field.
   * 
   * @return the minute-of-hour, from 0 to 59
   */
  public int getMinute() {

    return this.time.getMinute();
  }

  /**
   * Gets the second-of-minute field.
   * 
   * @return the second-of-minute, from 0 to 59
   */
  public int getSecond() {

    return this.time.getSecond();
  }

  /**
   * Gets the nano-of-second field.
   * 
   * @return the nano-of-second, from 0 to 999,999,999
   */
  public int getNano() {

    return this.time.getNano();
  }

  // -----------------------------------------------------------------------
  /**
   * Returns an adjusted time based on this time.
   * <p>
   * This adjusts the time according to the rules of the specified adjuster. A simple adjuster might simply
   * set the one of the fields, such as the hour field. A more complex adjuster might set the time to the last
   * hour of the day. The adjuster is responsible for handling special cases, such as the varying lengths of
   * month and leap years.
   * <p>
   * For example, were there to be a class {@code AmPm} implementing the adjuster interface then this method
   * could be used to change the AM/PM value.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return an {@code OffsetTime} based on this time with the adjustment made, not null
   * @throws DateTimeException if the adjustment cannot be made
   */
  @Override
  public OffsetTime with(WithAdjuster adjuster) {

    if (adjuster instanceof LocalTime) {
      return with((LocalTime) adjuster, this.offset);
    } else if (adjuster instanceof ZoneOffset) {
      return with(this.time, (ZoneOffset) adjuster);
    } else if (adjuster instanceof OffsetTime) {
      return (OffsetTime) adjuster;
    }
    return (OffsetTime) adjuster.doWithAdjustment(this);
  }

  /**
   * Returns a copy of this time with the specified field altered.
   * <p>
   * This method returns a new time based on this time with a new value for the specified field. This can be
   * used to change any field, for example to set the hour-of-day. The offset is not part of the calculation
   * and will be unchanged in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param field the field to set in the result, not null
   * @param newValue the new value of the field in the result
   * @return an {@code OffsetTime} based on this time with the specified field set, not null
   * @throws DateTimeException if the value is invalid
   */
  @Override
  public OffsetTime with(DateTimeField field, long newValue) {

    if (field instanceof ChronoField) {
      if (field == OFFSET_SECONDS) {
        ChronoField f = (ChronoField) field;
        return with(this.time, ZoneOffset.ofTotalSeconds(f.checkValidIntValue(newValue)));
      }
      return with(this.time.with(field, newValue), this.offset);
    }
    return field.doWith(this, newValue);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this {@code OffsetTime} with the hour-of-day value altered.
   * <p>
   * The offset does not affect the calculation and will be the same in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param hour the hour-of-day to set in the result, from 0 to 23
   * @return an {@code OffsetTime} based on this time with the requested hour, not null
   * @throws DateTimeException if the hour value is invalid
   */
  public OffsetTime withHour(int hour) {

    return with(this.time.withHour(hour), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the minute-of-hour value altered.
   * <p>
   * The offset does not affect the calculation and will be the same in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param minute the minute-of-hour to set in the result, from 0 to 59
   * @return an {@code OffsetTime} based on this time with the requested minute, not null
   * @throws DateTimeException if the minute value is invalid
   */
  public OffsetTime withMinute(int minute) {

    return with(this.time.withMinute(minute), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the second-of-minute value altered.
   * <p>
   * The offset does not affect the calculation and will be the same in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param second the second-of-minute to set in the result, from 0 to 59
   * @return an {@code OffsetTime} based on this time with the requested second, not null
   * @throws DateTimeException if the second value is invalid
   */
  public OffsetTime withSecond(int second) {

    return with(this.time.withSecond(second), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the nano-of-second value altered.
   * <p>
   * The offset does not affect the calculation and will be the same in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param nanoOfSecond the nano-of-second to set in the result, from 0 to 999,999,999
   * @return an {@code OffsetTime} based on this time with the requested nanosecond, not null
   * @throws DateTimeException if the nanos value is invalid
   */
  public OffsetTime withNano(int nanoOfSecond) {

    return with(this.time.withNano(nanoOfSecond), this.offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this {@code OffsetTime} with the time truncated.
   * <p>
   * Truncation returns a copy of the original time with fields smaller than the specified unit set to zero.
   * For example, truncating with the {@link ChronoUnit#MINUTES minutes} unit will set the second-of-minute
   * and nano-of-second field to zero.
   * <p>
   * Not all units are accepted. The {@link ChronoUnit#DAYS days} unit and time units with an exact duration
   * can be used, other units throw an exception.
   * <p>
   * The offset does not affect the calculation and will be the same in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param unit the unit to truncate to, not null
   * @return an {@code OffsetTime} based on this time with the time truncated, not null
   * @throws DateTimeException if unable to truncate
   */
  public OffsetTime truncatedTo(PeriodUnit unit) {

    return with(this.time.truncatedTo(unit), this.offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this date with the specified period added.
   * <p>
   * This method returns a new time based on this time with the specified period added. The adjuster is
   * typically {@link Period} but may be any other type implementing the
   * {@link java.time.calendrical.DateTime.PlusAdjuster} interface. The calculation is delegated to the
   * specified adjuster, which typically calls back to {@link #plus(long, PeriodUnit)}. The offset is not part
   * of the calculation and will be unchanged in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return an {@code OffsetTime} based on this time with the addition made, not null
   * @throws DateTimeException if the addition cannot be made
   * @throws ArithmeticException if numeric overflow occurs
   */
  @Override
  public OffsetTime plus(PlusAdjuster adjuster) {

    return (OffsetTime) adjuster.doPlusAdjustment(this);
  }

  /**
   * Returns a copy of this time with the specified period added.
   * <p>
   * This method returns a new time based on this time with the specified period added. This can be used to
   * add any period that is defined by a unit, for example to add hours, minutes or seconds. The unit is
   * responsible for the details of the calculation, including the resolution of any edge cases in the
   * calculation. The offset is not part of the calculation and will be unchanged in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param amountToAdd the amount of the unit to add to the result, may be negative
   * @param unit the unit of the period to add, not null
   * @return an {@code OffsetTime} based on this time with the specified period added, not null
   * @throws DateTimeException if the unit cannot be added to this type
   */
  @Override
  public OffsetTime plus(long amountToAdd, PeriodUnit unit) {

    if (unit instanceof ChronoUnit) {
      return with(this.time.plus(amountToAdd, unit), this.offset);
    }
    return unit.doPlus(this, amountToAdd);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in hours added.
   * <p>
   * This adds the specified number of hours to this time, returning a new time. The calculation wraps around
   * midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param hours the hours to add, may be negative
   * @return an {@code OffsetTime} based on this time with the hours added, not null
   */
  public OffsetTime plusHours(long hours) {

    return with(this.time.plusHours(hours), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in minutes added.
   * <p>
   * This adds the specified number of minutes to this time, returning a new time. The calculation wraps
   * around midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param minutes the minutes to add, may be negative
   * @return an {@code OffsetTime} based on this time with the minutes added, not null
   */
  public OffsetTime plusMinutes(long minutes) {

    return with(this.time.plusMinutes(minutes), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in seconds added.
   * <p>
   * This adds the specified number of seconds to this time, returning a new time. The calculation wraps
   * around midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param seconds the seconds to add, may be negative
   * @return an {@code OffsetTime} based on this time with the seconds added, not null
   */
  public OffsetTime plusSeconds(long seconds) {

    return with(this.time.plusSeconds(seconds), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in nanoseconds added.
   * <p>
   * This adds the specified number of nanoseconds to this time, returning a new time. The calculation wraps
   * around midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param nanos the nanos to add, may be negative
   * @return an {@code OffsetTime} based on this time with the nanoseconds added, not null
   */
  public OffsetTime plusNanos(long nanos) {

    return with(this.time.plusNanos(nanos), this.offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this time with the specified period subtracted.
   * <p>
   * This method returns a new time based on this time with the specified period subtracted. The adjuster is
   * typically {@link Period} but may be any other type implementing the
   * {@link java.time.calendrical.DateTime.MinusAdjuster} interface. The calculation is delegated to the
   * specified adjuster, which typically calls back to {@link #minus(long, PeriodUnit)}. The offset is not
   * part of the calculation and will be unchanged in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param adjuster the adjuster to use, not null
   * @return an {@code OffsetTime} based on this time with the subtraction made, not null
   * @throws DateTimeException if the subtraction cannot be made
   * @throws ArithmeticException if numeric overflow occurs
   */
  @Override
  public OffsetTime minus(MinusAdjuster adjuster) {

    return (OffsetTime) adjuster.doMinusAdjustment(this);
  }

  /**
   * Returns a copy of this time with the specified period subtracted.
   * <p>
   * This method returns a new time based on this time with the specified period subtracted. This can be used
   * to subtract any period that is defined by a unit, for example to subtract hours, minutes or seconds. The
   * unit is responsible for the details of the calculation, including the resolution of any edge cases in the
   * calculation. The offset is not part of the calculation and will be unchanged in the result.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param amountToSubtract the amount of the unit to subtract from the result, may be negative
   * @param unit the unit of the period to subtract, not null
   * @return an {@code OffsetTime} based on this time with the specified period subtracted, not null
   * @throws DateTimeException if the unit cannot be added to this type
   */
  @Override
  public OffsetTime minus(long amountToSubtract, PeriodUnit unit) {

    return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract,
        unit));
  }

  // -----------------------------------------------------------------------
  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in hours subtracted.
   * <p>
   * This subtracts the specified number of hours from this time, returning a new time. The calculation wraps
   * around midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param hours the hours to subtract, may be negative
   * @return an {@code OffsetTime} based on this time with the hours subtracted, not null
   */
  public OffsetTime minusHours(long hours) {

    return with(this.time.minusHours(hours), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in minutes subtracted.
   * <p>
   * This subtracts the specified number of minutes from this time, returning a new time. The calculation
   * wraps around midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param minutes the minutes to subtract, may be negative
   * @return an {@code OffsetTime} based on this time with the minutes subtracted, not null
   */
  public OffsetTime minusMinutes(long minutes) {

    return with(this.time.minusMinutes(minutes), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in seconds subtracted.
   * <p>
   * This subtracts the specified number of seconds from this time, returning a new time. The calculation
   * wraps around midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param seconds the seconds to subtract, may be negative
   * @return an {@code OffsetTime} based on this time with the seconds subtracted, not null
   */
  public OffsetTime minusSeconds(long seconds) {

    return with(this.time.minusSeconds(seconds), this.offset);
  }

  /**
   * Returns a copy of this {@code OffsetTime} with the specified period in nanoseconds subtracted.
   * <p>
   * This subtracts the specified number of nanoseconds from this time, returning a new time. The calculation
   * wraps around midnight.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param nanos the nanos to subtract, may be negative
   * @return an {@code OffsetTime} based on this time with the nanoseconds subtracted, not null
   */
  public OffsetTime minusNanos(long nanos) {

    return with(this.time.minusNanos(nanos), this.offset);
  }

  // -----------------------------------------------------------------------
  /**
   * Returns an offset date-time formed from this time at the specified date.
   * <p>
   * This merges the two objects - {@code this} and the specified date - to form an instance of
   * {@code OffsetDateTime}.
   * <p>
   * This instance is immutable and unaffected by this method call.
   * 
   * @param date the date to combine with, not null
   * @return the offset date-time formed from this time and the specified date, not null
   */
  public OffsetDateTime atDate(LocalDate date) {

    return OffsetDateTime.of(date, this.time, this.offset);
  }

  // -----------------------------------------------------------------------
  @Override
  public DateTime doWithAdjustment(DateTime dateTime) {

    return dateTime.with(OFFSET_SECONDS, getOffset().getTotalSeconds()).with(NANO_OF_DAY, this.time.toNanoOfDay());
  }

  @Override
  public long periodUntil(DateTime endDateTime, PeriodUnit unit) {

    if (endDateTime instanceof OffsetTime == false) {
      throw new DateTimeException("Unable to calculate period between objects of two different types");
    }
    if (unit instanceof ChronoUnit) {
      OffsetTime end = (OffsetTime) endDateTime;
      long nanosUntil = end.toEpochNano() - toEpochNano(); // no overflow
      switch ((ChronoUnit) unit) {
        case NANOS:
          return nanosUntil;
        case MICROS:
          return nanosUntil / 1000;
        case MILLIS:
          return nanosUntil / 1000000;
        case SECONDS:
          return nanosUntil / NANOS_PER_SECOND;
        case MINUTES:
          return nanosUntil / NANOS_PER_MINUTE;
        case HOURS:
          return nanosUntil / NANOS_PER_HOUR;
        case HALF_DAYS:
          return nanosUntil / (12 * NANOS_PER_HOUR);
      }
      throw new DateTimeException("Unsupported unit: " + unit.getName());
    }
    return unit.between(this, endDateTime).getAmount();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <R> R query(Query<R> query) {

    if (query == Query.TIME_PRECISION) {
      return (R) NANOS;
    } else if (query == Query.OFFSET) {
      return (R) getOffset();
    }
    return super.query(query);
  }

  // -----------------------------------------------------------------------
  /**
   * Converts this time to epoch nanos based on 1970-01-01Z.
   * 
   * @return the epoch nanos value
   */
  private long toEpochNano() {

    long nod = this.time.toNanoOfDay();
    long offsetNanos = this.offset.getTotalSeconds() * NANOS_PER_SECOND;
    return nod - offsetNanos;
  }

  // -----------------------------------------------------------------------
  /**
   * Compares this {@code OffsetTime} to another time.
   * <p>
   * The comparison is based first on the UTC equivalent instant, then on the local time. It is
   * "consistent with equals", as defined by {@link Comparable}.
   * <p>
   * For example, the following is the comparator order:
   * <ol>
   * <li>{@code 10:30+01:00}</li>
   * <li>{@code 11:00+01:00}</li>
   * <li>{@code 12:00+02:00}</li>
   * <li>{@code 11:30+01:00}</li>
   * <li>{@code 12:00+01:00}</li>
   * <li>{@code 12:30+01:00}</li>
   * </ol>
   * Values #2 and #3 represent the same instant on the time-line. When two values represent the same instant,
   * the local time is compared to distinguish them. This step is needed to make the ordering consistent with
   * {@code equals()}.
   * <p>
   * To compare the underlying local time of two {@code DateTimeAccessor} instances, use
   * {@link ChronoField#NANO_OF_DAY} as a comparator.
   * 
   * @param other the other time to compare to, not null
   * @return the comparator value, negative if less, positive if greater
   * @throws NullPointerException if {@code other} is null
   */
  @Override
  public int compareTo(OffsetTime other) {

    if (this.offset.equals(other.offset)) {
      return this.time.compareTo(other.time);
    }
    // int compare = Long.compare(toEpochNano(), other.toEpochNano());
    long x = toEpochNano();
    long y = other.toEpochNano();
    int compare = (x < y) ? -1 : ((x == y) ? 0 : 1);
    if (compare == 0) {
      compare = this.time.compareTo(other.time);
    }
    return compare;
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if the instant of this {@code OffsetTime} is after that of the specified time applying both times
   * to a common date.
   * <p>
   * This method differs from the comparison in {@link #compareTo} in that it only compares the instant of the
   * time. This is equivalent to converting both times to an instant using the same date and comparing the
   * instants.
   * 
   * @param other the other time to compare to, not null
   * @return true if this is after the instant of the specified time
   */
  public boolean isAfter(OffsetTime other) {

    return toEpochNano() > other.toEpochNano();
  }

  /**
   * Checks if the instant of this {@code OffsetTime} is before that of the specified time applying both times
   * to a common date.
   * <p>
   * This method differs from the comparison in {@link #compareTo} in that it only compares the instant of the
   * time. This is equivalent to converting both times to an instant using the same date and comparing the
   * instants.
   * 
   * @param other the other time to compare to, not null
   * @return true if this is before the instant of the specified time
   */
  public boolean isBefore(OffsetTime other) {

    return toEpochNano() < other.toEpochNano();
  }

  /**
   * Checks if the instant of this {@code OffsetTime} is equal to that of the specified time applying both
   * times to a common date.
   * <p>
   * This method differs from the comparison in {@link #compareTo} and {@link #equals} in that it only
   * compares the instant of the time. This is equivalent to converting both times to an instant using the
   * same date and comparing the instants.
   * 
   * @param other the other time to compare to, not null
   * @return true if this is equal to the instant of the specified time
   */
  public boolean isEqual(OffsetTime other) {

    return toEpochNano() == other.toEpochNano();
  }

  // -----------------------------------------------------------------------
  /**
   * Checks if this time is equal to another time.
   * <p>
   * The comparison is based on the local-time and the offset. To compare for the same instant on the
   * time-line, use {@link #isEqual(OffsetTime)}.
   * <p>
   * Only objects of type {@code OffsetTime} are compared, other types return false. To compare the underlying
   * local time of two {@code DateTimeAccessor} instances, use {@link ChronoField#NANO_OF_DAY} as a
   * comparator.
   * 
   * @param obj the object to check, null returns false
   * @return true if this is equal to the other time
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj instanceof OffsetTime) {
      OffsetTime other = (OffsetTime) obj;
      return this.time.equals(other.time) && this.offset.equals(other.offset);
    }
    return false;
  }

  /**
   * A hash code for this time.
   * 
   * @return a suitable hash code
   */
  @Override
  public int hashCode() {

    return this.time.hashCode() ^ this.offset.hashCode();
  }

  // -----------------------------------------------------------------------
  /**
   * Outputs this time as a {@code String}, such as {@code 10:15:30+01:00}.
   * <p>
   * The output will be one of the following ISO-8601 formats:
   * <p>
   * <ul>
   * <li>{@code HH:mmXXXXX}</li>
   * <li>{@code HH:mm:ssXXXXX}</li>
   * <li>{@code HH:mm:ss.SSSXXXXX}</li>
   * <li>{@code HH:mm:ss.SSSSSSXXXXX}</li>
   * <li>{@code HH:mm:ss.SSSSSSSSSXXXXX}</li>
   * </ul>
   * <p>
   * The format used will be the shortest that outputs the full value of the time where the omitted parts are
   * implied to be zero.
   * 
   * @return a string representation of this time, not null
   */
  @Override
  public String toString() {

    return this.time.toString() + this.offset.toString();
  }

}
