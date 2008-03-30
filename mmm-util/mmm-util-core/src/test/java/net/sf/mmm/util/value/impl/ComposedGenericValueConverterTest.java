/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import net.sf.mmm.util.date.Iso8601Util;

/**
 * This is the test-case for {@link ComposedGenericValueConverter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ComposedGenericValueConverterTest {

  @Test
  public void testConverter() throws Exception {

    ComposedGenericValueConverter converter = new ComposedGenericValueConverter();
    converter.addConverter(new ValueConverterToDate());
    converter.addConverter(new ValueConverterToNumber());
    converter.addConverter(new ValueConverterToString());
    converter.initialize();
    Object value;
    String valueSource = "test-case";
    // convert to integer
    Integer i = Integer.valueOf(1234);
    value = converter.convert(i.toString(), valueSource, Integer.class, Integer.class);
    assertEquals(i, value);
    value = converter
        .convert(Long.valueOf(i.intValue()), valueSource, Integer.class, Integer.class);
    assertEquals(i, value);
    // convert to date
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    value = converter.convert(calendar, valueSource, Date.class, Date.class);
    assertEquals(date, value);
    String dateString = Iso8601Util.getInstance().formatDateTime(date);
    value = converter.convert(dateString, valueSource, Date.class, Date.class);
    assertEquals(date, value);
    value = converter.convert(Long.valueOf(date.getTime()), valueSource, Date.class, Date.class);
    assertEquals(date, value);
    // convert to string
    value = converter.convert(date, valueSource, String.class, String.class);
    assertEquals(dateString, value);
    value = converter.convert(String.class, valueSource, String.class, String.class);
    assertEquals(String.class.getName(), value);
  }
}
