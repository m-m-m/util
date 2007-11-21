/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgBuilder;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.base.AbstractPojoPropertyAccessorBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArgBuilder}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorGetBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorNonArg> implements
    PojoPropertyAccessorNonArgBuilder {

  /** method name prefix for classic getter. */
  private static final String METHOD_PREFIX_GET = "get";

  /** alternative method name prefix for boolean getter. */
  private static final String METHOD_PREFIX_IS = "is";

  /** alternative method name prefix for boolean getter. */
  private static final String METHOD_PREFIX_HAS = "has";

  /**
   * The constructor.
   */
  public PojoPropertyAccessorGetBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArg create(Method method) {

    if (method.getParameterTypes().length == 0) {
      Class<?> propertyClass = method.getReturnType();
      String propertyName = null;
      if (propertyClass != Void.class) {
        String methodName = method.getName();
        // is property read method (getter)?
        if (methodName.startsWith(METHOD_PREFIX_GET)) {
          propertyName = getPropertyName(methodName, METHOD_PREFIX_GET.length());
        } else if ((propertyClass == boolean.class) || (propertyClass == Boolean.class)) {
          // boolean getters may be is* or has* ...
          if (methodName.startsWith(METHOD_PREFIX_IS)) {
            propertyName = getPropertyName(methodName, METHOD_PREFIX_IS.length());
          } else if (methodName.startsWith(METHOD_PREFIX_HAS)) {
            propertyName = getPropertyName(methodName, METHOD_PREFIX_HAS.length());
          }
        }
        if (propertyName != null) {
          return new PojoPropertyAccessorNonArgMethod(propertyName, method.getGenericReturnType(),
              propertyClass, method, PojoPropertyAccessorNonArgMode.GET);
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArg create(Field field) {

    return new PojoPropertyAccessorGetField(field);
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET;
  }

}
