/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.exception.api.ValueNotSetException;
import net.sf.mmm.util.exception.api.ValueOutOfRangeException;
import net.sf.mmm.util.exception.api.WrongValueTypeException;
import net.sf.mmm.util.lang.api.Conjunction;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.value.api.StringValueConverter;

/**
 * This is the test-case for {@link StringValueConverterImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StringValueConverterTest extends Assert {

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
    // value not set
  }

  @Test
  public void testByte() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    byte byteValue = converter.convertValue(valueString, source, byte.class).byteValue();
    assertEquals(value, byteValue);
    Byte byteObject = converter.convertValue(valueString, source, Byte.class);
    assertEquals(value, byteObject.byteValue());
  }

  @Test
  public void testShort() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    short shortValue = converter.convertValue(valueString, source, short.class).shortValue();
    assertEquals(value, shortValue);
    Short shortObject = converter.convertValue(valueString, source, Short.class);
    assertEquals(value, shortObject.shortValue());
  }

  @Test
  public void testCurrency() {

    // given
    String source = "test-case";
    String valueString = "EUR";
    StringValueConverter converter = getStringValueConverter();

    // then
    Currency currency = converter.convertValue(valueString, source, Currency.class);
    assertEquals(Currency.getInstance(valueString), currency);
  }

  @Test
  public void testInt() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    int intValue = converter.convertValue(valueString, source, int.class).intValue();
    assertEquals(value, intValue);
    Integer integerObject = converter.convertValue(valueString, source, Integer.class);
    assertEquals(value, integerObject.intValue());
  }

  @Test
  public void testLong() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    long longValue = converter.convertValue(valueString, source, long.class).longValue();
    assertEquals(value, longValue);
    Long longObject = converter.convertValue(valueString, source, Long.class);
    assertEquals(value, longObject.longValue());
  }

  @Test
  public void testFloat() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    float floatValue = converter.convertValue(valueString, source, float.class).floatValue();
    assertEquals(value, floatValue, 0.0);
    Float floatObject = converter.convertValue(valueString, source, Float.class);
    assertEquals(value, floatObject.floatValue(), 0.0);
  }

  @Test
  public void testDouble() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    double doubleValue = converter.convertValue(valueString, source, double.class).doubleValue();
    assertEquals(value, doubleValue, 0.0);
    Double doubleObject = converter.convertValue(valueString, source, Double.class);
    assertEquals(value, doubleObject.doubleValue(), 0.0);
  }

  @Test
  public void testNumber() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    Number numberObject = converter.convertValue(valueString, source, Number.class);
    assertEquals(value, numberObject.intValue());
    assertEquals(Byte.class, numberObject.getClass());
    Integer integerObject = converter.convertValue(valueString, source, value, value);
    assertEquals(value, integerObject.intValue());
    Integer minimum = Integer.valueOf(value - 2);
    Integer maximum = Integer.valueOf(value - 1);
    try {
      integerObject = converter.convertValue(valueString, source, minimum, maximum);
      fail("Exception expected");
    } catch (ValueOutOfRangeException e) {
      // this is expected
      assertEquals(Integer.valueOf(value), e.getNlsMessage().getArgument("value"));
      assertEquals(minimum, e.getNlsMessage().getArgument("min"));
      assertEquals(maximum, e.getNlsMessage().getArgument("max"));
      assertEquals(source, e.getNlsMessage().getArgument("source"));
    }
  }

  @Test
  public void testString() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    String stringObject = converter.convertValue(valueString, source, String.class);
    assertSame(valueString, stringObject);
  }

  @Test
  public void testBoolean() {

    // given
    String source = "test-case";
    StringValueConverter converter = getStringValueConverter();

    // then
    assertTrue(converter.convertValue(StringUtil.TRUE, source, Boolean.class).booleanValue());
    assertTrue(converter.convertValue(StringUtil.TRUE, source, boolean.class).booleanValue());
    assertFalse(converter.convertValue(StringUtil.FALSE, source, Boolean.class).booleanValue());
    assertFalse(converter.convertValue(StringUtil.FALSE, source, boolean.class).booleanValue());
  }

  @Test
  public void testChar() {

    // given
    String source = "test-case";
    StringValueConverter converter = getStringValueConverter();
    char c = '\u0223';
    Character character = Character.valueOf(c);
    String charString = Character.toString(c);

    // then
    assertEquals(character, converter.convertValue(charString, source, char.class));
    assertEquals(character, converter.convertValue(charString, source, Character.class));
  }

  @Test
  public void testDate() {

    // given
    String source = "test-case";
    StringValueConverter converter = getStringValueConverter();

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    Date date = calendar.getTime();
    String dateString = getIso8601Util().formatDateTime(date);

    // then
    assertEquals(date, converter.convertValue(dateString, source, Date.class));
  }

  @Test
  public void testClass() {

    // given
    String source = "test-case";
    StringValueConverter converter = getStringValueConverter();

    // then
    Class<?> type = Void.class;
    String typeString = type.getName();
    Class classObject = converter.convertValue(typeString, source, Class.class);
    assertEquals(type, classObject);
  }

  @Test
  public void testEnum() {

    // given
    String source = "test-case";
    StringValueConverter converter = getStringValueConverter();

    // then
    RetentionPolicy policy = RetentionPolicy.RUNTIME;
    assertSame(policy, converter.convertValue(policy.name(), source, RetentionPolicy.class));
    Conjunction conjunction = Conjunction.NOR;
    assertSame(conjunction, converter.convertValue(conjunction.name(), source, Conjunction.class));
    assertSame(conjunction, converter.convertValue("!|", source, Conjunction.class));
  }

  @Test
  public void testDefaultValue() {

    // given
    String source = "test-case";
    int value = 42;
    String valueString = Integer.toString(value);
    StringValueConverter converter = getStringValueConverter();

    // then
    assertEquals(valueString, converter.convertValue(null, source, String.class, valueString));
    assertEquals(valueString, converter.convertValue(valueString, source, String.class, "default"));
    Integer integerObject = converter.convertValue(null, source, value, value, value);
    assertEquals(value, integerObject.intValue());
  }

  @Test
  public void testWrongType() {

    // given
    String source = "test-case";
    StringValueConverter converter = getStringValueConverter();

    // then
    String noIntegerValue = "twentyfive";
    try {
      converter.convertValue(noIntegerValue, source, Integer.class);
      ExceptionHelper.failExceptionExpected();
    } catch (WrongValueTypeException e) {
      // expected
      assertEquals(noIntegerValue, e.getNlsMessage().getArgument("value"));
      assertEquals(String.class, e.getNlsMessage().getArgument("valueType"));
      assertEquals(Integer.class, e.getNlsMessage().getArgument("targetType"));
      assertEquals(source, e.getNlsMessage().getArgument("source"));
    }
  }

  @Test
  public void testValueNotSet() {

    // given
    String source = "test-case";
    StringValueConverter converter = getStringValueConverter();

    // then
    try {
      converter.convertValue(null, source, Integer.class);
      ExceptionHelper.failExceptionExpected();
    } catch (ValueNotSetException e) {
      // expected
    }
  }
}
