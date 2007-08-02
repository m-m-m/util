/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import java.util.Date;

import org.junit.Test;

import net.sf.mmm.util.NumericUtil;
import net.sf.mmm.value.api.GenericValue;
import net.sf.mmm.value.api.MutableGenericValue;
import net.sf.mmm.value.api.ValueInstanciationException;
import net.sf.mmm.value.api.ValueNotEditableException;
import net.sf.mmm.value.api.ValueNotSetException;
import net.sf.mmm.value.api.ValueOutOfRangeException;
import net.sf.mmm.value.api.WrongValueTypeException;

import static org.junit.Assert.*;

/**
 * This is the abstract test-case for testing sub-classes of
 * {@link AbstractGenericValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractGenericValueTest {

  /**
   * The constructor.
   */
  public AbstractGenericValueTest() {

    super();
  }

  /**
   * This method converts the given <code>plainValue</code> to a
   * {@link GenericValue}.
   * 
   * @param plainValue is the object to convert.
   * @return is the object as generic value.
   */
  protected abstract GenericValue convert(Object plainValue);

  @Test
  public void testEmptyValue() {

    GenericValue value = convert(null);
    assertTrue(value.isEmpty());
    boolean error = false;
    try {
      value.getObject();
    } catch (ValueNotSetException e) {
      error = true;
    }
    assertTrue("Exception expected", error);

    assertNull(value.getObject(null));
    assertNull(value.getBoolean(null));
    assertNull(value.getDate(null));
    assertNull(value.getDouble(null));
    assertNull(value.getInteger(null));
    assertNull(value.getJavaClass(null));
    assertNull(value.getLong(null));
    assertNull(value.getNumber(null));
    assertNull(value.getString(null));
    assertNull(value.getValue(Object.class, null));

    try {
      CharSequence test = value.getJavaClassInstance(CharSequence.class, CharSequence.class, false);
      fail("Exception expected");
    } catch (ValueInstanciationException e) {
    }

    assertTrue(value.isEmpty());
    if (value.isAddDefaults()) {
      String string = "value";
      assertEquals(string, value.getString(string));
      assertFalse(value.isEmpty());
      assertEquals(string, value.getString());
      assertEquals(string, value.getString(null));
      assertEquals(string, value.getString("foo"));
    }

  }

  @Test
  public void testMutableValue() {

    GenericValue value = convert(null);
    if (value instanceof MutableGenericValue) {
      MutableGenericValue mutableValue = (MutableGenericValue) value;
      String string = "value";
      if (mutableValue.isEditable()) {
        assertTrue(mutableValue.isEmpty());
        mutableValue.setString(string);
        assertEquals(string, value.getString());
        mutableValue.setObject(string);
        assertEquals(string, value.getString());
        mutableValue.setBoolean(true);
        assertTrue(value.getBoolean());
        Date date = new Date(1157004890000L);
        mutableValue.setDate(date);
        Date otherDate = value.getDate();
        assertEquals(date.getTime(), otherDate.getTime());
        double d = 42.42;
        mutableValue.setDouble(d);
        assertEquals(d, mutableValue.getDouble(), 0);
        int i = 42;
        mutableValue.setInteger(i);
        assertEquals(i, value.getInteger());
        Class type = String.class;
        mutableValue.setJavaClass(type);
        assertSame(type, value.getJavaClass());
      } else {
        boolean error = false;
        try {
          mutableValue.setString(string);
        } catch (ValueNotEditableException e) {
          error = true;
        }
        assertTrue("Exception expected", error);
      }
    }
  }

  @Test
  public void testBoolean() {

    GenericValue valueTrue = convert(Boolean.TRUE);
    assertTrue(valueTrue.getBoolean());
    assertTrue(valueTrue.getBoolean(null).booleanValue());
    assertTrue(valueTrue.getValue(Boolean.class).booleanValue());
    assertEquals(Boolean.TRUE.toString(), valueTrue.getString());

    GenericValue valueFalse = convert(Boolean.FALSE);
    assertFalse(valueFalse.getBoolean());
    assertFalse(valueFalse.getBoolean(null).booleanValue());
    assertFalse(valueFalse.getValue(Boolean.class).booleanValue());
    assertEquals(Boolean.FALSE.toString(), valueFalse.getString());
  }

  @Test
  public void testString() {

    String string = "value";
    GenericValue valueString = convert(string);
    assertEquals(string, valueString.getString());
    assertEquals(string, valueString.getObject());
  }

  @Test
  public void testJavaClass() {

    GenericValue valueString = convert(String.class);
    assertSame(String.class, valueString.getJavaClass());
    assertSame(String.class, valueString.getValue(Class.class));
    String s = valueString.getJavaClassInstance(String.class);
    assertEquals(new String(), s);
  }

  private void checkWrongType(GenericValue value, Class wrongType) {

    try {
      value.getValue(wrongType);
      fail("Exception expected");
    } catch (WrongValueTypeException e) {
      assertEquals(wrongType, e.getExpectedType());
      assertSame(value, e.getGenericValue());
    }
  }

  @Test
  public void testWrongValueType() {

    double d = 42.42;
    GenericValue value = convert(Double.valueOf(d));
    assertEquals(d, value.getDouble());
    assertEquals(Double.toString(d), value.getString());
    checkWrongType(value, Boolean.class);
    checkWrongType(value, Class.class);
    checkWrongType(value, Date.class);
    value = convert("foo");
    checkWrongType(value, Byte.class);
    checkWrongType(value, Short.class);
    checkWrongType(value, Integer.class);
    checkWrongType(value, Long.class);
    checkWrongType(value, Float.class);
    checkWrongType(value, Double.class);
    checkWrongType(value, Number.class);
  }

  @Test
  public void testNumbers() {

    byte byteValue = 42;
    checkNumber(convert(new Byte(byteValue)));
    short shortValue = 4242;
    checkNumber(convert(new Short(shortValue)));
    checkNumber(convert(new Integer(424242)));
    checkNumber(convert(new Long(4242424242424242L)));
    checkNumber(convert(new Float(42.25)));
    GenericValue doubleValue = convert(new Double(42.42));
    checkNumber(doubleValue);
    // TODO: validate range is stupid, add getNumber(min, max[. default])
    // doubleValue.getNumber(<T extends Number>, minimum, maximum, boolean
    // inclusive)
  }

  /**
   * @param value must be a generic value containing a number.
   */
  public void checkNumber(GenericValue value) {

    // this will only work for Number of java.lang.*
    assertFalse(value.isEmpty());
    Number number = value.getNumber();
    assertEquals(number.toString(), value.getString());
    assertEquals(number, value.getValue(Number.class));
    number = NumericUtil.toSimplestNumber(number);
    double doubleValue = number.doubleValue();
    assertEquals(doubleValue, value.getDouble(), 0);
    assertEquals(doubleValue, value.getDouble(null).doubleValue(), 0);
    assertEquals(doubleValue, value.getValue(Double.class).doubleValue(), 0);
    assertEquals(doubleValue, value.getValue(Double.class, null).doubleValue(), 0);
    Double bounds = Double.valueOf(doubleValue);
    assertEquals(doubleValue, value.getNumber(bounds, bounds).doubleValue(), 0);
    assertEquals(doubleValue, value.getNumber(bounds, bounds, null).doubleValue(), 0);
    Double greater = Double.valueOf(doubleValue + 1);
    if (greater.doubleValue() > doubleValue) {
      try {
        value.getNumber(greater, greater);
        fail("Exception expected");
      } catch (ValueOutOfRangeException e) {
        assertSame(value, e.getGenericValue());
      }
    }
    Double smaller = Double.valueOf(doubleValue - 1);
    if (smaller.doubleValue() < doubleValue) {
      try {
        value.getNumber(smaller, smaller);
        fail("Exception expected");
      } catch (ValueOutOfRangeException e) {
        assertSame(value, e.getGenericValue());
      }
    }
    Class type = number.getClass();
    if (type != Double.class) {
      float floatValue = number.floatValue();
      assertEquals(floatValue, value.getValue(Float.class).floatValue(), 0);
      assertEquals(floatValue, value.getValue(float.class).floatValue(), 0);
      if (type != Float.class) {
        long longValue = number.longValue();
        assertEquals(longValue, value.getLong());
        assertEquals(longValue, value.getLong(null).longValue());
        assertEquals(longValue, value.getValue(Long.class).longValue());
        assertEquals(longValue, value.getValue(long.class).longValue());
        if (type != Long.class) {
          int intValue = number.intValue();
          assertEquals(intValue, value.getInteger());
          assertEquals(intValue, value.getInteger(null).intValue());
          assertEquals(intValue, value.getValue(Integer.class).intValue());
          assertEquals(intValue, value.getValue(int.class).intValue());
          if (type != Integer.class) {
            short shortValue = number.shortValue();
            assertEquals(shortValue, value.getValue(Short.class).shortValue());
            assertEquals(shortValue, value.getValue(short.class).shortValue());
            if (type != Short.class) {
              byte byteValue = number.byteValue();
              assertEquals(byteValue, value.getValue(Byte.class).byteValue());
              assertEquals(byteValue, value.getValue(byte.class).byteValue());
            }
          }
        }
      }
    }
  }

}
