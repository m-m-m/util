/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.util.Iterator;

import net.sf.mmm.util.reflect.pojo.base.AbstractPojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.base.PojoPropertyIntrospector;

/**
 * This is an implementation of the {@link PojoPropertyIntrospector} that
 * introspects the fields to find property-accessors.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldPropertyIntrospector implements PojoPropertyIntrospector {

  /**
   * The constructor.
   */
  public FieldPropertyIntrospector() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<AbstractPojoPropertyAccessor> findAccessors(Class<?> pojoType) {

    return new FieldAccessorIterator(pojoType);
  }

}
