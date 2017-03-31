/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanPrototypeBuilder;
import net.sf.mmm.util.property.api.AbstractProperty;

/**
 * This class extends {@link BeanAccessPrototypeExternal} for a polymorphic
 * {@link net.sf.mmm.util.bean.api.BeanFactory#createPrototype(Class) prototype} of a {@link Bean} that will also
 * inherit its newly created properties to its inherited sub-{@link Bean}s.
 *
 * @param <BEAN> the generic type of the {@link Bean}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class BeanAccessPrototypePolymorphic<BEAN extends Bean> extends BeanAccessPrototypeExternal<BEAN> {

  private final List<BeanAccessPrototype<? extends BEAN>> children;

  private final BeanPrototypeBuilderImpl prototypeBuilder;

  /**
   * The constructor.
   *
   * @param prototypeBuilder - see {@link #getPrototypeBuilder()}.
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param dynamic - see {@link #isDynamic()}.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   */
  public BeanAccessPrototypePolymorphic(BeanPrototypeBuilderImpl prototypeBuilder, BeanAccessPrototype<BEAN> master, boolean dynamic, String qualifiedName) {
    super(master, dynamic, qualifiedName);
    this.prototypeBuilder = prototypeBuilder;
    this.children = new ArrayList<>();
  }

  /**
   * The constructor.
   *
   * @param prototypeBuilder - see {@link #getPrototypeBuilder()}.
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param dynamic - see {@link #isDynamic()}.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   * @param interfaces - the interfaces to be implemented by the {@link #getBean() dynamic proxy}.
   */
  protected BeanAccessPrototypePolymorphic(BeanPrototypeBuilderImpl prototypeBuilder, BeanAccessPrototype<BEAN> master, boolean dynamic, String qualifiedName,
      Class<?>... interfaces) {
    super(master, dynamic, qualifiedName, interfaces);
    this.prototypeBuilder = prototypeBuilder;
    this.children = new ArrayList<>();
  }

  /**
   * @return the {@link BeanPrototypeBuilder} that created this prototype.
   */
  protected BeanPrototypeBuilderImpl getPrototypeBuilder() {

    return this.prototypeBuilder;
  }

  @Override
  protected Map<String, BeanPrototypeProperty> createPropertyMap() {

    return new ConcurrentHashMap<>();
  }

  /**
   * @param childBean is the {@link Bean}-{@link #isPrototype() Prototype} for a sub-interface of this
   *        {@link #getBeanClass() bean-interface}. It will be used for polymorphism so that
   *        {@link #createProperty(String, Class) dynamic properties} will be inherited.
   */
  void addChild(BeanAccessPrototype<? extends BEAN> childBean) {

    this.children.add(childBean);
  }

  @Override
  protected void addProperty(AbstractProperty<?> property, boolean declared) {

    ReentrantLock lock = this.prototypeBuilder.getLock();
    lock.lock();
    try {
      addPropertyInternal(property, declared);
    } finally {
      lock.unlock();
    }
  }

  @Override
  protected void addPropertyInternal(AbstractProperty<?> property, boolean declared) {

    super.addPropertyInternal(property, declared);
    for (BeanAccessPrototype<? extends BEAN> child : this.children) {
      child.addPropertyInternal(property, false);
    }
  }

  static <BEAN extends Bean> BeanAccessPrototypePolymorphic<BEAN> get(BEAN bean) {

    BeanAccessPrototype<BEAN> prototype = BeanAccessPrototype.get(bean);
    try {
      return (BeanAccessPrototypePolymorphic<BEAN>) prototype;
    } catch (ClassCastException e) {
      throw new IllegalStateException("Prototype for " + prototype.getQualifiedName() + " is not polymorphic!", e);
    }
  }

}
