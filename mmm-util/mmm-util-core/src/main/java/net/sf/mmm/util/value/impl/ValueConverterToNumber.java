/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import net.sf.mmm.util.math.MathUtil;
import net.sf.mmm.util.math.api.NumberType;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link Number}. It supports objects given as
 * {@link CharSequence} (e.g. {@link String}) or {@link Number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToNumber extends AbstractSimpleValueConverter<Object, Number> {

  /** @see #getMathUtil() */
  private final MathUtil mathUtil;

  /**
   * The constructor.
   */
  public ValueConverterToNumber() {

    super();
    this.mathUtil = MathUtil.getInstance();
  }

  /**
   * The constructor.
   * 
   * @param mathUtil is the {@link MathUtil} to use.
   */
  public ValueConverterToNumber(MathUtil mathUtil) {

    super();
    this.mathUtil = mathUtil;
  }

  /**
   * This method gets the {@link MathUtil} to use.
   * 
   * @return the {@link MathUtil}.
   */
  protected MathUtil getMathUtil() {

    return this.mathUtil;
  }

  /**
   * This method determines if the conversion from one
   * {@link Number number-type} to another should
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
      }
    }
    return null;
  }

}
