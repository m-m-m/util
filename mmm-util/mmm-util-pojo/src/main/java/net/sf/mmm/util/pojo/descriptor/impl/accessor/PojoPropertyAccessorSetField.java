/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorField;
import net.sf.mmm.util.reflect.AccessFailedException;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArg}
 * interface for {@link PojoPropertyAccessorOneArgMode#SET setting} a
 * {@link Field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorSetField extends AbstractPojoPropertyAccessorField implements
    PojoPropertyAccessorOneArg {

  /**
   * The constructor.
   * 
   * @param descriptor is the descriptor this accessor is intended for.
   * @param configuration is the {@link PojoDescriptorConfiguration} to use.
   * @param field is the {@link #getField() field} to access.
   */
  public PojoPropertyAccessorSetField(PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration, Field field) {

    super(PojoPropertyAccessorOneArgMode.SET, descriptor, configuration, field);
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, Object argument) {

    Field field = getField();
    try {
      Object old = field.get(pojoInstance);
      field.set(pojoInstance, argument);
      return old;
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, field);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getArgumentTypes() {

    return new Type[] { getPropertyType() };
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.SET;
  }

}
