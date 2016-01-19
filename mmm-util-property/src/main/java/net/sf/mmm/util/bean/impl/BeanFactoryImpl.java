/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Named;

import javafx.beans.property.Property;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.base.AbstractGenericProperty;
import net.sf.mmm.util.property.impl.BooleanProperty;
import net.sf.mmm.util.property.impl.GenericProperty;
import net.sf.mmm.util.property.impl.IntegerProperty;
import net.sf.mmm.util.property.impl.LongProperty;
import net.sf.mmm.util.property.impl.StringProperty;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ValidatorNone;

/**
 * This is the implementation of {@link BeanFactory}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class BeanFactoryImpl extends AbstractLoggableComponent implements BeanFactory {

  /** @see #getInstance() */
  private static BeanFactory instance;

  private static final Type PROPERTY_VALUE_TYPE_VARIABLE = Property.class.getTypeParameters()[0];

  private final ClassLoader classLoader;

  private final ReentrantLock lock;

  private ReflectionUtil reflectionUtil;

  private Map<Class<? extends Bean>, WeakReference<BeanAccessPrototype<?>>> class2prototypeMap;

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
    this.lock = new ReentrantLock();
    this.class2prototypeMap = new WeakHashMap<>();
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
  <BEAN extends Bean> BEAN createProxy(BeanAccessBase<BEAN> access, Class<BEAN> beanType) {

    BEAN bean = (BEAN) Proxy.newProxyInstance(this.classLoader, new Class<?>[] { beanType }, access);
    return bean;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <BEAN extends Bean> BEAN create(BEAN bean) {

    BeanAccessBase<BEAN> access = (BeanAccessBase<BEAN>) bean.access();
    BeanAccessPrototype<BEAN> beanPrototype = access.getPrototype();
    BeanAccessMutable<BEAN> interceptor = new BeanAccessMutable<>(access.getBeanClass(), this, beanPrototype);
    return interceptor.getBean();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <BEAN extends Bean> BEAN getReadOnlyBean(BEAN bean) {

    BeanAccessBase<BEAN> access = (BeanAccessBase<BEAN>) bean.access();
    if (access.isReadOnly()) {
      return bean;
    }
    BeanAccessPrototype<BEAN> beanPrototype = access.getPrototype();
    BeanAccessReadOnly<BEAN> interceptor = new BeanAccessReadOnly<>(this, beanPrototype, access);
    return interceptor.getBean();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <BEAN extends Bean> BEAN getPrototype(BEAN bean) {

    BeanAccessBase<BEAN> access = (BeanAccessBase<BEAN>) bean.access();
    BeanAccessPrototype<BEAN> beanPrototype = access.getPrototype();
    return beanPrototype.getBean();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <BEAN extends Bean> BEAN copy(BEAN bean) {

    BEAN copy = create(bean);
    BeanAccess access = copy.access();
    for (WritableProperty<?> property : bean.access().getProperties()) {
      WritableProperty copyProperty = access.getRequiredProperty(property.getName());
      copyProperty.setValue(property.getValue());
    }
    return copy;
  }

  @Override
  public <BEAN extends Bean> BEAN createPrototype(Class<BEAN> type, boolean dynamic) {

    BeanAccessPrototype<BEAN> prototype = getPrototypeInternal(type);
    BeanAccessPrototype<BEAN> copy = new BeanAccessPrototype<>(prototype, dynamic);
    return copy.getBean();
  }

  /**
   * Gets the initial internal and not-{@link BeanAccessPrototype#isDynamic() dynamic} {@link BeanAccessPrototype}.
   * Using a {@link WeakHashMap} as cache to avoid memory leaking and a {@link ReentrantLock} to be thread-safe.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the {@link BeanAccessPrototype} instance of the specified {@link Bean}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <BEAN extends Bean> BeanAccessPrototype<BEAN> getPrototypeInternal(final Class<BEAN> type) {

    this.lock.lock();
    try {
      WeakReference<BeanAccessPrototype<?>> prototypeReference = this.class2prototypeMap.computeIfAbsent(type,
          x -> new WeakReference<>(createPrototypeInternal(type)));
      BeanAccessPrototype<BEAN> prototype = (BeanAccessPrototype) prototypeReference.get();
      if (prototype == null) {
        prototype = createPrototypeInternal(type);
        prototypeReference = new WeakReference<>(prototype);
        this.class2prototypeMap.put(type, prototypeReference);
      }
      return prototype;
    } finally {
      this.lock.unlock();
    }
  }

  /**
   * Creates the initial internal and not-{@link BeanAccessPrototype#isDynamic() dynamic} {@link BeanAccessPrototype}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the {@link BeanAccessPrototype} instance of the specified {@link Bean}.
   */
  protected <BEAN extends Bean> BeanAccessPrototype<BEAN> createPrototypeInternal(Class<BEAN> type) {

    BeanAccessPrototype<BEAN> prototype = new BeanAccessPrototype<>(type, this);
    GenericType<BEAN> beanType = this.reflectionUtil.createGenericType(type);
    Collection<BeanMethod> methods = new ArrayList<>();
    collectMethods(type, prototype, beanType, methods);
    processMethods(methods, prototype, beanType);
    return prototype;
  }

  /**
   * Recursively introspect, create and collect the {@link BeanMethod}s for a {@link Bean} {@link Class}. Also creates
   * the {@link AbstractGenericProperty properties} for the {@link BeanMethodType#PROPERTY property} methods.
   *
   * @param type is the current {@link Bean} {@link Class} to introspect.
   * @param prototype the {@link BeanAccessPrototype}.
   * @param beanType the {@link GenericType} of the initial {@link Bean}.
   * @param methods the {@link Collection} where to {@link Collection#add(Object) add} the {@link BeanMethod}.
   */
  protected void collectMethods(Class<?> type, BeanAccessPrototype<?> prototype, GenericType<?> beanType,
      Collection<BeanMethod> methods) {

    for (Method method : type.getDeclaredMethods()) {
      BeanMethod beanMethod = new BeanMethod(method);
      BeanMethodType methodType = beanMethod.getMethodType();
      if (methodType == null) {
        if (!Modifier.isStatic(method.getModifiers())) {
          getLogger().warn("Unmapped bean method {}", method);
        }
      } else {
        methods.add(beanMethod);
        if (methodType == BeanMethodType.PROPERTY) {
          AbstractGenericProperty<?> property = createProperty(beanMethod, beanType, prototype.getBean());
          prototype.addProperty(property);
        }
      }
    }
    if (type == Bean.class) {
      for (Method method : Object.class.getDeclaredMethods()) {
        BeanMethod beanMethod = new BeanMethod(method);
        BeanMethodType methodType = beanMethod.getMethodType();
        if (methodType != null) {
          methods.add(beanMethod);
        }

      }
    } else {
      for (Class<?> superInterface : type.getInterfaces()) {
        collectMethods(superInterface, prototype, beanType, methods);
      }
    }
  }

  /**
   * @param methods the {@link Collection} with all the
   *        {@link #collectMethods(Class, BeanAccessPrototype, GenericType, Bean, Collection) collected}
   *        {@link BeanMethod}s.
   * @param prototype the {@link BeanAccessPrototype} to process and complete.
   * @param beanType the {@link GenericType} reflecting the {@link Bean}.
   */
  private void processMethods(Collection<BeanMethod> methods, BeanAccessPrototype<?> prototype,
      GenericType<?> beanType) {

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
          AbstractGenericProperty<?> property = createProperty(propertyName, propertyType, prototype.getBean());
          prototype.addProperty(property);
        }
      }
      BeanPrototypeOperation operation = BeanPrototypeOperation.create(beanMethod, prototype);
      method2OperationMap.put(beanMethod.getMethod(), operation);
    }
  }

  /**
   * @param beanMethod the {@link BeanMethod}.
   * @param beanType the {@link GenericType} reflecting the {@link WritableProperty#getBean() bean owning the property}
   *        to create.
   * @param bean the {@link Bean} instance to create this property for.
   * @return the new property instance.
   */
  private AbstractGenericProperty<?> createProperty(BeanMethod beanMethod, GenericType<?> beanType, Bean bean) {

    Method method = beanMethod.getMethod();
    AbstractGenericProperty<?> property = null;
    if (method.isDefault()) {
      property = (AbstractGenericProperty<?>) LookupHelper.INSTANCE.invokeDefaultMethod(bean, method,
          ReflectionUtilLimited.NO_ARGUMENTS);
      property = property.copy(beanMethod.getPropertyName(), bean);
    }
    if (property == null) {
      GenericType<?> propertyType = this.reflectionUtil.createGenericType(beanMethod.getPropertyType(), beanType);
      GenericType<?> valueType = this.reflectionUtil.createGenericType(PROPERTY_VALUE_TYPE_VARIABLE, propertyType);
      property = createProperty(beanMethod.getPropertyName(), valueType, bean, propertyType.getRetrievalClass());
    }
    return property;
  }

  /**
   * @param <V> the generic type of {@code type}.
   * @param name the {@link WritableProperty#getName() property name}.
   * @param type the {@link WritableProperty#getType() property type}.
   * @param bean the {@link WritableProperty#getBean() property bean}.
   * @return the new property instance.
   */
  protected <V> AbstractGenericProperty<V> createProperty(String name, GenericType<V> type, Bean bean) {

    return createProperty(name, type, bean, WritableProperty.class);
  }

  /**
   * @param <V> the generic property type.
   * @param name the {@link WritableProperty#getName() property name}.
   * @param type the {@link WritableProperty#getType() property type}.
   * @param bean the {@link WritableProperty#getBean() property bean}.
   * @param propertyClass the {@link Class} reflecting the {@link WritableProperty} or <code>null</code> if no property
   *        method exists and this method is called for plain getter or setter.
   * @return the new instance of {@link AbstractGenericProperty}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <V> AbstractGenericProperty<V> createProperty(String name, GenericType<V> type, Bean bean,
      Class<?> propertyClass) {

    AbstractGenericProperty result;
    if ((!propertyClass.isInterface()) && (!Modifier.isAbstract(propertyClass.getModifiers()))) {
      result = createPropertyFromSpecifiedClass(name, type, bean, propertyClass);
    } else {
      Class<?> valueClass = type.getRetrievalClass();
      if (valueClass == String.class) {
        result = new StringProperty(name, bean);
      } else if (valueClass == Boolean.class) {
        result = new BooleanProperty(name, bean);
      } else if (valueClass == Integer.class) {
        result = new IntegerProperty(name, bean);
      } else if (valueClass == Long.class) {
        result = new LongProperty(name, bean);
      } else {
        result = new GenericProperty<>(name, type, bean);
      }
    }
    return result;
  }

  private AbstractGenericProperty<?> createPropertyFromSpecifiedClass(String name, GenericType<?> type, Bean bean,
      Class<?> propertyClass) {

    Constructor<?> constructor = null;
    try {
      for (Constructor<?> c : propertyClass.getConstructors()) {
        constructor = c;
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if ((parameterTypes.length >= 2) && (parameterTypes.length <= 4) && (parameterTypes[0] == String.class)) {
          if (parameterTypes[1] == Bean.class) {
            if (parameterTypes.length == 2) {
              return (AbstractGenericProperty<?>) constructor.newInstance(new Object[] { name, bean });
            } else if ((parameterTypes.length == 3) && (parameterTypes[2] == AbstractValidator.class)) {
              return (AbstractGenericProperty<?>) constructor
                  .newInstance(new Object[] { name, bean, ValidatorNone.getInstance() });
            }
          } else if ((parameterTypes[1] == GenericType.class) && (parameterTypes.length >= 3)
              && (parameterTypes[2] == Bean.class)) {
            if (parameterTypes.length == 3) {
              return (AbstractGenericProperty<?>) constructor.newInstance(new Object[] { name, type, bean });
            } else if (parameterTypes[3] == AbstractValidator.class) {
              return (AbstractGenericProperty<?>) constructor
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

}
