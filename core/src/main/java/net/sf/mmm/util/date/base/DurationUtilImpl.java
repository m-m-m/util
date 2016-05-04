/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.date.api.DurationUtil;

/**
 * This is the implementation of {@link DurationUtil}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Singleton
@Named(DurationUtil.CDI_NAME)
public class DurationUtilImpl implements DurationUtil {

  private  static DurationUtil instance;

  /**
   * The constructor.
   */
  public DurationUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of {@link DurationUtil}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static DurationUtil getInstance() {

    if (instance == null) {
      synchronized (DurationUtilImpl.class) {
        if (instance == null) {
          instance = new DurationUtilImpl();
        }
      }
    }
    return instance;
  }

  @Override
  public String formatNanoseconds(long nanos) {

    return formatDuration(nanos, 1000000000);
  }

  @Override
  public String formatMilliseconds(long millis) {

    return formatDuration(millis, 1000);
  }

  @Override
  public String formatSeconds(long seconds) {

    return formatDuration(seconds, 1);
  }

  /**
   * @param duration is the duration to format.
   * @param unitPerSeconds is the number of units per second ({@code duration/unitsPerSecond} is the duration in seconds).
   * @return the formatted duration.
   */
  private String formatDuration(long duration, long unitPerSeconds) {

    StringBuilder buffer = new StringBuilder();
    long secondFraction = duration % unitPerSeconds;
    long rest = duration / unitPerSeconds;
    long seconds = rest % 60;
    rest = rest / 60;
    long minutes = rest % 60;
    rest = rest / 60;
    long hours = rest % 24;
    long days = rest / 24;
    if (days > 0) {
      buffer.append(days);
      buffer.append('D');
    }
    appendSegment(buffer, hours, false);
    appendSegment(buffer, minutes, false);
    appendSegment(buffer, seconds, true);
    if (secondFraction > 0) {
      buffer.append('.');
      String fraction = Long.toString(unitPerSeconds + secondFraction).substring(1);
      buffer.append(fraction);
    }
    return buffer.toString();
  }

  /**
   * @param buffer is the {@link StringBuilder} to {@link StringBuilder#append(String) append} to.
   * @param segment is the segment to append (hours, minutes, or seconds).
   * @param seconds - {@code true} if segment are seconds, {@code false} otherwise.
   */
  private void appendSegment(StringBuilder buffer, long segment, boolean seconds) {

    if (segment > 0) {
      if ((segment < 10) && (buffer.length() > 0)) {
        buffer.append('0');
      }
      buffer.append(segment);
    } else if (buffer.length() > 0) {
      buffer.append("00");
    } else if (seconds) {
      buffer.append("0");
    } else {
      return;
    }
    if (!seconds) {
      buffer.append(':');
    }
  }

}
