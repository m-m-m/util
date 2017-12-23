/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.api;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * Default {@link Numeric} implementation based on {@link Number}-based {@link AttributeReadValue}.
 *
 * @param <N> generic type of the {@link Number} {@link #getValue() value}.
 * @author hohwille
 * @since 8.6.0
 */
public interface NumericValue<N extends Number> extends Numeric, AttributeReadValue<N> {

  @Override
  default byte byteValue() {

    N value = getValue();
    if (value == null) {
      return 0;
    }
    return value.byteValue();
  }

  @Override
  default short shortValue() {

    N value = getValue();
    if (value == null) {
      return 0;
    }
    return value.shortValue();
  }

  @Override
  default int intValue() {

    N value = getValue();
    if (value == null) {
      return 0;
    }
    return value.intValue();
  }

  @Override
  default long longValue() {

    N value = getValue();
    if (value == null) {
      return 0;
    }
    return value.longValue();
  }

  @Override
  default float floatValue() {

    N value = getValue();
    if (value == null) {
      return 0;
    }
    return value.floatValue();
  }

  @Override
  default double doubleValue() {

    N value = getValue();
    if (value == null) {
      return 0;
    }
    return value.doubleValue();
  }

  @Override
  default BigInteger bigIntegerValue() {

    N value = getValue();
    if (value == null) {
      return BigInteger.ZERO;
    } else if (value instanceof BigInteger) {
      return (BigInteger) value;
    } else if (value instanceof BigDecimal) {
      return ((BigDecimal) value).toBigInteger();
    } else {
      return BigInteger.valueOf(value.longValue());
    }
  }

  @Override
  default BigDecimal bigDecimalValue() {

    N value = getValue();
    if (value == null) {
      return BigDecimal.ZERO;
    } else if (value instanceof BigDecimal) {
      return (BigDecimal) value;
    } else if (value instanceof BigInteger) {
      return new BigDecimal((BigInteger) value);
    } else {
      return BigDecimal.valueOf(value.doubleValue());
    }
  }

}
