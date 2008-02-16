/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base;

import net.sf.mmm.util.reflect.pojo.api.PojoDescriptor;

/**
 * This is the interface for a generic enhancer of a {@link PojoDescriptor}.
 * 
 * @see #enhanceDescriptor(AbstractPojoDescriptor)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoDescriptorEnhancer {

  /**
   * This method performs the generic enhancements on the given
   * <code>descriptor</code>.
   * 
   * @param descriptor is the descriptor to enhance.
   */
  void enhanceDescriptor(AbstractPojoDescriptor<?> descriptor);

}
