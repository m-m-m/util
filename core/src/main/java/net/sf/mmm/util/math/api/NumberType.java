/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.api;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMaximumValue;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMinimumValue;

/**
 * This class represents the {@link Class type} reflecting a specific {@link Number}. It allows to check attributes like
 * {@link #isDecimal()} or to {@link #getExactnessDifference(NumberType) compare} {@link NumberType}s. <br>
 * Further it acts as factory to create according numbers {@link NumberType#valueOf(String) from string} or
 * {@link NumberType#valueOf(String) by converting an given number}. <br>
 * This is a class and NOT an {@link Enum} to be extensible.
 *
 * @see net.sf.mmm.util.math.base.MathUtilImpl#getNumberType(Class)
 * @param <NUMBER> is the generic type of the {@link #getNumberClass() represented number-class}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface NumberType<NUMBER extends Number>
    extends AttributeReadMinimumValue<NUMBER>, AttributeReadMaximumValue<NUMBER> {

  /**
   * This method gets an instance of the {@link #getNumberClass() represented number-class} with the numeric value given
   * by {@code number}.
   *
   * @param number is the numeric value to convert.
   * @param failIfUnprecise - if {@code true} and conversion causes precision loss, a {@link NumberConversionException}
   *        is thrown, if {@code false} this method acts like a primitive cast and conversion is always successfully.
   * @return a {@link Number} instance of the {@link #getNumberClass() represented number-class} with the value of the
   *         given {@code number}. This will be the same instance as {@code number} if it is an
   *         {@link Class#isInstance(Object) instance} of the {@link #getNumberClass() represented number-class} .
   * @throws NumberConversionException if the conversion will loose precision (e.g. when converting a decimal
   *         {@link Double} to {@link Integer})
   */
  NUMBER valueOf(Number number, boolean failIfUnprecise) throws NumberConversionException;

  /**
   * This method gets an instance of the {@link #getNumberClass() represented number-class} with the numeric value
   * identified by the given string {@code number}.
   *
   * @param number is the string to be parsed as number.
   * @return the parsed number of the according type.
   * @throws NumberConversionException if the given {@code number} has an illegal format.
   */
  NUMBER valueOf(String number) throws NumberConversionException;

  /**
   * This method gets the {@link Class} reflecting the {@link Number} represented by this {@link NumberType}.
   *
   * @return the number type.
   */
  Class<NUMBER> getNumberClass();

  /**
   * This method determines if the represented {@link #getNumberClass() Number} is a decimal value. <br>
   * E.g. {@link Double}, {@link Float}, or {@link java.math.BigDecimal} represent decimal values while {@link Integer}
   * or {@link Long} are NOT decimal.
   *
   * @return {@code true} if the represented {@link #getNumberClass() Number} is a decimal value.
   */
  boolean isDecimal();

  /**
   * This method gets the difference of the exactness of this {@link NumberType} and the given {@code otherType}. <br>
   * <b>ATTENTION:</b><br>
   * Some types such as {@link Double} and {@link java.math.BigInteger} are NOT really comparable so the exactness
   * difference might only make sense if the compared {@link NumberType types} are both {@link #isDecimal() decimal} or
   * both {@link #isDecimal() non-decimal} (mathematical integers). However the order of typical types is:<br>
   * <ol>
   * <li>{@link Byte}</li>
   * <li>{@link Short}</li>
   * <li>{@link Integer}</li>
   * <li>{@link Long}</li>
   * <li>{@link Float}</li>
   * <li>{@link Double}</li>
   * <li>{@link java.math.BigInteger}</li>
   * <li>{@link java.math.BigDecimal}</li>
   * </ol>
   *
   * @param otherType is the {@link NumberType} to compare with.
   * @return the difference of the exactness. Will be {@code 0} if this {@link NumberType} is equal to {@code otherType}
   *         , positive if this {@link NumberType} is more exact (later in the examples above) and negative if
   *         {@code otherType} is more exact.
   */
  int getExactnessDifference(NumberType<?> otherType);

  /**
   * {@inheritDoc}
   *
   * Will return {@code null} in case of an unbounded type such as {@link java.math.BigInteger}.
   *
   * @since 4.0.0
   */
  @Override
  NUMBER getMinimumValue();

  /**
   * {@inheritDoc}
   *
   * Will return {@code null} in case of an unbounded type such as {@link java.math.BigInteger}.
   *
   * @since 4.0.0
   */
  @Override
  NUMBER getMaximumValue();

}
