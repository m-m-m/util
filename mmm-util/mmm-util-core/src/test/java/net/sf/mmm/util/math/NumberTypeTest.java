/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

/**
 * This is the test-case for {@link NumberType}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NumberTypeTest {

  protected <NUMBER extends Number> void checkNumberType(NumberType<NUMBER> numberType, NUMBER value) {

    NUMBER parsed = numberType.valueOf(value.toString());
    assertEquals(value, parsed);
    Double d = Double.valueOf(value.doubleValue());
    NUMBER converted = numberType.valueOf(d, true);
    assertEquals(value, converted);
    NUMBER same = numberType.valueOf(value, true);
    assertSame(value, same);
    try {
      numberType.valueOf("illegal number");
      fail("exception expected");
    } catch (NumberConversionException e) {
    }
    assertEquals(0, numberType.getExactnessDifference(numberType));
  }

  protected <NUMBER extends Number> void checkNonDecimalNumberType(NumberType<NUMBER> numberType) {

    Double d = Double.valueOf(42.00001);
    NUMBER converted = numberType.valueOf(d, false);
    assertEquals(42, converted.longValue());
    try {
      converted = numberType.valueOf(d, true);
      fail("exception expected");
    } catch (NumberConversionException e) {
    }
  }

  @Test
  public void testByte() {

    NumberType<Byte> numberType = NumberType.BYTE;
    assertEquals(Byte.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    byte b = 123;
    Byte value = Byte.valueOf(b);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testShort() {

    NumberType<Short> numberType = NumberType.SHORT;
    assertEquals(Short.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    short s = 12345;
    Short value = Short.valueOf(s);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testInteger() {

    NumberType<Integer> numberType = NumberType.INTEGER;
    assertEquals(Integer.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    Integer value = Integer.valueOf(1234567890);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testAtomicInteger() {

    NumberType<AtomicInteger> numberType = NumberType.ATOMIC_INTEGER;
    assertEquals(AtomicInteger.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    AtomicInteger value = new AtomicInteger(1234567890);
    // equals does NOT work for AtomicInteger - only identity check
    // checkNumberType(numberType, value);
    AtomicInteger parsed = numberType.valueOf(value.toString());
    assertEquals(value.intValue(), parsed.intValue());
    Double d = Double.valueOf(value.doubleValue());
    AtomicInteger converted = numberType.valueOf(d, true);
    assertEquals(value.intValue(), converted.intValue());
    AtomicInteger same = numberType.valueOf(value, true);
    assertSame(value, same);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testLong() {

    NumberType<Long> numberType = NumberType.LONG;
    assertEquals(Long.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    Long value = Long.valueOf(1234567890123456L);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testAtomicLong() {

    NumberType<AtomicLong> numberType = NumberType.ATOMIC_LONG;
    assertEquals(AtomicLong.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    AtomicLong value = new AtomicLong(1234567890123456L);
    // equals does NOT work for AtomicInteger - only identity check
    // checkNumberType(numberType, value);
    AtomicLong parsed = numberType.valueOf(value.toString());
    assertEquals(value.longValue(), parsed.longValue());
    Double d = Double.valueOf(value.doubleValue());
    AtomicLong converted = numberType.valueOf(d, true);
    assertEquals(value.longValue(), converted.longValue());
    AtomicLong same = numberType.valueOf(value, true);
    assertSame(value, same);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testBigInteger() {

    NumberType<BigInteger> numberType = NumberType.BIG_INTEGER;
    assertEquals(BigInteger.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    BigInteger value = BigInteger.valueOf(1234567890123456L);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
    value = new BigInteger("1234567890123456789012345678901234567890");
    BigInteger parsed = numberType.valueOf(value.toString());
    assertEquals(value, parsed);
    NumberType<Long> longType = NumberType.LONG;
    try {
      longType.valueOf(value, true);
      fail("exception expected");
    } catch (NumberConversionException e) {
    }
  }

  @Test
  public void testFloat() {

    NumberType<Float> numberType = NumberType.FLOAT;
    assertEquals(Float.class, numberType.getNumberClass());
    assertTrue(numberType.isDecimal());
    Float value = Float.valueOf(123456789.123456789F);
    checkNumberType(numberType, value);
    Double d = Double.valueOf(42.00001);
    Float converted = numberType.valueOf(d, false);
    assertEquals(42, converted.longValue());
    try {
      converted = numberType.valueOf(d, true);
      fail("exception expected");
    } catch (NumberConversionException e) {
    }
    d = Double.valueOf(0.00001);
    converted = numberType.valueOf(d, true);
    assertEquals(d.floatValue(), converted.floatValue(), 0);
  }

  @Test
  public void testDouble() {

    NumberType<Double> numberType = NumberType.DOUBLE;
    assertEquals(Double.class, numberType.getNumberClass());
    assertTrue(numberType.isDecimal());
    Double value = Double.valueOf(123456789.123456789);
    checkNumberType(numberType, value);
    Float f = Float.valueOf(42.00001F);
    Double converted = numberType.valueOf(f, true);
    assertEquals(f.doubleValue(), converted.doubleValue(), 0);
  }

  @Test
  public void testBigDecimal() {

    NumberType<BigDecimal> numberType = NumberType.BIG_DECIMAL;
    assertEquals(BigDecimal.class, numberType.getNumberClass());
    assertTrue(numberType.isDecimal());
    BigDecimal value = BigDecimal.valueOf(123456789.123456789);
    checkNumberType(numberType, value);
    Double d = Double.valueOf(42.00001F);
    BigDecimal converted = numberType.valueOf(d, true);
    assertEquals(d.doubleValue(), converted.doubleValue(), 0);
    value = new BigDecimal("1234567890123456789.012345678901234567890");
    BigDecimal parsed = numberType.valueOf(value.toString());
    assertEquals(value, parsed);
    NumberType<Double> doubleType = NumberType.DOUBLE;
    Double convertedDouble = doubleType.valueOf(value, false);
    assertEquals(value.doubleValue(), convertedDouble.doubleValue(), 0);
    try {
      doubleType.valueOf(value, true);
    } catch (NumberConversionException e) {
    }
  }

  @Test
  public void testExcactnesDifference() {

    NumberType[] numberTypeOrder = new NumberType[] { NumberType.BYTE, NumberType.SHORT,
        NumberType.INTEGER, NumberType.LONG, NumberType.BIG_INTEGER };
    for (int i = 1; i < numberTypeOrder.length; i++) {
      NumberType lessExact = numberTypeOrder[i - i];
      NumberType moreExact = numberTypeOrder[i];
      int diff = moreExact.getExactnessDifference(lessExact);
      assertTrue(diff > 0);
      int inverseDiff = lessExact.getExactnessDifference(moreExact);
      assertEquals(diff, -inverseDiff);
    }

    int diff = NumberType.ATOMIC_INTEGER.getExactnessDifference(NumberType.INTEGER);
    assertEquals(0, diff);
    diff = NumberType.ATOMIC_LONG.getExactnessDifference(NumberType.LONG);
    assertEquals(0, diff);
  }
}
