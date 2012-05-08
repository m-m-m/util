/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.base.GuessingPojoFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueConvertException;
import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.util.value.base.AbstractRecursiveValueConverter;

/**
 * This is an implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts
 * an POJO to a POJO with the same properties. E.g. this can be useful when you have generated
 * transport-objects (maybe from some strange web-service-framework) and want to convert those from or to your
 * nice equivalent handwritten POJOs.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class ValueConverterToCompatiblePojo extends AbstractRecursiveValueConverter<Object, Object> {

  /** @see #setPojoFactory(PojoFactory) */
  private PojoFactory pojoFactory;

  /** @see #setPojoDescriptorBuilder(PojoDescriptorBuilder) */
  private PojoDescriptorBuilder pojoDescriptorBuilder;

  /** @see #setPojoDescriptorBuilderFactory(PojoDescriptorBuilderFactory) */
  private PojoDescriptorBuilderFactory pojoDescriptorBuilderFactory;

  /**
   * The constructor.
   */
  public ValueConverterToCompatiblePojo() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.pojoDescriptorBuilder == null) {
      if (this.pojoDescriptorBuilderFactory == null) {
        this.pojoDescriptorBuilderFactory = new PojoDescriptorBuilderFactoryImpl();
      }
      this.pojoDescriptorBuilder = this.pojoDescriptorBuilderFactory.createPublicMethodDescriptorBuilder();
    }
    if (this.pojoFactory == null) {
      this.pojoFactory = new GuessingPojoFactory();
    }
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getTargetType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <T> T convert(Object value, Object valueSource, GenericType<T> targetType) throws ValueException {

    if (value == null) {
      return null;
    }
    Class<?> sourceClass = value.getClass();
    Class<?> targetClass = targetType.getAssignmentClass();
    Object result = this.pojoFactory.newInstance(targetClass);
    PojoDescriptor sourceDescriptor = this.pojoDescriptorBuilder.getDescriptor(sourceClass);
    PojoDescriptor<?> targetDescriptor = this.pojoDescriptorBuilder.getDescriptor(result.getClass());
    for (PojoPropertyDescriptor targetPropertyDescriptor : targetDescriptor.getPropertyDescriptors()) {
      PojoPropertyAccessorOneArg setter = targetPropertyDescriptor.getAccessor(PojoPropertyAccessorOneArgMode.SET);
      if (setter != null) {
        try {
          Object sourcePropertyValue = sourceDescriptor.getProperty(value, targetPropertyDescriptor.getName());
          GenericType<?> targetPropertyType = setter.getPropertyType();

          Object targetPropertyValue = getComposedValueConverter().convert(sourcePropertyValue, valueSource,
              targetPropertyType);
          setter.invoke(result, targetPropertyValue);
        } catch (Exception e) {
          throw new ValueConvertException(e, value, targetType, valueSource);
        }
      }
    }
    return (T) result;
  }

  /**
   * This method sets the {@link PojoFactory} instance to used to create new instances of POJOs.
   * 
   * @param pojoFactory is the {@link PojoFactory} to use.
   */
  @Inject
  public void setPojoFactory(PojoFactory pojoFactory) {

    getInitializationState().requireNotInitilized();
    this.pojoFactory = pojoFactory;
  }

  /**
   * @return the {@link #setPojoDescriptorBuilder(PojoDescriptorBuilder) PojoDescriptorBuilder}.
   */
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    return this.pojoDescriptorBuilder;
  }

  /**
   * This method sets the {@link PojoDescriptorBuilder} instance to use. If no such instance is set, the
   * {@link #setPojoDescriptorBuilderFactory(PojoDescriptorBuilderFactory) factory} has to be set and an
   * individual instance will be created automatically on {@link #initialize() initialization}.
   * 
   * @param pojoDescriptorBuilder is the {@link PojoDescriptorBuilder} to use.
   */
  public void setPojoDescriptorBuilder(PojoDescriptorBuilder pojoDescriptorBuilder) {

    getInitializationState().requireNotInitilized();
    this.pojoDescriptorBuilder = pojoDescriptorBuilder;
  }

  /**
   * This method sets the {@link PojoDescriptorBuilderFactory} instance to use.
   * 
   * @param pojoDescriptorBuilderFactory is the pojoDescriptorBuilderFactory to set
   */
  @Inject
  public void setPojoDescriptorBuilderFactory(PojoDescriptorBuilderFactory pojoDescriptorBuilderFactory) {

    getInitializationState().requireNotInitilized();
    this.pojoDescriptorBuilderFactory = pojoDescriptorBuilderFactory;
  }

  /**
   * @return the pojoDescriptorBuilderFactory
   */
  protected PojoDescriptorBuilderFactory getPojoDescriptorBuilderFactory() {

    return this.pojoDescriptorBuilderFactory;
  }

}
