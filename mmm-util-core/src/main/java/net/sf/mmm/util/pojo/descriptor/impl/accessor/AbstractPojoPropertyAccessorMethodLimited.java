/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessor;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor} for accessing a
 * {@link java.lang.reflect.Method} (getter or setter) in a limited environment (GWT).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class AbstractPojoPropertyAccessorMethodLimited extends AbstractPojoPropertyAccessor {

  /** @see #getName() */
  private final String name;

  /** @see #getPropertyType() */
  private final GenericType<?> propertyType;

  /** @see #getDeclaringClass() */
  private final Class<?> declaringClass;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() property type}.
   * @param declaringClass is the {@link #getDeclaringClass() declaring class}.
   */
  public AbstractPojoPropertyAccessorMethodLimited(String propertyName, GenericType<?> propertyType,
      Class<?> declaringClass) {

    super();
    this.name = propertyName;
    this.propertyType = propertyType;
    this.declaringClass = declaringClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAccessibleObjectName() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getModifiers() {

    // might be wrong but we do not care here...
    return 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getPropertyType() {

    return this.propertyType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getDeclaringClass() {

    return this.declaringClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    return this.name;
  }

}
