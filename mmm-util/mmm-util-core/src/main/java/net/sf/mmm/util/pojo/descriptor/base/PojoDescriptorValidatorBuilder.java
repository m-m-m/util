/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a component that of a {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor}.
 * 
 * @see #buildValidator(AbstractPojoDescriptor)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@ComponentSpecification
public interface PojoDescriptorValidatorBuilder {

  /**
   * This method creates and assigns the
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getValidator() validators} for the
   * given <code>descriptor</code>.
   * 
   * @param descriptor is the descriptor to complete.
   */
  void buildValidator(AbstractPojoDescriptor<?> descriptor);

}
