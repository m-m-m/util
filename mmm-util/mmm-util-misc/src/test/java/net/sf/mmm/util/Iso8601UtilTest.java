/* $Id$ */
package net.sf.mmm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is the test-case for {@link DateUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class Iso8601UtilTest extends TestCase {

  /**
   * The constructor
   */
  public Iso8601UtilTest() {

    super();
  }
  
  private void dump(Date date) {
    System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date));
  }

  public void checkCombined(String date) {

    Calendar calendar = Iso8601Util.parseCalendar(date);
    String newDate = Iso8601Util.formatDateTime(calendar);
    assertEquals(date, newDate);
  }

  @Test
  public void testExtendedFormat() {

    checkCombined("1999-12-31T23:59:59+01:00");
    checkCombined("2000-01-01T00:00:00+00:00");
    checkCombined("2000-01-01T00:00:00-02:00");
    checkCombined("2000-01-01T00:00:00-00:30");
    Calendar calendar = Iso8601Util.parseCalendar("2007-01-31T11:22:33Z");
    assertEquals(2007, calendar.get(Calendar.YEAR));
    assertEquals(1, calendar.get(Calendar.MONTH) + 1);
    assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
    assertEquals(11, calendar.get(Calendar.HOUR_OF_DAY));
    assertEquals(22, calendar.get(Calendar.MINUTE));
    assertEquals(33, calendar.get(Calendar.SECOND));
    assertEquals(TimeZone.getTimeZone("UTC"), calendar.getTimeZone());
    Calendar newCalendar = Calendar.getInstance(Locale.GERMANY);
    newCalendar.setTime(calendar.getTime());
    String newString = Iso8601Util.formatDateTime(newCalendar);
    assertEquals("2007-01-31T12:22:33+01:00", newString);
  }

}
