/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorConfiguration;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor}
 * that acts as proxy to a {@link #getDelegate() delegate} allowing to add new
 * ways to access a property.<br>
 * It extends {@link AbstractPojoPropertyAccessorProxyAdapter} implementing
 * {@link #getPropertyType()} to return the
 * {@link net.sf.mmm.util.reflect.ReflectionUtil#getComponentType(Type, boolean) component-type}
 * for the
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor#getReturnType() return-type}
 * of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractPojoPropertyAccessorProxyAdapterComponentType extends
    AbstractPojoPropertyAccessorProxyAdapter {

  /** @see #getPropertyType() */
  private final Type propertyType;

  /** @see #getPropertyClass() */
  private final Class<?> propertyClass;

  /**
   * The constructor.
   * 
   * @param configuration is the configuration to use.
   * @param containerGetAccessor is the accessor delegate that gets an array or
   *        {@link java.util.Collection} property.
   */
  public AbstractPojoPropertyAccessorProxyAdapterComponentType(
      PojoDescriptorConfiguration configuration, PojoPropertyAccessorNonArg containerGetAccessor) {

    super(configuration, containerGetAccessor);
    ReflectionUtil reflectionUtil = getConfiguration().getReflectionUtil();
    this.propertyType = reflectionUtil.getComponentType(containerGetAccessor.getReturnType(), true);
    if (this.propertyType == null) {
      this.propertyClass = null;
    } else {
      this.propertyClass = reflectionUtil.toClass(this.propertyType);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getPropertyType() {

    return this.propertyType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getPropertyClass() {

    return this.propertyClass;
  }

}
