/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArg} interface for
 * {@link PojoPropertyAccessorOneArgMode#REMOVE removing} an element using the getter from another accessor
 * returning an array or {@link java.util.Collection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorProxyRemove extends AbstractPojoPropertyAccessorProxyAdapterComponentType implements
    PojoPropertyAccessorOneArg {

  /** The according setter to use if array has to be resized. */
  private final PojoPropertyAccessorOneArg containerSetAccessor;

  /**
   * The constructor.
   * 
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param containerGetAccessor is the accessor delegate that gets an array, or list property.
   * @param containerSetAccessor is the accessor that sets the array, or {@link java.util.Collection}
   *        property. May be {@code null} if NOT available.
   */
  public PojoPropertyAccessorProxyRemove(PojoDescriptorDependencies dependencies,
      PojoPropertyAccessorNonArg containerGetAccessor, PojoPropertyAccessorOneArg containerSetAccessor) {

    super(dependencies, containerGetAccessor);
    this.containerSetAccessor = containerSetAccessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.REMOVE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getReturnType() {

    return SimpleGenericTypeImpl.TYPE_VOID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return void.class;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, Object argument) {

    Object arrayOrCollection = getDelegate().invoke(pojoInstance);
    Object arrayCopy = getDependencies().getCollectionReflectionUtil().remove(arrayOrCollection, argument);
    if ((arrayCopy != arrayOrCollection) && (this.containerSetAccessor != null)) {
      // we will NOT create this proxy if the setter is missing for an array
      // type getter.
      this.containerSetAccessor.invoke(pojoInstance, arrayCopy);
    }
    return null;
  }

}
