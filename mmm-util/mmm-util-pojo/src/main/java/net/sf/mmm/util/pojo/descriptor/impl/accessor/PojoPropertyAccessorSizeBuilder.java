/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.mmm.util.math.MathUtil;
import net.sf.mmm.util.math.api.NumberType;
import net.sf.mmm.util.math.base.NumberTypeImpl;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgBuilder;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;

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
  public PojoPropertyAccessorNonArg create(Method method, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration) {

    if (method.getParameterTypes().length == 0) {
      Class<?> propertyClass = method.getReturnType();
      NumberType<? extends Number> numberType = MathUtil.getInstance().getNumberType(propertyClass);
      if ((numberType != null) && (!numberType.isDecimal())) {
        if (NumberTypeImpl.INTEGER.getExactnessDifference(numberType) >= 0) {
          String methodName = method.getName();
          // is property read method (getter)?
          String propertyName = getPropertyName(methodName, METHOD_PREFIXES, METHOD_SUFFIXES);
          if (propertyName != null) {
            return new PojoPropertyAccessorNonArgMethod(propertyName,
                method.getGenericReturnType(), PojoPropertyAccessorNonArgMode.GET_SIZE, descriptor,
                configuration, method);
          }
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArg create(Field field, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration) {

    // return new PojoPropertyAccessorGetField(field);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET_SIZE;
  }

}
