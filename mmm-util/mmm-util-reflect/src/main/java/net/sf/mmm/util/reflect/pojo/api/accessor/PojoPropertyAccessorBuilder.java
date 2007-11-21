/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This is the interface used to create a
 * {@link PojoPropertyAccessorOneArg one-arg accessor}.
 * 
 * @param <ACCESSOR> is the type of the accessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface PojoPropertyAccessorBuilder<ACCESSOR extends PojoPropertyAccessor> {

  /**
   * This method creates the {@link PojoPropertyAccessor accessor} for the given
   * <code>method</code> if that method is suitable for this builder (e.g.
   * starts with "set").
   * 
   * @param method the method to access.
   * @return the {@link PojoPropertyAccessor accessor} for the given
   *         <code>method</code> or <code>null</code> if the
   *         <code>method</code> is NOT suitable for this builder.
   */
  ACCESSOR create(Method method);

  /**
   * This method creates the {@link PojoPropertyAccessor accessor} for the given
   * <code>field</code> if that field is suitable for this builder (e.g.
   * contains a container type).
   * 
   * @param field is the field.
   * @return the {@link PojoPropertyAccessor accessor} for the given
   *         <code>field</code> or <code>null</code> if the
   *         <code>field</code> is NOT suitable for this builder.
   */
  ACCESSOR create(Field field);

  /**
   * This method gets the mode of this builder.
   * 
   * @return the mode.
   */
  PojoPropertyAccessorMode<ACCESSOR> getMode();

}
