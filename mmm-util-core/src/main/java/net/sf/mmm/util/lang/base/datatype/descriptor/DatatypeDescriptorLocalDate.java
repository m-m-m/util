/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.time.LocalDate;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.DatatypeDescriptor} for {@link LocalDate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
// @Named
public class DatatypeDescriptorLocalDate extends AbstractDatatypeDescriptor<LocalDate> {

  /**
   * The constructor.
   */
  public DatatypeDescriptorLocalDate() {

    super(LocalDate.class, new YearSegment(), new MonthSegment(), new DaySegment());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected LocalDate doCreate(Object... segments) {

    int year = ((Integer) segments[0]).intValue();
    int month = ((Integer) segments[1]).intValue();
    int day = ((Integer) segments[2]).intValue();
    return LocalDate.of(year, month, day);
  }

  /** Implementation for {@link LocalDate#getYear()}. */
  private static class YearSegment extends AbstractDatatypeSegmentDescriptor<LocalDate, Integer> {

    /**
     * The constructor.
     */
    public YearSegment() {

      super("year", Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer doGetSegment(LocalDate datatype) {

      return Integer.valueOf(datatype.getYear());
    }

  }

  /** Implementation for {@link LocalDate#getMonthValue()}. */
  private static class MonthSegment extends AbstractDatatypeSegmentDescriptor<LocalDate, Integer> {

    /**
     * The constructor.
     */
    public MonthSegment() {

      super("month", Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer doGetSegment(LocalDate datatype) {

      return Integer.valueOf(datatype.getMonthValue());
    }

  }

  /** Implementation for {@link LocalDate#getDayOfMonth()}. */
  private static class DaySegment extends AbstractDatatypeSegmentDescriptor<LocalDate, Integer> {

    /**
     * The constructor.
     */
    public DaySegment() {

      super("day", Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer doGetSegment(LocalDate datatype) {

      return Integer.valueOf(datatype.getDayOfMonth());
    }

  }
}
