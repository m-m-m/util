/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.impl;

import java.lang.reflect.Type;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.util.component.InitializationState;
import net.sf.mmm.util.reflect.CollectionUtil;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.impl.PojoDescriptorBuilderImpl;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunctionManager;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathMode;
import net.sf.mmm.util.reflect.pojo.path.base.AbstractPojoPathNavigator;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPathNavigator} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathNavigatorImpl extends AbstractPojoPathNavigator {

  /** @see #getFunctionManager() */
  private PojoPathFunctionManager functionManager;

  /** @see #getDescriptorBuilder() */
  private PojoDescriptorBuilder descriptorBuilder;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** @see #getCollectionUtil() */
  private CollectionUtil collectionUtil;

  /** @see #initialize() */
  private final InitializationState initializationState;

  /**
   * The constructor.<br>
   * <b>ATTENTION:</b><br>
   * You need to {@link #initialize()} this component before it can be used.
   */
  public PojoPathNavigatorImpl() {

    super();
    this.initializationState = new InitializationState();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoPathFunctionManager getFunctionManager() {

    return this.functionManager;
  }

  /**
   * This method sets the {@link #getFunctionManager() function-manager} used
   * for global {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunction}s.
   * 
   * @param functionManager is the {@link PojoPathFunctionManager}.
   */
  @Resource
  public void setFunctionManager(PojoPathFunctionManager functionManager) {

    this.initializationState.requireNotInitilized();
    this.functionManager = functionManager;
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
  @Resource
  public void setDescriptorBuilder(PojoDescriptorBuilder descriptorBuilder) {

    this.initializationState.requireNotInitilized();
    this.descriptorBuilder = descriptorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * This method sets the {@link #getReflectionUtil() reflection-util} to use.
   * 
   * @param reflectionUtil is the {@link ReflectionUtil} to use.
   */
  @Resource
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    this.initializationState.requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CollectionUtil getCollectionUtil() {

    return this.collectionUtil;
  }

  /**
   * This method sets the {@link #getCollectionUtil() collection-util} to use.
   * 
   * @param collectionUtil is the {@link CollectionUtil} to use.
   */
  @Resource
  public void setCollectionUtil(CollectionUtil collectionUtil) {

    this.collectionUtil = collectionUtil;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    if (this.initializationState.setInitialized()) {
      if (this.descriptorBuilder == null) {
        PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
        builder.initialize();
        this.descriptorBuilder = builder;
      }
      if (this.collectionUtil == null) {
        this.collectionUtil = CollectionUtil.getInstance();
      }
      if (this.reflectionUtil == null) {
        this.reflectionUtil = ReflectionUtil.getInstance();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Object getFromPojo(CachingPojoPath currentPath, PojoPathContext context,
      PojoPathState state, Object parentPojo) {

    PojoDescriptor<?> descriptor = getDescriptorBuilder().getDescriptor(parentPojo.getClass());
    // descriptor.getProperty(parentPojo, currentPath.getSegment());
    PojoPropertyAccessorNonArg getter;
    getter = descriptor.getAccessor(currentPath.getSegment(), PojoPropertyAccessorNonArgMode.GET,
        true);
    Type type = getter.getPropertyType();
    currentPath.setPojoType(type);
    Object result = getter.invoke(parentPojo);
    if ((result == null) && (state.getMode() == PojoPathMode.CREATE_IF_NULL)) {
      Class<?> clazz = getReflectionUtil().toClass(type);
      result = context.getPojoFactory().newInstance(clazz);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object setInPojo(CachingPojoPath currentPath, PojoPathContext context,
      PojoPathState state, Object parentPojo, Object value) {

    // TODO Auto-generated method stub
    return null;
  }

}
