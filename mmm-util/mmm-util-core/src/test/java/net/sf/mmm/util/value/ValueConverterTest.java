/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import net.sf.mmm.util.Conjunction;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.date.Iso8601Util;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.api.ValueNotSetException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;
import net.sf.mmm.util.value.api.WrongValueTypeException;
import net.sf.mmm.util.value.base.StringValueConverter;

/**
 * This is the test-case for {@link StringValueConverter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ValueConverterTest {

  public GenericValueConverter<String> getConverter() {

    return StringValueConverter.getInstance();
  }

  protected Iso8601Util getIso8601Util() {

    return Iso8601Util.getInstance();
  }

  @Test
  public void testConvert() {

    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    // test byte
    byte byteValue = getConverter().convertValue(valueString, source, byte.class);
    assertEquals(value, byteValue);
    Byte byteObject = getConverter().convertValue(valueString, source, Byte.class);
    assertEquals(value, byteObject.byteValue());
    // test short
    short shortValue = getConverter().convertValue(valueString, source, short.class);
    assertEquals(value, shortValue);
    Short shortObject = getConverter().convertValue(valueString, source, Short.class);
    assertEquals(value, shortObject.shortValue());
    // test int
    int intValue = getConverter().convertValue(valueString, source, int.class);
    assertEquals(value, intValue);
    Integer integerObject = getConverter().convertValue(valueString, source, Integer.class);
    assertEquals(value, integerObject.intValue());
    // test long
    long longValue = getConverter().convertValue(valueString, source, long.class);
    assertEquals(value, longValue);
    Long longObject = getConverter().convertValue(valueString, source, Long.class);
    assertEquals(value, longObject.longValue());
    // test float
    float floatValue = getConverter().convertValue(valueString, source, float.class);
    assertEquals(value, floatValue);
    Float floatObject = getConverter().convertValue(valueString, source, Float.class);
    assertEquals(value, floatObject.floatValue());
    // test double
    double doubleValue = getConverter().convertValue(valueString, source, double.class);
    assertEquals(value, doubleValue);
    Double doubleObject = getConverter().convertValue(valueString, source, Double.class);
    assertEquals(value, doubleObject.doubleValue());
    // test number
    Number numberObject = getConverter().convertValue(valueString, source, Number.class);
    assertEquals(value, numberObject.intValue());
    assertEquals(Byte.class, numberObject.getClass());
    integerObject = getConverter().convertValue(valueString, source, value, value);
    assertEquals(value, integerObject.intValue());
    Integer minimum = Integer.valueOf(value - 2);
    Integer maximum = Integer.valueOf(value - 1);
    try {
      integerObject = getConverter().convertValue(valueString, source, minimum, maximum);
      fail("Exception expected");
    } catch (ValueOutOfRangeException e) {
      // this is expected
      assertEquals(source, e.getNlsMessage().getArgument(0));
      assertEquals(Integer.valueOf(value), e.getNlsMessage().getArgument(1));
      assertEquals(minimum, e.getNlsMessage().getArgument(2));
      assertEquals(maximum, e.getNlsMessage().getArgument(3));
    }
    // test string
    assertEquals(valueString, getConverter().convertValue(valueString, source, String.class));
    // test boolean
    assertTrue(getConverter().convertValue(StringUtil.TRUE, source, Boolean.class).booleanValue());
    assertTrue(getConverter().convertValue(StringUtil.TRUE, source, boolean.class));
    assertFalse(getConverter().convertValue(StringUtil.FALSE, source, Boolean.class).booleanValue());
    assertFalse(getConverter().convertValue(StringUtil.FALSE, source, boolean.class));
    // test char
    char c = '\u0223';
    String charString = Character.toString(c);
    assertEquals(c, getConverter().convertValue(charString, source, char.class));
    assertEquals(c, getConverter().convertValue(charString, source, Character.class));
    // test date
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    String dateString = getIso8601Util().formatDateTime(date);
    assertEquals(date, getConverter().convertValue(dateString, source, Date.class));
    // test class
    Class<?> type = Void.class;
    String typeString = type.getName();
    assertEquals(type, getConverter().convertValue(typeString, source, Class.class));
    // test enum
    RetentionPolicy policy = RetentionPolicy.RUNTIME;
    assertEquals(policy, getConverter().convertValue(policy.name(), source, RetentionPolicy.class));
    Conjunction conjunction = Conjunction.NOR;
    assertEquals(conjunction, getConverter().convertValue(conjunction.name(), source,
        Conjunction.class));
    // test default-value
    assertEquals(valueString, getConverter().convertValue(null, source, String.class, type,
        valueString));
    assertEquals(valueString, getConverter().convertValue(valueString, source, String.class, type,
        "default"));
    integerObject = getConverter().convertValue(null, source, value, value, value);
    assertEquals(value, integerObject.intValue());
    // wrong value type
    String noIntegerValue = "twentyfive";
    try {
      getConverter().convertValue(noIntegerValue, source, Integer.class);
      fail("Exception expected");
    } catch (WrongValueTypeException e) {
      // expected
      assertEquals(noIntegerValue, e.getNlsMessage().getArgument(0));
      assertEquals(String.class, e.getNlsMessage().getArgument(1));
      assertEquals(Integer.class, e.getNlsMessage().getArgument(2));
      assertEquals(source, e.getNlsMessage().getArgument(3));
    }
    // value not set
    try {
      getConverter().convertValue(null, source, Integer.class);
      fail("Exception expected");
    } catch (ValueNotSetException e) {
      // expected
    }
  }
}
