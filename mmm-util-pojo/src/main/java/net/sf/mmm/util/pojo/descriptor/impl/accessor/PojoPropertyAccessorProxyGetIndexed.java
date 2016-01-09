/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorIndexedNonArg} interface for
 * {@link PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED getting} an element from another accessor
 * returning an array or {@link java.util.List}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorProxyGetIndexed extends AbstractPojoPropertyAccessorProxyAdapterComponentType
    implements PojoPropertyAccessorIndexedNonArg {

  /**
   * The constructor.
   * 
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param containerGetAccessor is the accessor delegate that gets an array, map or collection property.
   */
  public PojoPropertyAccessorProxyGetIndexed(PojoDescriptorDependencies dependencies,
      PojoPropertyAccessorNonArg containerGetAccessor) {

    super(dependencies, containerGetAccessor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorIndexedNonArgMode getMode() {

    return PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED;
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
  public Object invoke(Object pojoInstance, int index) {

    Object arrayOrList = getDelegate().invoke(pojoInstance);
    return getDependencies().getCollectionReflectionUtil().get(arrayOrList, index);
  }

}
