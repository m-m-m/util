/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.mmm.util.reflect.NumberType;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgBuilder;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArgBuilder}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorSizeBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorNonArg> implements
    PojoPropertyAccessorNonArgBuilder {

  /** the method name prefixes for getter. */
  private static final String[] METHOD_PREFIXES = new String[] { "get" };

  /** the method name suffixes for size getter. */
  private static final String[] METHOD_SUFFIXES = new String[] { "Size", "Count", "Length" };

  /**
   * The constructor.
   */
  public PojoPropertyAccessorSizeBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArg create(Method method) {

    if (method.getParameterTypes().length == 0) {
      Class<?> propertyClass = method.getReturnType();
      NumberType numberType = ReflectionUtil.getInstance().getNumberType(propertyClass);
      if ((numberType != null) && (!numberType.isDecimal())) {
        if (NumberType.INTEGER.getExactnessDifference(numberType) >= 0) {
          String methodName = method.getName();
          // is property read method (getter)?
          String propertyName = getPropertyName(methodName, METHOD_PREFIXES, METHOD_SUFFIXES);
          if (propertyName != null) {
            return new PojoPropertyAccessorNonArgMethod(propertyName,
                method.getGenericReturnType(), propertyClass, method,
                PojoPropertyAccessorNonArgMode.SIZE);
          }
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArg create(Field field) {

    // return new PojoPropertyAccessorGetField(field);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.SIZE;
  }

}
