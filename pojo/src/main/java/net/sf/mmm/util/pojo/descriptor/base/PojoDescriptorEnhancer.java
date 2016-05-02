/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a generic enhancer of a
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor}.
 * 
 * @see #enhanceDescriptor(AbstractPojoDescriptor)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@ComponentSpecification
public interface PojoDescriptorEnhancer {

  /**
   * This method performs the generic enhancements on the given <code>descriptor</code>.
   * 
   * @param descriptor is the descriptor to enhance.
   */
  void enhanceDescriptor(AbstractPojoDescriptor<?> descriptor);

}
