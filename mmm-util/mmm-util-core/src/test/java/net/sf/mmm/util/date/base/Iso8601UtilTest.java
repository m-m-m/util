/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.date.api.Iso8601Util;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link Iso8601UtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class Iso8601UtilTest extends Assert {

  public Iso8601Util getIso8601Util() {

    return Iso8601UtilImpl.getInstance();
  }

  private void dump(Date date) {

    System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date));
  }

  public void checkCombined(String date) {

    assertTrue(Iso8601Util.PATTERN_ALL.matcher(date).matches());
    Calendar calendar = getIso8601Util().parseCalendar(date);
    String newDate = getIso8601Util().formatDateTime(calendar);
    assertEquals(date, newDate);
  }

  @Test
  public void testPatterns() {

    Pattern datePattern = Pattern.compile(Iso8601Util.PATTERN_STRING_DATE);
    Matcher matcher = datePattern.matcher("1999-12-31");
    assertTrue(matcher.matches());
    assertEquals(3, matcher.groupCount());
    assertEquals("1999", matcher.group(1));
    assertEquals("12", matcher.group(2));
    assertEquals("31", matcher.group(3));

    Pattern timePattern = Pattern.compile(Iso8601Util.PATTERN_STRING_TIME);
    matcher = timePattern.matcher("23:59:58");
    assertTrue(matcher.matches());
    assertEquals(3, matcher.groupCount());
    assertEquals("23", matcher.group(1));
    assertEquals("59", matcher.group(2));
    assertEquals("58", matcher.group(3));

    Pattern timezonePattern = Pattern.compile(Iso8601Util.PATTERN_STRING_TIMEZONE);
    matcher = timezonePattern.matcher("+23:59:58");
    assertTrue(matcher.matches());
    assertEquals(6, matcher.groupCount());
    assertEquals("+", matcher.group(2));
    assertEquals("23", matcher.group(3));
    assertEquals("59", matcher.group(4));
    assertEquals("58", matcher.group(6));

    matcher = timezonePattern.matcher("-01:00");
    assertTrue(matcher.matches());
    assertEquals(6, matcher.groupCount());
    assertEquals("-", matcher.group(2));
    assertEquals("01", matcher.group(3));
    assertEquals("00", matcher.group(4));
    assertNull(matcher.group(6));

    matcher = timezonePattern.matcher("Z");
    assertTrue(matcher.matches());
    assertEquals(6, matcher.groupCount());
  }

  @Test
  public void testBasicFormat() {

    Calendar calendar = getIso8601Util().parseCalendar("19991231T235959Z");
    assertEquals(1999, calendar.get(Calendar.YEAR));
    assertEquals(Calendar.DECEMBER, calendar.get(Calendar.MONTH));
    assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
    assertEquals(23, calendar.get(Calendar.HOUR_OF_DAY));
    assertEquals(59, calendar.get(Calendar.MINUTE));
    assertEquals(59, calendar.get(Calendar.SECOND));
    assertEquals(0, calendar.get(Calendar.MILLISECOND));
    assertEquals(TimeZone.getTimeZone("UTC"), calendar.getTimeZone());
  }

  @Test
  public void testExtendedFormat() {

    checkCombined("1999-12-31T23:59:59+01:00");
    checkCombined("2000-01-01T00:00:00+00:00");
    checkCombined("2000-01-01T00:00:00-02:00");
    checkCombined("2000-01-01T00:00:00-00:30");
    Calendar calendar = getIso8601Util().parseCalendar("2007-01-31T11:22:33Z");
    assertEquals(2007, calendar.get(Calendar.YEAR));
    assertEquals(1, calendar.get(Calendar.MONTH) + 1);
    assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
    assertEquals(11, calendar.get(Calendar.HOUR_OF_DAY));
    assertEquals(22, calendar.get(Calendar.MINUTE));
    assertEquals(33, calendar.get(Calendar.SECOND));
    assertEquals(TimeZone.getTimeZone("UTC"), calendar.getTimeZone());
    Calendar newCalendar = Calendar.getInstance(Locale.GERMANY);
    // bug in linux JDK 1.5.x
    newCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
    newCalendar.setTime(calendar.getTime());
    String newString = getIso8601Util().formatDateTime(newCalendar);
    assertEquals("2007-01-31T12:22:33+01:00", newString);
  }

  @Test
  public void testNow() {

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    String formatted = getIso8601Util().formatDateTime(calendar);
    Calendar parsed = getIso8601Util().parseCalendar(formatted);
    assertEquals(formatted, getIso8601Util().formatDateTime(parsed));
    // ATTENTION: parsed and calendar may NOT be equal because the timezone
    // may have changed to the fixed UTC-Offset...
    assertEquals(calendar.getTimeInMillis(), parsed.getTimeInMillis());
    assertEquals(calendar.getTimeZone().getOffset(calendar.getTimeInMillis()),
        parsed.getTimeZone().getOffset(parsed.getTimeInMillis()));
  }

}
