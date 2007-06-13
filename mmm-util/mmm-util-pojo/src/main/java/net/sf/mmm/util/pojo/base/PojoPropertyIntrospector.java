/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.util.Iterator;

/**
 * This is the interface used to find
 * {@link AbstractPojoPropertyAccessor accessors} for properties of a given POJO
 * type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyIntrospector {

  /**
   * This method finds all accessors for properties of the given
   * <code>pojoType</code>.
   * 
   * @param pojoType is the type reflecting the POJO for which the
   *        property-accessors are requested.
   * @return a read-only iterator of all the property-accessors.
   */
  Iterator<AbstractPojoPropertyAccessor> findAccessors(Class<?> pojoType);

}
