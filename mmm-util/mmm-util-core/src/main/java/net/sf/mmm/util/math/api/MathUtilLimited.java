/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.api;

/**
 * This is a limited subset of {@link MathUtil} that is GWT compatible.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface MathUtilLimited {

  /**
   * This method gets the {@link NumberType} for the given <code>numericType</code>.
   * 
   * @see #getNumberTypeGeneric(Class)
   * 
   * @param numericType is the class reflecting a {@link Number}. It may be {@link Class#isPrimitive()
   *        primitive} (such as <code>int.class</code>). The signature is NOT bound to {@link Number} to make
   *        it easy for the caller (e.g. <code>Number.class.isAssignableFrom(int.class)</code> is
   *        <code>false</code>).
   * @return the {@link NumberType} representing the given <code>numericType</code> or <code>null</code> if
   *         the given <code>numericType</code> is no {@link Number} or is NOT known (you may extend this
   *         {@link MathUtilLimited} in such case).
   */
  NumberType<? extends Number> getNumberType(Class<?> numericType);

  /**
   * This method gets the {@link NumberType} for the given <code>numericType</code>.
   * 
   * @param <NUMBER> is the generic type of the <code>numericType</code>.
   * @param numericType is the class reflecting a {@link Number}. It may be {@link Class#isPrimitive()
   *        primitive} (such as <code>int.class</code>).
   * @return the {@link NumberType} representing the given <code>numericType</code> or <code>null</code> if
   *         the given <code>numericType</code> is NOT known (you may extend this {@link MathUtilLimited} in
   *         such case).
   */
  <NUMBER extends Number> NumberType<NUMBER> getNumberTypeGeneric(Class<NUMBER> numericType);

  /**
   * This method converts the given value to the simplest suitable {@link java.lang.Number number-type}. The
   * ordering implied by "simplest" is {@link Byte} &lt; {@link Short} &lt; {@link Integer} &lt; {@link Long}
   * &lt; {@link Float} &lt; {@link Double}.<br>
   * Please note that a decimal {@link Double} is only converted to {@link Float} if the result is exactly the
   * same. Be aware that <code>0.2F - 0.2</code> is NOT <code>0.0</code> (but
   * <code>2.980232227667301E-9</code>).
   * 
   * @param value is the value to convert.
   * @return a number with the same {@link Number#doubleValue()} as the given value and the simplest possible
   *         type.
   */
  Number toSimplestNumber(Number value);

}
