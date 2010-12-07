/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorIndexedNonArgBuilder;

/**
 * This is the implementation of the
 * {@link PojoPropertyAccessorIndexedNonArgBuilder} interface for
 * {@link PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed
 * getter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class PojoPropertyAccessorGetIndexedBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorIndexedNonArg> implements
    PojoPropertyAccessorIndexedNonArgBuilder {

  /** method name prefix for classic getter. */
  private static final String METHOD_PREFIX_GET = "get";

  /** alternative method name prefixes for boolean getters. */
  private static final String[] METHOD_PREFIXES = new String[] { METHOD_PREFIX_GET, "is", "has" };

  /** method name suffixes for indexed getters. */
  private static final String[] METHOD_SUFFIXES = new String[] { "", "At" };

  /**
   * The constructor.
   */
  public PojoPropertyAccessorGetIndexedBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedNonArg create(Method method, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies) {

    Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes.length == 1) {
      Class<?> argumentClass = parameterTypes[0];
      if ((argumentClass == int.class) || (argumentClass == Integer.class)) {
        Class<?> propertyClass = method.getReturnType();
        if (propertyClass != Void.class) {
          String methodName = method.getName();
          // is property read method (getter)?
          String propertyName = getPropertyName(methodName, METHOD_PREFIXES, METHOD_SUFFIXES);
          boolean isBooblean = isBooleanType(propertyClass);
          if (!isBooblean && !methodName.startsWith(METHOD_PREFIX_GET)) {
            // only boolean getters may use is* or has* ...
            propertyName = null;
          }
          if (propertyName != null) {
            return new PojoPropertyAccessorIndexedNonArgMethod(propertyName,
                method.getGenericReturnType(), PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED,
                descriptor, dependencies, method);
          }
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedNonArg create(Field field, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedNonArgMode getMode() {

    return PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED;
  }

}
