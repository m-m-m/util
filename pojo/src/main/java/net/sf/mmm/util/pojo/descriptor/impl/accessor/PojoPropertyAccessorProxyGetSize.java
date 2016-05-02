/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapter;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg} interface for
 * {@link PojoPropertyAccessorNonArgMode#GET getting} the size of an array, {@link java.util.List} or
 * {@link java.util.Map} from another accessor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorProxyGetSize extends AbstractPojoPropertyAccessorProxyAdapter implements
    PojoPropertyAccessorNonArg {

  /**
   * The constructor.
   * 
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param containerGetAccessor is the accessor delegate that gets an array, map or collection property.
   */
  public PojoPropertyAccessorProxyGetSize(PojoDescriptorDependencies dependencies,
      PojoPropertyAccessorNonArg containerGetAccessor) {

    super(dependencies, containerGetAccessor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET_SIZE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getPropertyType() {

    return SimpleGenericTypeImpl.TYPE_INT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getPropertyClass() {

    return int.class;
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
  public Object invoke(Object pojoInstance) {

    Object arrayMapOrCollection = getDelegate().invoke(pojoInstance);
    int size;
    if (arrayMapOrCollection == null) {
      size = 0;
    } else {
      size = getDependencies().getCollectionReflectionUtil().getSize(arrayMapOrCollection);
    }
    return Integer.valueOf(size);
  }

}
