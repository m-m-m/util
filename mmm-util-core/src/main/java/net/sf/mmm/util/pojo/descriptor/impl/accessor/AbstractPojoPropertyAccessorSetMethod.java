/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.AccessibleObject;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.SimpleGenericTypeLimited;

/**
 * This is the abstract base implementation of the {@link PojoPropertyAccessorOneArg} interface for accessing
 * a setter {@link java.lang.reflect.Method} in a limited environment (GWT).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractPojoPropertyAccessorSetMethod extends AbstractPojoPropertyAccessorMethodLimited implements
    PojoPropertyAccessorOneArg {

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() property type}.
   * @param declaringClass is the {@link #getDeclaringClass() declaring class}.
   */
  public AbstractPojoPropertyAccessorSetMethod(String propertyName, GenericType<?> propertyType, Class<?> declaringClass) {

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
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.SET;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getReturnType() {

    return SimpleGenericTypeLimited.TYPE_VOID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return Void.class;
  }

}
