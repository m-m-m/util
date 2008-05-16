/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.reflect.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This interface bundles the configuration for the POJO-introspection.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoDescriptorConfiguration {

  /**
   * This method gets the {@link ReflectionUtil} instance to use.
   * 
   * @return the {@link ReflectionUtil} to use.
   */
  ReflectionUtil getReflectionUtil();

  /**
   * This method gets the {@link CollectionReflectionUtil} instance to use.
   * 
   * @return the {@link CollectionReflectionUtil} to use.
   */
  CollectionReflectionUtil getCollectionReflectionUtil();

}
