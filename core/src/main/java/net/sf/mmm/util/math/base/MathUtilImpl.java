/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.api.NumberType;

/**
 * This class is a collection of utility functions for dealing with numbers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(MathUtil.CDI_NAME)
public class MathUtilImpl extends MathUtilLimitedImpl implements MathUtil {

  /** The {@link NumberTypeImpl} for {@link AtomicLong}. */
  public static final NumberTypeImpl<AtomicLong> ATOMIC_LONG = new NumberTypeImpl<AtomicLong>(4, null, null) {

    @Override
    public Class<AtomicLong> getNumberClass() {

      return AtomicLong.class;
    }

    @Override
    public boolean isDecimal() {

      return false;
    }

    @Override    protected AtomicLong convert(Number number) {

      return new AtomicLong(number.longValue());
    }

    @Override    protected AtomicLong parse(String number) throws NumberFormatException {

      return new AtomicLong(Long.parseLong(number));
    }

    @Override
    public AtomicLong getMinimumValue() {

      return new AtomicLong(Long.MIN_VALUE);
    }

    @Override
    public AtomicLong getMaximumValue() {

      return new AtomicLong(Long.MAX_VALUE);
    }

  };

  /** The {@link NumberTypeImpl} for {@link AtomicInteger}. */
  public static final NumberTypeImpl<AtomicInteger> ATOMIC_INTEGER = new NumberTypeImpl<AtomicInteger>(3, null, null) {

    @Override
    public Class<AtomicInteger> getNumberClass() {

      return AtomicInteger.class;
    }

    @Override
    public boolean isDecimal() {

      return false;
    }

    @Override    protected AtomicInteger convert(Number number) {

      return new AtomicInteger(number.intValue());
    }

    @Override    protected AtomicInteger parse(String number) throws NumberFormatException {

      return new AtomicInteger(Integer.parseInt(number));
    }

    @Override
    public AtomicInteger getMinimumValue() {

      return new AtomicInteger(Integer.MIN_VALUE);
    }

    @Override
    public AtomicInteger getMaximumValue() {

      return new AtomicInteger(Integer.MAX_VALUE);
    }

  };

  private  static MathUtil instance;

  /**
   * The constructor.
   */
  public MathUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of {@link MathUtil}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   * 
   * @return the singleton instance.
   */
  public static MathUtil getInstance() {

    if (instance == null) {
      synchronized (MathUtilImpl.class) {
        if (instance == null) {
          MathUtilImpl impl = new MathUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  @Override
  public NumberType<? extends Number> getNumberType(Class<?> numericType) {

    if ((AtomicInteger.class.isAssignableFrom(numericType))) {
      return ATOMIC_INTEGER;
    } else if ((AtomicLong.class.isAssignableFrom(numericType))) {
      return ATOMIC_LONG;
    } else {
      return super.getNumberType(numericType);
    }
  }

}
