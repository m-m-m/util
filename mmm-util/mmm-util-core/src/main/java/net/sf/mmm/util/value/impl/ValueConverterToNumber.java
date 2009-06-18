/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.api.NumberType;
import net.sf.mmm.util.math.base.MathUtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link Number}. It supports objects given as
 * {@link CharSequence} (e.g. {@link String}) or {@link Number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ValueConverterToNumber extends AbstractSimpleValueConverter<Object, Number> {

  /** @see #getMathUtil() */
  private MathUtil mathUtil;

  /**
   * The constructor.
   */
  public ValueConverterToNumber() {

    super();
  }

  /**
   * This method gets the {@link MathUtil} to use.
   * 
   * @return the {@link MathUtil} instance.
   */
  protected MathUtil getMathUtil() {

    return this.mathUtil;
  }

  /**
   * This method set the {@link MathUtil} to use.
   * 
   * @param mathUtil is the {@link MathUtil} instance.
   */
  @Resource
  public void setMathUtil(MathUtil mathUtil) {

    getInitializationState().requireNotInitilized();
    this.mathUtil = mathUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.mathUtil == null) {
      this.mathUtil = MathUtilImpl.getInstance();
    }
  }

  /**
   * This method determines if the conversion from one {@link Number
   * number-type} to another should
   * {@link net.sf.mmm.util.math.api.NumberConversionException fail} if it is
   * unprecise.
   * 
   * @see NumberType#valueOf(Number, boolean)
   * 
   * @return the fail-if-unprecise flag.
   */
  protected boolean isFailIfUnprecise() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Number> getTargetType() {

    return Number.class;
  }

  /**
   * {@inheritDoc}
   */
  public Number convert(Object value, Object valueSource, Class<? extends Number> targetClass) {

    if (value == null) {
      return null;
    }
    NumberType<? extends Number> numberType = getMathUtil().getNumberType(targetClass);
    if (numberType != null) {
      if (value instanceof Number) {
        return numberType.valueOf((Number) value, isFailIfUnprecise());
      } else if (value instanceof CharSequence) {
        return numberType.valueOf(value.toString());
      } else if (value instanceof Date) {
        if (targetClass == Long.class) {
          Date date = (Date) value;
          return Long.valueOf(date.getTime());
        }
      } else if (value instanceof Calendar) {
        if (targetClass == Long.class) {
          Calendar calendar = (Calendar) value;
          return Long.valueOf(calendar.getTimeInMillis());
        }
      }
    }
    return null;
  }

}
