/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import java.io.IOException;
import java.util.Date;

import net.sf.mmm.util.date.api.IllegalDateFormatException;
import net.sf.mmm.util.date.api.Iso8601UtilLimited;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of the {@link net.sf.mmm.util.date.api.Iso8601UtilLimited} interface. It does
 * NOT use {@link java.text.SimpleDateFormat}. All methods of this class are fast and thread-safe. <br>
 *
 * @see Iso8601UtilImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("deprecation")
public class Iso8601UtilLimitedImpl implements Iso8601UtilLimited {

  /** The maximum day of the month. */
  protected static final int MAX_DAY_OF_MONTH = 31;

  /** The maximum month of the year. */
  protected static final int MAX_MONTH = 12;

  private static Iso8601UtilLimitedImpl instance;

  /**
   * The constructor.
   */
  public Iso8601UtilLimitedImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link Iso8601UtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   * @since 6.0.0
   */
  public static Iso8601UtilLimited getInstance() {

    if (instance == null) {
      synchronized (Iso8601UtilLimitedImpl.class) {
        if (instance == null) {
          instance = new Iso8601UtilLimitedImpl();
        }
      }
    }
    return instance;
  }

  @Override
  public String formatDate(Date date) {

    return formatDate(date, true);
  }

  @Override
  public String formatDate(Date date, boolean extended) {

    // we could save 2*2 bytes here according to extended flag ;)
    // "yyyy-MM-dd".length() == 10
    StringBuilder buffer = new StringBuilder(10);
    formatDate(date, extended, buffer);
    return buffer.toString();
  }

  @Override
  public void formatDate(Date date, boolean extended, Appendable buffer) {

    int year = date.getYear();
    int month = date.getMonth() - 1;
    int day = date.getDay();
    formatDate(year, month, day, extended, buffer);
  }

  /**
   * @see #formatDate(Date, boolean, Appendable)
   *
   * @param year is the {@link java.util.Calendar#YEAR year}
   * @param month is the month (1-12).
   * @param day is the {@link java.util.Calendar#DAY_OF_MONTH day}.
   * @param extended if {@code false} the basic date format ("yyyyMMdd") is used, if {@code true} the extended
   *        date format ("yyyy-MM-dd") is used.
   * @param buffer is where to append the formatted date.
   */
  public void formatDate(int year, int month, int day, boolean extended, Appendable buffer) {

    try {
      // year
      String yearString = String.valueOf(year);
      buffer.append(yearString);
      if (extended) {
        buffer.append('-');
      }
      // month
      String monthString = String.valueOf(month);
      if (monthString.length() < 2) {
        buffer.append('0');
      }
      buffer.append(monthString);
      if (extended) {
        buffer.append('-');
      }
      // day
      String dayString = String.valueOf(day);
      if (dayString.length() < 2) {
        buffer.append('0');
      }
      buffer.append(dayString);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String formatDateTime(Date date) {

    return formatDateTime(date, true, true, true);
  }

  @Override
  public String formatDateTime(Date date, boolean extendedDate, boolean extendedTime, boolean extendedTimezone) {

    // "yyyy-MM-ddTHH:mm:ssZ".length() == 20
    StringBuilder buffer = new StringBuilder(20);
    formatDateTime(date, extendedDate, extendedTime, extendedTimezone, buffer);
    return buffer.toString();
  }

  @Override
  public void formatDateTime(Date date, boolean extendedDate, boolean extendedTime, boolean extendedTimeZone, Appendable buffer) {

    try {
      formatDate(date, extendedDate, buffer);
      buffer.append('T');
      formatTime(date, extendedTime, buffer);
      buffer.append('Z');
      formatTimeZone(date.getTimezoneOffset(), extendedTimeZone, buffer);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void formatTime(Date date, boolean extended, Appendable buffer) {

    int hours = date.getHours();
    int minutes = date.getMinutes();
    int seconds = date.getSeconds();
    formatTime(hours, minutes, seconds, extended, buffer);
  }

  /**
   * @see #formatTime(Date, boolean, Appendable)
   *
   * @param hours are the {@link java.util.Calendar#HOUR_OF_DAY hours}.
   * @param minutes are the {@link java.util.Calendar#MINUTE minutes}.
   * @param seconds are the {@link java.util.Calendar#SECOND seconds}.
   * @param extended if {@code false} the basic time format ("HHmmss") is used, if {@code true} the extended
   *        time format ("HH:mm:ss") is used.
   * @param buffer is where to append the formatted date.
   */
  public void formatTime(int hours, int minutes, int seconds, boolean extended, Appendable buffer) {

    try {
      // append hours
      String hour = String.valueOf(hours);
      if (hour.length() < 2) {
        buffer.append('0');
      }
      buffer.append(hour);
      if (extended) {
        buffer.append(':');
      }
      String minute = String.valueOf(minutes);
      // append minutes
      if (minute.length() < 2) {
        buffer.append('0');
      }
      buffer.append(minute);
      if (extended) {
        buffer.append(':');
      }
      // append seconds
      String second = String.valueOf(seconds);
      if (second.length() < 2) {
        buffer.append('0');
      }
      buffer.append(second);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
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

  @Override
  public Date parseDate(String dateIso8601) {

    Date date;
    CharSequenceScanner scanner = new CharSequenceScanner(dateIso8601);
    int[] ymd = parseDate(scanner);
    int year = ymd[0] - 1900;
    int month = ymd[1] - 1;
    int day = ymd[2];

    char c = scanner.forceNext();
    if (c == 'T') {
      int[] hms = parseTime(scanner);
      Integer timeZoneOffset = parseTimezoneOffset(scanner);
      int hour = hms[0];
      int min = hms[1];
      int sec = hms[2];
      date = new Date(year, month, day, hour, min, sec);
      if (timeZoneOffset != null) {
        date = new Date(date.getTime() + timeZoneOffset.intValue());
      }
    } else if (c == 0) {
      date = new Date(ymd[0] + 1900, ymd[1] - 1, ymd[2]);
    } else {
      throw new IllegalArgumentException(dateIso8601);
    }
    return date;
  }

  /**
   * This method parses the timezone offset from the given {@code scanner}.
   *
   * @param scanner is the parser pointing to the timezone or at the end of the string.
   * @return the parsed timezone offset or {@code null} for UTC (Z) or if no timezone is present.
   */
  protected Integer parseTimezoneOffset(CharSequenceScanner scanner) {

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
      return Integer.valueOf(timezoneOffset);
    } else if (c == 0) {
      return null;
    } else if (c == 'Z') {
      // UTC
      return null;
    }
    throw new IllegalArgumentException("Illegal date-format \"" + scanner.toString() + "\"!");
  }

  /**
   * This method parses the date from the given {@code scanner}. The format is {@code yyyy[-]MM[-]dd}.
   *
   * @param scanner is the parser pointing to the date.
   * @return an array containing the year, month, and day in that order.
   */
  protected int[] parseDate(CharSequenceScanner scanner) {

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
        throw new IllegalDateFormatException(scanner.getOriginalString());
      }
      return new int[] { year, month, day };
    } catch (Exception e) {
      throw new IllegalDateFormatException(scanner.getOriginalString(), e);
    }
  }

  /**
   * This method reads two digits from the given {@code scanner}.
   *
   * @param scanner is the scanner potentially pointing to the digits.
   * @return {@code -1} if the {@code scanner} does NOT point to a digit or the number represented by the two
   *         digits consumed from the {@code scanner}.
   * @throws IllegalDateFormatException if the {@code scanner} only contained a single digit.
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
   * This method parses the time (or timezone offset) from the given {@code scanner}. The format is
   * {@code hh[[:]mm[[:]ss]]}.
   *
   * @param scanner is the parser pointing to the time.
   * @return an int-array containing the hour, minute and second in that order.
   */
  protected int[] parseTime(CharSequenceScanner scanner) {

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
      if (((hour < 0) || (hour > 23)) || ((minute < 0) || (minute > 59)) || ((second < 0) || (second > 59))) {
        throw new IllegalDateFormatException(scanner.getOriginalString());
      }
    }
    return new int[] { hour, minute, second };
  }

}
