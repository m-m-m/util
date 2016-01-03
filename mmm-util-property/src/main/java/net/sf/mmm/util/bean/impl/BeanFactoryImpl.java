/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.inject.Named;

import javafx.beans.property.Property;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.impl.BooleanPropertyImpl;
import net.sf.mmm.util.property.impl.GenericPropertyImpl;
import net.sf.mmm.util.property.impl.StringPropertyImpl;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ValidatorNone;

/**
 * This is the implementation of {@link BeanFactory}.
 *
 * @author hohwille
 * @since 7.1.0
 */
@Named
public class BeanFactoryImpl extends AbstractLoggableComponent implements BeanFactory {

  /** @see #getInstance() */
  private static BeanFactory instance;

  private final ClassLoader classLoader;

  private ReflectionUtil reflectionUtil;

  private static final Type PROPERTY_VALUE_TYPE_VARIABLE;

  private static final Class<?>[] PROPERTY_CONSTRUCTOR_ARGS = new Class<?>[] { String.class, Bean.class };

  static {
    PROPERTY_VALUE_TYPE_VARIABLE = Property.class.getTypeParameters()[0];
  }

  /**
   * The constructor.
   */
  public BeanFactoryImpl() {
    this(Thread.currentThread().getContextClassLoader());
  }

  /**
   * The constructor.
   *
   * @param classLoader the {@link ClassLoader} to use.
   */
  public BeanFactoryImpl(ClassLoader classLoader) {
    super();
    this.classLoader = classLoader;
  }

  /**
   * This method gets the singleton instance of this {@link BeanFactory}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static BeanFactory getInstance() {

    if (instance == null) {
      synchronized (BeanFactoryImpl.class) {
        if (instance == null) {
          BeanFactoryImpl impl = new BeanFactoryImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  @Override
  protected void doInitialize() {

    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    super.doInitialize();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <BEAN extends Bean> BEAN getPrototype(Class<BEAN> type, boolean dynamic) {

    BeanAccessPrototype<BEAN> prototype = new BeanAccessPrototype<>(type, dynamic, this);
    BEAN bean = (BEAN) Proxy.newProxyInstance(this.classLoader, new Class<?>[] { type }, prototype);
    GenericType<BEAN> beanType = this.reflectionUtil.createGenericType(type);
    Collection<BeanMethod> methods = new ArrayList<>();
    collectMethods(type, prototype, beanType, bean, methods);
    processMethods(methods, prototype, beanType, bean);
    // TODO Auto-generated method stub
    return bean;
  }

  /**
   * Recursively introspects, creates and collects the {@link BeanMethod}s for a {@link Bean} {@link Class}. Also
   * creates the {@link GenericPropertyImpl properties} for the {@link BeanMethodType#PROPERTY property} methods.
   *
   * @param type is the current {@link Bean} {@link Class} to introspect.
   * @param prototype the {@link BeanAccessPrototype}.
   * @param beanType the {@link GenericType} of the initial {@link Bean}.
   * @param bean the {@link Bean} instance.
   * @param methods the {@link Collection} where to {@link Collection#add(Object) add} the {@link BeanMethod}.
   */
  protected void collectMethods(Class<?> type, BeanAccessPrototype<?> prototype, GenericType<?> beanType,
      Bean bean, Collection<BeanMethod> methods) {

    for (Method method : type.getDeclaredMethods()) {
      BeanMethod beanMethod = new BeanMethod(method);
      BeanMethodType methodType = beanMethod.getMethodType();
      if (methodType == null) {
        getLogger().warn("Unmapped bean method {}", method);
      } else {
        methods.add(beanMethod);
        if (methodType == BeanMethodType.PROPERTY) {
          GenericPropertyImpl<?> property = createProperty(beanMethod, beanType, bean);
          prototype.addProperty(property);
        }
      }
    }
    if (type != Bean.class) {
      for (Class<?> superInterface : type.getInterfaces()) {
        collectMethods(superInterface, prototype, beanType, bean, methods);
      }
    }
  }

  /**
   * TODO: javadoc
   *
   * @param methods
   * @param prototype
   */
  private void processMethods(Collection<BeanMethod> methods, BeanAccessPrototype<?> prototype,
      GenericType<?> beanType, Bean bean) {

    Map<Method, BeanPrototypeOperation> method2OperationMap = prototype.getMethod2OperationMap();
    Map<String, BeanPrototypeProperty> name2PropertyMap = prototype.getName2PropertyMap();
    for (BeanMethod beanMethod : methods) {
      BeanMethodType methodType = beanMethod.getMethodType();
      if ((methodType == BeanMethodType.GET) || (methodType == BeanMethodType.SET)) {
        String propertyName = beanMethod.getPropertyName();
        BeanPrototypeProperty prototypeProperty = name2PropertyMap.get(propertyName);
        if (prototypeProperty == null) {
          GenericType<?> propertyType = this.reflectionUtil.createGenericType(beanMethod.getPropertyType(),
              beanType);
          GenericPropertyImpl<?> property = createProperty(propertyName, propertyType, bean);
          prototype.addProperty(property);
        }
      }
      BeanPrototypeOperation operation = BeanPrototypeOperation.create(beanMethod, prototype);
      method2OperationMap.put(beanMethod.getMethod(), operation);
    }
  }

  /**
   * TODO: javadoc
   *
   * @param beanMethod
   */
  private GenericPropertyImpl<?> createProperty(BeanMethod beanMethod, GenericType<?> beanType, Bean bean) {

    Method method = beanMethod.getMethod();
    GenericPropertyImpl<?> property = null;
    if (method.isDefault()) {
      property = (GenericPropertyImpl<?>) LookupHelper.INSTANCE.invokeDefaultMethod(method,
          ReflectionUtil.NO_ARGUMENTS);
    }
    if (property == null) {
      GenericType<?> propertyType = this.reflectionUtil.createGenericType(beanMethod.getPropertyType(), beanType);
      GenericType<?> valueType = this.reflectionUtil.createGenericType(PROPERTY_VALUE_TYPE_VARIABLE, propertyType);
      property = createProperty(beanMethod.getPropertyName(), valueType, bean, propertyType.getRetrievalClass());
    }
    return property;
  }

  protected <V> GenericPropertyImpl<V> createProperty(String name, GenericType<V> type, Bean bean) {

    return createProperty(name, type, bean, GenericProperty.class);
  }

  /**
   * @param <V> the generic property type.
   * @param name the {@link GenericPropertyImpl#getName() property name}.
   * @param type the {@link GenericPropertyImpl#getType() property type}.
   * @param bean
   * @return the new instance of {@link GenericPropertyImpl}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <V> GenericPropertyImpl<V> createProperty(String name, GenericType<V> type, Bean bean,
      Class<?> propertyClass) {

    GenericPropertyImpl result;
    if ((!propertyClass.isInterface()) && (!Modifier.isAbstract(propertyClass.getModifiers()))) {
      result = createPropertyFromSpecifiedClass(name, type, bean, propertyClass);
    } else {
      Class<?> valueClass = type.getRetrievalClass();
      if (valueClass == String.class) {
        result = new StringPropertyImpl(name, bean);
      } else if (valueClass == Boolean.class) {
        result = new BooleanPropertyImpl(name, bean);
      } else {
        result = new GenericPropertyImpl<>(name, type, bean);
      }
    }
    return result;
  }

  private GenericPropertyImpl<?> createPropertyFromSpecifiedClass(String name, GenericType<?> type, Bean bean,
      Class<?> propertyClass) {

    Constructor<?> constructor = null;
    try {
      for (Constructor<?> c : propertyClass.getConstructors()) {
        constructor = c;
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if ((parameterTypes.length >= 2) && (parameterTypes.length <= 4) && (parameterTypes[0] == String.class)) {
          if (parameterTypes[1] == Bean.class) {
            if (parameterTypes.length == 2) {
              return (GenericPropertyImpl<?>) constructor.newInstance(new Object[] { name, bean });
            } else if ((parameterTypes.length == 3) && (parameterTypes[2] == AbstractValidator.class)) {
              return (GenericPropertyImpl<?>) constructor
                  .newInstance(new Object[] { name, bean, ValidatorNone.getInstance() });
            }
          } else if ((parameterTypes[1] == GenericType.class) && (parameterTypes.length >= 3)
              && (parameterTypes[2] == Bean.class)) {
            if (parameterTypes.length == 3) {
              return (GenericPropertyImpl<?>) constructor.newInstance(new Object[] { name, type, bean });
            } else if (parameterTypes[3] == AbstractValidator.class) {
              return (GenericPropertyImpl<?>) constructor
                  .newInstance(new Object[] { name, type, bean, ValidatorNone.getInstance() });
            }
          }
        }
      }
      return null;
    } catch (InstantiationException e) {
      throw new InstantiationFailedException(e, propertyClass);
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, propertyClass);
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, constructor);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <BEAN extends Bean> BEAN create(BEAN prototype) {

    BeanAccessBase access = (BeanAccessBase) prototype.access();
    BeanAccessPrototype<?> beanPrototype = access.getPrototype();
    BeanAccessMutable interceptor = new BeanAccessMutable(beanPrototype);
    BEAN bean = (BEAN) Proxy.newProxyInstance(this.classLoader, new Class<?>[] { beanPrototype.getBeanType() },
        interceptor);
    interceptor.setBean(bean);
    return bean;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <BEAN extends Bean> BEAN getReadOnlyBean(BEAN beanProxy) {

    BeanAccessBase access = (BeanAccessBase) beanProxy.access();
    if (access.isReadOnly()) {
      return beanProxy;
    }
    BeanAccessPrototype<?> beanPrototype = access.getPrototype();
    BeanAccessReadOnly interceptor = new BeanAccessReadOnly(beanPrototype, access);
    BEAN bean = (BEAN) Proxy.newProxyInstance(this.classLoader, new Class<?>[] { beanPrototype.getBeanType() },
        interceptor);
    interceptor.setBean(bean);
    return bean;
  }

}
