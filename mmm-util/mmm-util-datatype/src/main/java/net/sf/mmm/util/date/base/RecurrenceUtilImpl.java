/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.datatype.api.date.DateUnit;
import net.sf.mmm.util.datatype.api.date.WeekOfMonth;
import net.sf.mmm.util.datatype.api.date.Weekday;
import net.sf.mmm.util.datatype.api.date.WeekdaySet;
import net.sf.mmm.util.date.api.Recurrence;
import net.sf.mmm.util.date.api.RecurrenceUtil;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.date.api.RecurrenceUtil} interface.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class RecurrenceUtilImpl extends AbstractLoggableComponent implements RecurrenceUtil {

  /** @see #getInstance() */
  private static RecurrenceUtil instance;

  /**
   * The constructor.
   */
  public RecurrenceUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link RecurrenceUtil}.<br>
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
  public static RecurrenceUtil getInstance() {

    if (instance == null) {
      synchronized (RecurrenceUtilImpl.class) {
        if (instance == null) {
          RecurrenceUtilImpl impl = new RecurrenceUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  public Date getNextDate(Date date, Recurrence recurrence) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    Calendar result = getNextDate(calendar, recurrence);
    return result.getTime();
  }

  /**
   * {@inheritDoc}
   */
  public Calendar getNextDate(Calendar calendar, Recurrence recurrence) {

    Calendar result = Calendar.getInstance();
    result.setTimeZone(calendar.getTimeZone());
    result.setTime(calendar.getTime());
    DateUnit unit = recurrence.getUnit();
    NlsNullPointerException.checkNotNull(DateUnit.class, unit);
    int step = recurrence.getStep();
    ValueOutOfRangeException.checkRange(Integer.valueOf(step), Integer.valueOf(1),
        Integer.valueOf(Integer.MAX_VALUE), recurrence);
    result.add(unit.getCalendarId(), step);
    WeekdaySet weekdaySet = recurrence.getWeekdays();
    if (weekdaySet == null) {
      weekdaySet = WeekdaySet.ALL;
    }
    Weekday weekday;
    switch (unit) {
      case DAY:
        weekday = Weekday.fromCalendar(result);
        if (!weekdaySet.contains(weekday)) {
          int add = 0;
          int count = 0;
          do {
            count++;
            if (count == 7) {
              // avoid infinity loop, illegal recurrence for start date
              return null;
            }
            add = add + step;
            weekday = weekday.add(step);
          } while (!weekdaySet.contains(weekday));
          result.add(unit.getCalendarId(), add);
        }
        break;
      case WEEK:
        // nothing to do...
        break;
      case MONTH:
      case YEAR:
        WeekOfMonth weekOfMonth = recurrence.getWeekOfMonth();
        int dayOfMonth = 1;
        if (weekOfMonth != null) {
          if (weekdaySet.getSet().isEmpty()) {
            return null;
          }
          if (weekOfMonth == WeekOfMonth.LAST) {
            dayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH);
            result.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            weekday = Weekday.fromCalendarId(result.get(Calendar.DAY_OF_WEEK));
            while (!weekdaySet.contains(weekday)) {
              weekday = weekday.getPrevious();
              dayOfMonth--;
            }
          } else {
            result.set(Calendar.DAY_OF_MONTH, 1);
            weekday = Weekday.fromCalendarId(result.get(Calendar.DAY_OF_WEEK));
            dayOfMonth = dayOfMonth + (weekOfMonth.ordinal() * 7);
            while (!weekdaySet.contains(weekday)) {
              weekday = weekday.getNext();
              dayOfMonth++;
            }
          }
          result.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
        break;
      default :
        throw new IllegalCaseException(DateUnit.class, unit);
    }

    return result;
  }
}
