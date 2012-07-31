/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Date;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is a limited subset of {@link Iso8601Util} that is GWT compatible.
 * 
 * @see Iso8601Util
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
@ComponentSpecification
public interface Iso8601UtilLimited {

  /** The regex-pattern for the date-format. */
  String PATTERN_STRING_DATE = "([0-9][0-9][0-9][0-9])[-]?([0-1][0-9])[-]?([0-3][0-9])";

  /** The regex-pattern for the time-format. */
  String PATTERN_STRING_TIME = "([0-2][0-9])[:]?([0-5][0-9])[:]?([0-5][0-9])";

  /** The regex-pattern for the timezone-format. */
  String PATTERN_STRING_TIMEZONE = "(([+-])([0-2][0-9])[:]?([0-5][0-9])([:]?([0-5][0-9]))?|Z)";

  /**
   * The regex-pattern for the full date-format (date with optional time and optional timezone).
   */
  String PATTERN_STRING_ALL = PATTERN_STRING_DATE + "(T" + PATTERN_STRING_TIME + "(" + PATTERN_STRING_TIMEZONE + ")?)?";

  /**
   * This method formats the given <code>date</code> in the format "yyyy-MM-dd" in GMT according to
   * {@link Iso8601UtilLimited ISO 8601}.
   * 
   * @param date is the {@link Date} to format.
   * @return the given <code>date</code> as date string.
   * @since 1.0.2
   */
  String formatDate(Date date);

  /**
   * This method formats the given <code>calendar</code> as a date in the format "yyyy-MM-dd" according to
   * {@link Iso8601UtilLimited ISO 8601}.
   * 
   * @param date is the {@link Date} to format.
   * @return the given <code>calendar</code> as date string.
   * @param extended if <code>false</code> the basic format ("yyyyMMdd") is used, if <code>true</code> the
   *        extended format ("yyyy-MM-dd") is used.
   * @since 3.0.0
   */
  String formatDate(Date date, boolean extended);

  /**
   * This method formats the given <code>date</code> as a date according to {@link Iso8601UtilLimited ISO
   * 8601}.
   * 
   * @param date is the {@link Date} to format.
   * @param extended if <code>false</code> the basic date format ("yyyyMMdd") is used, if <code>true</code>
   *        the extended date format ("yyyy-MM-dd") is used.
   * @param buffer is where to append the formatted date.
   * @since 3.0.0
   */
  void formatDate(Date date, boolean extended, Appendable buffer);

  /**
   * This method formats the given <code>date</code> as a date and time in the format "yyyy-MM-ddTHH:mm:ssZ"
   * (UTC) according to {@link Iso8601UtilLimited ISO 8601}.
   * 
   * @param date is the date to format.
   * @return the given <code>calendar</code> as date string.
   * @since 1.0.2
   */
  String formatDateTime(Date date);

  /**
   * This method formats the given <code>date</code> as a date according to {@link Iso8601UtilLimited ISO
   * 8601}.
   * 
   * @param date is the {@link Date} to format.
   * @param extendedDate if <code>false</code> the basic date format ("yyyyMMdd") is used, if
   *        <code>true</code> the extended date format ("yyyy-MM-dd") is used.
   * @param extendedTime if <code>false</code> the basic time format ("HHmmss") is used, if <code>true</code>
   *        the extended time format ("HH:mm:ss") is used.
   * @param extendedTimezone if <code>false</code> the basic timezone format ("&#177;HHmm[ss]") is used, if
   *        <code>true</code> the extended timezone format ("&#177;HH:mm[:ss]") is used.
   * @return the given <code>calendar</code> as date string.
   * @since 3.0.0
   */
  String formatDateTime(Date date, boolean extendedDate, boolean extendedTime, boolean extendedTimezone);

  /**
   * This method formats the given <code>date</code> as a date and time according to
   * {@link Iso8601UtilLimited ISO 8601}.
   * 
   * @param date is the {@link Date} to format.
   * @param extendedDate if <code>false</code> the basic date format ("yyyyMMdd") is used, if
   *        <code>true</code> the extended date format ("yyyy-MM-dd") is used.
   * @param extendedTime if <code>false</code> the basic time format ("HHmmss") is used, if <code>true</code>
   *        the extended time format ("HH:mm:ss") is used.
   * @param extendedTimezone if <code>false</code> the basic timezone format ("&#177;HHmm[ss]") is used, if
   *        <code>true</code> the extended timezone format ("&#177;HH:mm[:ss]") is used.
   * @param buffer is where to append the formatted date and time.
   * @since 3.0.0
   */
  void formatDateTime(Date date, boolean extendedDate, boolean extendedTime, boolean extendedTimezone, Appendable buffer);

  /**
   * This method formats the given <code>date</code> as time according to {@link Iso8601UtilLimited ISO 8601}.
   * 
   * @param date is the {@link Date} to format.
   * @param extended if <code>false</code> the basic time format ("HHmmss") is used, if <code>true</code> the
   *        extended time format ("HH:mm:ss") is used.
   * @param buffer is where to append the formatted date.
   * @since 3.0.0
   */
  void formatTime(Date date, boolean extended, Appendable buffer);

  /**
   * This method formats the given <code>timezone</code> according to {@link Iso8601Util ISO 8601}.<br>
   * 
   * @param timezoneOffset is the timezone-offset in milliseconds.
   * @param extended - if <code>false</code> the basic timezone format ("&#177;HHmm[ss]") is used, if
   *        <code>true</code> the extended timezone format ("&#177;HH:mm[:ss]") is used.
   * @param buffer is where to append the formatted timezone.
   */
  void formatTimeZone(int timezoneOffset, boolean extended, Appendable buffer);

  /**
   * This method parses the given string <code>date</code> according to {@link Iso8601UtilLimited ISO 8601}.
   * 
   * @param date is the date to parse.
   * @return the parsed date.
   * @since 1.0.2
   */
  Date parseDate(String date);

}
