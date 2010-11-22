/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.api.NumberType;

/**
 * This class is a collection of utility functions for dealing with numbers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class MathUtilImpl extends AbstractLoggableComponent implements MathUtil {

  /** @see #getInstance() */
  private static MathUtil instance;

  /**
   * The constructor.
   */
  public MathUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of {@link MathUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static MathUtil getInstance() {

    if (instance == null) {
      synchronized (MathUtilImpl.class) {
        if (instance == null) {
          MathUtilImpl impl = new MathUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
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
    } else if ((BigInteger.class.isAssignableFrom(numericType))) {
      return NumberTypeImpl.BIG_INTEGER;
    } else if ((BigDecimal.class.isAssignableFrom(numericType))) {
      return NumberTypeImpl.BIG_DECIMAL;
    } else if ((AtomicInteger.class.isAssignableFrom(numericType))) {
      return NumberTypeImpl.ATOMIC_INTEGER;
    } else if ((AtomicLong.class.isAssignableFrom(numericType))) {
      return NumberTypeImpl.ATOMIC_LONG;
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <NUMBER extends Number> NumberType<NUMBER> getNumberTypeGeneric(Class<NUMBER> numericType) {

    return (NumberType<NUMBER>) getNumberType(numericType);
  }

  /**
   * {@inheritDoc}
   */
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
