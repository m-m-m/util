/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class is a collection of utility functions for dealing with numbers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MathUtil {

  /** @see #getInstance() */
  private static MathUtil instance;

  /**
   * The constructor.
   */
  public MathUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link MathUtil}.<br>
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
      synchronized (MathUtil.class) {
        if (instance == null) {
          instance = new MathUtil();
        }
      }
    }
    return instance;
  }

  /**
   * This method gets the {@link NumberType} for the given
   * <code>numericType</code>.
   * 
   * @see #getNumberTypeGeneric(Class)
   * 
   * @param numericType is the class reflecting a {@link Number}. It may be
   *        {@link Class#isPrimitive() primitive} (such as
   *        <code>int.class</code>). The signature is NOT bound to
   *        {@link Number} to make it easy for the caller (e.g.
   *        <code>Number.class.isAssignableFrom(int.class)</code> is
   *        <code>false</code>).
   * @return the {@link NumberType} representing the given
   *         <code>numericType</code> or <code>null</code> if the given
   *         <code>numericType</code> is no {@link Number} or is NOT known
   *         (you may extend this {@link MathUtil} in such case).
   */
  @SuppressWarnings("unchecked")
  public NumberType<? extends Number> getNumberType(Class<?> numericType) {

    if ((numericType == int.class) || (numericType == Integer.class)) {
      return NumberType.INTEGER;
    } else if ((numericType == long.class) || (numericType == Long.class)) {
      return NumberType.LONG;
    } else if ((numericType == double.class) || (numericType == Double.class)) {
      return NumberType.DOUBLE;
    } else if ((numericType == float.class) || (numericType == Float.class)) {
      return NumberType.FLOAT;
    } else if ((numericType == short.class) || (numericType == Short.class)) {
      return NumberType.SHORT;
    } else if ((numericType == byte.class) || (numericType == Byte.class)) {
      return NumberType.BYTE;
    } else if ((BigInteger.class.isAssignableFrom(numericType))) {
      return NumberType.BIG_INTEGER;
    } else if ((BigDecimal.class.isAssignableFrom(numericType))) {
      return NumberType.BIG_DECIMAL;
    } else if ((AtomicInteger.class.isAssignableFrom(numericType))) {
      return NumberType.ATOMIC_INTEGER;
    } else if ((AtomicLong.class.isAssignableFrom(numericType))) {
      return NumberType.ATOMIC_LONG;
    } else {
      return null;
    }
  }

  /**
   * This method gets the {@link NumberType} for the given
   * <code>numericType</code>.
   * 
   * @param <NUMBER> is the generic type of the <code>numericType</code>.
   * @param numericType is the class reflecting a {@link Number}. It may be
   *        {@link Class#isPrimitive() primitive} (such as
   *        <code>int.class</code>).
   * @return the {@link NumberType} representing the given
   *         <code>numericType</code> or <code>null</code> if the given
   *         <code>numericType</code> is NOT known (you may extend this
   *         {@link MathUtil} in such case).
   */
  @SuppressWarnings("unchecked")
  public <NUMBER extends Number> NumberType<NUMBER> getNumberTypeGeneric(Class<NUMBER> numericType) {

    return (NumberType<NUMBER>) getNumberType(numericType);
  }
}
