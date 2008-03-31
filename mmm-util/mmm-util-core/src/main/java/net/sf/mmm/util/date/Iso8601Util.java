/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import net.sf.mmm.util.filter.CharFilter;
import net.sf.mmm.util.scanner.CharSequenceScanner;

/**
 * This class is a collection of utility functions for formatting and parsing
 * dates according to ISO 8601 formats.<br>
 * This implementation does NOT use {@link java.text.SimpleDateFormat}. All
 * methods of this class are fast and thread-safe.<br>
 * The ISO 8601 defines multiple formats for date and times. The following forms
 * are handled by this implementation:<br>
 * <table border="1">
 * <tr bgcolor="#ccccff">
 * <th>Type</th>
 * <th>Basic</th>
 * <th>Extended</th>
 * <th>Special</th>
 * </tr>
 * <tr>
 * <td>Date</td>
 * <td>yyyyMMdd</td>
 * <td>yyyy-MM-dd</td>
 * <td>&#160;</td>
 * </tr>
 * <tr>
 * <td>Time</td>
 * <td>HHmmss</td>
 * <td>HH:mm:ss</td>
 * <td>&#160;</td>
 * </tr>
 * <tr>
 * <td>Timezone</td>
 * <td>&#177;HH[mm[ss]]</td>
 * <td>&#177;HH[:mm[:ss]]</td>
 * <td>'Z'</td>
 * </tr>
 * </table><br>
 * Please note that the special timezone character <code>Z</code> means UTC.<br>
 * Out of these forms the following combinations are supported:
 * <ul>
 * <li>&lt;Date&gt;</li>
 * <li>&lt;Date&gt;T&lt;Time&gt;</li>
 * <li>&lt;Date&gt;T&lt;Time&gt;&lt;Timezone&gt;</li>
 * </ul>
 * Examples:<br>
 * <ul>
 * <li>1999-12-31</li>
 * <li>1999-12-31T23:59:59</li>
 * <li>1999-12-31T23:59:59+01:00</li>
 * <li>2000-01-01T00:00:00Z</li>
 * <li>20000101T000000Z</li>
 * </ul>
 * As you can see by the example the basic format is harder to read (for
 * humans). Therefore you should use the extended format if possible.<br>
 * The {@link #parseCalendar(String) parse} methods support all formats
 * described above. For {@link #formatDateTime(Calendar) formatting} various
 * methods exist for different format combinations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class Iso8601Util {

  /**
   * This is the singleton instance of this {@link Iso8601Util}. Instead of
   * declaring the methods static, we declare this static instance what gives
   * the same way of access while still allowing a design for extension by
   * inheriting from this class.
   */
  private static Iso8601Util instance;

  /** The ID for UTC (Coordinated Universal Time). */
  private static final String UTC_ID = "UTC";

  /** The UTC TimeZone. */
  private static final TimeZone TZ_UTC = TimeZone.getTimeZone(UTC_ID);

  /**
   * The constructor.
   */
  public Iso8601Util() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link Iso8601Util}.<br>
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
  public static Iso8601Util getInstance() {

    if (instance == null) {
      synchronized (Iso8601Util.class) {
        if (instance == null) {
          instance = new Iso8601Util();
        }
      }
    }
    return instance;
  }

  /**
   * This method formats the given <code>date</code> in the format
   * "yyyy-MM-dd" in GMT according to {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to format.
   * @return the given <code>date</code> as date string.
   */
  public String formatDate(Date date) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return formatDate(calendar, true);
  }

  /**
   * This method formats the given <code>calendar</code> as a date in the
   * format "yyyy-MM-dd" according to {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @return the given <code>calendar</code> as date string.
   */
  public String formatDate(Calendar calendar) {

    return formatDate(calendar, true);
  }

  /**
   * This method formats the given <code>calendar</code> as a date in the
   * format "yyyy-MM-dd" according to {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @return the given <code>calendar</code> as date string.
   * @param extended if <code>false</code> the basic format ("yyyyMMdd") is
   *        used, if <code>true</code> the extended format ("yyyy-MM-dd") is
   *        used.
   */
  public String formatDate(Calendar calendar, boolean extended) {

    // we could save 2*2 bytes here according to extended flag ;)
    // "yyyy-MM-dd".length() == 10
    StringBuffer buffer = new StringBuffer(10);
    formatDate(calendar, extended, buffer);
    return buffer.toString();
  }

  /**
   * This method formats the given <code>calendar</code> as a date according
   * to {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @param extended if <code>false</code> the basic date format ("yyyyMMdd")
   *        is used, if <code>true</code> the extended date format
   *        ("yyyy-MM-dd") is used.
   * @param buffer is where to append the formatted date.
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
   * This method formats the given <code>date</code> as a date and time in the
   * format "yyyy-MM-ddTHH:mm:ssZ" (UTC) according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to format.
   * @return the given <code>calendar</code> as date string.
   */
  public String formatDateTime(Date date) {

    Calendar calendar = Calendar.getInstance(TZ_UTC);
    calendar.setTime(date);
    // "yyyy-MM-ddTHH:mm:ssZ".length() == 20
    StringBuffer buffer = new StringBuffer(20);
    formatDate(calendar, true, buffer);
    buffer.append('T');
    formatTime(calendar, true, buffer);
    buffer.append('Z');
    return buffer.toString();
  }

  /**
   * This method formats the given <code>calendar</code> as a date and time in
   * the format "yyyy-MM-ddTHH:mm:ss&#177;hh:mm" according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @return the given <code>calendar</code> as date string.
   */
  public String formatDateTime(Calendar calendar) {

    return formatDateTime(calendar, true, true, true);
  }

  /**
   * This method formats the given <code>calendar</code> as a date and time in
   * the format "yyyy-MM-ddTHH:mm:ss&#177;hh:mm" according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @param extendedDate if <code>false</code> the basic date format
   *        ("yyyyMMdd") is used, if <code>true</code> the extended date
   *        format ("yyyy-MM-dd") is used.
   * @param extendedTime if <code>false</code> the basic time format
   *        ("HHmmss") is used, if <code>true</code> the extended time format
   *        ("HH:mm:ss") is used.
   * @param extendedTimezone if <code>false</code> the basic timezone format
   *        ("&#177;HHmm[ss]") is used, if <code>true</code> the extended
   *        timezone format ("&#177;HH:mm[:ss]") is used.
   * @return the given <code>calendar</code> as date string.
   */
  public String formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime,
      boolean extendedTimezone) {

    // "yyyy-MM-ddTHH:mm:ss+hh:ss".length() == 25
    StringBuffer buffer = new StringBuffer(25);
    formatDateTime(calendar, extendedDate, extendedTime, extendedTimezone, buffer);
    return buffer.toString();
  }

  /**
   * This method formats the given <code>calendar</code> as a date and time in
   * the format "yyyy-MM-ddTHH:mm:ss&#177;hh:mm" according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @param extendedDate if <code>false</code> the basic date format
   *        ("yyyyMMdd") is used, if <code>true</code> the extended date
   *        format ("yyyy-MM-dd") is used.
   * @param extendedTime if <code>false</code> the basic time format
   *        ("HHmmss") is used, if <code>true</code> the extended time format
   *        ("HH:mm:ss") is used.
   * @param extendedTimezone if <code>false</code> the basic timezone format
   *        ("&#177;HHmm[ss]") is used, if <code>true</code> the extended
   *        timezone format ("&#177;HH:mm[:ss]") is used.
   * @param buffer is where to append the formatted date and time.
   */
  public void formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime,
      boolean extendedTimezone, Appendable buffer) {

    try {
      formatDate(calendar, extendedDate, buffer);
      buffer.append('T');
      formatTime(calendar, extendedTime, buffer);
      TimeZone timezone = calendar.getTimeZone();
      int timezoneOffset = timezone.getOffset(calendar.getTimeInMillis());
      formatTimeZone(timezoneOffset, extendedTimezone, buffer);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * This method formats the given <code>calendar</code> as time according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @param extended if <code>false</code> the basic time format ("HHmmss") is
   *        used, if <code>true</code> the extended time format ("HH:mm:ss")
   *        is used.
   * @param buffer is where to append the formatted date.
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
   * This method formats the given <code>timezone</code> according to
   * {@link Iso8601Util ISO 8601}.<br>
   * 
   * @param timezoneOffset is the timezone-offset in milliseconds.
   * @param extended - if <code>false</code> the basic timezone format
   *        ("&#177;HHmm[ss]") is used, if <code>true</code> the extended
   *        timezone format ("&#177;HH:mm[:ss]") is used.
   * @param buffer is where to append the formatted timezone.
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
   * This method parses the given string <code>date</code> according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to parse.
   * @return the parsed date.
   */
  public Date parseDate(String date) {

    return parseCalendar(date).getTime();
  }

  /**
   * This method parses the given string <code>date</code> according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to parse.
   * @return the parsed date.
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
    boolean colon = scanner.skipOver(":", false);
    int minute = read2Digits(scanner);
    int second = 0;
    if (minute == -1) {
      if (!colon) {
        minute = 0;
      }
    } else {
      colon = scanner.skipOver(":", false);
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
      calendar.set(year, month - 1, day, hour, minute, second);
      TimeZone timeZone = parseTimezone(scanner);
      if (timeZone != null) {
        calendar.setTimeZone(timeZone);
      }
    } else if (c == 0) {
      calendar.set(year, month - 1, day);
    } else {
      throw new IllegalArgumentException("Illegal date-format \"" + scanner.toString() + "\"!");
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
   * This method parses the given <code>date</code> according to
   * {@link Iso8601Util ISO 8601} using the given <code>calendar</code>. If
   * the given <code>date</code> does NOT specify the time or timezone, the
   * values from the given <code>calendar</code> will be kept.
   * 
   * @param date is the date to parse.
   * @param calendar is the calendar where the parsed date will be set.
   */
  public void parseCalendar(String date, Calendar calendar) {

    CharSequenceScanner parser = new CharSequenceScanner(date);
    int year = 0;
    int month = -1;
    int day = -1;
    // proceed date
    try {
      // TODO: peek for +/-
      String yearString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
      char c = parser.forceNext();
      if (c == '-') {
        year = Integer.parseInt(yearString);
        String monthString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
        if (monthString.length() == 2) {
          month = Integer.parseInt(monthString);
          c = parser.forceNext();
          if (c == '-') {
            String dayString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
            if (dayString.length() == 2) {
              day = Integer.parseInt(dayString);
            }
          }
        }
      } else if (yearString.length() == 8) {
        // "yyyyMMdd".length() == 8
        year = Integer.parseInt(yearString.substring(0, 4));
        month = Integer.parseInt(yearString.substring(4, 6));
        day = Integer.parseInt(yearString.substring(6, 8));
      }
      if (((month < 1) || (month > 12)) || ((day < 1) || (day > 31))) {
        throw new IllegalDateFormatException(date);
      }
      // proceed time (and timezone)
      parseTime(parser, calendar, year, month, day);
    } catch (IllegalArgumentException e) {
      throw new IllegalDateFormatException(date);
    }
  }

}
