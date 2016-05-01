/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.mapping;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanPrototypeBuilder;
import net.sf.mmm.util.bean.api.mapping.GenericPojoBeanMapper;
import net.sf.mmm.util.bean.base.mapping.AbstractPojoBeanMapper;
import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of {@link net.sf.mmm.util.bean.impl.mapping.GenericPojoBeanMapperImpl}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class GenericPojoBeanMapperImpl extends AbstractPojoBeanMapper<Object, Bean>
    implements GenericPojoBeanMapper {

  private PojoDescriptorBuilderFactory descriptorBuilderFactory;

  private PojoDescriptorBuilder descriptorBuilder;

  private PojoFactory pojoFactory;

  private CollectionReflectionUtil collectionReflectionUtil;

  /**
   * The constructor.
   */
  public GenericPojoBeanMapperImpl() {
    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if ((this.descriptorBuilder == null) && (this.descriptorBuilderFactory != null)) {
      this.descriptorBuilder = this.descriptorBuilderFactory.createPublicMethodDescriptorBuilder();
    }
  }

  /**
   * @return the {@link PojoDescriptorBuilderFactory}.
   */
  protected PojoDescriptorBuilderFactory getDescriptorBuilderFactory() {

    return this.descriptorBuilderFactory;
  }

  /**
   * @param descriptorBuilderFactory is the {@link PojoDescriptorBuilderFactory} to {@link Inject}.
   */
  @Inject
  public void setDescriptorBuilderFactory(PojoDescriptorBuilderFactory descriptorBuilderFactory) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilderFactory = descriptorBuilderFactory;
  }

  /**
   * @return the {@link PojoDescriptorBuilder} instance.
   */
  protected PojoDescriptorBuilder getDescriptorBuilder() {

    return this.descriptorBuilder;
  }

  /**
   * @param descriptorBuilder is the {@link PojoDescriptorBuilder} to set.
   */
  public void setDescriptorBuilder(PojoDescriptorBuilder descriptorBuilder) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilder = descriptorBuilder;
  }

  /**
   * @return the {@link PojoFactory} instance.
   */
  protected PojoFactory getPojoFactory() {

    return this.pojoFactory;
  }

  /**
   * @param pojoFactory is the {@link PojoFactory} to {@link Inject}.
   */
  @Inject
  public void setPojoFactory(PojoFactory pojoFactory) {

    getInitializationState().requireNotInitilized();
    this.pojoFactory = pojoFactory;
  }

  /**
   * @return the {@link CollectionReflectionUtil}.
   */
  protected CollectionReflectionUtil getCollectionReflectionUtil() {

    return this.collectionReflectionUtil;
  }

  /**
   * @param collectionReflectionUtil is the {@link CollectionReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setCollectionReflectionUtil(CollectionReflectionUtil collectionReflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.collectionReflectionUtil = collectionReflectionUtil;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <T extends Bean> T toBean(Object pojo, T prototype) {

    if (pojo == null) {
      return null;
    }
    PojoDescriptor<?> descriptor = this.descriptorBuilder.getDescriptor(pojo.getClass());
    BeanPrototypeBuilder prototypeBuilder = getBeanPrototypeBuilder();
    T bean = prototypeBuilder.create(prototype);
    BeanAccess access = bean.access();
    for (PojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
      PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
      if (getter != null) {
        String name = getter.getName();
        if (!name.equals("class")) {
          WritableProperty property = access.getProperty(name);
          if (property == null) {
            if (access.isDynamic()) {
              GenericType<?> propertyType = getter.getPropertyType();
              getLogger().debug("Dynamically creating property {}.{} of type {}.", access.getQualifiedName(), name,
                  propertyType);
              property = access.createProperty(name, propertyType);
            } else {
              getLogger().trace("Ignoring missing property {}.{}", access.getQualifiedName(), name);
            }
          }
          if (property != null) {
            Object value = getter.invoke(pojo);
            value = toBeanValue(pojo, prototypeBuilder, bean, value, property.getType());
            property.setValue(value);
          }
        }
      }
    }
    return bean;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private <T extends Bean> Object toBeanValue(Object pojo, BeanPrototypeBuilder prototypeBuilder, T bean,
      Object value, GenericType<?> type) {

    if (value instanceof Bean) {
      if (value == pojo) {
        // handle self-reference (getter returning this) without infinity loop.
        return bean;
      } else {
        Bean propertyPrototype = prototypeBuilder.getOrCreatePrototype((Class) type.getAssignmentClass());
        return toBean(value, propertyPrototype);
      }
    } else if (value instanceof Collection) {
      Collection<?> valueCollcetion = (Collection<?>) value;
      Collection copy = this.collectionReflectionUtil.create((Class) value.getClass(), valueCollcetion.size());
      GenericType<?> elementType = type.getComponentType();
      for (Object element : valueCollcetion) {
        copy.add(toBeanValue(pojo, prototypeBuilder, bean, element, elementType));
      }
      return copy;
    } else if (value instanceof Map) {
      Map<?, ?> valueMap = (Map<?, ?>) value;
      Map copy = this.collectionReflectionUtil.createMap((Class) value.getClass(), valueMap.size());
      GenericType<?> keyType = type.getKeyType();
      GenericType<?> valueType = type.getComponentType();
      for (Entry<?, ?> entry : valueMap.entrySet()) {
        Object mapKey = toBeanValue(pojo, prototypeBuilder, bean, entry.getKey(), keyType);
        Object mapValue = toBeanValue(pojo, prototypeBuilder, bean, entry.getValue(), valueType);
        copy.put(mapKey, mapValue);
      }
      return copy;
    }
    return value;
  }

  @Override
  public <T> T fromBean(Bean bean, Class<T> type) {

    if (bean == null) {
      return null;
    }
    PojoDescriptor<T> descriptor = this.descriptorBuilder.getDescriptor(type);
    T pojo = this.pojoFactory.newInstance(type);
    BeanAccess access = bean.access();
    for (WritableProperty<?> property : access.getProperties()) {
      Object value = property.getValue();
      String name = property.getName();
      PojoPropertyAccessorOneArg setter = descriptor.getAccessor(name, PojoPropertyAccessorOneArgMode.SET);
      if (setter == null) {
        getLogger().info("Ignoring property {}.{} in mapping as it does not exist.", type, name);
      } else {
        value = fromBeanValue(bean, pojo, value, setter.getPropertyType());
        setter.invoke(pojo, value);
      }
    }
    return pojo;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private <T> Object fromBeanValue(Bean bean, T pojo, Object value, GenericType<?> type) {

    if (value instanceof Bean) {
      if (value == bean) {
        // handle self-reference (getter returning this) without infinity loop.
        return pojo;
      } else {
        return fromBean((Bean) value, type.getAssignmentClass());
      }
    } else if (value instanceof Collection) {
      Collection<?> valueCollcetion = (Collection<?>) value;
      Collection copy = this.collectionReflectionUtil.create((Class) value.getClass(), valueCollcetion.size());
      GenericType<?> elementType = type.getComponentType();
      for (Object element : valueCollcetion) {
        copy.add(fromBeanValue(bean, pojo, element, elementType));
      }
      return copy;
    } else if (value instanceof Map) {
      Map<?, ?> valueMap = (Map<?, ?>) value;
      Map copy = this.collectionReflectionUtil.createMap((Class) value.getClass(), valueMap.size());
      GenericType<?> keyType = type.getKeyType();
      GenericType<?> valueType = type.getComponentType();
      for (Entry<?, ?> entry : valueMap.entrySet()) {
        Object mapKey = fromBeanValue(bean, pojo, entry.getKey(), keyType);
        Object mapValue = fromBeanValue(bean, pojo, entry.getValue(), valueType);
        copy.put(mapKey, mapValue);
      }
      return copy;
    }
    return value;
  }

}
