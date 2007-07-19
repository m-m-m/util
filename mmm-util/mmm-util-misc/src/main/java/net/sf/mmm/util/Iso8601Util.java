/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import net.sf.mmm.util.filter.CharFilter;

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

  /** The ID for UTC (Coordinated Universal Time). */
  private static final String UTC_ID = "UTC";

  /** The UTC TimeZone. */
  private static final TimeZone TZ_UTC = TimeZone.getTimeZone(UTC_ID);
  
  /**
   * The forbidden constructor.
   */
  private Iso8601Util() {

  }

  /**
   * This method formats the given <code>date</code> in the format
   * "yyyy-MM-dd" in GMT according to {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to format.
   * @return the given <code>date</code> as date string.
   */
  public static String formatDate(Date date) {

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
  public static String formatDate(Calendar calendar) {

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
  public static String formatDate(Calendar calendar, boolean extended) {

    // we could save 2*2 bytes here according to extended flag ;)
    // "yyyy-MM-dd".length() == 10
    StringBuffer buffer = new StringBuffer(10);
    formatDate(calendar, buffer, extended);
    return buffer.toString();
  }

  /**
   * This method formats the given <code>calendar</code> as a date according
   * to {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @param buffer is where to append the formatted date.
   * @param extended if <code>false</code> the basic date format ("yyyyMMdd")
   *        is used, if <code>true</code> the extended date format
   *        ("yyyy-MM-dd") is used.
   */
  private static void formatDate(Calendar calendar, StringBuffer buffer, boolean extended) {

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
  }

  /**
   * This method formats the given <code>date</code> as a date and time in the
   * format "yyyy-MM-ddTHH:mm:ssZ" (UTC) according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to format.
   * @return the given <code>calendar</code> as date string.
   */
  public static String formatDateTime(Date date) {

    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC_ID));
    calendar.setTime(date);
    // "yyyy-MM-ddTHH:mm:ssZ".length() == 20
    StringBuffer buffer = new StringBuffer(20);
    formatDate(calendar, buffer, true);
    buffer.append('T');
    formatTime(calendar, buffer, true);
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
  public static String formatDateTime(Calendar calendar) {

    return formatDateTime(calendar, true, true, true);
  }

  /**
   * This method formats the given <code>calendar</code> as a date and time in
   * the format "yyyy-MM-ddTHH:mm:ss&#177;hh:mm" according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @return the given <code>calendar</code> as date string.
   * @param extendedDate if <code>false</code> the basic date format
   *        ("yyyyMMdd") is used, if <code>true</code> the extended date
   *        format ("yyyy-MM-dd") is used.
   * @param extendedTime if <code>false</code> the basic time format
   *        ("HHmmss") is used, if <code>true</code> the extended time format
   *        ("HH:mm:ss") is used.
   * @param extendedTimezone if <code>false</code> the basic timzone format
   *        ("&#177;HHmm[ss]") is used, if <code>true</code> the extended
   *        timezone format ("&#177;HH:mm[:ss]") is used.
   */
  public static String formatDateTime(Calendar calendar, boolean extendedDate,
      boolean extendedTime, boolean extendedTimezone) {

    // "yyyy-MM-ddTHH:mm:ss+hh:ss".length() == 25
    StringBuffer buffer = new StringBuffer(25);
    formatDate(calendar, buffer, extendedDate);
    buffer.append('T');
    formatTime(calendar, buffer, extendedTime);
    formatTimeZone(calendar.getTimeZone(), buffer, extendedTimezone);
    return buffer.toString();
  }

  /**
   * This method formats the given <code>calendar</code> as time according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param calendar is the date to format.
   * @param buffer is where to append the formatted date.
   * @param extended if <code>false</code> the basic time format ("HHmmss") is
   *        used, if <code>true</code> the extended time format ("HH:mm:ss")
   *        is used.
   */
  public static void formatTime(Calendar calendar, StringBuffer buffer, boolean extended) {

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
  }

  /**
   * This method formats the given <code>timezone</code> according to
   * {@link Iso8601Util ISO 8601}.<br>
   * 
   * @param timezone is the date to format.
   * @param buffer is where to append the formatted timezone.
   * @param extended - if <code>false</code> the basic timzone format
   *        ("&#177;HHmm[ss]") is used, if <code>true</code> the extended
   *        timezone format ("&#177;HH:mm[:ss]") is used.
   */
  public static void formatTimeZone(TimeZone timezone, StringBuffer buffer, boolean extended) {

    int offsetSeconds = timezone.getRawOffset() / 1000;
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
  }

  /**
   * This method parses the given string <code>date</code> according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to parse.
   * @return the parsed date.
   */
  public static Date parseDate(String date) {

    return parseCalendar(date).getTime();
  }

  /**
   * This method parses the given string <code>date</code> according to
   * {@link Iso8601Util ISO 8601}.
   * 
   * @param date is the date to parse.
   * @return the parsed date.
   */
  public static Calendar parseCalendar(String date) {

    Calendar calendar = Calendar.getInstance();
    parseCalendar(date, calendar);
    return calendar;
  }

  /**
   * This method parses the timezone from the given <code>parser</code>.
   * 
   * @param parser is the parser pointing to the timezone or at the end of the
   *        string
   * @return the parsed timezone or <code>null</code> if parser already at the
   *         end of the string.
   */
  private static TimeZone parseTimezone(StringParser parser) {

    char c = parser.forceNext();
    if ((c == '+') || (c == '-')) {
      boolean negate = (c == '-');
      int hour = -1;
      int minute = -1;
      int second = -1;
      String hourString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
      if (hourString.length() == 2) {
        // format is +/-hh[:mm[:ss]]
        hour = Integer.parseInt(hourString);
        c = parser.forceNext();
        if (c == ':') {
          String minuteString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
          if (minuteString.length() == 2) {
            // format is +/-hh:mm[:ss]
            minute = Integer.parseInt(minuteString);
            c = parser.forceNext();
            if (c == ':') {
              String secondString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
              if (secondString.length() == 2) {
                // format is +/-hh:mm:ss
                second = Integer.parseInt(secondString);
              }
            } else if (c == 0) {
              // format is +/-hh:mm
              second = 0;
            }
          }
        } else if (c == 0) {
          // format is +/-hh
          minute = 0;
          second = 0;
        }
      } else if (hourString.length() == 4) {
        // format is +/-hhmm
        hour = Integer.parseInt(hourString.substring(0, 2));
        minute = Integer.parseInt(hourString.substring(2, 4));
        second = 0;
      } else if (hourString.length() == 6) {
        // format is +/-hhmmss
        hour = Integer.parseInt(hourString.substring(0, 2));
        minute = 0;
        second = 0;
      }
      if (((hour < 0) || (hour > 23)) || ((minute < 0) || (minute > 59))
          || ((second < 0) || (second > 59))) {
        throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
      }
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
      //return new ZoneInfo("GMT", timezoneOffset);
    } else if (c == 0) {
      return null;
    } else if (c == 'Z') {
      // UTC
      return TZ_UTC;
    }
    throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
  }

  /**
   * This method parses the time (and timezone) from the given
   * <code>parser</code> and sets it to the given <code>calendar</code>
   * including <code>year</code>, <code>month</code> and <code>date</code>.
   * 
   * @param parser is the parser pointing to the time or at the end of the
   *        string
   * @param calendar is the calendar where the parsed date and time will be set.
   * @param year is the year to set that has already been parsed.
   * @param month is the month to set that has already been parsed (in the range
   *        of 1-12).
   * @param day is the day to set that has already been parsed.
   */
  private static void parseTime(StringParser parser, Calendar calendar, int year, int month, int day) {

    char c = parser.forceNext();
    if (c == 'T') {
      int hour = 0;
      int minute = 0;
      int second = -1;
      String hourString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
      if (hourString.length() == 2) {
        hour = Integer.parseInt(hourString);
        c = parser.forceNext();
        if (c == ':') {
          String minuteString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
          if (minuteString.length() == 2) {
            minute = Integer.parseInt(minuteString);
            c = parser.forceNext();
            if (c == ':') {
              String secondString = parser.readWhile(CharFilter.LATIN_DIGIT_FILTER);
              if (secondString.length() == 2) {
                second = Integer.parseInt(secondString);
                TimeZone timeZone = parseTimezone(parser);
                if (timeZone != null) {
                  calendar.setTimeZone(timeZone);
                }
              }
            } else if (c == 0) {
              second = 0;
            }
          }
        } else if (c == 0) {
          minute = 0;
          second = 0;
        }
      } else if (hourString.length() == 4) {
        hour = Integer.parseInt(hourString.substring(0, 2));
        minute = Integer.parseInt(hourString.substring(2, 4));
        second = 0;
      } else if (hourString.length() == 6) {
        hour = Integer.parseInt(hourString.substring(0, 2));
        minute = Integer.parseInt(hourString.substring(2, 4));
        second = Integer.parseInt(hourString.substring(4, 6));
      }
      if (((hour < 0) || (hour > 23)) || ((minute < 0) || (minute > 59))
          || ((second < 0) || (second > 59))) {
        throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
      }
      calendar.set(year, month - 1, day, hour, minute, second);
    } else if (c == 0) {
      calendar.set(year, month - 1, day);
    } else {
      throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
    }
    calendar.set(Calendar.MILLISECOND, 0);
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
  public static void parseCalendar(String date, Calendar calendar) {

    StringParser parser = new StringParser(date);
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
        throw new IllegalArgumentException("Illegal date-format \"" + date + "\"!");
      }
      // proceed time (and timezone)
      parseTime(parser, calendar, year, month, day);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal date-format \"" + date + "\"!", e);
    }
  }

}
