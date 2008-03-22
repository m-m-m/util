/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.impl.converter;

import java.lang.reflect.Type;

import net.sf.mmm.util.math.MathUtil;
import net.sf.mmm.util.math.NumberType;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPath;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathConverter;

/**
 * This is an implementation of the {@link PojoPathConverter} interface that
 * converts an {@link Object} to a {@link Number}. It supports objects given as
 * {@link CharSequence} (String) or {@link Number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathConverterToNumber implements PojoPathConverter<Object, Number> {

  /**
   * The constructor.
   */
  public PojoPathConverterToNumber() {

    super();
  }

  /**
   * This method gets the {@link MathUtil} to use.
   * 
   * @return the {@link MathUtil}.
   */
  protected MathUtil getMathUtil() {

    return MathUtil.getInstance();
  }

  /**
   * This method determines if the conversion from one
   * {@link Number number-type} to another should
   * {@link net.sf.mmm.util.math.NumberConversionException fail} if it is
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
  public Number convert(Object pojo, Class<? extends Number> targetClass, Type targetType,
      PojoPath path) {

    if (pojo == null) {
      throw new NlsIllegalArgumentException(null);
    }
    NumberType<? extends Number> numberType = getMathUtil().getNumberType(targetClass);
    if (numberType != null) {
      if (pojo instanceof Number) {
        return numberType.valueOf((Number) pojo, isFailIfUnprecise());
      } else if (pojo instanceof CharSequence) {
        return numberType.valueOf(pojo.toString());
      }
    }
    return null;
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

}
