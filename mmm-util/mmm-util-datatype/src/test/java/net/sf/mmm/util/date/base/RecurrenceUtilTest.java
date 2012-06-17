/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import java.util.Calendar;

import net.sf.mmm.util.datatype.api.date.DateUnit;
import net.sf.mmm.util.datatype.api.date.WeekOfMonth;
import net.sf.mmm.util.datatype.api.date.Weekday;
import net.sf.mmm.util.datatype.api.date.WeekdaySet;
import net.sf.mmm.util.date.api.Recurrence;
import net.sf.mmm.util.date.api.RecurrenceUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RecurrenceUtilTest {

  /**
   * @return the {@link RecurrenceUtil} to test.
   */
  protected RecurrenceUtil getRecurrenceUtil() {

    return RecurrenceUtilImpl.getInstance();
  }

  /**
   * Test for {@link RecurrenceUtil#getNextDate(Calendar, Recurrence)}.
   */
  @Test
  public void testGetNextDate() {

    RecurrenceUtil util = getRecurrenceUtil();

    // next day..
    Recurrence recurrence = new RecurrenceBean(DateUnit.DAY, 1, WeekdaySet.ALL, null);
    Calendar calendar = Calendar.getInstance();
    calendar.set(2000, Calendar.JANUARY, 1, 23, 59, 59);
    Calendar next = util.getNextDate(calendar, recurrence);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    Assert.assertEquals(calendar, next);

    // next Saturday... (current calendar is Sunday)
    Assert.assertEquals(Weekday.SUNDAY, Weekday.fromCalendar(calendar));
    recurrence = new RecurrenceBean(DateUnit.DAY, 1, new WeekdaySet(Weekday.SATURDAY), null);
    next = util.getNextDate(calendar, recurrence);
    calendar.add(Calendar.DAY_OF_MONTH, 6);
    Assert.assertEquals(calendar, next);

    // first Monday in 2 months...
    recurrence = new RecurrenceBean(DateUnit.MONTH, 2, new WeekdaySet(Weekday.WEDNESDAY),
        WeekOfMonth.FIRST);
    next = util.getNextDate(calendar, recurrence);
    calendar.set(2000, Calendar.MARCH, 1);
    Assert.assertEquals(calendar, next);

    // first Sunday of month in one year...
    recurrence = new RecurrenceBean(DateUnit.YEAR, 1, new WeekdaySet(Weekday.SUNDAY),
        WeekOfMonth.LAST);
    next = util.getNextDate(calendar, recurrence);
    calendar.set(2001, Calendar.MARCH, 25);
    Assert.assertEquals(calendar, next);
  }

}
