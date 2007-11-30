/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

/**
 * This is the interface used to create a
 * {@link PojoPropertyAccessorIndexedOneArg indexed one-arg accessor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyAccessorIndexedOneArgBuilder extends
    PojoPropertyAccessorBuilder<PojoPropertyAccessorIndexedOneArg> {

  /**
   * {@inheritDoc}
   */
  PojoPropertyAccessorIndexedOneArgMode getMode();

}
