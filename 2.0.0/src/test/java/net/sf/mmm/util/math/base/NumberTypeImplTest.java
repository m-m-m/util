/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

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

import net.sf.mmm.util.math.api.NumberConversionException;
import net.sf.mmm.util.math.api.NumberType;

/**
 * This is the test-case for {@link NumberType}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class NumberTypeImplTest {

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

    NumberType<Byte> numberType = NumberTypeImpl.BYTE;
    assertEquals(Byte.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    byte b = 123;
    Byte value = Byte.valueOf(b);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testShort() {

    NumberType<Short> numberType = NumberTypeImpl.SHORT;
    assertEquals(Short.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    short s = 12345;
    Short value = Short.valueOf(s);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testInteger() {

    NumberType<Integer> numberType = NumberTypeImpl.INTEGER;
    assertEquals(Integer.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    Integer value = Integer.valueOf(1234567890);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testAtomicInteger() {

    NumberType<AtomicInteger> numberType = NumberTypeImpl.ATOMIC_INTEGER;
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

    NumberType<Long> numberType = NumberTypeImpl.LONG;
    assertEquals(Long.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    Long value = Long.valueOf(1234567890123456L);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
  }

  @Test
  public void testAtomicLong() {

    NumberType<AtomicLong> numberType = NumberTypeImpl.ATOMIC_LONG;
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

    NumberType<BigInteger> numberType = NumberTypeImpl.BIG_INTEGER;
    assertEquals(BigInteger.class, numberType.getNumberClass());
    assertFalse(numberType.isDecimal());
    BigInteger value = BigInteger.valueOf(1234567890123456L);
    checkNumberType(numberType, value);
    checkNonDecimalNumberType(numberType);
    value = new BigInteger("1234567890123456789012345678901234567890");
    BigInteger parsed = numberType.valueOf(value.toString());
    assertEquals(value, parsed);
    NumberType<Long> longType = NumberTypeImpl.LONG;
    try {
      longType.valueOf(value, true);
      fail("exception expected");
    } catch (NumberConversionException e) {
    }
  }

  @Test
  public void testFloat() {

    NumberType<Float> numberType = NumberTypeImpl.FLOAT;
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

    NumberType<Double> numberType = NumberTypeImpl.DOUBLE;
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

    NumberType<BigDecimal> numberType = NumberTypeImpl.BIG_DECIMAL;
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
    NumberType<Double> doubleType = NumberTypeImpl.DOUBLE;
    Double convertedDouble = doubleType.valueOf(value, false);
    assertEquals(value.doubleValue(), convertedDouble.doubleValue(), 0);
    try {
      doubleType.valueOf(value, true);
    } catch (NumberConversionException e) {
    }
  }

  @Test
  public void testExcactnesDifference() {

    NumberType[] numberTypeOrder = new NumberType[] { NumberTypeImpl.BYTE, NumberTypeImpl.SHORT,
        NumberTypeImpl.INTEGER, NumberTypeImpl.LONG, NumberTypeImpl.BIG_INTEGER };
    for (int i = 1; i < numberTypeOrder.length; i++) {
      NumberType lessExact = numberTypeOrder[i - i];
      NumberType moreExact = numberTypeOrder[i];
      int diff = moreExact.getExactnessDifference(lessExact);
      assertTrue(diff > 0);
      int inverseDiff = lessExact.getExactnessDifference(moreExact);
      assertEquals(diff, -inverseDiff);
    }

    int diff = NumberTypeImpl.ATOMIC_INTEGER.getExactnessDifference(NumberTypeImpl.INTEGER);
    assertEquals(0, diff);
    diff = NumberTypeImpl.ATOMIC_LONG.getExactnessDifference(NumberTypeImpl.LONG);
    assertEquals(0, diff);
  }
}
