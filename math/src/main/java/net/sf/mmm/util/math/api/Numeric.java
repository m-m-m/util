/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.api;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Interface for a {@link Number numeric} value type.
 *
 * @author hohwille
 * @since 7.6.0
 */
public interface Numeric {

  /**
   * @return the value as a {@code byte}. Will be {@code 0} if value is {@code null}.
   */
  byte byteValue();

  /**
   * @return the value as a {@code short}. Will be {@code 0} if value is {@code null}.
   */
  short shortValue();

  /**
   * @return the value as a {@code int}. Will be {@code 0} if value is {@code null}.
   */
  int intValue();

  /**
   * @return the value as a {@code long}. Will be {@code 0} if value is {@code null}.
   */
  long longValue();

  /**
   * @return the value as a {@code float}. Will be {@code 0} if value is {@code null}.
   */
  float floatValue();

  /**
   * @return the value as a {@code double}. Will be {@code 0} if value is {@code null}.
   */
  double doubleValue();

  /**
   * @return the value as a {@link BigInteger}. Will be {@code 0} if value is {@code null}.
   */
  BigInteger bigIntegerValue();

  /**
   * @return the value as a {@link BigInteger}. Will be {@code 0} if value is {@code null}.
   */
  BigDecimal bigDecimalValue();
}
