/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorNonArgBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArgBuilder} interface for
 * {@link PojoPropertyAccessorNonArgMode#GET getter-access}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class PojoPropertyAccessorSizeBuilder extends AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorNonArg>
    implements PojoPropertyAccessorNonArgBuilder {

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

  @Override
  public PojoPropertyAccessorNonArg create(Method method, PojoDescriptor<?> descriptor, PojoDescriptorDependencies dependencies) {

    if (method.getParameterTypes().length == 0) {
      Class<?> propertyClass = method.getReturnType();
      if ((propertyClass == Integer.class) || (propertyClass == int.class)) {
        String methodName = method.getName();
        // is property read method (getter)?
        String propertyName = getPropertyName(methodName, METHOD_PREFIXES, METHOD_SUFFIXES);
        if (propertyName != null) {
          return new PojoPropertyAccessorNonArgMethod(propertyName, method.getGenericReturnType(), PojoPropertyAccessorNonArgMode.GET_SIZE, descriptor,
              dependencies, method);
        }
      }
    }
    return null;
  }

  @Override
  public PojoPropertyAccessorNonArg create(Field field, PojoDescriptor<?> descriptor, PojoDescriptorDependencies dependencies) {

    // return new PojoPropertyAccessorGetField(field);
    return null;
  }

  @Override
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET_SIZE;
  }

}
