/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Inject;

import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.base.GuessingPojoFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueConvertException;
import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.util.value.base.AbstractRecursiveValueConverter;

/**
 * This is an abstract base implementation of the {@link net.sf.mmm.util.value.api.ValueConverter} interface that
 * converts an POJO to a POJO with the same properties. E.g. this can be useful when you convert to transfer-objects.
 *
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target-type}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractValueConverterToCompatiblePojo<SOURCE, TARGET>
    extends AbstractRecursiveValueConverter<SOURCE, TARGET> {

  private PojoFactory pojoFactory;

  private PojoDescriptorBuilder pojoDescriptorBuilder;

  private PojoDescriptorBuilderFactory pojoDescriptorBuilderFactory;

  /**
   * The constructor.
   */
  public AbstractValueConverterToCompatiblePojo() {

    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.pojoDescriptorBuilder == null) {
      if (this.pojoDescriptorBuilderFactory == null) {
        this.pojoDescriptorBuilderFactory = PojoDescriptorBuilderFactoryImpl.getInstance();
      }
      this.pojoDescriptorBuilder = this.pojoDescriptorBuilderFactory.createPublicMethodDescriptorBuilder();
    }
    if (this.pojoFactory == null) {
      this.pojoFactory = new GuessingPojoFactory();
    }
  }

  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <T extends TARGET> T convert(SOURCE value, Object valueSource, GenericType<T> targetType)
      throws ValueException {

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
          // NOTE: This cast is actually a bug in compiler handling for generics...
          PojoPropertyAccessorNonArg getter = (PojoPropertyAccessorNonArg) sourceDescriptor
              .getAccessor(targetPropertyDescriptor.getName(), PojoPropertyAccessorNonArgMode.GET);
          if (getter == null) {
            handleNoGetterForSetter(setter, targetClass, value, sourceClass);
          } else {
            Object sourcePropertyValue = getter.invoke(value);
            GenericType<?> targetPropertyType = setter.getPropertyType();

            Object targetPropertyValue = getComposedValueConverter().convert(sourcePropertyValue, valueSource,
                targetPropertyType);
            setter.invoke(result, targetPropertyValue);
          }
        } catch (Exception e) {
          throw new ValueConvertException(e, value, targetType, valueSource);
        }
      }
    }
    return (T) result;
  }

  /**
   * Called if the target object of the conversion has a setter that has no corresponding getter in the source object to
   * convert.
   *
   * @param setter is the existing setter.
   * @param targetClass is the {@link Class} reflecting the target object to convert to.
   * @param sourceObject is the source object to convert that has no corresponding getter.
   * @param sourceClass is the {@link Class} reflecting the source object.
   */
  protected void handleNoGetterForSetter(PojoPropertyAccessorOneArg setter, Class<?> targetClass,
      Object sourceObject, Class<?> sourceClass) {

    throw new PojoPropertyNotFoundException(sourceClass, setter.getName());
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
   * {@link #setPojoDescriptorBuilderFactory(PojoDescriptorBuilderFactory) factory} has to be set and an individual
   * instance will be created automatically on {@link #initialize() initialization}.
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
