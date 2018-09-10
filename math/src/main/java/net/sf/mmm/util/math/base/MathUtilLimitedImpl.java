/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.math.api.MathUtilLimited;
import net.sf.mmm.util.math.api.NumberType;

/**
 * This class is a collection of utility functions for dealing with numbers.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MathUtilLimitedImpl extends AbstractComponent implements MathUtilLimited {

  /**
   * The constructor.
   */
  public MathUtilLimitedImpl() {

    super();
  }

  @Override
  public NumberType<? extends Number> getNumberType(Class<?> numericType) {

    if ((numericType == int.class) || (numericType == Integer.class)) {
      return NumberTypeImpl.INTEGER;
    } else if ((numericType == long.class) || (numericType == Long.class)) {
      return NumberTypeImpl.LONG;
    } else if ((numericType == double.class) || (numericType == Double.class)) {
      return NumberTypeImpl.DOUBLE;
    } else if ((numericType == float.class) || (numericType == Float.class)) {
      return NumberTypeImpl.FLOAT;
    } else if ((numericType == short.class) || (numericType == Short.class)) {
      return NumberTypeImpl.SHORT;
    } else if ((numericType == byte.class) || (numericType == Byte.class)) {
      return NumberTypeImpl.BYTE;
    } else if ((BigInteger.class.equals(numericType))) {
      return NumberTypeImpl.BIG_INTEGER;
    } else if ((BigDecimal.class.equals(numericType))) {
      return NumberTypeImpl.BIG_DECIMAL;
    } else {
      return null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <NUMBER extends Number> NumberType<NUMBER> getNumberTypeGeneric(Class<NUMBER> numericType) {

    return (NumberType<NUMBER>) getNumberType(numericType);
  }

  @Override
  public Number toSimplestNumber(Number value) {

    double d = value.doubleValue();
    long l = value.longValue();
    // is the value a numeric integer value?
    // findbugs is wrong here!
    if (l == d) {
      if (value.byteValue() == d) {
        return Byte.valueOf(value.byteValue());
      } else if (value.shortValue() == d) {
        return Short.valueOf(value.shortValue());
      } else if (value.intValue() == d) {
        return Integer.valueOf(value.intValue());
      } else {
        return Long.valueOf(l);
      }
    } else {
      if (value.floatValue() == d) {
        return Float.valueOf(value.floatValue());
      } else {
        return value;
      }
    }
  }

}
