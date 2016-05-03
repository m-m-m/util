/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.util.Map;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorTwoArg} interface for
 * {@link PojoPropertyAccessorTwoArgMode#SET_MAPPED setting} an element in a {@link java.util.Map} returned
 * from another accessor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorProxySetMapped extends AbstractPojoPropertyAccessorProxyAdapterComponentType implements
    PojoPropertyAccessorTwoArg {

  /**
   * The constructor.
   * 
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param mapGetAccessor is the accessor delegate that gets a {@link java.util.Map} property.
   */
  public PojoPropertyAccessorProxySetMapped(PojoDescriptorDependencies dependencies,
      PojoPropertyAccessorNonArg mapGetAccessor) {

    super(dependencies, mapGetAccessor);
    assert (Map.class.isAssignableFrom(mapGetAccessor.getReturnClass()));
  }

  @Override
  public PojoPropertyAccessorTwoArgMode getMode() {

    return PojoPropertyAccessorTwoArgMode.SET_MAPPED;
  }

  @Override
  public GenericType<?> getReturnType() {

    return getPropertyType();
  }

  @Override
  public Class<?> getReturnClass() {

    return getPropertyClass();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Object invoke(Object pojoInstance, Object key, Object value) {

    Map map = (Map) getDelegate().invoke(pojoInstance);
    Object result = null;
    if (map != null) {
      result = map.put(key, value);
    }
    return result;
  }

}
