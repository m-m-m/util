/* $Id$
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.util.Iterator;

import net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor;
import net.sf.mmm.util.pojo.base.PojoPropertyIntrospector;

/**
 * This is an implementation of the {@link PojoPropertyIntrospector} that 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldPropertyIntrospector implements PojoPropertyIntrospector {

  /**
   * The constructor
   */
  public FieldPropertyIntrospector() {

    super();
  }

  /**
   * @see net.sf.mmm.util.pojo.base.PojoPropertyIntrospector#findAccessors(java.lang.Class)
   */
  public Iterator<AbstractPojoPropertyAccessor> findAccessors(Class<?> pojoType) {
  
    return new FieldAccessorIterator(pojoType);
  }
  
}
