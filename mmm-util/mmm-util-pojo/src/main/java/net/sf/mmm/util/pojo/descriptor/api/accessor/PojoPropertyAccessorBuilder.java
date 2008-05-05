/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;

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
   * @param descriptor is the descriptor of the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} having the given
   *        <code>method</code>.
   * @param configuration is the configuration with injected helper components.
   * @return the {@link PojoPropertyAccessor accessor} for the given
   *         <code>method</code> or <code>null</code> if the
   *         <code>method</code> is NOT suitable for this builder.
   */
  ACCESSOR create(Method method, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration);

  /**
   * This method creates the {@link PojoPropertyAccessor accessor} for the given
   * <code>field</code> if that field is suitable for this builder (e.g.
   * contains a container type).
   * 
   * @param field is the field.
   * @param descriptor is the descriptor of the
   *        {@link net.sf.mmm.util.pojo.api.Pojo} having the given
   *        <code>field</code>.
   * @param configuration is the configuration with injected helper components.
   * @return the {@link PojoPropertyAccessor accessor} for the given
   *         <code>field</code> or <code>null</code> if the
   *         <code>field</code> is NOT suitable for this builder.
   */
  ACCESSOR create(Field field, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration);

  /**
   * This method gets the mode of this builder.
   * 
   * @return the mode.
   */
  PojoPropertyAccessorMode<ACCESSOR> getMode();

}
