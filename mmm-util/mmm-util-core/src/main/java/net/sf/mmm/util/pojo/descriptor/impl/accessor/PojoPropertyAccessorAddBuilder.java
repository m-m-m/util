/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorOneArgBuilder;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArgBuilder} interface for
 * {@link PojoPropertyAccessorOneArgMode#ADD add-access}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class PojoPropertyAccessorAddBuilder extends AbstractPojoPropertyAccessorBuilder<PojoPropertyAccessorOneArg>
    implements PojoPropertyAccessorOneArgBuilder {

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
  public PojoPropertyAccessorOneArg create(Method method, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies) {

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
              PojoPropertyAccessorOneArgMode.ADD, descriptor, dependencies, method);
        }
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArg create(Field field, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies) {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.ADD;
  }

}
