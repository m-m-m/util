/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.AccessibleObject;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base implementation of {@link PojoPropertyAccessorNonArg} for accessing a getter
 * {@link java.lang.reflect.Method} in a limited environment (GWT).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractPojoPropertyAccessorGetMethod extends AbstractPojoPropertyAccessorMethodLimited implements
    PojoPropertyAccessorNonArg {

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() property type}.
   * @param declaringClass is the {@link #getDeclaringClass() declaring class}.
   */
  public AbstractPojoPropertyAccessorGetMethod(String propertyName, GenericType<?> propertyType, Class<?> declaringClass) {

    super(propertyName, propertyType, declaringClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccessibleObject getAccessibleObject() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getReturnType() {

    return getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return getPropertyClass();
  }

}
