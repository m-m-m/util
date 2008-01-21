/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.base;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * This is the interface used to find {@link Method methods} that can
 * (potentially) {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptor access}
 * a {@link net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor property} of
 * a given {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptor POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoMethodIntrospector {

  /**
   * This method finds all {@link Method}s of the given <code>pojoType</code>.
   * 
   * @param pojoType is the type reflecting the POJO for which the
   *        {@link Method}s are requested.
   * @return a read-only iterator of all the {@link Method}s.
   */
  Iterator<Method> findMethods(Class<?> pojoType);

}
