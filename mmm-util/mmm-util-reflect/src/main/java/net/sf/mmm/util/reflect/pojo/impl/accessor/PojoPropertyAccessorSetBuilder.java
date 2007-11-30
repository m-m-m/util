/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgBuilder;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArgBuilder}
 * interface for {@link PojoPropertyAccessorOneArgMode#SET setter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorSetBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorOneArg> implements
    PojoPropertyAccessorOneArgBuilder {

  /** method name prefix for classic getter */
  private static final String METHOD_PREFIX_SET = "set";

  /**
   * The constructor.
   */
  public PojoPropertyAccessorSetBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArg create(Method method) {

    String methodName = method.getName();
    if (methodName.startsWith(METHOD_PREFIX_SET)) {
      Class<?>[] argumentClasses = method.getParameterTypes();
      if (argumentClasses.length == 1) {
        Type[] argumentTypes = method.getGenericParameterTypes();
        assert (argumentTypes.length == 1);
        // found compliant setter
        String propertyName = getPropertyName(methodName, METHOD_PREFIX_SET.length(), 0);
        if (propertyName != null) {
          return new PojoPropertyAccessorOneArgMethod(propertyName, argumentTypes[0],
              argumentClasses[0], method, PojoPropertyAccessorOneArgMode.SET);
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArg create(Field field) {

    if (!Modifier.isFinal(field.getModifiers())) {
      return new PojoPropertyAccessorSetField(field);
    }
    // even though it is possible to set final fields via reflection since
    // java5, it is sick to do so and we therefore do NOT support this.
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.SET;
  }

}
