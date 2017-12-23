/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.api;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A {@link NumericOperation} is a function to compute a {@link Number} from two {@link Numeric} values.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.6.0
 */
public abstract class NumericOperation {

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link Byte}.
   */
  public abstract Byte computeByte(Numeric value1, Numeric value2);

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link Short}.
   */
  public abstract Short computeShort(Numeric value1, Numeric value2);

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link Integer}.
   */
  public abstract Integer computeInteger(Numeric value1, Numeric value2);

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link Long}.
   */
  public abstract Long computeLong(Numeric value1, Numeric value2);

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link Float}.
   */
  public abstract Float computeFloat(Numeric value1, Numeric value2);

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link Double}.
   */
  public abstract Double computeDouble(Numeric value1, Numeric value2);

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link BigInteger}.
   */
  public abstract BigInteger computeBigInteger(Numeric value1, Numeric value2);

  /**
   * @param value1 the first {@link Numeric} value.
   * @param value2 the second {@link Numeric} value.
   * @return the computed value as {@link BigDecimal}.
   */
  public abstract BigDecimal computeBigDecimal(Numeric value1, Numeric value2);

  /**
   * {@link NumericOperation} for add ({@code +}).
   */
  public static final NumericOperation ADD = new NumericOperation() {

    @Override
    public Byte computeByte(Numeric value1, Numeric value2) {

      return Byte.valueOf((byte) (value1.byteValue() + value2.byteValue()));
    }

    @Override
    public Short computeShort(Numeric value1, Numeric value2) {

      return Short.valueOf((short) (value1.shortValue() + value2.shortValue()));
    }

    @Override
    public Integer computeInteger(Numeric value1, Numeric value2) {

      return Integer.valueOf(value1.intValue() + value2.intValue());
    }

    @Override
    public Long computeLong(Numeric value1, Numeric value2) {

      return Long.valueOf(value1.longValue() + value2.longValue());
    }

    @Override
    public Float computeFloat(Numeric value1, Numeric value2) {

      return Float.valueOf(value1.floatValue() + value2.floatValue());
    }

    @Override
    public Double computeDouble(Numeric value1, Numeric value2) {

      return Double.valueOf(value1.doubleValue() + value2.doubleValue());
    }

    @Override
    public BigInteger computeBigInteger(Numeric value1, Numeric value2) {

      return value1.bigIntegerValue().add(value2.bigIntegerValue());
    }

    @Override
    public BigDecimal computeBigDecimal(Numeric value1, Numeric value2) {

      return value1.bigDecimalValue().add(value2.bigDecimalValue());
    }
  };

  /**
   * {@link NumericOperation} for subtract ({@code -}).
   */
  public static final NumericOperation SUBTRACT = new NumericOperation() {

    @Override
    public Byte computeByte(Numeric value1, Numeric value2) {

      return Byte.valueOf((byte) (value1.byteValue() - value2.byteValue()));
    }

    @Override
    public Short computeShort(Numeric value1, Numeric value2) {

      return Short.valueOf((short) (value1.shortValue() - value2.shortValue()));
    }

    @Override
    public Integer computeInteger(Numeric value1, Numeric value2) {

      return Integer.valueOf(value1.intValue() - value2.intValue());
    }

    @Override
    public Long computeLong(Numeric value1, Numeric value2) {

      return Long.valueOf(value1.longValue() - value2.longValue());
    }

    @Override
    public Float computeFloat(Numeric value1, Numeric value2) {

      return Float.valueOf(value1.floatValue() - value2.floatValue());
    }

    @Override
    public Double computeDouble(Numeric value1, Numeric value2) {

      return Double.valueOf(value1.doubleValue() - value2.doubleValue());
    }

    @Override
    public BigInteger computeBigInteger(Numeric value1, Numeric value2) {

      return value1.bigIntegerValue().subtract(value2.bigIntegerValue());
    }

    @Override
    public BigDecimal computeBigDecimal(Numeric value1, Numeric value2) {

      return value1.bigDecimalValue().subtract(value2.bigDecimalValue());
    }
  };

  /**
   * {@link NumericOperation} for multiply ({@code *}).
   */
  public static final NumericOperation MULTIPLY = new NumericOperation() {

    @Override
    public Byte computeByte(Numeric value1, Numeric value2) {

      return Byte.valueOf((byte) (value1.byteValue() * value2.byteValue()));
    }

    @Override
    public Short computeShort(Numeric value1, Numeric value2) {

      return Short.valueOf((short) (value1.shortValue() * value2.shortValue()));
    }

    @Override
    public Integer computeInteger(Numeric value1, Numeric value2) {

      return Integer.valueOf(value1.intValue() * value2.intValue());
    }

    @Override
    public Long computeLong(Numeric value1, Numeric value2) {

      return Long.valueOf(value1.longValue() * value2.longValue());
    }

    @Override
    public Float computeFloat(Numeric value1, Numeric value2) {

      return Float.valueOf(value1.floatValue() * value2.floatValue());
    }

    @Override
    public Double computeDouble(Numeric value1, Numeric value2) {

      return Double.valueOf(value1.doubleValue() * value2.doubleValue());
    }

    @Override
    public BigInteger computeBigInteger(Numeric value1, Numeric value2) {

      return value1.bigIntegerValue().multiply(value2.bigIntegerValue());
    }

    @Override
    public BigDecimal computeBigDecimal(Numeric value1, Numeric value2) {

      return value1.bigDecimalValue().multiply(value2.bigDecimalValue());
    }
  };

  /**
   * {@link NumericOperation} for divide ({@code /}).
   */
  public static final NumericOperation DIVIDE = new NumericOperation() {

    @Override
    public Byte computeByte(Numeric value1, Numeric value2) {

      return Byte.valueOf((byte) (value1.byteValue() / value2.byteValue()));
    }

    @Override
    public Short computeShort(Numeric value1, Numeric value2) {

      return Short.valueOf((short) (value1.shortValue() / value2.shortValue()));
    }

    @Override
    public Integer computeInteger(Numeric value1, Numeric value2) {

      return Integer.valueOf(value1.intValue() / value2.intValue());
    }

    @Override
    public Long computeLong(Numeric value1, Numeric value2) {

      return Long.valueOf(value1.longValue() / value2.longValue());
    }

    @Override
    public Float computeFloat(Numeric value1, Numeric value2) {

      return Float.valueOf(value1.floatValue() / value2.floatValue());
    }

    @Override
    public Double computeDouble(Numeric value1, Numeric value2) {

      return Double.valueOf(value1.doubleValue() / value2.doubleValue());
    }

    @Override
    public BigInteger computeBigInteger(Numeric value1, Numeric value2) {

      return value1.bigIntegerValue().divide(value2.bigIntegerValue());
    }

    @Override
    public BigDecimal computeBigDecimal(Numeric value1, Numeric value2) {

      return value1.bigDecimalValue().divide(value2.bigDecimalValue());
    }
  };
}
