/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import net.sf.mmm.util.math.MathUtil;

/**
 * This class represents the {@link Class type} reflecting a specific
 * {@link Number}. It allows to check attributes like {@link #isDecimal()} or
 * to {@link #getExactnessDifference(NumberType) compare} {@link NumberType}s.<br>
 * Further it acts as factory to create according numbers
 * {@link NumberType#valueOf(String) from string} or
 * {@link NumberType#valueOf(String) by converting an given number}.<br>
 * This is a class and NOT an {@link Enum} to be extensible.
 * 
 * @see MathUtil#getNumberType(Class)
 * @param <NUMBER> is the generic type of the
 *        {@link #getNumberClass() represented number-class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class NumberType<NUMBER extends Number> {

  /** The maximum delta allowed for {@link #valueOf(Number, boolean)}. */
  private static final double REQUIRED_PRECISION = 0.0000001;

  /** The {@link NumberType} for {@link Byte}. */
  public static final NumberType<Byte> BYTE = new NumberType<Byte>(1) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Byte> getNumberClass() {

      return Byte.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Byte convert(Number number) {

      return Byte.valueOf(number.byteValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Byte parse(String number) throws NumberFormatException {

      return Byte.valueOf(number);
    }

  };

  /** The {@link NumberType} for {@link Short}. */
  public static final NumberType<Short> SHORT = new NumberType<Short>(2) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Short> getNumberClass() {

      return Short.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Short convert(Number number) {

      return Short.valueOf(number.shortValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Short parse(String number) throws NumberFormatException {

      return Short.valueOf(number);
    }

  };

  /** The {@link NumberType} for {@link Integer}. */
  public static final NumberType<Integer> INTEGER = new NumberType<Integer>(3) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Integer> getNumberClass() {

      return Integer.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer convert(Number number) {

      return Integer.valueOf(number.intValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer parse(String number) throws NumberFormatException {

      return Integer.valueOf(number);
    }

  };

  /** The {@link NumberType} for {@link AtomicInteger}. */
  public static final NumberType<AtomicInteger> ATOMIC_INTEGER = new NumberType<AtomicInteger>(3) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<AtomicInteger> getNumberClass() {

      return AtomicInteger.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicInteger convert(Number number) {

      return new AtomicInteger(number.intValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicInteger parse(String number) throws NumberFormatException {

      return new AtomicInteger(Integer.parseInt(number));
    }

  };

  /** The {@link NumberType} for {@link Long}. */
  public static final NumberType<Long> LONG = new NumberType<Long>(4) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Long> getNumberClass() {

      return Long.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long convert(Number number) {

      return Long.valueOf(number.longValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long parse(String number) throws NumberFormatException {

      return Long.valueOf(number);
    }

  };

  /** The {@link NumberType} for {@link AtomicLong}. */
  public static final NumberType<AtomicLong> ATOMIC_LONG = new NumberType<AtomicLong>(4) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<AtomicLong> getNumberClass() {

      return AtomicLong.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicLong convert(Number number) {

      return new AtomicLong(number.longValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicLong parse(String number) throws NumberFormatException {

      return new AtomicLong(Long.parseLong(number));
    }

  };

  /** The {@link NumberType} for {@link Float}. */
  public static final NumberType<Float> FLOAT = new NumberType<Float>(5) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Float> getNumberClass() {

      return Float.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Float convert(Number number) {

      return Float.valueOf(number.floatValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Float parse(String number) throws NumberFormatException {

      return Float.valueOf(number);
    }

  };

  /** The {@link NumberType} for {@link Double}. */
  public static final NumberType<Double> DOUBLE = new NumberType<Double>(6) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Double> getNumberClass() {

      return Double.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Double convert(Number number) {

      return Double.valueOf(number.doubleValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Double parse(String number) throws NumberFormatException {

      return Double.valueOf(number);
    }

  };

  /** The {@link NumberType} for {@link BigInteger}. */
  public static final NumberType<BigInteger> BIG_INTEGER = new NumberType<BigInteger>(7) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<BigInteger> getNumberClass() {

      return BigInteger.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BigInteger convert(Number number) {

      return BigInteger.valueOf(number.longValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BigInteger parse(String number) throws NumberFormatException {

      return new BigInteger(number);
    }

  };

  /** The {@link NumberType} for {@link BigDecimal}. */
  public static final NumberType<BigDecimal> BIG_DECIMAL = new NumberType<BigDecimal>(8) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<BigDecimal> getNumberClass() {

      return BigDecimal.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BigDecimal convert(Number number) {

      return BigDecimal.valueOf(number.doubleValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BigDecimal parse(String number) throws NumberFormatException {

      return new BigDecimal(number);
    }

  };

  /** @see #getExactnessDifference(NumberType) */
  private final int exactness;

  /**
   * The constructor.
   * 
   * @param exactness is the internal exactness level for
   *        {@link #getExactnessDifference(NumberType)}.
   */
  NumberType(int exactness) {

    this.exactness = exactness;
  }

  /**
   * This method converts the given <code>number</code> to the
   * {@link #getNumberClass() number-class} represented by this object. Like a
   * cast this operation may loose precision (e.g. when converting a
   * {@link Double} to {@link Integer}) without warning. Use
   * {@link #valueOf(Number, boolean)} instead to avoid this.
   * 
   * @param number is the number to convert.
   * @return the converted number.
   */
  protected abstract NUMBER convert(Number number);

  /**
   * This method gets an instance of the
   * {@link #getNumberClass() represented number-class} with the numeric value
   * given by <code>number</code>.
   * 
   * @param number is the numeric value to convert.
   * @param failIfUnprecise - if <code>true</code> and conversion causes
   *        precision loss, a {@link NumberConversionException} is thrown, if
   *        <code>false</code> this method acts like a primitive cast and
   *        conversion is always successfully.
   * @return a {@link Number} instance of the
   *         {@link #getNumberClass() represented number-class} with the value
   *         of the given <code>number</code>. This will be the same instance
   *         as <code>number</code> if it is an
   *         {@link Class#isInstance(Object) instance} of the
   *         {@link #getNumberClass() represented number-class}.
   * @throws NumberConversionException if the conversion will loose precision
   *         (e.g. when converting a decimal {@link Double} to {@link Integer})
   */
  public NUMBER valueOf(Number number, boolean failIfUnprecise) throws NumberConversionException {

    if (getNumberClass().isInstance(number)) {
      return getNumberClass().cast(number);
    }
    NUMBER converted = convert(number);
    if (failIfUnprecise) {
      double delta = number.doubleValue() - converted.doubleValue();
      if (delta == 0) {
        // e.g. to detect precision loss for BigDecimal
        delta = number.longValue() - converted.longValue();
      }
      if (delta < 0) {
        delta = -delta;
      }
      if (delta > REQUIRED_PRECISION) {
        throw new NumberConversionException(number, getNumberClass());
      }
      // TODO: BigInt / BigDeci
    }
    return converted;
  }

  /**
   * This method gets an instance of the
   * {@link #getNumberClass() represented number-class} with the numeric value
   * identified by the given string <code>number</code>.
   * 
   * @param number is the string to be parsed as number.
   * @return the parsed number of the according type.
   * @throws NumberConversionException if the given <code>number</code> has an
   *         illegal format.
   */
  public NUMBER valueOf(String number) throws NumberConversionException {

    try {
      return parse(number);
    } catch (NumberFormatException e) {
      throw new NumberConversionException(e, number, getNumberClass());
    }
  }

  /**
   * This method gets an instance of the
   * {@link #getNumberClass() represented number-class} with the numeric value
   * identified by the given string <code>number</code>.
   * 
   * @param number is the string to be parsed as number.
   * @return the parsed number of the according type.
   * @throws NumberConversionException if the given <code>number</code> has an
   *         illegal format.
   * @throws NumberFormatException if the given <code>number</code> has an
   *         illegal format. This exception will be converted to a
   *         {@link NumberConversionException}.
   */
  protected abstract NUMBER parse(String number) throws NumberConversionException,
      NumberFormatException;

  /**
   * This method gets the {@link Class} reflecting the {@link Number}
   * represented by this {@link NumberType}.
   * 
   * @return the number type.
   */
  public abstract Class<NUMBER> getNumberClass();

  /**
   * This method determines if the represented {@link #getNumberClass() Number}
   * is a decimal value.<br>
   * E.g. {@link Double}, {@link Float}, or {@link BigDecimal} represent
   * decimal values while {@link Integer} or {@link Long} are NOT decimal.
   * 
   * @return <code>true</code> if the represented
   *         {@link #getNumberClass() Number} is a decimal value.
   */
  public abstract boolean isDecimal();

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
  public int getExactnessDifference(NumberType<?> otherType) {

    return this.exactness - otherType.exactness;
  }

}
