/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorIndexedOneArgBuilder;

/**
 * This is the implementation of the
 * {@link PojoPropertyAccessorIndexedOneArgBuilder} interface for
 * {@link PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED indexed
 * setter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class PojoPropertyAccessorSetIndexedBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorIndexedOneArg> implements
    PojoPropertyAccessorIndexedOneArgBuilder {

  /** alternative method name prefixes for boolean getters. */
  private static final String[] METHOD_PREFIXES = new String[] { "set" };

  /** method name suffixes for indexed getters. */
  private static final String[] METHOD_SUFFIXES = new String[] { "", "At" };

  /**
   * The constructor.
   */
  public PojoPropertyAccessorSetIndexedBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedOneArg create(Method method, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration) {

    String methodName = method.getName();
    // is property read method (getter)?
    String propertyName = getPropertyName(methodName, METHOD_PREFIXES, METHOD_SUFFIXES);
    if (propertyName != null) {
      Class<?>[] parameterTypes = method.getParameterTypes();
      if (parameterTypes.length == 2) {
        Class<?> argument1Class = parameterTypes[0];
        Class<?> argument2Class = parameterTypes[1];
        if (isIntegerType(argument1Class)) {
          return new PojoPropertyAccessorIndexedOneArgMethod(propertyName, argument2Class,
              PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED, descriptor, configuration, method,
              false);
        } else if (isIntegerType(argument2Class)) {
          return new PojoPropertyAccessorIndexedOneArgMethod(propertyName, argument1Class,
              PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED, descriptor, configuration, method,
              true);
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedOneArg create(Field field, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedOneArgMode getMode() {

    return PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED;
  }

}
