/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.util.Map;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArg} interface for
 * {@link PojoPropertyAccessorOneArgMode#GET_MAPPED getting} an element from another accessor returning a
 * {@link java.util.Map}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorProxyGetMapped extends AbstractPojoPropertyAccessorProxyAdapterComponentType implements
    PojoPropertyAccessorOneArg {

  /**
   * The constructor.
   * 
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param mapGetAccessor is the accessor delegate that gets a {@link java.util.Map} property.
   */
  public PojoPropertyAccessorProxyGetMapped(PojoDescriptorDependencies dependencies,
      PojoPropertyAccessorNonArg mapGetAccessor) {

    super(dependencies, mapGetAccessor);
    assert (Map.class.isAssignableFrom(mapGetAccessor.getReturnClass()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.GET_MAPPED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getReturnType() {

    return getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return getPropertyClass();
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, Object argument) {

    Map<?, ?> map = (Map<?, ?>) getDelegate().invoke(pojoInstance);
    Object result = null;
    if (map != null) {
      result = map.get(argument);
    }
    return result;
  }

}
