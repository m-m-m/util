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
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code byte}.
   */
  public byte computeByte(Numeric value1, Numeric value2) {

    return computeByte(value1.byteValue(), value2.byteValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code byte}.
   */
  public byte computeByte(Number value1, Number value2) {

    return computeByte(value1.byteValue(), value2.byteValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code byte}.
   */
  public abstract byte computeByte(byte value1, byte value2);

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code short}.
   */
  public short computeShort(Numeric value1, Numeric value2) {

    return computeShort(value1.shortValue(), value2.shortValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code short}.
   */
  public short computeShort(Number value1, Number value2) {

    return computeShort(value1.shortValue(), value2.shortValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code short}.
   */
  public abstract short computeShort(short value1, short value2);

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code int}.
   */
  public int computeInteger(Numeric value1, Numeric value2) {

    return computeInteger(value1.intValue(), value2.intValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code int}.
   */
  public int computeInteger(Number value1, Number value2) {

    return computeInteger(value1.intValue(), value2.intValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code int}.
   */
  public abstract int computeInteger(int value1, int value2);

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code long}.
   */
  public long computeLong(Numeric value1, Numeric value2) {

    return computeLong(value1.longValue(), value2.longValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code long}.
   */
  public long computeLong(Number value1, Number value2) {

    return computeLong(value1.longValue(), value2.longValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code long}.
   */
  public abstract long computeLong(long value1, long value2);

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code float}.
   */
  public float computeFloat(Numeric value1, Numeric value2) {

    return computeFloat(value1.floatValue(), value2.floatValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code float}.
   */
  public float computeFloat(Number value1, Number value2) {

    return computeFloat(value1.floatValue(), value2.floatValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code float}.
   */
  public abstract float computeFloat(float value1, float value2);

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code double}.
   */
  public double computeDouble(Numeric value1, Numeric value2) {

    return computeDouble(value1.doubleValue(), value2.doubleValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code double}.
   */
  public double computeDouble(Number value1, Number value2) {

    return computeDouble(value1.doubleValue(), value2.doubleValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@code double}.
   */
  public abstract double computeDouble(double value1, double value2);

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@link BigInteger}.
   */
  public BigInteger computeBigInteger(Numeric value1, Numeric value2) {

    return computeBigInteger(value1.bigIntegerValue(), value2.bigIntegerValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@link BigInteger}.
   */
  public abstract BigInteger computeBigInteger(BigInteger value1, BigInteger value2);

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@link BigDecimal}.
   */
  public BigDecimal computeBigDecimal(Numeric value1, Numeric value2) {

    return computeBigDecimal(value1.bigDecimalValue(), value2.bigDecimalValue());
  }

  /**
   * @param value1 the first value.
   * @param value2 the second value.
   * @return the computed value as {@link BigDecimal}.
   */
  public abstract BigDecimal computeBigDecimal(BigDecimal value1, BigDecimal value2);

  /**
   * {@link NumericOperation} for add ({@code +}).
   */
  public static final NumericOperation ADD = new NumericOperation() {

    @Override
    public byte computeByte(byte value1, byte value2) {

      return (byte) (value1 + value2);
    }

    @Override
    public short computeShort(short value1, short value2) {

      return (short) (value1 + value2);
    }

    @Override
    public int computeInteger(int value1, int value2) {

      return value1 + value2;
    }

    @Override
    public long computeLong(long value1, long value2) {

      return value1 + value2;
    }

    @Override
    public float computeFloat(float value1, float value2) {

      return value1 + value2;
    }

    @Override
    public double computeDouble(double value1, double value2) {

      return value1 + value2;
    }

    @Override
    public BigInteger computeBigInteger(BigInteger value1, BigInteger value2) {

      return value1.add(value2);
    }

    @Override
    public BigDecimal computeBigDecimal(BigDecimal value1, BigDecimal value2) {

      return value1.add(value2);
    }
  };

  /**
   * {@link NumericOperation} for subtract ({@code -}).
   */
  public static final NumericOperation SUBTRACT = new NumericOperation() {

    @Override
    public byte computeByte(byte value1, byte value2) {

      return (byte) (value1 - value2);
    }

    @Override
    public short computeShort(short value1, short value2) {

      return (short) (value1 - value2);
    }

    @Override
    public int computeInteger(int value1, int value2) {

      return value1 - value2;
    }

    @Override
    public long computeLong(long value1, long value2) {

      return value1 - value2;
    }

    @Override
    public float computeFloat(float value1, float value2) {

      return value1 - value2;
    }

    @Override
    public double computeDouble(double value1, double value2) {

      return value1 - value2;
    }

    @Override
    public BigInteger computeBigInteger(BigInteger value1, BigInteger value2) {

      return value1.subtract(value2);
    }

    @Override
    public BigDecimal computeBigDecimal(BigDecimal value1, BigDecimal value2) {

      return value1.subtract(value2);
    }
  };

  /**
   * {@link NumericOperation} for multiply ({@code *}).
   */
  public static final NumericOperation MULTIPLY = new NumericOperation() {

    @Override
    public byte computeByte(byte value1, byte value2) {

      return (byte) (value1 * value2);
    }

    @Override
    public short computeShort(short value1, short value2) {

      return (short) (value1 * value2);
    }

    @Override
    public int computeInteger(int value1, int value2) {

      return value1 * value2;
    }

    @Override
    public long computeLong(long value1, long value2) {

      return value1 * value2;
    }

    @Override
    public float computeFloat(float value1, float value2) {

      return value1 * value2;
    }

    @Override
    public double computeDouble(double value1, double value2) {

      return value1 * value2;
    }

    @Override
    public BigInteger computeBigInteger(BigInteger value1, BigInteger value2) {

      return value1.multiply(value2);
    }

    @Override
    public BigDecimal computeBigDecimal(BigDecimal value1, BigDecimal value2) {

      return value1.multiply(value2);
    }
  };

  /**
   * {@link NumericOperation} for divide ({@code /}).
   */
  public static final NumericOperation DIVIDE = new NumericOperation() {

    @Override
    public byte computeByte(byte value1, byte value2) {

      return (byte) (value1 / value2);
    }

    @Override
    public short computeShort(short value1, short value2) {

      return (short) (value1 / value2);
    }

    @Override
    public int computeInteger(int value1, int value2) {

      return value1 / value2;
    }

    @Override
    public long computeLong(long value1, long value2) {

      return value1 / value2;
    }

    @Override
    public float computeFloat(float value1, float value2) {

      return value1 / value2;
    }

    @Override
    public double computeDouble(double value1, double value2) {

      return value1 / value2;
    }

    @Override
    public BigInteger computeBigInteger(BigInteger value1, BigInteger value2) {

      return value1.divide(value2);
    }

    @Override
    public BigDecimal computeBigDecimal(BigDecimal value1, BigDecimal value2) {

      return value1.divide(value2);
    }
  };
}
