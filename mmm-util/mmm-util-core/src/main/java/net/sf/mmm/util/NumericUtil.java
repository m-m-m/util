/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

/**
 * This class is a collection of utility functions for handling and manipulation
 * of {@link Number numbers}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NumericUtil {

  /**
   * This is the singleton instance of this {@link NumericUtil}. Instead of
   * declaring the methods static, we declare this static instance what gives
   * the same way of access while still allowing a design for extension by
   * inheriting from this class.
   */
  public static final NumericUtil INSTANCE = new NumericUtil();

  /**
   * The constructor.
   */
  public NumericUtil() {

    super();
  }

  /**
   * This method converts the given value to the simplest suitable
   * {@link java.lang.Number number-type}. The ordering implied by "simplest"
   * is {@link Byte} &lt; {@link Short} &lt; {@link Integer} &lt; {@link Long}
   * &lt; {@link Float} &lt; {@link Double}.<br>
   * Please note that a decimal {@link Double} is only converted to
   * {@link Float} if the result is exaclty the same. Be aware that
   * <code>0.2F - 0.2</code> is NOT <code>0.0</code> (but
   * <code>2.980232227667301E-9</code>).
   * 
   * @param value is the value to convert.
   * @return a number with the same {@link Number#doubleValue()} as the given
   *         value and the simplest possible type.
   */
  public Number toSimplestNumber(Number value) {

    double d = value.doubleValue();
    long l = value.longValue();
    // is the value a numeric integer value?
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
