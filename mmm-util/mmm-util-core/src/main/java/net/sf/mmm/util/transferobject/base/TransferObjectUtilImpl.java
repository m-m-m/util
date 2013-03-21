/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.transferobject.api.EntityTo;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.transferobject.api.TransferObjectUtil;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.impl.DefaultComposedValueConverter;

/**
 * This is the implementation of {@link TransferObjectUtil}.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Named
public class TransferObjectUtilImpl extends TransferObjectUtilLimitedImpl implements TransferObjectUtil {

  /** @see #getInstance() */
  private static TransferObjectUtil instance;

  /** @see #getComposedValueConverter() */
  private ComposedValueConverter composedValueConverter;

  /** @see #getPojoDescriptorBuilderFactory() */
  private PojoDescriptorBuilderFactory pojoDescriptorBuilderFactory;

  /** @see #getPojoDescriptorBuilder() */
  private PojoDescriptorBuilder pojoDescriptorBuilder;

  /**
   * The constructor.
   */
  public TransferObjectUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link TransferObjectUtilImpl}.<br/>
   * <b>ATTENTION:</b><br/>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   * 
   * @return the singleton instance.
   */
  public static TransferObjectUtil getInstance() {

    if (instance == null) {
      synchronized (TransferObjectUtilImpl.class) {
        if (instance == null) {
          TransferObjectUtilImpl impl = new TransferObjectUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.composedValueConverter == null) {
      this.composedValueConverter = DefaultComposedValueConverter.getInstance();
    }
    if (this.pojoDescriptorBuilderFactory == null) {
      this.pojoDescriptorBuilderFactory = PojoDescriptorBuilderFactoryImpl.getInstance();
    }
    if (this.pojoDescriptorBuilder == null) {
      // VisibilityModifier methodVisibility = VisibilityModifier.PROTECTED;
      // VisibilityModifier fieldVisibility = null;
      // this.pojoDescriptorBuilder =
      // this.pojoDescriptorBuilderFactory.createDescriptorBuilder(methodVisibility,
      // fieldVisibility);
      this.pojoDescriptorBuilder = this.pojoDescriptorBuilderFactory.createPublicMethodDescriptorBuilder();
    }
  }

  /**
   * @return the instance of {@link ComposedValueConverter}.
   */
  protected ComposedValueConverter getComposedValueConverter() {

    return this.composedValueConverter;
  }

  /**
   * @param composedValueConverter is the instance of {@link ComposedValueConverter} to {@link Inject}.
   */
  @Inject
  public void setComposedValueConverter(ComposedValueConverter composedValueConverter) {

    getInitializationState().requireNotInitilized();
    this.composedValueConverter = composedValueConverter;
  }

  /**
   * @return the instance of {@link PojoDescriptorBuilderFactory}.
   */
  protected PojoDescriptorBuilderFactory getPojoDescriptorBuilderFactory() {

    return this.pojoDescriptorBuilderFactory;
  }

  /**
   * @param pojoDescriptorBuilderFactory is the instance of {@link PojoDescriptorBuilderFactory} to
   *        {@link Inject}.
   */
  @Inject
  public void setPojoDescriptorBuilderFactory(PojoDescriptorBuilderFactory pojoDescriptorBuilderFactory) {

    getInitializationState().requireNotInitilized();
    this.pojoDescriptorBuilderFactory = pojoDescriptorBuilderFactory;
  }

  /**
   * @return the {@link PojoDescriptorBuilder}.
   */
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    return this.pojoDescriptorBuilder;
  }

  /**
   * @param pojoDescriptorBuilder is the {@link PojoDescriptorBuilder} to set.
   */
  public void setPojoDescriptorBuilder(PojoDescriptorBuilder pojoDescriptorBuilder) {

    this.pojoDescriptorBuilder = pojoDescriptorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> TO convertFromEntity(ENTITY entity,
      Class<TO> toType) {

    return this.composedValueConverter.convert(entity, null, toType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <ID, ENTITY extends PersistenceEntity<ID>, TO extends EntityTo<ID>> ENTITY convertToEntity(TO transferObject,
      Class<ENTITY> entityType) {

    return this.composedValueConverter.convert(transferObject, null, entityType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateModificationCounter(TransferObject transferObject) {

    if (transferObject == null) {
      return;
    }
    if (transferObject instanceof EntityTo) {
      EntityTo<?> entityTo = (EntityTo<?>) transferObject;
      // we call this method to update the field...
      entityTo.getModificationCounter();
    } else {
      Class<? extends TransferObject> toClass = transferObject.getClass();
      PojoDescriptor<? extends TransferObject> descriptor = this.pojoDescriptorBuilder.getDescriptor(toClass);
      for (PojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
        PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
        if (getter != null) {
          Object propertyValue = getter.invoke(transferObject);
          if (propertyValue instanceof TransferObject) {
            updateModificationCounter((TransferObject) propertyValue);
          }
        }
      }
    }
  }

}
