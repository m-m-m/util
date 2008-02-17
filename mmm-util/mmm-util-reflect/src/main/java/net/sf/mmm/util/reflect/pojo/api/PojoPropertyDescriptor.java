/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api;

import java.util.Collection;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.pojo.api.attribute.PojoAttributeName;

/**
 * This interface represents a property of a POJO. It is an alternative to
 * {@link java.beans.PropertyDescriptor} but only has focus on reflectively
 * accessing objects. Therefore it works on any POJO. A POJO (plain old java
 * object) in this manner is more or less any java object.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyDescriptor extends PojoAttributeName {

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} to access the
   * represented property in the way given by <code>mode</code>.
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the
   *        requested accessor.
   * @return the accessor for the given <code>mode</code> or <code>null</code>
   *         if no such accessor exists.
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(
      PojoPropertyAccessorMode<ACCESSOR> mode);

  /**
   * This method gets all available
   * {@link #getAccessor(PojoPropertyAccessorMode) accessors} for the
   * represented property.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptor property descriptor}s
   */
  Collection<? extends PojoPropertyAccessor> getAccessors();

}
