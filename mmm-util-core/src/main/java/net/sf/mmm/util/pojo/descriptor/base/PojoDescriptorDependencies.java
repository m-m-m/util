/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This interface bundles the dependencies for the POJO-introspection. It contains the required components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0 (renamed, 1.1.0)
 */
public interface PojoDescriptorDependencies {

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
