/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Calendar;
import java.util.regex.Pattern;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is a collection of utility functions for formatting and parsing dates according to ISO 8601 formats.
 * <br>
 * The ISO 8601 defines multiple formats for date and times. The following forms are handled by this
 * implementation:<br>
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
 * </table>
 * <br>
 * Please note that the special timezone character {@code Z} means UTC. <br>
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
 * As you can see by the example the basic format is harder to read (for humans). Therefore you should use the
 * extended format if possible. <br>
 * The {@link #parseCalendar(String) parse} methods support all formats described above. For
 * {@link #formatDateTime(Calendar) formatting} various methods exist for different format combinations. <br>
 * <b>ATTENTION:</b><br>
 * If you are using java 8+ please use {@code java.time} where everything is build in and APIs are clean.
 *
 * @see net.sf.mmm.util.date.base.Iso8601UtilImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface Iso8601Util extends Iso8601UtilLimited {

  /** The regex-pattern to check the format. */
  Pattern PATTERN_ALL = Pattern.compile(PATTERN_STRING_ALL);

  /**
   * This method formats the given {@code calendar} as a date in the format "yyyy-MM-dd" according to
   * {@link Iso8601Util ISO 8601}.
   *
   * @param calendar is the date to format.
   * @return the given {@code calendar} as date string.
   */
  String formatDate(Calendar calendar);

  /**
   * This method formats the given {@code calendar} as a date in the format "yyyy-MM-dd" according to
   * {@link Iso8601Util ISO 8601}.
   *
   * @param calendar is the date to format.
   * @return the given {@code calendar} as date string.
   * @param extended if {@code false} the basic format ("yyyyMMdd") is used, if {@code true} the extended
   *        format ("yyyy-MM-dd") is used.
   */
  String formatDate(Calendar calendar, boolean extended);

  /**
   * This method formats the given {@code calendar} as a date according to {@link Iso8601Util ISO 8601}.
   *
   * @param calendar is the date to format.
   * @param extended if {@code false} the basic date format ("yyyyMMdd") is used, if {@code true} the extended
   *        date format ("yyyy-MM-dd") is used.
   * @param buffer is where to append the formatted date.
   */
  void formatDate(Calendar calendar, boolean extended, Appendable buffer);

  /**
   * This method formats the given {@code calendar} as a date and time in the format
   * "yyyy-MM-ddTHH:mm:ss&#177;hh:mm" according to {@link Iso8601Util ISO 8601}.
   *
   * @param calendar is the date to format.
   * @return the given {@code calendar} as date string.
   */
  String formatDateTime(Calendar calendar);

  /**
   * This method formats the given {@code calendar} as a date and time according to {@link Iso8601Util ISO
   * 8601}.
   *
   * @param calendar is the {@link Calendar} to format.
   * @param extendedDate if {@code false} the basic date format ("yyyyMMdd") is used, if {@code true} the
   *        extended date format ("yyyy-MM-dd") is used.
   * @param extendedTime if {@code false} the basic time format ("HHmmss") is used, if {@code true} the
   *        extended time format ("HH:mm:ss") is used.
   * @param extendedTimezone if {@code false} the basic timezone format ("&#177;HHmm[ss]") is used, if
   *        {@code true} the extended timezone format ("&#177;HH:mm[:ss]") is used.
   * @return the given {@code calendar} as date string.
   */
  String formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime, boolean extendedTimezone);

  /**
   * This method formats the given {@code calendar} as a date and time according to {@link Iso8601Util ISO
   * 8601}.
   *
   * @param calendar is the {@link Calendar} to format.
   * @param extendedDate if {@code false} the basic date format ("yyyyMMdd") is used, if {@code true} the
   *        extended date format ("yyyy-MM-dd") is used.
   * @param extendedTime if {@code false} the basic time format ("HHmmss") is used, if {@code true} the
   *        extended time format ("HH:mm:ss") is used.
   * @param extendedTimezone if {@code false} the basic timezone format ("&#177;HHmm[ss]") is used, if
   *        {@code true} the extended timezone format ("&#177;HH:mm[:ss]") is used.
   * @param buffer is where to append the formatted date and time.
   */
  void formatDateTime(Calendar calendar, boolean extendedDate, boolean extendedTime, boolean extendedTimezone,
      Appendable buffer);

  /**
   * This method formats the given {@code calendar} as time according to {@link Iso8601Util ISO 8601}.
   *
   * @param calendar is the {@link Calendar} to format.
   * @param extended if {@code false} the basic time format ("HHmmss") is used, if {@code true} the extended
   *        time format ("HH:mm:ss") is used.
   * @param buffer is where to append the formatted date.
   */
  void formatTime(Calendar calendar, boolean extended, Appendable buffer);

  /**
   * This method formats the given {@code timezone} according to {@link Iso8601Util ISO 8601}. <br>
   *
   * @param calendar is the {@link Calendar} to format.
   * @param extended - if {@code false} the basic timezone format ("&#177;HHmm[ss]") is used, if {@code true}
   *        the extended timezone format ("&#177;HH:mm[:ss]") is used.
   * @param buffer is where to append the formatted timezone.
   * @since 2.0.0
   */
  void formatTimeZone(Calendar calendar, boolean extended, Appendable buffer);

  /**
   * This method parses the given string {@code date} according to {@link Iso8601Util ISO 8601}.
   *
   * @param date is the date to parse.
   * @return the parsed date.
   */
  Calendar parseCalendar(String date);

  /**
   * This method parses the given {@code date} according to {@link Iso8601Util ISO 8601} using the given
   * {@code calendar}. If the given {@code date} does NOT specify the time or timezone, the values from the
   * given {@code calendar} will be kept.
   *
   * @param date is the date to parse.
   * @param calendar is the calendar where the parsed date will be set.
   */
  void parseCalendar(String date, Calendar calendar);

}
