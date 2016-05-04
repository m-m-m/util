/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the abstract base implementation of the {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor} interface.
 *
 * @param <POJO> is the templated type of the {@link #getPojoClass() POJO}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoDescriptorImpl<POJO> extends AbstractPojoDescriptorImpl<POJO> {

  /**
   * The constructor.
   *
   * @param pojoType is the {@link #getPojoType() pojo-type}.
   * @param pojoDescriptorBuilder is the {@link PojoDescriptorBuilder}.
   */
  public PojoDescriptorImpl(GenericType<POJO> pojoType, PojoDescriptorBuilder pojoDescriptorBuilder) {

    super(pojoType, pojoDescriptorBuilder);
  }

  @Override
  public POJO newInstance() {

    try {
      return getPojoClass().newInstance();
    } catch (InstantiationException e) {
      throw new InstantiationFailedException(e, getPojoClass());
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, getPojoClass());
    }
  }

}
