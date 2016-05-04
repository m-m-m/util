/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.util.Date;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.date.api.Iso8601UtilLimited;
import net.sf.mmm.util.date.base.Iso8601UtilLimitedImpl;
import net.sf.mmm.util.math.api.MathUtilLimited;
import net.sf.mmm.util.math.api.NumberType;
import net.sf.mmm.util.math.base.MathUtilLimitedImpl;
import net.sf.mmm.util.value.api.SimpleGenericValueConverter;
import net.sf.mmm.util.value.api.ValueConvertException;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This is a generic value converter that is GWT compatible and therefore has reduced flexibility. If you have no GWT
 * limitations you should use {@link net.sf.mmm.util.value.api.ComposedValueConverter} instead.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class SimpleGenericValueConverterImpl extends AbstractLoggableComponent
    implements SimpleGenericValueConverter {

  private Iso8601UtilLimited iso8601Util;

  private MathUtilLimited mathUtil;

  /**
   * The constructor.
   */
  public SimpleGenericValueConverterImpl() {

    super();
  }

  /**
   * @return the iso8601Util
   */
  protected Iso8601UtilLimited getIso8601Util() {

    return this.iso8601Util;
  }

  /**
   * @param iso8601Util is the iso8601Util to set
   */
  @Inject
  public void setIso8601Util(Iso8601UtilLimited iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * @return the mathUtil
   */
  protected MathUtilLimited getMathUtil() {

    return this.mathUtil;
  }

  /**
   * @param mathUtil is the mathUtil to set
   */
  @Inject
  public void setMathUtil(MathUtilLimited mathUtil) {

    getInitializationState().requireNotInitilized();
    this.mathUtil = mathUtil;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.iso8601Util == null) {
      this.iso8601Util = new Iso8601UtilLimitedImpl();
    }
    if (this.mathUtil == null) {
      this.mathUtil = new MathUtilLimitedImpl();
    }
  }

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <T> T convert(Object value, Object valueSource, Class<T> targetClass) throws ValueException {

    if (value == null) {
      return null;
    }
    Object result;
    if (value.getClass().equals(targetClass)) {
      result = value;
    } else if (String.class.equals(targetClass)) {
      result = value.toString();
    } else if (Boolean.class.equals(targetClass)) {
      result = Boolean.valueOf(value.toString());
    } else if (targetClass.isEnum()) {
      result = Enum.valueOf((Class) targetClass, value.toString());
    } else if (Number.class.equals(targetClass)) {
      NumberType<? extends Number> numberType = this.mathUtil.getNumberType(targetClass);
      if (value instanceof String) {
        result = numberType.valueOf((String) value);
      } else if (value instanceof Number) {
        result = numberType.valueOf((Number) value, false);
      } else {
        throw new ValueConvertException(value, targetClass, valueSource);
      }
    } else if (Date.class.equals(targetClass)) {
      if (value instanceof String) {
        result = this.iso8601Util.parseDate((String) value);
      } else if (value instanceof Long) {
        result = new Date(((Long) value).longValue());
      } else {
        throw new ValueConvertException(value, targetClass, valueSource);
      }
    } else {
      throw new ValueConvertException(value, targetClass, valueSource);
    }
    return (T) result;
  }
}
