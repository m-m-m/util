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

  /** @see #getInstance() */
  private static NumericUtil instance;

  /**
   * The constructor.
   */
  public NumericUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link NumericUtil}.<br>
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
  public static NumericUtil getInstance() {

    if (instance == null) {
      synchronized (NumericUtil.class) {
        if (instance == null) {
          instance = new NumericUtil();
        }
      }
    }
    return instance;
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
