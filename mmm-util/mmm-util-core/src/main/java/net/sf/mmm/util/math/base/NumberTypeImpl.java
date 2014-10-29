/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.sf.mmm.util.math.api.NumberConversionException;
import net.sf.mmm.util.math.api.NumberType;

/**
 * This is the implementation of the {@link NumberType} interface.
 * 
 * @see net.sf.mmm.util.math.base.MathUtilImpl#getNumberType(Class)
 * @param <NUMBER> is the generic type of the {@link #getNumberClass() represented number-class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class NumberTypeImpl<NUMBER extends Number> implements NumberType<NUMBER> {

  /** The maximum delta allowed for {@link #valueOf(Number, boolean)}. */
  private static final double REQUIRED_PRECISION = 0.0000001;

  /** The {@link NumberTypeImpl} for {@link Byte}. */
  public static final NumberTypeImpl<Byte> BYTE = new NumberTypeImpl<Byte>(1, Byte.valueOf(Byte.MIN_VALUE),
      Byte.valueOf(Byte.MAX_VALUE)) {

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

  /** The {@link NumberTypeImpl} for {@link Short}. */
  public static final NumberTypeImpl<Short> SHORT = new NumberTypeImpl<Short>(2, Short.valueOf(Short.MIN_VALUE),
      Short.valueOf(Short.MAX_VALUE)) {

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

  /** The {@link NumberTypeImpl} for {@link Integer}. */
  public static final NumberTypeImpl<Integer> INTEGER = new NumberTypeImpl<Integer>(3,
      Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE)) {

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

  /** The {@link NumberTypeImpl} for {@link Long}. */
  public static final NumberTypeImpl<Long> LONG = new NumberTypeImpl<Long>(4, Long.valueOf(Long.MIN_VALUE),
      Long.valueOf(Long.MAX_VALUE)) {

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

  /** The {@link NumberTypeImpl} for {@link Float}. */
  public static final NumberTypeImpl<Float> FLOAT = new NumberTypeImpl<Float>(5, Float.valueOf(Float.MIN_VALUE),
      Float.valueOf(Float.MAX_VALUE)) {

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

  /** The {@link NumberTypeImpl} for {@link Double}. */
  public static final NumberTypeImpl<Double> DOUBLE = new NumberTypeImpl<Double>(6,
      Double.valueOf(Double.MIN_VALUE), Double.valueOf(Double.MAX_VALUE)) {

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

  /** The {@link NumberTypeImpl} for {@link BigInteger}. */
  public static final NumberTypeImpl<BigInteger> BIG_INTEGER = new NumberTypeImpl<BigInteger>(7, null, null) {

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

  /** The {@link NumberTypeImpl} for {@link BigDecimal}. */
  public static final NumberTypeImpl<BigDecimal> BIG_DECIMAL = new NumberTypeImpl<BigDecimal>(8, null, null) {

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

  /** @see #getMinimumValue() */
  private final NUMBER minimumValue;

  /** @see #getMaximumValue() */
  private final NUMBER maximumValue;

  /**
   * The constructor.
   * 
   * @param exactness is the internal exactness level for {@link #getExactnessDifference(NumberType)}.
   * @param min is the {@link #getMinimumValue() minimum value}.
   * @param max is the {@link #getMaximumValue() maximum value}.
   */
  NumberTypeImpl(int exactness, NUMBER min, NUMBER max) {

    super();
    this.exactness = exactness;
    this.minimumValue = min;
    this.maximumValue = max;
  }

  /**
   * This method converts the given <code>number</code> to the {@link #getNumberClass() number-class} represented by
   * this object. Like a cast this operation may loose precision (e.g. when converting a {@link Double} to
   * {@link Integer}) without warning. Use {@link #valueOf(Number, boolean)} instead to avoid this.
   * 
   * @param number is the number to convert.
   * @return the converted number.
   */
  protected abstract NUMBER convert(Number number);

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public NUMBER valueOf(Number number, boolean failIfUnprecise) throws NumberConversionException {

    if (number == null) {
      return null;
    }
    if (getNumberClass().equals(number.getClass())) {
      // cast is not yet supported by GWT
      return (NUMBER) number;
      // return getNumberClass().cast(number);
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
      // TODO: BigInt / BigDecimal
    }
    return converted;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NUMBER valueOf(String number) throws NumberConversionException {

    try {
      return parse(number);
    } catch (NumberFormatException e) {
      throw new NumberConversionException(e, number, getNumberClass());
    }
  }

  /**
   * This method gets an instance of the {@link #getNumberClass() represented number-class} with the numeric value
   * identified by the given string <code>number</code>.
   * 
   * @param number is the string to be parsed as number.
   * @return the parsed number of the according type.
   * @throws NumberConversionException if the given <code>number</code> has an illegal format.
   * @throws NumberFormatException if the given <code>number</code> has an illegal format. This exception will be
   *         converted to a {@link NumberConversionException}.
   */
  protected abstract NUMBER parse(String number) throws NumberConversionException, NumberFormatException;

  /**
   * This method gets the difference of the exactness of this {@link NumberTypeImpl} and the given
   * <code>otherType</code>. <br>
   * <b>ATTENTION:</b><br>
   * Some types such as {@link Double} and {@link BigInteger} are NOT really comparable so the exactness difference
   * might only make sense if the compared {@link NumberTypeImpl types} are both {@link #isDecimal() decimal} or both
   * {@link #isDecimal() non-decimal} (mathematical integers). However the order of typical types is:<br>
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
   * @param otherType is the {@link NumberTypeImpl} to compare with.
   * @return the difference of the exactness. Will be <code>0</code> if this {@link NumberTypeImpl} is equal to
   *         <code>otherType</code>, positive if this {@link NumberTypeImpl} is more exact (later in the examples above)
   *         and negative if <code>otherType</code> is more exact.
   */
  @Override
  public int getExactnessDifference(NumberType<?> otherType) {

    return this.exactness - ((NumberTypeImpl<?>) otherType).exactness;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NUMBER getMinimumValue() {

    return this.minimumValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NUMBER getMaximumValue() {

    return this.maximumValue;
  }

}
