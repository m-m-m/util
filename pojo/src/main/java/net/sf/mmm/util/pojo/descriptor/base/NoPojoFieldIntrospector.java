/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.lang.reflect.Field;
import java.util.Iterator;

import net.sf.mmm.util.collection.base.EmptyIterator;

/**
 * This is a dummy implementation of the {@link PojoFieldIntrospector} interface that never {@link #findFields(Class)
 * finds} any field.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class NoPojoFieldIntrospector implements PojoFieldIntrospector {

  /**
   * The constructor.
   */
  public NoPojoFieldIntrospector() {

    super();
  }

  @Override
  public Iterator<Field> findFields(Class<?> pojoType) {

    return EmptyIterator.getInstance();
  }

}
