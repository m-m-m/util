/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorField;
import net.sf.mmm.util.reflect.api.AccessFailedException;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getting} a
 * {@link Field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorGetField extends AbstractPojoPropertyAccessorField implements
    PojoPropertyAccessorNonArg {

  /**
   * The constructor.
   * 
   * @param descriptor is the descriptor this accessor is intended for.
   * @param configuration is the {@link PojoDescriptorConfiguration} to use.
   * @param field is the {@link #getField() field} to access.
   */
  public PojoPropertyAccessorGetField(PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration, Field field) {

    super(PojoPropertyAccessorNonArgMode.GET, descriptor, configuration, field);
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) {

    try {
      return getField().get(pojoInstance);
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, getField());
    }
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET;
  }

}
