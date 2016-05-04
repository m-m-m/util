/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.lang.reflect.Method;
import java.util.Iterator;

import net.sf.mmm.util.collection.base.EmptyIterator;

/**
 * This is a dummy implementation of the {@link PojoMethodIntrospector} interface that never {@link #findMethods(Class)
 * finds} any method.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class NoPojoMethodIntrospector implements PojoMethodIntrospector {

  /**
   * The constructor.
   */
  public NoPojoMethodIntrospector() {

    super();
  }

  @Override
  public Iterator<Method> findMethods(Class<?> pojoType) {

    return EmptyIterator.getInstance();
  }

}
