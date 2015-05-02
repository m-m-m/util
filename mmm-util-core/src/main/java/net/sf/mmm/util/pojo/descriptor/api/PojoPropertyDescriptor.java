/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api;

import java.lang.reflect.Field;
import java.util.Collection;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.attribute.PojoAttributeName;

/**
 * This interface represents a property of a {@link net.sf.mmm.util.pojo.api.Pojo}. A property is some
 * attribute of a {@link net.sf.mmm.util.pojo.api.Pojo} that can be accessed directly via a
 * {@link java.lang.reflect.Field} or via a {@link java.lang.reflect.Method} (getter, setter, etc.). This
 * {@link PojoPropertyDescriptor} is an alternative to {@link java.beans.PropertyDescriptor} but only has
 * focus on reflectively accessing objects. Therefore it works on any {@link net.sf.mmm.util.pojo.api.Pojo}. <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface PojoPropertyDescriptor extends PojoAttributeName {

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} to access the represented property in the way
   * given by <code>mode</code>.
   *
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the requested accessor. Use
   *        {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorModes} for available
   *        modes.
   * @return the accessor for the given <code>mode</code> or <code>null</code> if no such accessor exists.
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(PojoPropertyAccessorMode<ACCESSOR> mode);

  /**
   * This method gets all available {@link #getAccessor(PojoPropertyAccessorMode) accessors} for the
   * represented property.
   *
   * @return a collection with all {@link PojoPropertyDescriptor property descriptor}s
   */
  Collection<? extends PojoPropertyAccessor> getAccessors();

  /**
   * This method returns the {@link Field} this {@link PojoPropertyDescriptor descriptor} represents or
   * <code>null</code> if this {@link PojoPropertyDescriptor descriptor} does not represent any {@link Field
   * field}. <br/>
   * <b>ATTENTION:</b><br/>
   * {@link PojoPropertyAccessor#getAccessibleObject()} will return the accessible object for the
   * {@link #getAccessor(PojoPropertyAccessorMode) according accessor}. Depending on the introspection variant
   * this will represent the actual method or field used to access the property. Therefore if you want to
   * evaluate annotations of a property you should by default use
   * {@link PojoPropertyAccessor#getAccessibleObject()} of the
   * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}. However,
   * if you are using method introspection and want to additionally access the field with the same property
   * name if available in the {@link net.sf.mmm.util.pojo.api.Pojo} then this method is the right one.
   *
   * @return the {@link Field}, which is part of this {@link PojoPropertyDescriptor} or <code>null</code> if
   *         this {@link PojoPropertyDescriptor} is not related to a {@link Field}
   */
  Field getField();

}
