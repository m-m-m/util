/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorOneArgBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArgBuilder} interface for
 * {@link PojoPropertyAccessorOneArgMode#GET_MAPPED mapped getter-access}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class PojoPropertyAccessorGetMappedBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorOneArg> implements PojoPropertyAccessorOneArgBuilder {

  /** method name prefix for classic getter. */
  private static final String METHOD_PREFIX_GET = "get";

  /** alternative method name prefix for boolean getter. */
  private static final String METHOD_PREFIX_IS = "is";

  /** alternative method name prefix for boolean getter. */
  private static final String METHOD_PREFIX_HAS = "has";

  /** alternative method name prefixes for boolean getters. */
  private static final String[] METHOD_PREFIXES_BOOLEAN = new String[] { METHOD_PREFIX_IS, METHOD_PREFIX_HAS };

  /**
   * The constructor.
   */
  public PojoPropertyAccessorGetMappedBuilder() {

    super();
  }

  @Override
  public PojoPropertyAccessorOneArg create(Method method, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies) {

    Class<?>[] parameterTypes = method.getParameterTypes();
    if (parameterTypes.length == 1) {
      Class<?> propertyClass = method.getReturnType();
      String propertyName = null;
      if (propertyClass != Void.class) {
        String methodName = method.getName();
        // is property read method (getter)?
        propertyName = getPropertyName(methodName, METHOD_PREFIX_GET, "");
        if ((propertyName == null) && ((propertyClass == boolean.class) || (propertyClass == Boolean.class))) {
          // boolean getters may be is* or has* ...
          propertyName = getPropertyName(methodName, METHOD_PREFIXES_BOOLEAN, StringUtil.EMPTY_STRING_ARRAY);
        }
        if (propertyName != null) {
          return new PojoPropertyAccessorOneArgMethod(propertyName, method.getGenericReturnType(),
              PojoPropertyAccessorOneArgMode.GET_MAPPED, descriptor, dependencies, method);
        }
      }
    }
    return null;
  }

  @Override
  public PojoPropertyAccessorOneArg create(Field field, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies) {

    return null;
  }

  @Override
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.GET_MAPPED;
  }

}
