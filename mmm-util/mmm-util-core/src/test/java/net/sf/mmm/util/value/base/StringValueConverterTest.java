/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.value.api.StringValueConverter;
import net.sf.mmm.util.value.api.ValueNotSetException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;
import net.sf.mmm.util.value.api.WrongValueTypeException;

/**
 * This is the test-case for {@link StringValueConverterImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringValueConverterTest {

  public StringValueConverter getStringValueConverter() {

    return StringValueConverterImpl.getInstance();
  }

  protected Iso8601Util getIso8601Util() {

    return Iso8601UtilImpl.getInstance();
  }

  @Test
  public void testConvert() {

    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    // test byte
    byte byteValue = getStringValueConverter().convertValue(valueString, source, byte.class);
    assertEquals(value, byteValue);
    Byte byteObject = getStringValueConverter().convertValue(valueString, source, Byte.class);
    assertEquals(value, byteObject.byteValue());
    // test short
    short shortValue = getStringValueConverter().convertValue(valueString, source, short.class);
    assertEquals(value, shortValue);
    Short shortObject = getStringValueConverter().convertValue(valueString, source, Short.class);
    assertEquals(value, shortObject.shortValue());
    // test int
    int intValue = getStringValueConverter().convertValue(valueString, source, int.class);
    assertEquals(value, intValue);
    Integer integerObject = getStringValueConverter().convertValue(valueString, source, Integer.class);
    assertEquals(value, integerObject.intValue());
    // test long
    long longValue = getStringValueConverter().convertValue(valueString, source, long.class);
    assertEquals(value, longValue);
    Long longObject = getStringValueConverter().convertValue(valueString, source, Long.class);
    assertEquals(value, longObject.longValue());
    // test float
    float floatValue = getStringValueConverter().convertValue(valueString, source, float.class);
    assertEquals(value, floatValue, 0.0);
    Float floatObject = getStringValueConverter().convertValue(valueString, source, Float.class);
    assertEquals(value, floatObject.floatValue(), 0.0);
    // test double
    double doubleValue = getStringValueConverter().convertValue(valueString, source, double.class);
    assertEquals(value, doubleValue, 0.0);
    Double doubleObject = getStringValueConverter().convertValue(valueString, source, Double.class);
    assertEquals(value, doubleObject.doubleValue(), 0.0);
    // test number
    Number numberObject = getStringValueConverter().convertValue(valueString, source, Number.class);
    assertEquals(value, numberObject.intValue());
    assertEquals(Byte.class, numberObject.getClass());
    integerObject = getStringValueConverter().convertValue(valueString, source, value, value);
    assertEquals(value, integerObject.intValue());
    Integer minimum = Integer.valueOf(value - 2);
    Integer maximum = Integer.valueOf(value - 1);
    try {
      integerObject = getStringValueConverter().convertValue(valueString, source, minimum, maximum);
      fail("Exception expected");
    } catch (ValueOutOfRangeException e) {
      // this is expected
      assertEquals(Integer.valueOf(value), e.getNlsMessage().getArgument("value"));
      assertEquals(minimum, e.getNlsMessage().getArgument("min"));
      assertEquals(maximum, e.getNlsMessage().getArgument("max"));
      assertEquals(source, e.getNlsMessage().getArgument("source"));
    }
    // test string
    assertEquals(valueString, getStringValueConverter().convertValue(valueString, source, String.class));
    // test boolean
    assertTrue(getStringValueConverter().convertValue(StringUtil.TRUE, source, Boolean.class).booleanValue());
    assertTrue(getStringValueConverter().convertValue(StringUtil.TRUE, source, boolean.class));
    assertFalse(getStringValueConverter().convertValue(StringUtil.FALSE, source, Boolean.class).booleanValue());
    assertFalse(getStringValueConverter().convertValue(StringUtil.FALSE, source, boolean.class));
    // test char
    char c = '\u0223';
    String charString = Character.toString(c);
    assertEquals(Character.valueOf(c), getStringValueConverter().convertValue(charString, source, char.class));
    assertEquals(Character.valueOf(c), getStringValueConverter().convertValue(charString, source, Character.class));
    // test date
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    String dateString = getIso8601Util().formatDateTime(date);
    assertEquals(date, getStringValueConverter().convertValue(dateString, source, Date.class));
    // test class
    Class<?> type = Void.class;
    String typeString = type.getName();
    assertEquals(type, getStringValueConverter().convertValue(typeString, source, Class.class));
    // test enum
    RetentionPolicy policy = RetentionPolicy.RUNTIME;
    assertEquals(policy, getStringValueConverter().convertValue(policy.name(), source, RetentionPolicy.class));
    Conjunction conjunction = Conjunction.NOR;
    assertEquals(conjunction, getStringValueConverter().convertValue(conjunction.name(), source, Conjunction.class));
    // test default-value
    assertEquals(valueString, getStringValueConverter().convertValue(null, source, String.class, type, valueString));
    assertEquals(valueString, getStringValueConverter()
        .convertValue(valueString, source, String.class, type, "default"));
    integerObject = getStringValueConverter().convertValue(null, source, value, value, value);
    assertEquals(value, integerObject.intValue());
    // wrong value type
    String noIntegerValue = "twentyfive";
    try {
      getStringValueConverter().convertValue(noIntegerValue, source, Integer.class);
      fail("Exception expected");
    } catch (WrongValueTypeException e) {
      // expected
      assertEquals(noIntegerValue, e.getNlsMessage().getArgument("value"));
      assertEquals(String.class, e.getNlsMessage().getArgument("valueType"));
      assertEquals(Integer.class, e.getNlsMessage().getArgument("targetType"));
      assertEquals(source, e.getNlsMessage().getArgument("source"));
    }
    // value not set
    try {
      getStringValueConverter().convertValue(null, source, Integer.class);
      fail("Exception expected");
    } catch (ValueNotSetException e) {
      // expected
    }
  }
}
