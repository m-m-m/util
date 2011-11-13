/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.date.api.IllegalDateFormatException;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.date.api.Iso8601Util} interface. It does NOT use
 * {@link java.text.SimpleDateFormat}. All methods of this class are fast and
 * thread-safe.<br>
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public final class Iso8601UtilImpl implements Iso8601Util {

  /** The maximum day of the month. */
  private static final int MAX_DAY_OF_MONTH = 31;

  /** The maximum month of the year. */
  private static final int MAX_MONTH = 12;

  /**
   * This is the singleton instance of this {@link Iso8601UtilImpl}. Instead of
   * declaring the methods static, we declare this static instance what gives
   * the same way of access while still allowing a design for extension by
   * inheriting from this class.
   */
  private static Iso8601UtilImpl instance;

  /** The ID for UTC (Coordinated Universal Time). */
  private static final String UTC_ID = "UTC";

  /** The UTC TimeZone. */
  private static final TimeZone TZ_UTC = TimeZone.getTimeZone(UTC_ID);

  /**
   * The constructor.
   */
  public Iso8601UtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link Iso8601UtilImpl}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static Iso8601UtilImpl getInstance() {

    if (instance == null) {
      synchronized (Iso8601UtilImpl.class) {
        if (instance == null) {
          instance = new Iso8601UtilImpl();
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  public String formatDate(Date date) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return formatDate(calendar, true);
  }

  /**
   * {@inheritDoc}
   */
  public String formatDate(Calendar calendar) {

    return formatDate(calendar, true);
  }

  /**
   * {@inheritDoc}
   */
  public String formatDate(Calendar calendar, boolean extended) {

    // we could save 2*2 bytes here according to extended flag ;)
    // "yyyy-MM-dd".length() == 10
    StringBuilder buffer = new StringBuilder(10);
    formatDate(calendar, extended, buffer);
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void formatDate(Calendar calendar, boolean extended, Appendable buffer) {

    try {
      // year
      String year = String.valueOf(calendar.get(Calendar.YEAR));
      buffer.append(year);
      if (extended) {
        buffer.append('-');
      }
      // month
      String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
      if (month.length() < 2) {
        buffer.append('0');
      }
      buffer.append(month);
      if (extended) {
        buffer.append('-');
      }
      // day
      String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
      if (day.length() < 2) {
        buffer.append('0');
      }
      buffer.append(day);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String formatDateTime(Date date) {

    Calendar calendar = Calendar.getInstance(TZ_UTC);
    calendar.setTime(date);
    // "yyyy-MM-ddTHH:mm:ssZ".length() == 20
    StringBuilder buffer = new StringBuilder(20);
    formatDate(calendar, true, buffer);
    buffer.append('T');
    formatTime(calendar, true, buffer);
    buffer.append('Z');
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  public String formatDateTime(Calendar calendar) {

    return formatDateTime(calendar, true, true, true);
  }

  /**
   * {@inheritDoc}
   */
  public String formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime,
      boolean extendedTimezone) {

    // "yyyy-MM-ddTHH:mm:ss+hh:ss".length() == 25
    StringBuilder buffer = new StringBuilder(25);
    formatDateTime(calendar, extendedDate, extendedTime, extendedTimezone, buffer);
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  public void formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime,
      boolean extendedTimezone, Appendable buffer) {

    try {
      formatDate(calendar, extendedDate, buffer);
      buffer.append('T');
      formatTime(calendar, extendedTime, buffer);
      formatTimeZone(calendar, extendedTimezone, buffer);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void formatTime(Calendar calendar, boolean extended, Appendable buffer) {

    try {
      // append hours
      String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
      if (hour.length() < 2) {
        buffer.append('0');
      }
      buffer.append(hour);
      if (extended) {
        buffer.append(':');
      }
      String minute = String.valueOf(calendar.get(Calendar.MINUTE));
      // append minutes
      if (minute.length() < 2) {
        buffer.append('0');
      }
      buffer.append(minute);
      if (extended) {
        buffer.append(':');
      }
      // append seconds
      String second = String.valueOf(calendar.get(Calendar.SECOND));
      if (second.length() < 2) {
        buffer.append('0');
      }
      buffer.append(second);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void formatTimeZone(Calendar calendar, boolean extended, Appendable buffer) {

    TimeZone timezone = calendar.getTimeZone();
    int timezoneOffset = timezone.getOffset(calendar.getTimeInMillis());
    formatTimeZone(timezoneOffset, extended, buffer);
  }

  /**
   * {@inheritDoc}
   */
  public void formatTimeZone(int timezoneOffset, boolean extended, Appendable buffer) {

    try {
      int offsetSeconds = timezoneOffset / 1000;
      if (offsetSeconds < 0) {
        buffer.append('-');
        offsetSeconds = -offsetSeconds;
      } else {
        buffer.append('+');
      }
      int offsetMinutes = offsetSeconds / 60;
      String hours = String.valueOf(offsetMinutes / 60);
      if (hours.length() < 2) {
        buffer.append('0');
      }
      buffer.append(hours);
      if (extended) {
        buffer.append(':');
      }
      String minutes = String.valueOf(offsetMinutes % 60);
      if (minutes.length() < 2) {
        buffer.append('0');
      }
      buffer.append(minutes);
      int seconds = offsetSeconds % 60;
      if (seconds != 0) {
        if (extended) {
          buffer.append(':');
          String secs = String.valueOf(seconds);
          if (secs.length() < 2) {
            buffer.append('0');
          }
          buffer.append(secs);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Date parseDate(String date) {

    return parseCalendar(date).getTime();
  }

  /**
   * {@inheritDoc}
   */
  public Calendar parseCalendar(String date) {

    Calendar calendar = Calendar.getInstance();
    parseCalendar(date, calendar);
    return calendar;
  }

  /**
   * This method reads two digits from the given <code>scanner</code>.
   * 
   * @param scanner is the scanner potentially pointing to the digits.
   * @return <code>-1</code> if the <code>scanner</code> does NOT point to a
   *         digit or the number represented by the two digits consumed from the
   *         <code>scanner</code>.
   * @throws IllegalDateFormatException if the <code>scanner</code> only
   *         contained a single digit.
   */
  private int read2Digits(CharSequenceScanner scanner) throws IllegalDateFormatException {

    int highDigit = scanner.readDigit();
    if (highDigit == -1) {
      return -1;
    }
    int lowDigit = scanner.readDigit();
    if (lowDigit == -1) {
      throw new IllegalDateFormatException(scanner.getOriginalString());
    }
    return (highDigit * 10) + lowDigit;
  }

  /**
   * This method parses the time (or timezone offset) from the given
   * <code>parser</code>. The format is <code>hh[[:]mm[[:]ss]]</code>
   * 
   * @param scanner is the parser pointing to the time.
   * @return an int-array containing the hour, minute and second in that order.
   */
  private int[] parseTime(CharSequenceScanner scanner) {

    int hour = read2Digits(scanner);
    boolean colon = scanner.expect(':');
    int minute = read2Digits(scanner);
    int second = 0;
    if (minute == -1) {
      if (!colon) {
        minute = 0;
      }
    } else {
      colon = scanner.expect(':');
      second = read2Digits(scanner);
      if ((second == -1) && (!colon)) {
        second = 0;
      }
      if (((hour < 0) || (hour > 23)) || ((minute < 0) || (minute > 59))
          || ((second < 0) || (second > 59))) {
        throw new IllegalDateFormatException(scanner.getOriginalString());
      }
    }
    return new int[] { hour, minute, second };
  }

  /**
   * This method parses the time (and timezone) from the given
   * <code>parser</code> and sets it to the given <code>calendar</code>
   * including <code>year</code>, <code>month</code> and <code>date</code>.
   * 
   * @param scanner is the parser pointing to the time or at the end of the
   *        string
   * @param calendar is the calendar where the parsed date and time will be set.
   * @param year is the year to set that has already been parsed.
   * @param month is the month to set that has already been parsed (in the range
   *        of 1-12).
   * @param day is the day to set that has already been parsed.
   */
  private void parseTime(CharSequenceScanner scanner, Calendar calendar, int year, int month,
      int day) {

    char c = scanner.forceNext();
    if (c == 'T') {
      int[] hourMinuteSecond = parseTime(scanner);
      int hour = hourMinuteSecond[0];
      int minute = hourMinuteSecond[1];
      int second = hourMinuteSecond[2];
      TimeZone timeZone = parseTimezone(scanner);
      if (timeZone != null) {
        calendar.setTimeZone(timeZone);
      }
      calendar.set(year, month - 1, day, hour, minute, second);
    } else if (c == 0) {
      calendar.set(year, month - 1, day);
    } else {
      throw new NlsParseException(scanner.toString(), PATTERN_STRING_TIME, "time",
          scanner.toString());
    }
    calendar.set(Calendar.MILLISECOND, 0);
  }

  /**
   * This method parses the timezone from the given <code>parser</code>.
   * 
   * @param scanner is the parser pointing to the timezone or at the end of the
   *        string
   * @return the parsed timezone or <code>null</code> if parser already at the
   *         end of the string.
   */
  private TimeZone parseTimezone(CharSequenceScanner scanner) {

    char c = scanner.forceNext();
    if ((c == '+') || (c == '-')) {
      boolean negate = (c == '-');
      int[] hourMinuteSecond = parseTime(scanner);
      int hour = hourMinuteSecond[0];
      int minute = hourMinuteSecond[1];
      int second = hourMinuteSecond[2];
      int timezoneOffset = ((((hour * 60) + minute) * 60) + second) * 1000;
      if (negate) {
        timezoneOffset = -timezoneOffset;
      }
      String tzName = "GMT";
      if (hour != 0) {
        if (negate) {
          tzName += "-";
        } else {
          tzName += "+";
        }
        tzName += hour;
      }
      return new SimpleTimeZone(timezoneOffset, tzName);
      // return new ZoneInfo("GMT", timezoneOffset);
    } else if (c == 0) {
      return null;
    } else if (c == 'Z') {
      // UTC
      return TZ_UTC;
    }
    throw new IllegalArgumentException("Illegal date-format \"" + scanner.toString() + "\"!");
  }

  /**
   * {@inheritDoc}
   */
  public void parseCalendar(String date, Calendar calendar) {

    CharSequenceScanner scanner = new CharSequenceScanner(date);
    int year = 0;
    int month = -1;
    int day = -1;
    // proceed date
    try {
      String yearString = scanner.readWhile(CharFilter.LATIN_DIGIT_FILTER);
      if (yearString.length() == 8) {
        // "yyyyMMdd".length() == 8
        year = Integer.parseInt(yearString.substring(0, 4));
        month = Integer.parseInt(yearString.substring(4, 6));
        day = Integer.parseInt(yearString.substring(6, 8));
      } else {
        char c = scanner.forceNext();
        if (c == '-') {
          year = Integer.parseInt(yearString);
          String monthString = scanner.readWhile(CharFilter.LATIN_DIGIT_FILTER);
          if (monthString.length() == 2) {
            month = Integer.parseInt(monthString);
            c = scanner.forceNext();
            if (c == '-') {
              String dayString = scanner.readWhile(CharFilter.LATIN_DIGIT_FILTER);
              if (dayString.length() == 2) {
                day = Integer.parseInt(dayString);
              }
            }
          }
        }
      }
      if (((month < 1) || (month > MAX_MONTH)) || ((day < 1) || (day > MAX_DAY_OF_MONTH))) {
        throw new IllegalDateFormatException(date);
      }
      // proceed time (and timezone)
      parseTime(scanner, calendar, year, month, day);
    } catch (Exception e) {
      throw new IllegalDateFormatException(date, e);
    }
  }

}
