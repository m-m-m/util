/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import net.sf.mmm.util.collection.base.HashKey;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.pojo.api.PojoUtil;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;

/**
 * This class is a collection of utility functions for dealing with {@link java.lang.reflect reflection}.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.1.0
 */
public class PojoUtilImpl extends AbstractLoggableComponent implements PojoUtil {

  private static PojoUtil instance;

  private PojoDescriptorBuilderFactory pojoDescriptorBuilderFactory;

  private PojoDescriptorBuilder pojoDescriptorBuilder;

  /**
   * The constructor.
   */
  public PojoUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link PojoUtil}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static PojoUtil getInstance() {

    if (instance == null) {
      PojoUtilImpl impl = null;
      synchronized (PojoUtilImpl.class) {
        if (instance == null) {
          impl = new PojoUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    }
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.pojoDescriptorBuilderFactory == null) {
      this.pojoDescriptorBuilderFactory = PojoDescriptorBuilderFactoryImpl.getInstance();
    }
    if (this.pojoDescriptorBuilder == null) {
      this.pojoDescriptorBuilder = this.pojoDescriptorBuilderFactory.createPrivateFieldDescriptorBuilder();
    }
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

  @Override
  public void visitObjectRecursive(Object object, Filter<Object> visitor) {

    visitObjectRecursive(object, visitor, true);
  }

  @Override
  public void visitObjectRecursive(Object object, Filter<Object> visitor, boolean loopProtection) {

    Set<HashKey<Object>> visitedSet = null;
    if (loopProtection) {
      visitedSet = new HashSet<>();
    }
    visitObjectRecursive(object, visitor, visitedSet);
  }

  /**
   * @see #visitObjectRecursive(Object, Filter, boolean)
   *
   * @param object is the {@link Object} to traverse recursively.
   * @param visitor is the {@link Filter} {@link Filter#accept(Object) invoked} for all traversed
   *        {@link Object}s. If an {@link Object} is not {@link Filter#accept(Object) accepted} by this
   *        {@link Filter} the recursion stops at this point.
   * @param visitedSet is the {@link Set} where to collect all object to visit in order to prevent infinity
   *        loops or {@code null} to disable.
   */
  protected void visitObjectRecursive(Object object, Filter<Object> visitor, Set<HashKey<Object>> visitedSet) {

    if (object == null) {
      return;
    }
    if (visitedSet != null) {
      HashKey<Object> hashKey = new HashKey<>(object);
      boolean added = visitedSet.add(hashKey);
      if (!added) {
        // already visited same object...
        return;
      }
    }
    boolean accepted = visitor.accept(object);
    if (!accepted) {
      return;
    }
    if (object instanceof Collection) {
      Collection<?> collection = (Collection<?>) object;
      for (Object element : collection) {
        visitObjectRecursive(element, visitor, visitedSet);
      }
    } else if (object instanceof Map) {
      Map<?, ?> map = (Map<?, ?>) object;
      // ETOs should only be used as values and not as keys...
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        visitObjectRecursive(entry.getKey(), visitor, visitedSet);
        visitObjectRecursive(entry.getValue(), visitor, visitedSet);
      }
    } else if (object instanceof Object[]) {
      Object[] array = (Object[]) object;
      for (Object element : array) {
        visitObjectRecursive(element, visitor, visitedSet);
      }
    } else if (object instanceof Type) {
      // we do not traverse types (Class, ParameterizedType, TypeVariable, ...)
      return;
    } else {
      Class<?> objectClass = object.getClass();
      if (objectClass.isArray()) {
        return;
      }
      PojoDescriptor<?> descriptor = this.pojoDescriptorBuilder.getDescriptor(objectClass);
      for (PojoPropertyDescriptor propertyDescriptor : descriptor.getPropertyDescriptors()) {
        if (!"class".equals(propertyDescriptor.getName())) {
          PojoPropertyAccessorNonArg getter = propertyDescriptor.getAccessor(PojoPropertyAccessorNonArgMode.GET);
          if (getter != null) {
            Object propertyValue = getter.invoke(object);
            visitObjectRecursive(propertyValue, visitor, visitedSet);
          }
        }
      }
    }
  }
}
