/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorNonArgBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArgBuilder}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getter-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class PojoPropertyAccessorGetBuilder extends
    AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorNonArg> implements
    PojoPropertyAccessorNonArgBuilder {

  /** method name prefix for classic getter. */
  private static final String METHOD_PREFIX_GET = "get";

  /** alternative method name prefix for boolean getter. */
  private static final String METHOD_PREFIX_IS = "is";

  /** alternative method name prefix for boolean getter. */
  private static final String METHOD_PREFIX_HAS = "has";

  /** alternative method name prefixes for boolean getters. */
  private static final String[] METHOD_PREFIXES_BOOLEAN = new String[] { METHOD_PREFIX_IS,
      METHOD_PREFIX_HAS };

  /**
   * The constructor.
   */
  public PojoPropertyAccessorGetBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArg create(Method method, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration) {

    if (method.getParameterTypes().length == 0) {
      Class<?> propertyClass = method.getReturnType();
      String propertyName = null;
      if (propertyClass != Void.class) {
        String methodName = method.getName();
        // is property read method (getter)?
        propertyName = getPropertyName(methodName, METHOD_PREFIX_GET, "");
        if ((propertyName == null)
            && ((propertyClass == boolean.class) || (propertyClass == Boolean.class))) {
          // boolean getters may be is* or has* ...
          propertyName = getPropertyName(methodName, METHOD_PREFIXES_BOOLEAN,
              StringUtil.EMPTY_STRING_ARRAY);
        }
        if (propertyName != null) {
          return new PojoPropertyAccessorNonArgMethod(propertyName, method.getGenericReturnType(),
              PojoPropertyAccessorNonArgMode.GET, descriptor, configuration, method);
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

    return new PojoPropertyAccessorGetField(descriptor, configuration, field);
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET;
  }

}
