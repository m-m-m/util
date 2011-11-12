/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderImpl;
import net.sf.mmm.util.pojo.path.api.PojoPathAccessException;
import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathMode;
import net.sf.mmm.util.pojo.path.api.PojoPathUnsafeException;
import net.sf.mmm.util.pojo.path.base.AbstractPojoPathNavigator;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathNavigator} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class PojoPathNavigatorImpl extends AbstractPojoPathNavigator {

  /** @see #getDescriptorBuilder() */
  private PojoDescriptorBuilder descriptorBuilder;

  /** @see #setDescriptorBuilderFactory(PojoDescriptorBuilderFactory) */
  private PojoDescriptorBuilderFactory descriptorBuilderFactory;

  /**
   * The constructor.<br>
   * <b>ATTENTION:</b><br>
   * You need to {@link #initialize()} this component before it can be used.
   */
  public PojoPathNavigatorImpl() {

    super();
  }

  /**
   * This method gets the {@link PojoDescriptorBuilder} used for the underlying
   * reflectional property access.
   * 
   * @return the descriptorBuilder
   */
  protected PojoDescriptorBuilder getDescriptorBuilder() {

    return this.descriptorBuilder;
  }

  /**
   * This method sets the {@link #getDescriptorBuilder() descriptor-builder} to
   * use.
   * 
   * @param descriptorBuilder is the descriptorBuilder to use.
   */
  public void setDescriptorBuilder(PojoDescriptorBuilder descriptorBuilder) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilder = descriptorBuilder;
  }

  /**
   * @param descriptorBuilderFactory is the descriptorBuilderFactory to set
   */
  @Inject
  public void setDescriptorBuilderFactory(PojoDescriptorBuilderFactory descriptorBuilderFactory) {

    this.descriptorBuilderFactory = descriptorBuilderFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.descriptorBuilder == null) {
      if (this.descriptorBuilderFactory != null) {
        this.descriptorBuilder = this.descriptorBuilderFactory
            .createPublicMethodDescriptorBuilder();
      } else {
        PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
        builder.initialize();
        this.descriptorBuilder = builder;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked", "null" })
  protected Object getFromPojo(CachingPojoPath currentPath, PojoPathContext context,
      PojoPathState state) {

    CachingPojoPath parentPath = currentPath.getParent();
    PojoDescriptor<?> descriptor = getDescriptorBuilder().getDescriptor(parentPath.getPojoType());
    boolean getType = state.isGetType();
    PojoPropertyAccessorNonArg getter = descriptor.getAccessor(currentPath.getSegment(),
        PojoPropertyAccessorNonArgMode.GET, !getType);
    GenericType pojoType;
    Class pojoClass;
    if (getter == null) {
      if (getType) {
        if (state.getMode() == PojoPathMode.FAIL_IF_NULL) {
          throw new PojoPathUnsafeException(parentPath.getPojoType(), currentPath.getSegment());
        } else {
          pojoType = null;
          pojoClass = null;
        }
      } else {
        throw new PojoPathAccessException(currentPath.getSegment(), parentPath.getPojoType());
      }
    } else {
      pojoType = getter.getPropertyType();
      pojoClass = getter.getPropertyClass();
    }
    currentPath.setPojoType(pojoType);
    currentPath.setPojoClass(pojoClass);
    Object result = null;
    if (!getType) {
      Object parentPojo = parentPath.getPojo();
      result = getter.invoke(parentPojo);
      if ((result == null) && (state.getMode() == PojoPathMode.CREATE_IF_NULL)) {
        PojoFactory factory = context.getPojoFactory();
        if (factory == null) {
          factory = getPojoFactory();
        }
        result = factory.newInstance(pojoClass);
        setInPojo(currentPath, context, state, parentPojo, result);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object setInPojo(CachingPojoPath currentPath, PojoPathContext context,
      PojoPathState state, Object parentPojo, Object value) {

    PojoDescriptor<?> descriptor = getDescriptorBuilder().getDescriptor(parentPojo.getClass());
    PojoPropertyAccessorOneArg setAccessor = descriptor.getAccessor(currentPath.getSegment(),
        PojoPropertyAccessorOneArgMode.SET, true);
    GenericType<?> targetType = setAccessor.getPropertyType();
    Class<?> targetClass = setAccessor.getPropertyClass();
    currentPath.setPojoClass(targetClass);
    currentPath.setPojoType(targetType);
    Object convertedValue = convert(currentPath, context, value, targetClass, targetType);
    return setAccessor.invoke(parentPojo, convertedValue);
  }

}
