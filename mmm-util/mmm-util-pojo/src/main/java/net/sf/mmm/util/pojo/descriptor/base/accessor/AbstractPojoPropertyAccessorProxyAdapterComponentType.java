/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.reflect.GenericType;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor}
 * that acts as proxy to a {@link #getDelegate() delegate} allowing to add new
 * ways to access a property.<br>
 * It extends {@link AbstractPojoPropertyAccessorProxyAdapter} implementing
 * {@link #getPropertyType()} to return the
 * {@link GenericType#getComponentType() component-type} from the
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor#getReturnType() return-type}
 * of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessorProxyAdapterComponentType extends
    AbstractPojoPropertyAccessorProxyAdapter {

  /** @see #getPropertyType() */
  private final GenericType propertyType;

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
    this.propertyType = containerGetAccessor.getReturnType().getComponentType();
    assert (this.propertyType != null) : "propertyType is null";
    if (getMode().isReading()) {
      this.propertyClass = this.propertyType.getUpperBound();
    } else {
      this.propertyClass = this.propertyType.getLowerBound();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType getPropertyType() {

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
