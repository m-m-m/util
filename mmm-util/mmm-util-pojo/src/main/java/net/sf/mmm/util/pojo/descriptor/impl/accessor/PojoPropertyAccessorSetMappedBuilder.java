/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgBuilder;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorTwoArgBuilder}
 * interface for
 * {@link PojoPropertyAccessorTwoArgMode#SET_MAPPED mapped setter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorSetMappedBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorTwoArg> implements
    PojoPropertyAccessorTwoArgBuilder {

  /** method name prefix for classic setter */
  private static final String METHOD_PREFIX_SET = "set";

  /** method name prefix for classic getter */
  private static final String METHOD_PREFIX_PUT = "put";

  /** alternative method name prefixes for boolean getters. */
  private static final String[] METHOD_PREFIXES = new String[] { METHOD_PREFIX_SET,
      METHOD_PREFIX_PUT };

  /**
   * The constructor.
   */
  public PojoPropertyAccessorSetMappedBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorTwoArg create(Method method) {

    String methodName = method.getName();
    String propertyName = getPropertyName(methodName, METHOD_PREFIXES,
        StringUtil.EMPTY_STRING_ARRAY);
    if (propertyName != null) {
      Class<?>[] argumentClasses = method.getParameterTypes();
      if (argumentClasses.length == 2) {
        Type[] argumentTypes = method.getGenericParameterTypes();
        assert (argumentTypes.length == 2);
        // found compliant setter
        return new PojoPropertyAccessorTwoArgMethod(propertyName, argumentTypes[1],
            argumentClasses[1], method, PojoPropertyAccessorTwoArgMode.SET_MAPPED);
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorTwoArg create(Field field) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorTwoArgMode getMode() {

    return PojoPropertyAccessorTwoArgMode.SET_MAPPED;
  }

}
