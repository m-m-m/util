/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.HashMapFactory;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.NoPojoFieldIntrospector;
import net.sf.mmm.util.pojo.descriptor.base.PojoFieldIntrospector;
import net.sf.mmm.util.pojo.descriptor.base.PojoMethodIntrospector;
import net.sf.mmm.util.pojo.descriptor.base.accessor.PojoPropertyAccessorBuilder;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the generic implementation of the {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder}
 * interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoDescriptorBuilderImpl extends AbstractPojoDescriptorBuilder {

  /** @see #getDependencies() */
  private ExtendedPojoDescriptorDependenciesImpl configuration;

  /** @see #getMethodIntrospector() */
  private PojoMethodIntrospector methodIntrospector;

  /** @see #getFieldIntrospector() */
  private PojoFieldIntrospector fieldIntrospector;

  /**
   * The constructor. By default it only introspects {@link Method methods} that are public and NOT static. <br>
   * <b>ATTENTION:</b><br>
   * You need to {@link #initialize()} this component before it can be used.
   */
  public PojoDescriptorBuilderImpl() {

    this(HashMapFactory.INSTANCE);
  }

  /**
   * The constructor. By default it only introspects {@link Method methods} that are public and NOT static. <br>
   * <b>ATTENTION:</b><br>
   * You need to {@link #initialize()} this component before it can be used.
   *
   * @param mapFactory is the factory used to create the descriptor cache.
   */
  @SuppressWarnings("rawtypes")
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
      this.fieldIntrospector = new NoPojoFieldIntrospector();
    }
    if (this.configuration == null) {
      this.configuration = new ExtendedPojoDescriptorDependenciesImpl();
      this.configuration.initialize();
    }
  }

  /**
   * This method gets the introspector used to find potential {@link Method methods} for {@link PojoPropertyAccessor
   * accessing} {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties} of a
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
   *
   * @return the introspector to use.
   */
  protected PojoMethodIntrospector getMethodIntrospector() {

    return this.methodIntrospector;
  }

  /**
   * This method sets the {@link #getMethodIntrospector() method-introspector}.
   *
   * @param introspector the introspector to set.
   */
  @Inject
  public void setMethodIntrospector(PojoMethodIntrospector introspector) {

    getInitializationState().requireNotInitilized();
    this.methodIntrospector = introspector;
  }

  /**
   * This method gets the introspector used to find potential {@link Field fields} for {@link PojoPropertyAccessor
   * accessing} {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties} of a
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
   *
   * @return the introspector to use.
   */
  protected PojoFieldIntrospector getFieldIntrospector() {

    return this.fieldIntrospector;
  }

  /**
   * This method sets the {@link #getFieldIntrospector() field-introspector}.
   *
   * @param introspector the introspector to set.
   */
  public void setFieldIntrospector(PojoFieldIntrospector introspector) {

    getInitializationState().requireNotInitilized();
    this.fieldIntrospector = introspector;
  }

  /**
   * This method gets the accessor-builders used to create the {@link PojoPropertyAccessor accessors} for
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor properties} of a
   * {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor POJO}.
   *
   * @return the accessorBuilders.
   */
  protected Collection<PojoPropertyAccessorBuilder<?>> getAccessorBuilders() {

    return this.configuration.getAccessorBuilders();
  }

  /**
   * This method gets the {@link ExtendedPojoDescriptorDependenciesImpl}.
   *
   * @return the {@link ExtendedPojoDescriptorDependenciesImpl}.
   */
  protected ExtendedPojoDescriptorDependenciesImpl getDependencies() {

    return this.configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ReflectionUtilLimited getReflectionUtil() {

    return this.configuration.getReflectionUtil();
  }

  /**
   * This method sets the {@link ExtendedPojoDescriptorDependenciesImpl}.
   *
   * @param configuration is the {@link ExtendedPojoDescriptorDependenciesImpl} .
   */
  @Inject
  public void setConfiguration(ExtendedPojoDescriptorDependenciesImpl configuration) {

    getInitializationState().requireNotInitilized();
    this.configuration = configuration;
  }

  /**
   * This method registers the given <code>accessor</code> for the given <code>descriptor</code>.
   *
   * @param descriptor is the {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor}.
   * @param accessor is the {@link PojoPropertyAccessor} to register.
   * @return <code>true</code> if the given <code>accessor</code> has been registered or <code>false</code> if it has
   *         been ignored (it is a duplicate).
   */
  protected boolean registerAccessor(PojoDescriptorImpl<?> descriptor, PojoPropertyAccessor accessor) {

    PojoPropertyDescriptorImpl propertyDescriptor = descriptor.getOrCreatePropertyDescriptor(accessor.getName());
    boolean added = false;
    PojoPropertyAccessor existing = propertyDescriptor.getAccessor(accessor.getMode());
    if (existing == null) {
      propertyDescriptor.putAccessor(accessor);
      added = true;
    } else {
      // Workaround for JVM-Bug with overridden methods
      if (existing.getReturnType().isAssignableFrom(accessor.getReturnType())) {
        AccessibleObject accessorAccessible = accessor.getAccessibleObject();
        if (accessorAccessible instanceof Method) {
          propertyDescriptor.putAccessor(accessor);
          added = true;
        }
      }
      // this will also happen for private fields with the same name and is
      // then a regular log message...
      if (added) {
        logDuplicateAccessor(accessor, existing);
      } else {
        logDuplicateAccessor(existing, accessor);
      }
    }
    return added;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected <P> PojoDescriptorImpl<P> createDescriptor(GenericType<P> pojoType) {

    getInitializationState().requireInitilized();
    Class<P> pojoClass = pojoType.getRetrievalClass();
    if (pojoClass.isArray()) {
      throw new NlsIllegalArgumentException(pojoClass.getName());
    }

    PojoDescriptorImpl<P> descriptor = new PojoDescriptorImpl<>(pojoType, this);

    List<AccessibleObject> nonPublicAccessibleObjects = new ArrayList<>();
    // process methods...
    introspectMethods(pojoClass, descriptor, nonPublicAccessibleObjects);
    // process fields...
    introspectFields(pojoClass, descriptor, nonPublicAccessibleObjects);

    // make non-public fields/methods accessible for us...
    makeAccessible(nonPublicAccessibleObjects);

    this.configuration.getDescriptorEnhancer().enhanceDescriptor(descriptor);

    mergeDescriptorWithSuperClass(pojoClass, descriptor);

    return descriptor;
  }

  /**
   * Makes all {@link AccessibleObject}s in the given {@link List} {@link AccessibleObject#setAccessible(boolean)
   * accessible}.
   *
   * @param nonPublicAccessibleObjects is the {@link List} where the non-public {@link AccessibleObject}s have been
   *        collected.
   */
  private void makeAccessible(List<AccessibleObject> nonPublicAccessibleObjects) {

    if (nonPublicAccessibleObjects.size() > 0) {
      final AccessibleObject[] nonPublicAccessibles = new AccessibleObject[nonPublicAccessibleObjects.size()];
      nonPublicAccessibleObjects.toArray(nonPublicAccessibles);
      // enable reflective access that violates visibility - this will
      // fail if disallowed by security-manager.
      AccessController.doPrivileged(new PrivilegedAction<Object>() {

        @Override
        public Object run() {

          AccessibleObject.setAccessible(nonPublicAccessibles, true);
          return null;
        }
      });
    }
  }

  /**
   * Introspects the {@link Field}s of the given {@link Class} and adds them to the given {@link PojoDescriptorImpl}.
   *
   * @param <P> is the generic type of <code>pojoClass</code>.
   * @param pojoClass is the {@link Class} for the {@link PojoDescriptorImpl#getPojoType() pojo type}.
   * @param descriptor is the {@link PojoDescriptorImpl} where to add {@link PojoPropertyAccessor}s for detected
   *        properties.
   * @param nonPublicAccessibleObjects is the {@link List} where to add non-public {@link AccessibleObject}s.
   */
  private <P> void introspectFields(Class<P> pojoClass, PojoDescriptorImpl<P> descriptor,
      List<AccessibleObject> nonPublicAccessibleObjects) {

    if (getFieldIntrospector() != null) {
      Iterator<Field> fieldIterator = getFieldIntrospector().findFields(pojoClass);
      while (fieldIterator.hasNext()) {
        Field field = fieldIterator.next();
        boolean fieldUsed = false;
        for (PojoPropertyAccessorBuilder<?> builder : getAccessorBuilders()) {
          PojoPropertyAccessor accessor = builder.create(field, descriptor, getDependencies());
          if (accessor != null) {
            boolean registered = registerAccessor(descriptor, accessor);
            if (registered) {
              fieldUsed = true;
            }
          }
        }
        if (fieldUsed && !isPublicAccessible(field)) {
          // reflective access that violates visibility
          nonPublicAccessibleObjects.add(field);
        }
        PojoPropertyDescriptorImpl propertyDescriptor = descriptor.getPropertyDescriptor(field.getName());
        if (propertyDescriptor != null) { // should actually never be null here...
          propertyDescriptor.setField(field);
        }
      }
    }
  }

  /**
   * Introspects the {@link Method}s of the given {@link Class} and adds them to the given {@link PojoDescriptorImpl}.
   *
   * @param <P> is the generic type of <code>pojoClass</code>.
   * @param pojoClass is the {@link Class} for the {@link PojoDescriptorImpl#getPojoType() pojo type}.
   * @param descriptor is the {@link PojoDescriptorImpl} where to add {@link PojoPropertyAccessor}s for detected
   *        properties.
   * @param nonPublicAccessibleObjects is the {@link List} where to add non-public {@link AccessibleObject}s.
   */
  private <P> void introspectMethods(Class<P> pojoClass, PojoDescriptorImpl<P> descriptor,
      List<AccessibleObject> nonPublicAccessibleObjects) {

    if (getMethodIntrospector() != null) {
      Iterator<Method> methodIterator = getMethodIntrospector().findMethods(pojoClass);
      while (methodIterator.hasNext()) {
        Method method = methodIterator.next();
        boolean methodUsed = false;
        for (PojoPropertyAccessorBuilder<?> builder : getAccessorBuilders()) {
          PojoPropertyAccessor accessor = builder.create(method, descriptor, getDependencies());
          if (accessor != null) {
            boolean registered = registerAccessor(descriptor, accessor);
            if (registered) {
              methodUsed = true;
            }
          }
        }
        if (methodUsed && !isPublicAccessible(method)) {
          // reflective access that violates visibility
          nonPublicAccessibleObjects.add(method);
        }
      }
    }
  }

  /**
   * This method creates the {@link PojoDescriptorImpl} for the {@link Class#getSuperclass() superclass} of
   * <code>pojoClass</code> and merges {@link PojoPropertyDescriptorImpl} from inherited properties.
   *
   * @param <P> is the generic type of <code>pojoClass</code>.
   * @param pojoClass is the {@link Class} for the {@link PojoDescriptorImpl#getPojoType() pojo type}.
   * @param descriptor is the {@link PojoDescriptorImpl} to merge.
   */
  private <P> void mergeDescriptorWithSuperClass(Class<P> pojoClass, PojoDescriptorImpl<P> descriptor) {

    // create descriptor for super-class to reuse information (#55)
    Class<? super P> superClass = pojoClass.getSuperclass();
    PojoDescriptorImpl<?> superDescriptor = null;
    if (superClass != null) {
      GenericType<?> superType = getReflectionUtil().createGenericType(superClass, descriptor.getPojoType());
      superDescriptor = createDescriptor(superType);
      for (PojoPropertyDescriptorImpl superPropertyDescriptor : superDescriptor.getPropertyDescriptors()) {
        PojoPropertyDescriptorImpl propertyDescriptor = descriptor
            .getPropertyDescriptor(superPropertyDescriptor.getName());
        if (propertyDescriptor == null) {
          descriptor.addPropertyDescriptor(superPropertyDescriptor);
        } else {
          mergePropertyDescriptorWithSuperClass(propertyDescriptor, superPropertyDescriptor);
        }
      }
    }
  }

  /**
   * This method merges the <code>superPropertyDescriptor</code> into the <code>propertyDescriptor</code>.
   *
   * @param propertyDescriptor is the {@link PojoPropertyDescriptorImpl} to build for the
   *        {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param superPropertyDescriptor is the super {@link PojoPropertyDescriptorImpl} to "inherit" from.
   */
  private void mergePropertyDescriptorWithSuperClass(PojoPropertyDescriptorImpl propertyDescriptor,
      PojoPropertyDescriptorImpl superPropertyDescriptor) {

    for (PojoPropertyAccessor superPropertyAccessor : superPropertyDescriptor.getAccessors()) {

      PojoPropertyAccessor propertyAccessor = propertyDescriptor.getAccessor(superPropertyAccessor.getMode());
      if (propertyAccessor == null) {
        propertyDescriptor.putAccessor(superPropertyAccessor);
      }
    }
    Field field = superPropertyDescriptor.getField();
    if ((field != null) && (propertyDescriptor.getField() == null)) {
      propertyDescriptor.setField(field);
    }
  }

  /**
   * This method determines if the given {@link Member} is {@link AccessibleObject#isAccessible() accessible} or
   * <code>public</code> (declared as public in a public class).
   *
   * @param member is the {@link Member} to check.
   * @return <code>true</code> if it can be accessed, <code>false</code> if it needs to be made accessible.
   */
  private boolean isPublicAccessible(Member member) {

    if (((AccessibleObject) member).isAccessible()) {
      return true;
    }
    if (!Modifier.isPublic(member.getModifiers())) {
      return false;
    }
    if (Modifier.isPublic(member.getDeclaringClass().getModifiers())) {
      return true;
    }
    return false;
  }

  /**
   * This method is called if the {@link PojoPropertyAccessor accessor} given by <code>duplicate</code> was ignored
   * because it has the same {@link PojoPropertyAccessor#getName() name} and {@link PojoPropertyAccessor#getMode() mode}
   * as the given <code>accessor</code> that is already registered. This method does nothing. It may be overridden to do
   * some debug logging.
   *
   * @param accessor is the accessor that is already registered.
   * @param duplicate is the duplicate that has been ignored.
   */
  protected void logDuplicateAccessor(PojoPropertyAccessor accessor, PojoPropertyAccessor duplicate) {

    getLogger().debug("accessor '{}' - is a duplicate of '{}'!", duplicate, accessor);
  }

}
