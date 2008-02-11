/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.component.InitializationState;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;
import net.sf.mmm.util.reflect.VisibilityModifier;
import net.sf.mmm.util.reflect.pojo.api.PojoDescriptor;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorBuilder;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.pojo.base.AbstractPojoDescriptorBuilder;
import net.sf.mmm.util.reflect.pojo.base.PojoFieldIntrospector;
import net.sf.mmm.util.reflect.pojo.base.PojoMethodIntrospector;
import net.sf.mmm.util.reflect.pojo.impl.accessor.PojoPropertyAccessorAddBuilder;
import net.sf.mmm.util.reflect.pojo.impl.accessor.PojoPropertyAccessorGetBuilder;
import net.sf.mmm.util.reflect.pojo.impl.accessor.PojoPropertyAccessorSetBuilder;

/**
 * This is the generic implementation of the
 * {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptorBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(shareable = true)
public class PojoDescriptorBuilderImpl extends AbstractPojoDescriptorBuilder {

  /** @see #initialize() */
  private final InitializationState initializationState;

  /** @see #getMethodIntrospector() */
  private PojoMethodIntrospector methodIntrospector;

  /** the introspector to use. */
  private PojoFieldIntrospector fieldIntrospector;

  /** @see PojoDescriptorBuilderImpl */
  private Collection<PojoPropertyAccessorBuilder<?>> accessorBuilders;

  /** @see #getLogger() */
  private Log logger;

  /**
   * The constructor.
   */
  public PojoDescriptorBuilderImpl() {

    super();
    this.initializationState = new InitializationState();
  }

  /**
   * The constructor.
   * 
   * @param mapFactory is the factory used to create the descriptor cache.
   */
  public PojoDescriptorBuilderImpl(MapFactory mapFactory) {

    super(mapFactory);
    this.initializationState = new InitializationState();
  }

  /**
   * @return the initializationState
   */
  protected InitializationState getInitializationState() {

    return this.initializationState;
  }

  /**
   * @return the logger
   */
  protected Log getLogger() {

    return this.logger;
  }

  /**
   * @param logger the logger to set
   */
  @Resource
  public void setLogger(Log logger) {

    this.initializationState.requireNotInitilized();
    this.logger = logger;
  }

  /**
   * This method gets the introspector used to find potential
   * {@link Method methods} for {@link PojoPropertyAccessor accessing}
   * {@link PojoPropertyDescriptor properties} of a {@link PojoDescriptor POJO}.
   * 
   * @return the introspector to use.
   */
  public PojoMethodIntrospector getMethodIntrospector() {

    return this.methodIntrospector;
  }

  /**
   * This method sets the {@link #getMethodIntrospector() method-introspector}.
   * 
   * @param introspector the introspector to set.
   */
  @Resource
  public void setMethodIntrospector(PojoMethodIntrospector introspector) {

    this.initializationState.requireNotInitilized();
    this.methodIntrospector = introspector;
  }

  /**
   * This method gets the introspector used to find potential
   * {@link Field fields} for {@link PojoPropertyAccessor accessing}
   * {@link PojoPropertyDescriptor properties} of a {@link PojoDescriptor POJO}.
   * 
   * @return the introspector to use.
   */
  public PojoFieldIntrospector getFieldIntrospector() {

    return this.fieldIntrospector;
  }

  /**
   * This method sets the {@link #getFieldIntrospector() field-introspector}.
   * 
   * @param introspector the introspector to set.
   */
  @Resource
  public void setFieldIntrospector(PojoFieldIntrospector introspector) {

    this.initializationState.requireNotInitilized();
    this.fieldIntrospector = introspector;
  }

  /**
   * This method gets the accessor-builders used to create the
   * {@link PojoPropertyAccessor accessors} for
   * {@link PojoPropertyDescriptor properties} of a {@link PojoDescriptor POJO}.
   * 
   * @return the accessorBuilders.
   */
  public Collection<PojoPropertyAccessorBuilder<?>> getAccessorBuilders() {

    return this.accessorBuilders;
  }

  /**
   * This method sets the {@link #getAccessorBuilders() accessor-builders}.
   * 
   * @param accessorBuilders is a collection with the accessorBuilders to use.
   *        It must NOT contain two entries with the same
   *        {@link PojoPropertyAccessorBuilder#getMode() mode}.
   */
  @Resource
  public void setAccessorBuilders(Collection<PojoPropertyAccessorBuilder<?>> accessorBuilders) {

    this.initializationState.requireNotInitilized();
    Set<PojoPropertyAccessorMode<?>> modeSet = new HashSet<PojoPropertyAccessorMode<?>>();
    List<PojoPropertyAccessorBuilder<?>> builderList = new ArrayList<PojoPropertyAccessorBuilder<?>>();
    for (PojoPropertyAccessorBuilder<?> builder : accessorBuilders) {
      PojoPropertyAccessorMode<?> mode = builder.getMode();
      boolean duplicate = modeSet.add(mode);
      if (duplicate) {
        throw new NlsIllegalArgumentException("Duplicate accessor for mode \"{0}\"!", mode);
      }
      builderList.add(builder);
    }
    this.accessorBuilders = Collections.unmodifiableList(builderList);
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   */
  @PostConstruct
  public void initialize() {

    if (this.initializationState.setInitialized()) {
      if ((this.methodIntrospector == null) && (this.fieldIntrospector == null)) {
        // by default only introspect public and non-static methods
        this.methodIntrospector = new PojoMethodIntrospectorImpl(VisibilityModifier.PUBLIC, false);
      }
      if (this.accessorBuilders == null) {
        this.accessorBuilders = new ArrayList<PojoPropertyAccessorBuilder<?>>();
        this.accessorBuilders.add(new PojoPropertyAccessorGetBuilder());
        this.accessorBuilders.add(new PojoPropertyAccessorSetBuilder());
        this.accessorBuilders.add(new PojoPropertyAccessorAddBuilder());
        // TODO: add, size/count, remove, indexed-get, indexed-set
      }
      if (this.logger == null) {
        this.logger = LogFactory.getLog(getClass());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <P> PojoDescriptorImpl<P> createDescriptor(Class<P> pojoType) {

    PojoDescriptorImpl<P> descriptor = new PojoDescriptorImpl<P>(pojoType);
    // process methods...
    if (this.methodIntrospector != null) {
      Iterator<Method> methodIterator = this.methodIntrospector.findMethods(pojoType);
      while (methodIterator.hasNext()) {
        Method method = methodIterator.next();
        boolean methodUsed = false;
        for (PojoPropertyAccessorBuilder<?> builder : this.accessorBuilders) {
          PojoPropertyAccessor accessor = builder.create(method);
          if (accessor != null) {
            PojoPropertyDescriptorImpl propertyDescriptor = descriptor
                .getOrCreatePropertyDescriptor(accessor.getName());
            PojoPropertyAccessor existing = propertyDescriptor.getAccessor(accessor.getMode());
            if (existing == null) {
              propertyDescriptor.addAccessor(accessor);
              methodUsed = true;
            } else {
              logDuplicateAccessor(existing, accessor);
            }
          }
        }
        if (methodUsed && !Modifier.isPublic(method.getModifiers())) {
          // enable reflective access that violates visibility - this will
          // fail if disallowed by security-manager. To avoid such trouble
          // set visibility of introspector to public!
          method.setAccessible(true);
        }
      }
    }
    // process fields...
    if (this.fieldIntrospector != null) {
      Iterator<Field> fieldIterator = this.fieldIntrospector.findFields(pojoType);
      while (fieldIterator.hasNext()) {
        Field field = fieldIterator.next();
        boolean fieldUsed = false;
        for (PojoPropertyAccessorBuilder<?> builder : this.accessorBuilders) {
          PojoPropertyAccessor accessor = builder.create(field);
          if (accessor != null) {
            PojoPropertyDescriptorImpl propertyDescriptor = descriptor
                .getOrCreatePropertyDescriptor(accessor.getName());
            PojoPropertyAccessor existing = propertyDescriptor.getAccessor(accessor.getMode());
            if (existing == null) {
              propertyDescriptor.addAccessor(accessor);
              fieldUsed = true;
            } else {
              logDuplicateAccessor(existing, accessor);
            }
          }
        }
        if (fieldUsed && !Modifier.isPublic(field.getModifiers())) {
          // enable reflective access that violates visibility - this will
          // fail if disallowed by security-manager. To avoid such trouble
          // set visibility of introspector to public!
          field.setAccessible(true);
        }
      }
    }
    return descriptor;
  }

  /**
   * This method is called if the {@link PojoPropertyAccessor accessor} given by
   * <code>duplicate</code> was ignored because it has the same
   * {@link PojoPropertyAccessor#getName() name} and
   * {@link PojoPropertyAccessor#getMode() mode} as the given
   * <code>accessor</code> that is already registered. This method does
   * nothing. It may be overridden to do some debug logging.
   * 
   * @param accessor is the accessor that is already registered.
   * @param duplicate is the duplicate that has been ignored.
   */
  protected void logDuplicateAccessor(PojoPropertyAccessor accessor, PojoPropertyAccessor duplicate) {

    if (accessor.getDeclaringClass().isAssignableFrom(duplicate.getDeclaringClass())) {
      this.logger.warn("duplicate accessor '" + duplicate + "' - already have '" + accessor + "'!");
    }
  }

}
