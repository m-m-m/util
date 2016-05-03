/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.util.Date;

import javax.inject.Named;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.DatatypeDescriptor} for {@link Date}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
@Named
@SuppressWarnings("deprecation")
public class DatatypeDescriptorDate extends AbstractDatatypeDescriptor<Date> {

  /**
   * The constructor.
   */
  public DatatypeDescriptorDate() {

    super(Date.class, new YearSegment(), new MonthSegment(), new DaySegment(), new HourSegment(),
        new MinuteSegment(), new SecondSegment());
  }

  @Override
  protected Date doCreate(Object... segments) {

    int year = ((Integer) segments[0]).intValue();
    int month = ((Integer) segments[1]).intValue();
    int day = ((Integer) segments[2]).intValue();
    if (segments.length > 3) {
      int hour = ((Integer) segments[3]).intValue();
      int minute = 0;
      int second = 0;
      if (segments.length > 4) {
        minute = ((Integer) segments[4]).intValue();
        if (segments.length > 5) {
          second = ((Integer) segments[5]).intValue();
        }
      }
      return new Date(year - 1900, month - 1, day, hour, minute, second);
    } else {
      return new Date(year - 1900, month - 1, day);
    }
  }

  /** Implementation for {@link Date#getYear()}. */
  private static class YearSegment extends AbstractDatatypeSegmentDescriptor<Date, Integer> {

    /**
     * The constructor.
     */
    public YearSegment() {

      super("year", Integer.class);
    }

    @Override    protected Integer doGetSegment(Date datatype) {

      return Integer.valueOf(datatype.getYear() + 1900);
    }

  }

  /** Implementation for {@link Date#getMonth()}. */
  private static class MonthSegment extends AbstractDatatypeSegmentDescriptor<Date, Integer> {

    /**
     * The constructor.
     */
    public MonthSegment() {

      super("month", Integer.class);
    }

    @Override    protected Integer doGetSegment(Date datatype) {

      return Integer.valueOf(datatype.getMonth() + 1);
    }

  }

  /** Implementation for {@link Date#getDate()}. */
  private static class DaySegment extends AbstractDatatypeSegmentDescriptor<Date, Integer> {

    /**
     * The constructor.
     */
    public DaySegment() {

      super("day", Integer.class);
    }

    @Override    protected Integer doGetSegment(Date datatype) {

      return Integer.valueOf(datatype.getDate());
    }

  }

  /** Implementation for {@link Date#getHours()}. */
  private static class HourSegment extends AbstractDatatypeSegmentDescriptor<Date, Integer> {

    /**
     * The constructor.
     */
    public HourSegment() {

      super("hour", Integer.class, true);
    }

    @Override    protected Integer doGetSegment(Date datatype) {

      return Integer.valueOf(datatype.getHours());
    }

  }

  /** Implementation for {@link Date#getMinutes()}. */
  private static class MinuteSegment extends AbstractDatatypeSegmentDescriptor<Date, Integer> {

    /**
     * The constructor.
     */
    public MinuteSegment() {

      super("minute", Integer.class, true);
    }

    @Override    protected Integer doGetSegment(Date datatype) {

      return Integer.valueOf(datatype.getMinutes());
    }

  }

  /** Implementation for {@link Date#getSeconds()}. */
  private static class SecondSegment extends AbstractDatatypeSegmentDescriptor<Date, Integer> {

    /**
     * The constructor.
     */
    public SecondSegment() {

      super("second", Integer.class, true);
    }

    @Override    protected Integer doGetSegment(Date datatype) {

      return Integer.valueOf(datatype.getSeconds());
    }

  }
}
