/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.util.Collection;

import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorBuilder;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface ExtendedPojoDescriptorDependencies extends PojoDescriptorDependencies {

  /**
   * This method gets the accessor-builders used to create the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor accessors} for
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties} of a
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
   *
   * @return the accessorBuilders.
   */
  Collection<PojoPropertyAccessorBuilder<?>> getAccessorBuilders();

  /**
   * @return the {@link PojoDescriptorEnhancer} instance to use.
   */
  PojoDescriptorEnhancer getDescriptorEnhancer();

}
