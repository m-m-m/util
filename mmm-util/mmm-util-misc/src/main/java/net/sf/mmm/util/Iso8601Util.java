/* $Id$ */
package net.sf.mmm.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import sun.util.calendar.ZoneInfo;

/**
 * This class is a collection of utility functions for {@link Date} handling and
 * manipulation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Iso8601Util {

  /** the default zimezone is GMT */
  private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

  /** the default zimezone is GMT */
  private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

  /**
   * The forbidden constructor
   */
  private Iso8601Util() {

  }

  /**
   * This method fromats the given <code>date</code> in the format
   * "yyyy-MM-dd" in GMT according to ISO 8601. This implementation is
   * thread-safe. It does NOT use {@link java.text.SimpleDateFormat} and
   * neigther requires nor performs any synchronization.
   * 
   * @param date
   *        is the date to format.
   * @return the given <code>date</code> as date string.
   */
  public static String formatDate(Date date) {

    Calendar calendar = Calendar.getInstance(GMT);
    calendar.setTime(date);
    return formatDate(calendar);
  }

  /**
   * This method fromats the given <code>calendar</code> as a date in the
   * format "yyyy-MM-dd" according to ISO 8601. This implementation is
   * thread-safe. It does NOT use {@link java.text.SimpleDateFormat} and
   * neigther requires nor performs any synchronization.
   * 
   * @param calendar
   *        is the date to format.
   * @return the given <code>calendar</code> as date string.
   */
  public static String formatDate(Calendar calendar) {

    // "yyyy-MM-dd".length() == 10
    StringBuffer buffer = new StringBuffer(10);
    formatDate(calendar, buffer);
    return buffer.toString();
  }

  /**
   * This method fromats the given <code>calendar</code> as a date in the
   * format "yyyy-MM-dd" according to ISO 8601. This implementation is
   * thread-safe. It does NOT use {@link java.text.SimpleDateFormat} and
   * neigther requires nor performs any synchronization.
   * 
   * @param calendar
   *        is the date to format.
   * @param buffer
   *        is where to append the formatted date.
   */
  private static void formatDate(Calendar calendar, StringBuffer buffer) {

    // year
    String year = String.valueOf(calendar.get(Calendar.YEAR));
    buffer.append(year);
    buffer.append('-');
    // month
    String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
    if (month.length() < 2) {
      buffer.append('0');
    }
    buffer.append(month);
    buffer.append('-');
    // day
    String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    if (day.length() < 2) {
      buffer.append('0');
    }
    buffer.append(day);
  }

  /**
   * This method fromats the given <code>date</code> as a GMT date and time in
   * the format "yyyy-MM-dd'T'HH:mm:ss" according to ISO 8601. This
   * implementation is thread-safe. It does NOT use
   * {@link java.text.SimpleDateFormat} and neigther requires nor performs any
   * synchronization.
   * 
   * @param date
   *        is the date to format.
   * @return the given <code>calendar</code> as date string.
   */
  public static String formatDateAndTime(Date date) {

    Calendar calendar = Calendar.getInstance(GMT);
    calendar.setTime(date);
    // "yyyy-MM-ddTHH:mm:ss".length() == 19
    StringBuffer buffer = new StringBuffer(19);
    formatDate(calendar, buffer);
    buffer.append('T');
    formatTime(calendar, buffer);
    return buffer.toString();
  }

  /**
   * This method fromats the given <code>calendar</code> as a date and time in
   * the format "yyyy-MM-dd'T'HH:mm:ssZ" but with timezone (Z) in the format
   * "[+|-]hh:mm" according to ISO 8601. This implementation is thread-safe. It
   * does NOT use {@link java.text.SimpleDateFormat} and neigther requires nor
   * performs any synchronization.
   * 
   * @param calendar
   *        is the date to format.
   * @return the given <code>calendar</code> as date string.
   */
  public static String formatDateAndTime(Calendar calendar) {

    // TODO: this is nuts!!!
    // !!!The date and time has to be converted to GMT!!!

    // "yyyy-MM-ddTHH:mm:ss+hh:ss".length() == 25
    StringBuffer buffer = new StringBuffer(25);
    formatDate(calendar, buffer);
    buffer.append('T');
    formatTime(calendar, buffer);
    formatTimeZone(calendar.getTimeZone(), buffer, true);
    return buffer.toString();
  }

  /**
   * This method fromats the given <code>calendar</code> as time in the format
   * "HH:mm:ss" according to ISO 8601. This implementation is thread-safe. It
   * does NOT use {@link java.text.SimpleDateFormat} and neigther requires nor
   * performs any synchronization.
   * 
   * @param calendar
   *        is the date to format.
   * @param buffer
   *        is where to append the formatted date.
   */
  public static void formatTime(Calendar calendar, StringBuffer buffer) {

    // append hours
    String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
    if (hour.length() < 2) {
      buffer.append('0');
    }
    buffer.append(hour);
    buffer.append(':');
    String minute = String.valueOf(calendar.get(Calendar.MINUTE));
    // append minutes
    if (minute.length() < 2) {
      buffer.append('0');
    }
    buffer.append(minute);
    buffer.append(':');
    // append seconds
    String second = String.valueOf(calendar.get(Calendar.SECOND));
    if (second.length() < 2) {
      buffer.append('0');
    }
    buffer.append(second);
  }

  /**
   * This method fromats the given <code>timezone</code> in the format
   * "[+|-]HHmm[ss]" (basic) or "[+|-]HH:mm[:ss]" (extended) according to ISO
   * 8601.<br>
   * This implementation is thread-safe. It does NOT use
   * {@link java.text.SimpleDateFormat} and neigther requires nor performs any
   * synchronization.
   * 
   * @param timezone
   *        is the date to format.
   * @param buffer
   *        is where to append the formatted timezone.
   * @param extended -
   *        if <code>false</code> the basic format ("[+|-]HHmm[ss]") is used,
   *        if <code>true</code> the extended format ("[+|-]HH:mm[:ss]") is
   *        used.
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
   * 
   * @param date
   * @return
   */
  public static Date parseDate(String date) {

    return parseCalendar(date).getTime();
  }

  /**
   * 
   * @param date
   * @return
   */
  public static Calendar parseCalendar(String date) {

    Calendar calendar = Calendar.getInstance(GMT);
    parseCalendar(date, calendar);
    return calendar;
  }

  /**
   * This method parses the timezone from the given <code>parser</code>.
   * 
   * @param parser
   *        is the parser pointing to the timezone or at the end of the string
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
      if ((hour < 0) || (hour > 23)) {
        throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
      }
      if ((minute < 0) || (minute > 59)) {
        throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
      }
      if ((second < 0) || (second > 59)) {
        throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
      }
      int timezoneOffset = ((((hour * 60) + minute) * 60) + second) * 1000;
      if (negate) {
        timezoneOffset = -timezoneOffset;
      }
      return new ZoneInfo("GMT", timezoneOffset);
    } else if (c == 0) {
      return null;
    } else if (c == 'Z') {
      // UTC
      return UTC;
    }
    throw new IllegalArgumentException("Illegal date-format \"" + parser.toString() + "\"!");
  }

  /**
   * 
   * @param date
   * @return
   */
  public static void parseCalendar(String date, Calendar calendar) {

    // "yyyy-MM-dd[THH:mm:ss[+hh:ss]]"
    // year: in format "yyyy-MM-dd" or "yyyyMMdd"
    // time: followed after year and separated by 'T' in format "HH:mm:ss"
    // if omitted, assumed as "00:00:00"
    // timzone: followed after time, "[+|-]HH:mm"
    // if omitted, assumed as "+00:00"
    StringParser parser = new StringParser(date);
    int year = 0;
    int month = -1;
    int day = -1;
    // proceed date
    // TODO: peek for +/-
    try {
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
              c = parser.forceNext();
            }
          }
        }
      } else if (yearString.length() == 8) {
        // "yyyyMMdd".length() == 8
        year = Integer.parseInt(yearString.substring(0, 4));
        month = Integer.parseInt(yearString.substring(4, 6));
        day = Integer.parseInt(yearString.substring(6, 8));
      } else {
        throw new IllegalArgumentException("Illegal date-format \"" + date + "\"!");
      }
      if ((month < 1) || (month > 12)) {
        throw new IllegalArgumentException("Illegal date-format \"" + date + "\"!");
      }
      if ((day < 1) || (day > 31)) {
        throw new IllegalArgumentException("Illegal date-format \"" + date + "\"!");
      }
      // proceed time
      boolean okay = false;
      int hour = 0;
      int minute = 0;
      int second = 0;
      TimeZone timeZone = null;
      if (c == 'T') {
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
                  timeZone = parseTimezone(parser);
                  okay = true;
                }
              }
            }
          }
        }
      } else if (c == 0) {
        okay = true;
      }
      if (!okay) {
        throw new IllegalArgumentException("Illegal date-format \"" + date + "\"!");
      }
      calendar.set(year, month - 1, day, hour, minute, second);
      if (timeZone != null) {
        calendar.setTimeZone(timeZone);
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal date-format \"" + date + "\"!", e);
    }
  }

  public static void main(String[] args) {

    System.out.println(formatDateAndTime(Calendar.getInstance()));
  }

}
