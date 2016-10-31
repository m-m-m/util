/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.BeanPrototypeBuilder;
import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.exception.api.DuplicateObjectException;

/**
 * Implementation of {@link BeanPrototypeBuilder}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class BeanPrototypeBuilderImpl extends AbstractLoggableObject implements BeanPrototypeBuilder {

  private final BeanFactoryImpl beanFactory;

  private final ReentrantLock lock;

  private final boolean dynamic;

  private final Map<String, Bean> name2prototypeMap;

  /**
   * The constructor.
   *
   * @param beanFactory - see {@link #getBeanFactory()}.
   * @param dynamic - see {@link #isDynamic()}.
   */
  public BeanPrototypeBuilderImpl(BeanFactoryImpl beanFactory, boolean dynamic) {
    super();
    this.beanFactory = beanFactory;
    this.lock = new ReentrantLock();
    this.dynamic = dynamic;
    this.name2prototypeMap = new ConcurrentHashMap<>();
  }

  /**
   * @return the {@link BeanFactory} instance.
   */
  protected BeanFactory getBeanFactory() {

    return this.beanFactory;
  }

  @Override
  public boolean isDynamic() {

    return this.dynamic;
  }

  /**
   * @return the {@link ReentrantLock} for synchronization of concurrent access.
   */
  ReentrantLock getLock() {

    return this.lock;
  }

  @Override
  public Bean getPrototype(String qualifiedName) {

    return this.name2prototypeMap.get(qualifiedName);
  }

  @Override
  public <BEAN extends Bean> BEAN getOrCreatePrototype(Class<BEAN> type) {

    String qualifiedName = this.beanFactory.getQualifiedName(type);
    Bean bean = this.name2prototypeMap.get(qualifiedName);
    if (bean != null) {
      return type.cast(bean);
    }
    this.lock.lock();
    try {
      return doGetOrCreatePrototype(type, qualifiedName);
    } finally {
      this.lock.unlock();
    }
  }

  private <BEAN extends Bean> BEAN doGetOrCreatePrototype(Class<BEAN> type) {

    return doGetOrCreatePrototype(type, this.beanFactory.getQualifiedName(type));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private <BEAN extends Bean> BEAN doGetOrCreatePrototype(Class<BEAN> type, String qualifiedName) {

    Bean prototypeBean = this.name2prototypeMap.computeIfAbsent(qualifiedName, k -> doCreatePrototype(type));
    BeanAccessPrototype<BEAN> prototype = ((BeanAccessBase<BEAN>) prototypeBean.access()).getPrototype();
    for (Class<?> superInterface : type.getInterfaces()) {
      if ((Bean.class.isAssignableFrom(superInterface)) && (superInterface != Bean.class)) {
        Bean superBean = doGetOrCreatePrototype((Class) superInterface);
        BeanAccessBase<BEAN> access = (BeanAccessBase<BEAN>) superBean.access();
        BeanAccessPrototypePolymorphic<BEAN> superPrototype = (BeanAccessPrototypePolymorphic<BEAN>) access.getPrototype();
        superPrototype.addChild(prototype);
      }
    }
    return type.cast(prototypeBean);
  }

  private <BEAN extends Bean> BEAN doCreatePrototype(Class<BEAN> type) {

    getLogger().debug("Creating bean prototype for {}.", type);
    BeanAccessPrototype<BEAN> prototypeInteranl = this.beanFactory.getPrototypeInternal(type);
    BeanAccessPrototypePolymorphic<BEAN> prototypeExternal = new BeanAccessPrototypePolymorphic<>(this, prototypeInteranl, this.dynamic,
        prototypeInteranl.getQualifiedName());
    return prototypeExternal.getBean();
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <BEAN extends Bean> BEAN createPrototype(BEAN superPrototypeBean, String name, Bean... superBeanPrototypes) {

    Class<? extends Bean> type = superPrototypeBean.access().getBeanClass();
    Set<Class<?>> interfaceSet = new HashSet<>(superBeanPrototypes.length + 20);
    interfaceSet.add(type);
    // List<>
    for (int i = 0; i < superBeanPrototypes.length; i++) {
      Bean prototypeBean = superBeanPrototypes[i];
      BeanAccessPrototype<?> prototype = BeanAccessPrototype.get(prototypeBean);
      Class<?> beanClass = prototype.getBeanClass();
      if (beanClass.isAssignableFrom(type) && !prototype.isVirtual()) {
        getLogger().warn("Redundant super-type {} for {}.", beanClass, type);
        superBeanPrototypes[i] = null;
      } else {
        for (Class<?> beanInterface : prototype.getInterfaces()) {
          interfaceSet.add(beanInterface);
        }
      }
    }
    BeanAccessPrototypePolymorphic<BEAN> superPrototype = BeanAccessPrototypePolymorphic.get(superPrototypeBean);
    String qualifiedName = this.beanFactory.getQualifiedName(type, name);
    Class<?>[] interfaces = interfaceSet.toArray(new Class<?>[interfaceSet.size()]);
    BeanAccessPrototypeVirtual<BEAN> prototype = new BeanAccessPrototypeVirtual<>(this, superPrototype, this.dynamic, qualifiedName, interfaces);
    BEAN bean = prototype.getBean();

    this.lock.lock();
    try {
      Bean duplicate = this.name2prototypeMap.putIfAbsent(qualifiedName, bean);
      if (duplicate != null) {
        throw new DuplicateObjectException(bean, qualifiedName, duplicate);
      }
      superPrototype.addChild(prototype);
      for (Bean prototypeBean : superBeanPrototypes) {
        if (prototypeBean != null) {
          BeanAccessPrototypePolymorphic<?> superProto = BeanAccessPrototypePolymorphic.get(prototypeBean);
          superProto.addChild((BeanAccessPrototype) prototype);
        }
      }
    } finally {
      this.lock.unlock();
    }

    return bean;
  }

  @Override
  public <BEAN extends Bean> BEAN getPrototype(BEAN bean) {

    return this.beanFactory.getPrototype(bean);
  }

  @Override
  public <BEAN extends Bean> BEAN create(BEAN prototype) {

    return this.beanFactory.create(prototype);
  }

  @Override
  public <BEAN extends Bean> BEAN getReadOnlyBean(BEAN bean) {

    return this.beanFactory.getReadOnlyBean(bean);
  }

  @Override
  public <BEAN extends Bean> BEAN copy(BEAN bean) {

    return this.beanFactory.copy(bean);
  }

}
