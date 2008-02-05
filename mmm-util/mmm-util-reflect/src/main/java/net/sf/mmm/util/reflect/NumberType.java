/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class represents the {@link Class type} of a {@link Number}. It allows
 * to check attributes like {@link #isDecimal()} or to
 * {@link #getExactnessDifference(NumberType) compare} {@link NumberType}s.<br>
 * This is a class and NOT an {@link Enum} to be extensible.
 * 
 * @see ReflectionUtil#getNumberType(Class)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class NumberType {

  /** The {@link NumberType} for {@link Byte}. */
  public static final NumberType BYTE = new NumberType(Byte.class, false, 1);

  /** The {@link NumberType} for {@link Short}. */
  public static final NumberType SHORT = new NumberType(Short.class, false, 2);

  /** The {@link NumberType} for {@link Integer}. */
  public static final NumberType INTEGER = new NumberType(Integer.class, false, 3);

  /** The {@link NumberType} for {@link AtomicInteger}. */
  public static final NumberType ATOMIC_INTEGER = new NumberType(AtomicInteger.class, false, 4);

  /** The {@link NumberType} for {@link Long}. */
  public static final NumberType LONG = new NumberType(Long.class, false, 5);

  /** The {@link NumberType} for {@link AtomicLong}. */
  public static final NumberType ATOMIC_LONG = new NumberType(AtomicLong.class, false, 6);

  /** The {@link NumberType} for {@link Float}. */
  public static final NumberType FLOAT = new NumberType(Float.class, true, 7);

  /** The {@link NumberType} for {@link Double}. */
  public static final NumberType DOUBLE = new NumberType(Double.class, true, 8);

  /** The {@link NumberType} for {@link BigInteger}. */
  public static final NumberType BIG_INTEGER = new NumberType(BigInteger.class, false, 9);

  /** The {@link NumberType} for {@link BigDecimal}. */
  public static final NumberType BIG_DECIMAL = new NumberType(BigDecimal.class, true, 10);

  /** @see #getNumberClass() */
  private final Class<? extends Number> numberClass;

  /** @see #getExactnessDifference(NumberType) */
  private final int ordinal;

  /** @see #isDecimal() */
  private final boolean decimal;

  /**
   * The constructor.
   * 
   * @param numberClass is the associated
   *        {@link #getNumberClass() numeric-class}.
   * @param decimal is the {@link #isDecimal() decimal-flag}.
   * @param ordinal is the ordinal for
   *        {@link #getExactnessDifference(NumberType)}.
   */
  NumberType(Class<? extends Number> numberClass, boolean decimal, int ordinal) {

    this.numberClass = numberClass;
    this.decimal = decimal;
    this.ordinal = ordinal;
  }

  /**
   * This method gets the {@link Class} reflecting the {@link Number}
   * represented by this {@link NumberType}.
   * 
   * @return the number type.
   */
  public Class<? extends Number> getNumberClass() {

    return this.numberClass;
  }

  /**
   * This method determines if the represented {@link #getNumberClass() Number}
   * is a decimal value.<br>
   * E.g. {@link Double}, {@link Float}, or {@link BigDecimal} represent
   * decimal values while {@link Integer} or {@link Long} are NOT decimal.
   * 
   * @return <code>true</code> if the represented
   *         {@link #getNumberClass() Number} is a decimal value.
   */
  public boolean isDecimal() {

    return this.decimal;
  }

  /**
   * This method gets the difference of the exactness of this {@link NumberType}
   * and the given <code>otherType</code>.<br>
   * <b>ATTENTION:</b><br>
   * Some types such as {@link Double} and {@link BigInteger} are NOT really
   * comparable so the exactness difference might only make sense if the
   * compared {@link NumberType types} are both {@link #isDecimal() decimal} or
   * both {@link #isDecimal() non-decimal} (mathematical integers). However the
   * order of typical types is:<br>
   * <ol>
   * <li>{@link Byte}</li>
   * <li>{@link Short}</li>
   * <li>{@link Integer}</li>
   * <li>{@link Long}</li>
   * <li>{@link Float}</li>
   * <li>{@link Double}</li>
   * <li>{@link BigInteger}</li>
   * <li>{@link BigDecimal}</li>
   * </ol>
   * 
   * @param otherType is the {@link NumberType} to compare with.
   * @return the difference of the exactness. Will be <code>0</code> if this
   *         {@link NumberType} is equal to <code>otherType</code>, positive
   *         if this {@link NumberType} is more exact (later in the examples
   *         above) and negative if <code>otherType</code> is more exact.
   */
  public int getExactnessDifference(NumberType otherType) {

    return this.ordinal - otherType.ordinal;
  }

}
