/* $Id$ */
package net.sf.mmm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

  public void checkCombined(String date) {

    Calendar calendar = Iso8601Util.parseCalendar(date);
    String newDate = Iso8601Util.formatDateAndTime(calendar);
    assertEquals(date, newDate);
  }

  @Test
  public void testISO8601() {

    checkCombined("1999-12-31T23:59:59+01:00");
    checkCombined("2000-01-01T00:00:00+00:00");
    checkCombined("2000-01-01T00:00:00-02:00");
    checkCombined("2000-01-01T00:00:00-00:30");
  }

}
