/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgBuilder;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgBuilder;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArgBuilder}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorAddBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorOneArg> implements
    PojoPropertyAccessorOneArgBuilder {

  /** method name prefix for an add method. */
  private static final String METHOD_PREFIX_ADD = "add";

  /**
   * The constructor.
   */
  public PojoPropertyAccessorAddBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArg create(Method method) {

    String methodName = method.getName();
    if (methodName.startsWith(METHOD_PREFIX_ADD)) {
      Class<?>[] argumentClasses = method.getParameterTypes();
      if (argumentClasses.length == 1) {
        Type[] argumentTypes = method.getGenericParameterTypes();
        assert (argumentTypes.length == 1);
        // found compliant add method
        String propertyName = getPropertyName(methodName, METHOD_PREFIX_ADD.length(), 0);
        if (propertyName != null) {
          return new PojoPropertyAccessorOneArgMethod(propertyName, argumentTypes[0],
              argumentClasses[0], method, PojoPropertyAccessorOneArgMode.ADD);
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArg create(Field field) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.ADD;
  }

}
