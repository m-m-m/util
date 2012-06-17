/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;

/**
 * This is the interface used to create a {@link PojoPropertyAccessorIndexedOneArg indexed one-arg accessor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoPropertyAccessorIndexedOneArgBuilder extends
    PojoPropertyAccessorBuilder<PojoPropertyAccessorIndexedOneArg> {

  /**
   * {@inheritDoc}
   */
  PojoPropertyAccessorIndexedOneArgMode getMode();

}
