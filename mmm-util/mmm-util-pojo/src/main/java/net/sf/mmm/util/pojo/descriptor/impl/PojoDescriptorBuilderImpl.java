/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorEnhancer;
import net.sf.mmm.util.pojo.descriptor.base.PojoFieldIntrospector;
import net.sf.mmm.util.pojo.descriptor.base.PojoMethodIntrospector;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorAddBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorGetBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorGetIndexedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorGetMappedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorRemoveBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSetBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSetIndexedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSetMappedBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.accessor.PojoPropertyAccessorSizeBuilder;
import net.sf.mmm.util.reflect.GenericType;
import net.sf.mmm.util.reflect.VisibilityModifier;

/**
 * This is the generic implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(shareable = true)
public class PojoDescriptorBuilderImpl extends AbstractPojoDescriptorBuilder {

  /** @see #getMethodIntrospector() */
  private PojoMethodIntrospector methodIntrospector;

  /** @see #getFieldIntrospector() */
  private PojoFieldIntrospector fieldIntrospector;

  /** @see #getAccessorBuilders() */
  private Collection<PojoPropertyAccessorBuilder<?>> accessorBuilders;

  /** @see #getDescriptorEnhancer() */
  private PojoDescriptorEnhancer descriptorEnhancer;

  /**
   * The constructor. By default it only introspects {@link Method methods} that
   * are public and NOT static.<br>
   * <b>ATTENTION:</b><br>
   * You need to {@link #initialize()} this component before it can be used.
   */
  public PojoDescriptorBuilderImpl() {

    this(MapFactory.INSTANCE_HASH_MAP);
  }

  /**
   * The constructor. By default it only introspects {@link Method methods} that
   * are public and NOT static.<br>
   * <b>ATTENTION:</b><br>
   * You need to {@link #initialize()} this component before it can be used.
   * 
   * @param mapFactory is the factory used to create the descriptor cache.
   */
  @SuppressWarnings("unchecked")
  public PojoDescriptorBuilderImpl(MapFactory mapFactory) {

    super(mapFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if ((this.methodIntrospector == null) && (this.fieldIntrospector == null)) {
      // by default only introspect public and non-static methods
      this.methodIntrospector = new PojoMethodIntrospectorImpl(VisibilityModifier.PUBLIC, false);
    }
    if (this.accessorBuilders == null) {
      this.accessorBuilders = new ArrayList<PojoPropertyAccessorBuilder<?>>();
      this.accessorBuilders.add(new PojoPropertyAccessorGetBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSetBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorAddBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorRemoveBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorGetIndexedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorGetMappedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSetIndexedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSetMappedBuilder());
      this.accessorBuilders.add(new PojoPropertyAccessorSizeBuilder());
      this.accessorBuilders = Collections.unmodifiableCollection(this.accessorBuilders);
    }
    if (this.descriptorEnhancer == null) {
      DefaultPojoDescriptorEnhancer enhancer = new DefaultPojoDescriptorEnhancer();
      enhancer.setConfiguration(getConfiguration());
      enhancer.initialize();
      this.descriptorEnhancer = enhancer;
    }
  }

  /**
   * This method gets the introspector used to find potential
   * {@link Method methods} for {@link PojoPropertyAccessor accessing}
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties}
   * of a {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
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

    getInitializationState().requireNotInitilized();
    this.methodIntrospector = introspector;
  }

  /**
   * This method gets the introspector used to find potential
   * {@link Field fields} for {@link PojoPropertyAccessor accessing}
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties}
   * of a {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
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

    getInitializationState().requireNotInitilized();
    this.fieldIntrospector = introspector;
  }

  /**
   * This method gets the accessor-builders used to create the
   * {@link PojoPropertyAccessor accessors} for
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties}
   * of a {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
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

    getInitializationState().requireNotInitilized();
    Set<PojoPropertyAccessorMode<?>> modeSet = new HashSet<PojoPropertyAccessorMode<?>>();
    List<PojoPropertyAccessorBuilder<?>> builderList = new ArrayList<PojoPropertyAccessorBuilder<?>>();
    for (PojoPropertyAccessorBuilder<?> builder : accessorBuilders) {
      PojoPropertyAccessorMode<?> mode = builder.getMode();
      boolean duplicate = modeSet.add(mode);
      if (duplicate) {
        // TODO: NLS
        throw new NlsIllegalArgumentException("Duplicate accessor for mode \"{0}\"!", mode);
      }
      builderList.add(builder);
    }
    this.accessorBuilders = Collections.unmodifiableCollection(builderList);
  }

  /**
   * This method gets the {@link PojoDescriptorEnhancer} to use.
   * 
   * @return the {@link PojoDescriptorEnhancer}.
   */
  public PojoDescriptorEnhancer getDescriptorEnhancer() {

    return this.descriptorEnhancer;
  }

  /**
   * This method sets the {@link #getDescriptorEnhancer() descriptor-enhancer}.
   * 
   * @param descriptorEnhancer is the {@link PojoDescriptorEnhancer} to set.
   */
  @Resource
  public void setDescriptorEnhancer(PojoDescriptorEnhancer descriptorEnhancer) {

    getInitializationState().requireNotInitilized();
    this.descriptorEnhancer = descriptorEnhancer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <P> PojoDescriptorImpl<P> createDescriptor(Class<P> pojoClass, GenericType pojoType) {

    getInitializationState().requireInitilized();
    PojoDescriptorImpl<P> descriptor = new PojoDescriptorImpl<P>(pojoClass, pojoType);
    // process methods...
    List<AccessibleObject> nonPublicAccessibleObjects = new ArrayList<AccessibleObject>();
    if (this.methodIntrospector != null) {
      Iterator<Method> methodIterator = this.methodIntrospector.findMethods(pojoClass);
      while (methodIterator.hasNext()) {
        Method method = methodIterator.next();
        boolean methodUsed = false;
        for (PojoPropertyAccessorBuilder<?> builder : this.accessorBuilders) {
          PojoPropertyAccessor accessor = builder.create(method, descriptor, getConfiguration());
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
          // reflective access that violates visibility
          nonPublicAccessibleObjects.add(method);
        }
      }
    }
    // process fields...
    if (this.fieldIntrospector != null) {
      Iterator<Field> fieldIterator = this.fieldIntrospector.findFields(pojoClass);
      while (fieldIterator.hasNext()) {
        Field field = fieldIterator.next();
        boolean fieldUsed = false;
        for (PojoPropertyAccessorBuilder<?> builder : this.accessorBuilders) {
          PojoPropertyAccessor accessor = builder.create(field, descriptor, getConfiguration());
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
          // reflective access that violates visibility
          nonPublicAccessibleObjects.add(field);
        }
      }
    }
    final AccessibleObject[] nonPublicAccessibles = new AccessibleObject[nonPublicAccessibleObjects
        .size()];
    nonPublicAccessibleObjects.toArray(nonPublicAccessibles);
    // enable reflective access that violates visibility - this will
    // fail if disallowed by security-manager.
    AccessController.doPrivileged(new PrivilegedAction<Object>() {

      public Object run() {

        AccessibleObject.setAccessible(nonPublicAccessibles, true);
        return null;
      }
    });
    getDescriptorEnhancer().enhanceDescriptor(descriptor);
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
      getLogger().warn("duplicate accessor '" + duplicate + "' - already have '" + accessor + "'!");
    }
  }

}
