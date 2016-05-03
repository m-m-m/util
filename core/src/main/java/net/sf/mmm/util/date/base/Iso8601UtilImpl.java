/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
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
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;

/**
 * This is the implementation of the {@link net.sf.mmm.util.date.api.Iso8601Util} interface. It does NOT use
 * {@link java.text.SimpleDateFormat}. All methods of this class are fast and thread-safe. <br>
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(Iso8601Util.CDI_NAME)
public final class Iso8601UtilImpl extends Iso8601UtilLimitedImpl implements Iso8601Util {

  /** @see #getInstance() */
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
   * This method gets the singleton instance of this {@link Iso8601UtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
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

  @Override
  public void formatDate(Date date, boolean extended, Appendable buffer) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    formatDate(calendar, extended, buffer);
  }

  @Override
  public String formatDate(Calendar calendar) {

    return formatDate(calendar, true);
  }

  @Override
  public String formatDate(Calendar calendar, boolean extended) {

    // we could save 2*2 bytes here according to extended flag ;)
    // "yyyy-MM-dd".length() == 10
    StringBuilder buffer = new StringBuilder(10);
    formatDate(calendar, extended, buffer);
    return buffer.toString();
  }

  @Override
  public void formatDate(Calendar calendar, boolean extended, Appendable buffer) {

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    formatDate(year, month, day, extended, buffer);
  }

  @Override
  public void formatDateTime(Date date, boolean extendedDate, boolean extendedTime, boolean extendedTimeZone,
      Appendable buffer) {

    try {
      // TODO use timezone offset from date?
      Calendar calendar = Calendar.getInstance(TZ_UTC);
      calendar.setTime(date);
      formatDate(calendar, extendedDate, buffer);
      buffer.append('T');
      formatTime(calendar, extendedTime, buffer);
      buffer.append('Z');
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public String formatDateTime(Calendar calendar) {

    return formatDateTime(calendar, true, true, true);
  }

  @Override
  public String formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime, boolean extendedTimezone) {

    // "yyyy-MM-ddTHH:mm:ss+hh:ss".length() == 25
    StringBuilder buffer = new StringBuilder(25);
    formatDateTime(calendar, extendedDate, extendedTime, extendedTimezone, buffer);
    return buffer.toString();
  }

  @Override
  public void formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime, boolean extendedTimezone,
      Appendable buffer) {

    try {
      formatDate(calendar, extendedDate, buffer);
      buffer.append('T');
      formatTime(calendar, extendedTime, buffer);
      formatTimeZone(calendar, extendedTimezone, buffer);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void formatTime(Calendar calendar, boolean extended, Appendable buffer) {

    int hours = calendar.get(Calendar.HOUR_OF_DAY);
    int minutes = calendar.get(Calendar.MINUTE);
    int seconds = calendar.get(Calendar.SECOND);
    formatTime(hours, minutes, seconds, extended, buffer);
  }

  @Override
  public void formatTimeZone(Calendar calendar, boolean extended, Appendable buffer) {

    TimeZone timezone = calendar.getTimeZone();
    int timezoneOffset = timezone.getOffset(calendar.getTimeInMillis());
    formatTimeZone(timezoneOffset, extended, buffer);
  }

  @Override
  public Date parseDate(String date) {

    return parseCalendar(date).getTime();
  }

  @Override
  public Calendar parseCalendar(String date) {

    Calendar calendar = Calendar.getInstance();
    calendar.clear();
    parseCalendar(date, calendar);
    return calendar;
  }

  /**
   * This method parses the timezone from the given {@code parser}.
   *
   * @param scanner is the parser pointing to the timezone or at the end of the string
   * @return the parsed timezone or {@code null} if parser already at the end of the string.
   */
  private TimeZone parseTimezone(CharSequenceScanner scanner) {

    int pos = scanner.getCurrentIndex();
    Integer offset = parseTimezoneOffset(scanner);
    // has offset?
    if (offset != null) {
      int offsetMs = offset.intValue();
      String tzName = "GMT";
      if (offsetMs != 0) {
        if (offsetMs < 0) {
          tzName += "-";
        } else {
          tzName += "+";
        }
        tzName += scanner.substring(pos, scanner.getCurrentIndex());
      }
      return new SimpleTimeZone(offsetMs, tzName);
    } else if (scanner.getCurrentIndex() == pos) {
      return null;
    } else {
      // UTC
      return TZ_UTC;
    }
  }

  @Override
  public void parseCalendar(String date, Calendar calendar) {

    CharSequenceScanner scanner = new CharSequenceScanner(date);
    // proceed date
    try {
      int[] ymd = parseDate(scanner);
      int year = ymd[0];
      int month = ymd[1];
      int day = ymd[2];
      // proceed time (and timezone)
      char c = scanner.forceNext();
      if (c == 'T') {
        int[] hms = parseTime(scanner);
        int hour = hms[0];
        int minute = hms[1];
        int second = hms[2];
        TimeZone timeZone = parseTimezone(scanner);
        if (timeZone != null) {
          calendar.setTimeZone(timeZone);
        }
        calendar.set(year, month - 1, day, hour, minute, second);
      } else if (c == 0) {
        calendar.set(year, month - 1, day);
      } else {
        throw new NlsParseException(scanner.toString(), PATTERN_STRING_TIME, "time", scanner.toString());
      }
      calendar.set(Calendar.MILLISECOND, 0);
    } catch (Exception e) {
      throw new IllegalDateFormatException(date, e);
    }
  }

}
