/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor} that acts as proxy to a
 * {@link #getDelegate() delegate} allowing to add new ways to access a property. <br>
 * It extends {@link AbstractPojoPropertyAccessorProxyAdapter} implementing {@link #getPropertyType()} to
 * return the {@link GenericType#getComponentType() component-type} from the
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor#getReturnType() return-type} of
 * the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPropertyAccessorProxyAdapterComponentType extends
    AbstractPojoPropertyAccessorProxyAdapter {

  /** @see #getPropertyType() */
  private final GenericType<?> propertyType;

  /**
   * The constructor.
   * 
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param containerGetAccessor is the accessor delegate that gets an array or {@link java.util.Collection}
   *        property.
   */
  public AbstractPojoPropertyAccessorProxyAdapterComponentType(PojoDescriptorDependencies dependencies,
      PojoPropertyAccessorNonArg containerGetAccessor) {

    super(dependencies, containerGetAccessor);
    this.propertyType = containerGetAccessor.getReturnType().getComponentType();
    assert (this.propertyType != null) : "propertyType is null";
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
  public Class<?> getPropertyClass() {

    if (getMode().isReading()) {
      return this.propertyType.getRetrievalClass();
    } else {
      return this.propertyType.getAssignmentClass();
    }
  }

}
