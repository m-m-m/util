/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

/**
 * This is a collection of utility functions for dealing with time and durations.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface DurationUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.date.api.DurationUtil";

  /**
   * This method formats the given nanoseconds as a human readable {@link String}.
   * <table border="1">
   * <tr bgcolor="#ccccff">
   * <th>nanos</th>
   * <th>{@link #formatNanoseconds(long) formatNanoseconds(nanos)}</th>
   * </tr>
   * <tr>
   * <td>1</td>
   * <td>"0.000000001"</td>
   * </tr>
   * <tr>
   * <td>61000000000</td>
   * <td>"1:01"</td>
   * </tr>
   * <tr>
   * <td>7141123456789"</td>
   * <td>"1:59:01.123456789"</td>
   * </tr>
   * <tr>
   * <td>1234567890123456"</td>
   * <td>"14D06:47:07.890123456"</td>
   * </tr>
   * </table>
   *
   * @param nanos is a duration in nanoseconds.
   * @return a human readable duration.
   */
  String formatNanoseconds(long nanos);

  /**
   * This method formats the given milliseconds as a human readable {@link String}.
   * <table border="1">
   * <tr bgcolor="#ccccff">
   * <th>millis</th>
   * <th>{@link #formatMilliseconds(long) formatMilliseconds(millis)}</th>
   * </tr>
   * <tr>
   * <td>1</td>
   * <td>"0.001"</td>
   * </tr>
   * <tr>
   * <td>61000</td>
   * <td>"1:01"</td>
   * </tr>
   * <tr>
   * <td>7141123"</td>
   * <td>"1:59:01.123"</td>
   * </tr>
   * <tr>
   * <td>1234567890"</td>
   * <td>"14D06:47:07.890"</td>
   * </tr>
   * </table>
   *
   * @param millis is a duration in milliseconds.
   * @return a human readable duration.
   */
  String formatMilliseconds(long millis);

  /**
   * This method formats the given seconds as a human readable {@link String}.
   * <table border="1">
   * <tr bgcolor="#ccccff">
   * <th>seconds</th>
   * <th>{@link #formatSeconds(long) formatSeconds(seconds)}</th>
   * </tr>
   * <tr>
   * <td>1</td>
   * <td>"1"</td>
   * </tr>
   * <tr>
   * <td>61</td>
   * <td>"1:01"</td>
   * </tr>
   * <tr>
   * <td>7141"</td>
   * <td>"1:59:01"</td>
   * </tr>
   * <tr>
   * <td>1234567"</td>
   * <td>"14D06:56:07"</td>
   * </tr>
   * </table>
   *
   * @param seconds is a duration in seconds.
   * @return a human readable duration.
   */
  String formatSeconds(long seconds);

}
