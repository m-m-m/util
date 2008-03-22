/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

/**
 * This is the test-case for {@link MathUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class MathUtilTest {

  protected MathUtil getMathUtil() {

    return MathUtil.getInstance();
  }

  protected <NUMBER extends Number> void check(Class<NUMBER> numberClass, Class<?> primitiveClass) {

    MathUtil util = getMathUtil();
    NumberType<NUMBER> numberType = util.getNumberTypeGeneric(numberClass);
    assertEquals(numberClass, numberType.getNumberClass());
    assertSame(numberType, util.getNumberType(numberClass));
    if (primitiveClass != null) {
      assertSame(numberType, util.getNumberType(primitiveClass));
    }
    NUMBER value = numberType.valueOf("42");
    assertEquals(42, value.intValue());
    assertSame(value, numberType.valueOf(value, true));
  }

  @Test
  public void testGetNumberType() {

    check(Byte.class, byte.class);
    check(Short.class, short.class);
    check(Integer.class, int.class);
    check(Long.class, long.class);
    check(Float.class, float.class);
    check(Double.class, double.class);
    check(AtomicInteger.class, null);
    check(AtomicLong.class, null);
    check(BigInteger.class, null);
    check(BigDecimal.class, null);

    assertNull(getMathUtil().getNumberType(String.class));
  }

}
