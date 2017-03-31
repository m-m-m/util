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
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Named;

import javafx.beans.property.Property;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.BeanPrototypeBuilder;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.json.api.JsonUtil;
import net.sf.mmm.util.json.base.JsonUtilImpl;
import net.sf.mmm.util.lang.api.Builder;
import net.sf.mmm.util.property.api.AbstractProperty;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.factory.PropertyFactoryManager;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.property.impl.factory.PropertyFactoryManagerImpl;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;
import net.sf.mmm.util.reflect.api.NamedSignature;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorNone;
import net.sf.mmm.util.validation.base.jsr303.BeanValidationProcessor;
import net.sf.mmm.util.validation.base.jsr303.BeanValidationProcessorImpl;

/**
 * This is the implementation of {@link BeanFactory}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class BeanFactoryImpl extends AbstractLoggableComponent implements BeanFactory {

  private static BeanFactory instance;

  private static final Type PROPERTY_VALUE_TYPE_VARIABLE = Property.class.getTypeParameters()[0];

  private final ClassLoader classLoader;

  private final MemoryCache<Class<? extends Bean>, BeanAccessPrototypeInternal<?>> cache;

  private ReflectionUtil reflectionUtil;

  private JsonUtil jsonUtil;

  private PropertyFactoryManager propertyFactoryManager;

  private BeanValidationProcessor beanValidationProcessor;

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
    this.cache = new MemoryCache<>();
  }

  /**
   * @return the {@link PropertyFactoryManager} to use.
   */
  protected PropertyFactoryManager getPropertyFactoryManager() {

    return this.propertyFactoryManager;
  }

  /**
   * @param propertyFactoryManager the {@link PropertyFactoryManager} to {@link Inject}.
   */
  @Inject
  public void setPropertyFactoryManager(PropertyFactoryManager propertyFactoryManager) {

    getInitializationState().requireNotInitilized();
    this.propertyFactoryManager = propertyFactoryManager;
  }

  /**
   * @return the {@link ReflectionUtil} to use.
   */
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * @return the {@link JsonUtil} instance to use.
   */
  protected JsonUtil getJsonUtil() {

    return this.jsonUtil;
  }

  /**
   * @param jsonUtil is the {@link JsonUtil} to {@link Inject}.
   */
  @Inject
  public void setJsonUtil(JsonUtil jsonUtil) {

    this.jsonUtil = jsonUtil;
  }

  /**
   * @param beanValidationProcessor the {@link BeanValidationProcessor} to {@link Inject}.
   */
  @Inject
  public void setBeanValidationProcessor(BeanValidationProcessor beanValidationProcessor) {

    this.beanValidationProcessor = beanValidationProcessor;
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
    if (this.propertyFactoryManager == null) {
      this.propertyFactoryManager = PropertyFactoryManagerImpl.getInstance();
    }
    if (this.jsonUtil == null) {
      this.jsonUtil = JsonUtilImpl.getInstance();
    }
    if (this.beanValidationProcessor == null) {
      BeanValidationProcessorImpl impl = new BeanValidationProcessorImpl();
      impl.initialize();
      this.beanValidationProcessor = impl;
    }
    super.doInitialize();
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    }
  }

  @SuppressWarnings("unchecked")
  <BEAN extends Bean> BEAN createProxy(BeanAccessBase<BEAN> access, Class<?>... interfaces) {

    BEAN bean = (BEAN) Proxy.newProxyInstance(this.classLoader, interfaces, access);
    return bean;
  }

  @Override
  public BeanPrototypeBuilder createPrototypeBuilder(boolean dynamic) {

    return new BeanPrototypeBuilderImpl(this, dynamic);
  }

  @Override
  public <BEAN extends Bean> BEAN create(BEAN bean) {

    BeanAccessPrototype<BEAN> beanPrototype = BeanAccessPrototype.get(bean);
    BeanAccessMutable<BEAN> interceptor = new BeanAccessMutable<>(this, beanPrototype);
    return interceptor.getBean();
  }

  @Override
  public <BEAN extends Bean> BEAN getReadOnlyBean(BEAN bean) {

    BeanAccessBase<BEAN> access = BeanAccessBase.get(bean);
    if (access.isReadOnly()) {
      return bean;
    }
    BeanAccessPrototype<BEAN> beanPrototype = access.getPrototype();
    BeanAccessReadOnly<BEAN> interceptor = new BeanAccessReadOnly<>(this, beanPrototype, access);
    return interceptor.getBean();
  }

  @Override
  public <BEAN extends Bean> BEAN getPrototype(BEAN bean) {

    BeanAccessPrototype<BEAN> beanPrototype = BeanAccessPrototype.get(bean);
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
  public <BEAN extends Bean> BEAN createPrototype(Class<BEAN> type) {

    BeanAccessPrototypeInternal<BEAN> prototype = getPrototypeInternal(type);
    BeanAccessPrototypeExternal<BEAN> copy = new BeanAccessPrototypeExternal<>(prototype, false, getQualifiedName(type));
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
  protected <BEAN extends Bean> BeanAccessPrototypeInternal<BEAN> getPrototypeInternal(final Class<BEAN> type) {

    BeanAccessPrototypeInternal prototype = this.cache.get(type, () -> createPrototypeInternal(type));
    return prototype;
  }

  /**
   * Creates the initial internal and not-{@link BeanAccessPrototype#isDynamic() dynamic} {@link BeanAccessPrototype}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the {@link BeanAccessPrototype} instance of the specified {@link Bean}.
   */
  protected <BEAN extends Bean> BeanAccessPrototypeInternal<BEAN> createPrototypeInternal(Class<BEAN> type) {

    String qualifiedName = getQualifiedName(type);
    BeanAccessPrototypeInternal<BEAN> prototype = new BeanAccessPrototypeInternal<>(type, qualifiedName, this);
    GenericType<BEAN> beanType = this.reflectionUtil.createGenericType(type);
    BeanIntrospectionData introspectionData = new BeanIntrospectionData(beanType, prototype);
    collectMethods(type, introspectionData);
    processMethods(introspectionData, prototype, beanType);
    return prototype;
  }

  /**
   * Recursively introspect, create and collect the {@link BeanMethod}s for a {@link Bean} {@link Class}. Also creates
   * the {@link AbstractProperty properties} for the {@link BeanMethodType#PROPERTY property} methods.
   *
   * @param type is the current {@link Bean} {@link Class} to introspect.
   * @param introspectionData the {@link BeanIntrospectionData}.
   */
  protected void collectMethods(Class<?> type, BeanIntrospectionData introspectionData) {

    if (!introspectionData.visitType(type)) {
      return;
    }
    for (Method method : type.getDeclaredMethods()) {
      introspectionData.addMethod(method, type);
    }
    if (type == Bean.class) {
      for (Method method : Object.class.getDeclaredMethods()) {
        introspectionData.addMethod(method, Object.class);
      }
    } else {
      for (Class<?> superInterface : type.getInterfaces()) {
        collectMethods(superInterface, introspectionData);
      }
    }
  }

  /**
   * @param introspectionData the {@link BeanIntrospectionData}.
   * @param prototype the {@link BeanAccessPrototype} to process and complete.
   * @param beanType the {@link GenericType} reflecting the {@link Bean}.
   */
  private void processMethods(BeanIntrospectionData introspectionData, BeanAccessPrototype<?> prototype, GenericType<?> beanType) {

    for (BeanMethod beanMethod : introspectionData.methods) {
      BeanMethodType methodType = beanMethod.getMethodType();
      if ((methodType == BeanMethodType.GET) || (methodType == BeanMethodType.SET)) {
        String propertyName = beanMethod.getPropertyName();
        BeanPrototypeProperty prototypeProperty = prototype.getPrototypeProperty(propertyName);
        if (prototypeProperty == null) {
          GenericType<?> propertyType = this.reflectionUtil.createGenericType(beanMethod.getPropertyType(), beanType);
          AbstractProperty<?> property = createProperty(propertyName, propertyType, prototype.getBean());
          property = decorateProperty(beanMethod.getMethod(), property, propertyType);
          prototype.addProperty(property, introspectionData.isDeclared(beanMethod.getMethod()));
        } else if (methodType == BeanMethodType.GET) {
          AbstractProperty<?> property = prototypeProperty.getProperty();
          AbstractProperty<?> decoratedProperty = decorateProperty(beanMethod.getMethod(), property, property.getType());
          if (decoratedProperty != property) {
            prototypeProperty.setProperty(decoratedProperty);
          }
        }
      }

      BeanPrototypeOperation operation;
      if ((methodType == BeanMethodType.EQUALS) && (introspectionData.customEquals != null)) {
        operation = new BeanPrototypeOperationCustomEquals(prototype, beanMethod.getMethod(), introspectionData.customEquals.getMethod(), this.reflectionUtil);
      } else if ((methodType == BeanMethodType.HASH_CODE) && (introspectionData.customHashCode != null)) {
        operation = new BeanPrototypeOperationDefaultMethod(prototype, beanMethod.getMethod(), introspectionData.customHashCode.getMethod());
      } else {
        operation = BeanPrototypeOperation.create(beanMethod, prototype);
      }
      prototype.registerOperation(operation);

      if ((methodType == BeanMethodType.GET) || (methodType == BeanMethodType.PROPERTY)) {
        Named alias = beanMethod.getMethod().getAnnotation(Named.class);
        if (alias != null) {
          prototype.registerAlias(alias.value(), beanMethod.getPropertyName());
        }
      }
    }
  }

  /**
   * @param beanMethod the {@link BeanMethod}.
   * @param beanType the {@link GenericType} reflecting the {@link WritableProperty#getBean() bean owning the property}
   *        to create.
   * @param bean the {@link Bean} instance to create this property for.
   * @return the new property instance.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private AbstractProperty<?> createProperty(BeanMethod beanMethod, GenericType<?> beanType, Bean bean) {

    Method method = beanMethod.getMethod();
    AbstractProperty<?> property = null;
    if (method.isDefault()) {
      property = (AbstractProperty<?>) LookupHelper.INSTANCE.invokeDefaultMethod(bean, method, ReflectionUtilLimited.NO_ARGUMENTS);
      property = property.copy(beanMethod.getPropertyName(), bean);
    }
    if (property == null) {
      GenericType<?> propertyType = this.reflectionUtil.createGenericType(beanMethod.getPropertyType(), beanType);
      GenericType<?> valueType = this.reflectionUtil.createGenericType(PROPERTY_VALUE_TYPE_VARIABLE, propertyType);
      property = createProperty(beanMethod.getPropertyName(), valueType, bean, (Class) propertyType.getRetrievalClass());
      property = decorateProperty(method, property, valueType);
    }
    return property;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private AbstractProperty<?> decorateProperty(Method method, AbstractProperty<?> property, GenericType<?> valueType) {

    ObjectValidatorBuilder<?, Builder<? extends AbstractProperty<?>>, ?> valdidatorBuilder = (ObjectValidatorBuilder) property.withValdidator();
    this.beanValidationProcessor.processConstraints(method, valdidatorBuilder, valueType);
    return valdidatorBuilder.and().build();
  }

  /**
   * @param <V> the generic type of {@code type}.
   * @param name the {@link WritableProperty#getName() property name}.
   * @param type the {@link WritableProperty#getType() property type}.
   * @param bean the {@link WritableProperty#getBean() property bean}.
   * @return the new property instance.
   */
  @SuppressWarnings("unchecked")
  protected <V> AbstractProperty<V> createProperty(String name, GenericType<V> type, Bean bean) {

    return createProperty(name, type, bean, WritableProperty.class);
  }

  /**
   * @param <V> the generic property type.
   * @param <PROPERTY> the generic type of the {@link ReadableProperty property}.
   * @param name the {@link WritableProperty#getName() property name}.
   * @param valueType the {@link WritableProperty#getType() property type}.
   * @param bean the {@link WritableProperty#getBean() property bean}.
   * @param propertyClass the {@link Class} reflecting the {@link WritableProperty} or {@code null} if no property
   *        method exists and this method is called for plain getter or setter.
   * @return the new instance of {@link AbstractProperty}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <V, PROPERTY extends ReadableProperty<V>> AbstractProperty<V> createProperty(String name, GenericType<? extends V> valueType, Bean bean,
      Class<PROPERTY> propertyClass) {

    AbstractProperty result;
    Class<? extends V> valueClass = null;
    if (valueType != null) {
      valueClass = valueType.getRetrievalClass();
    }
    PropertyFactory<V, ?> factory = this.propertyFactoryManager.getFactory(propertyClass, valueClass, false);
    if (factory != null) {
      result = (AbstractProperty) factory.create(name, valueType, bean, null);
    } else if ((propertyClass != null) && (!propertyClass.isInterface()) && (!Modifier.isAbstract(propertyClass.getModifiers()))) {
      result = createPropertyFromSpecifiedClass(name, valueType, bean, propertyClass);
    } else {
      getLogger().debug("Could not resolve specific property for class '{}' and value-type '{}'. Using GenericProperty as fallback.", propertyClass, valueType);
      result = new GenericProperty<>(name, valueType, bean);
    }
    return result;
  }

  private AbstractProperty<?> createPropertyFromSpecifiedClass(String name, GenericType<?> type, Bean bean, Class<?> propertyClass) {

    Constructor<?> constructor = null;
    try {
      for (Constructor<?> c : propertyClass.getConstructors()) {
        constructor = c;
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if ((parameterTypes.length >= 2) && (parameterTypes.length <= 4) && (parameterTypes[0] == String.class)) {
          if (parameterTypes[1] == Object.class) {
            if (parameterTypes.length == 2) {
              return (AbstractProperty<?>) constructor.newInstance(new Object[] { name, bean });
            } else if ((parameterTypes.length == 3) && (parameterTypes[2] == AbstractValidator.class)) {
              return (AbstractProperty<?>) constructor.newInstance(new Object[] { name, bean, ValidatorNone.getInstance() });
            }
          } else if ((parameterTypes[1] == GenericType.class) && (parameterTypes.length >= 3) && (parameterTypes[2] == Object.class)) {
            if (type == null) {
              throw new IllegalStateException(constructor.toGenericString());
            }
            if (parameterTypes.length == 3) {
              return (AbstractProperty<?>) constructor.newInstance(new Object[] { name, type, bean });
            } else if (parameterTypes[3] == AbstractValidator.class) {
              return (AbstractProperty<?>) constructor.newInstance(new Object[] { name, type, bean, ValidatorNone.getInstance() });
            }
          }
        }
      }
      throw new IllegalStateException(propertyClass.getName());
    } catch (InstantiationException e) {
      throw new InstantiationFailedException(e, propertyClass);
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, propertyClass);
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, constructor);
    }
  }

  /**
   * A container for the methods of a {@link Bean} and related introspection data.
   */
  protected class BeanIntrospectionData {

    private final GenericType<?> beanType;

    private final String beanTypeName;

    private final BeanAccessPrototype<?> prototype;

    private final Collection<BeanMethod> methods;

    private final Set<Class<?>> typesVisited;

    private final Set<NamedSignature> signatures;

    private BeanMethod customHashCode;

    private BeanMethod customEquals;

    /**
     * The constructor.
     *
     * @param beanType the {@link GenericType} of the {@link Bean} to introspect.
     * @param prototype the corresponding {@link BeanAccessPrototype}.
     */
    public BeanIntrospectionData(GenericType<?> beanType, BeanAccessPrototype<?> prototype) {
      super();
      this.beanType = beanType;
      this.prototype = prototype;
      this.methods = new ArrayList<>();
      this.typesVisited = new HashSet<>();
      this.signatures = new HashSet<>();
      this.beanTypeName = this.beanType.getAssignmentClass().getSimpleName();
    }

    /**
     * @param type the {@link Class} reflecting the {@link Bean} or one of its parent types to visit.
     * @return {@code true} if the given {@link Class} should be visited (introspected), {@code false} if it has already
     *         been visited.
     */
    public boolean visitType(Class<?> type) {

      boolean added = this.typesVisited.add(type);
      if (added) {
        getLogger().trace("{}: Introspecting type {}", this.beanTypeName, type);
      } else {
        getLogger().trace("{}: Already visited type {}", this.beanTypeName, type);
      }
      return added;
    }

    /**
     * @param method the {@link Method} to check.
     * @return {@code true} if the given {@link Method} is {@link Method#getDeclaringClass() declared} by the
     *         {@link Class} of this {@link BeanIntrospectionData}.
     */
    public boolean isDeclared(Method method) {

      return (method.getDeclaringClass() == this.beanType.getRetrievalClass());
    }

    /**
     * Analyze and add the given {@link Method}.
     *
     * @param method the {@link Method} to analyze and add if relevant.
     * @param type the {@link Class} (or mainly interface) declaring the {@link Method}.
     */
    public void addMethod(Method method, Class<?> type) {

      BeanMethod beanMethod = new BeanMethod(method);
      BeanMethodType methodType = beanMethod.getMethodType();
      if (methodType == null) {
        if ((type != Object.class) && !Modifier.isStatic(method.getModifiers())) {
          NamedSignature signature = new NamedSignature(method);
          if (!this.signatures.contains(signature)) {
            getLogger().warn("{}: Unmapped bean method {}", this.beanTypeName, method);
          }
        }
      } else {
        NamedSignature signature = new NamedSignature(method);
        boolean added = this.signatures.add(signature);
        if (added) {
          getLogger().trace("{}: Added signature {} for method {}", this.beanTypeName, signature, method);
        } else {
          getLogger().debug("{}: Duplicate signature {} ignoring method {}", this.beanTypeName, signature, method);
          return;
        }
        this.methods.add(beanMethod);
        if (methodType == BeanMethodType.PROPERTY) {
          AbstractProperty<?> property = createProperty(beanMethod, this.beanType, this.prototype.getBean());
          this.prototype.addProperty(property, isDeclared(method));
        } else if (methodType == BeanMethodType.CUSTOM_EQUALS) {
          this.customEquals = beanMethod;
        } else if (methodType == BeanMethodType.CUSTOM_HASH_CODE) {
          this.customHashCode = beanMethod;
        }
      }
    }

  }

}
