/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import net.sf.mmm.util.cli.api.CliConstraintContainer;
import net.sf.mmm.util.cli.api.CliConstraintFile;
import net.sf.mmm.util.cli.api.CliConstraintIllegalException;
import net.sf.mmm.util.cli.api.CliConstraintNumber;
import net.sf.mmm.util.file.api.FileAlreadyExistsException;
import net.sf.mmm.util.file.api.FileNotExistsException;
import net.sf.mmm.util.file.api.FileType;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.api.AnnotationUtil;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the container for constraints of a {@link CliParameterContainer
 * CLI-parameter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliConstraint {

  /** The {@link CliParameterContainer}. */
  private final CliParameterContainer parameterContainer;

  /** @see #getConstraintContainer() */
  private final CliConstraintContainer constraintContainer;

  /** @see #getConstraintValue() */
  private final Annotation constraintValue;

  /** @see #validate(Object) */
  private final CollectionReflectionUtil collectionReflectionUtil;

  /**
   * The {@link GenericType#getComponentType() component-type} or
   * <code>null</code> if no container.
   */
  private final Class<?> componentType;

  /**
   * The constructor.
   * 
   * @param parameterContainer is the {@link CliParameterContainer} for the
   *        CLI-parameter potentially annotated with constraints.
   * @param reflectionUtil is the {@link ReflectionUtil} to use.
   * @param collectionReflectionUtil is the {@link CollectionReflectionUtil}
   *        instance to use.
   * @param annotationUtil is the {@link AnnotationUtil} instance to use.
   */
  public CliConstraint(CliParameterContainer parameterContainer, ReflectionUtil reflectionUtil,
      CollectionReflectionUtil collectionReflectionUtil, AnnotationUtil annotationUtil) {

    super();
    this.parameterContainer = parameterContainer;
    this.collectionReflectionUtil = collectionReflectionUtil;
    PojoPropertyAccessorOneArg setter = parameterContainer.getSetter();
    AccessibleObject accessible = setter.getAccessibleObject();
    Method method = null;
    if (accessible instanceof Method) {
      method = (Method) accessible;
    }
    Class<?> propertyClass = setter.getPropertyClass();
    GenericType<?> genericComponentType = setter.getPropertyType().getComponentType();
    Class<?> valueClass;
    if (genericComponentType == null) {
      this.componentType = null;
      valueClass = propertyClass;
    } else {
      this.componentType = genericComponentType.getAssignmentClass();
      valueClass = this.componentType;
    }
    this.constraintContainer = getAnnotation(method, accessible, annotationUtil,
        CliConstraintContainer.class);
    if (this.constraintContainer != null) {
      if (this.componentType == null) {
        // illegal type, no container
        throw new CliConstraintIllegalException(this.constraintContainer, setter);
      }
      if (this.constraintContainer.min() < 0) {
        throw new CliConstraintIllegalException(setter, this.constraintContainer);
      }
      if (this.constraintContainer.max() < this.constraintContainer.min()) {
        throw new CliConstraintIllegalException(setter, this.constraintContainer);
      }
    }
    Annotation constraintValueAnnotation = null;
    CliConstraintNumber constraintNumber = getAnnotation(method, accessible, annotationUtil,
        CliConstraintNumber.class);
    if (constraintNumber != null) {
      if (!Number.class.isAssignableFrom(reflectionUtil.getNonPrimitiveType(valueClass))) {
        throw new CliConstraintIllegalException(constraintNumber, setter);
      }
      if (constraintNumber.max() < constraintNumber.min()) {
        throw new CliConstraintIllegalException(setter, constraintNumber);
      }
      constraintValueAnnotation = constraintNumber;
    }
    CliConstraintFile constraintFile = getAnnotation(method, accessible, annotationUtil,
        CliConstraintFile.class);
    if (constraintFile != null) {
      if (!File.class.equals(valueClass)) {
        throw new CliConstraintIllegalException(constraintFile, setter);
      }
      constraintValueAnnotation = constraintFile;
    }
    this.constraintValue = constraintValueAnnotation;
  }

  /**
   * This method is a "macro" for reading an annotation from some property.
   * 
   * @param <A> is the generic type of the requested annotation.
   * @param method is the potentially annotated method or <code>null</code> if
   *        no method available.
   * @param accessible is the {@link AccessibleObject} to use as fallback if
   *        <code>method</code> is <code>null</code>.
   * @param annotationUtil is the {@link AnnotationUtil} used for
   *        {@link AnnotationUtil#getMethodAnnotation(Method, Class)}.
   * @param annotationClass is the type of the requested annotation.
   * @return the requested annotation or <code>null</code> if no such annotation
   *         exists.
   */
  private static <A extends Annotation> A getAnnotation(Method method, AccessibleObject accessible,
      AnnotationUtil annotationUtil, Class<A> annotationClass) {

    if (method == null) {
      return accessible.getAnnotation(annotationClass);
    } else {
      return annotationUtil.getMethodAnnotation(method, annotationClass);
    }
  }

  /**
   * This method gets the optional constraint-annotation that applies to an
   * actual value of the according {@link CliParameterContainer CLI-parameter}.
   * If combined with {@link #getConstraintContainer()} it applies to the
   * {@link net.sf.mmm.util.reflect.api.GenericType#getComponentType()
   * component-type} of the container.
   * 
   * @see net.sf.mmm.util.cli.api.CliConstraintNumber
   * @see net.sf.mmm.util.cli.api.CliConstraintFile
   * 
   * @return the constraintValue or <code>null</code> for no constraintValue.
   */
  public Annotation getConstraintValue() {

    return this.constraintValue;
  }

  /**
   * This method gets the {@link #getConstraintValue() constraintValue} in a
   * type-safe way.
   * 
   * @param <A> is the generic type of the requested annotation.
   * @param annotationClass is the expected type of the annotation.
   * @return the constraintValue or <code>null</code> for no constraintValue.
   */
  public <A extends Annotation> A getConstraint(Class<A> annotationClass) {

    NlsNullPointerException.checkNotNull(Class.class, annotationClass);
    if (this.constraintValue == null) {
      return null;
    }
    try {
      return annotationClass.cast(this.constraintValue);
    } catch (ClassCastException e) {
      throw new NlsClassCastException(e, this.constraintValue, annotationClass);
    }
  }

  /**
   * This method gets the optional
   * {@link net.sf.mmm.util.cli.api.CliConstraintContainer}.<br/>
   * This is separate from {@link #getConstraintValue()} as it can be combined.
   * In such case the {@link #getConstraintValue() value-constraint} applies to
   * the {@link net.sf.mmm.util.reflect.api.GenericType#getComponentType()
   * component-type} of the container.
   * 
   * @return the constraintValue or <code>null</code> for no constraintValue.
   */
  public CliConstraintContainer getConstraintContainer() {

    return this.constraintContainer;
  }

  public void validate(Object value) {

    int size = -1;
    if (this.constraintContainer != null) {
      size = this.collectionReflectionUtil.getSize(value);
      ValueOutOfRangeException.checkRange(Integer.valueOf(size),
          Integer.valueOf(this.constraintContainer.min()),
          Integer.valueOf(this.constraintContainer.max()), this.parameterContainer);
    }
    if (this.constraintValue != null) {
      if (this.componentType == null) {

      } else {

      }
    }
  }

  protected void validateValue(Object value) {

    if (this.constraintValue instanceof CliConstraintFile) {
      CliConstraintFile constraint = (CliConstraintFile) this.constraintValue;
      File file = (File) value;
      FileType fileType = FileType.getType(file);
      if (fileType != null) {
        if (!fileType.equals(constraint.type())) {
          // throw new WrongFileTypeException();
        }
      }
      if (!constraint.ignoreExists()) {
        if (constraint.exists()) {
          // if (!file.exists()) {
          if (fileType == null) {
            throw new FileNotExistsException(file);
          }
        } else {
          // if (file.exists()) {
          if (fileType != null) {
            throw new FileAlreadyExistsException(file);
          }
        }
      }
    } else if (this.constraintValue instanceof CliConstraintNumber) {
      CliConstraintNumber constraint = (CliConstraintNumber) this.constraintValue;
      Number number = (Number) value;
      ValueOutOfRangeException.checkRange(number, Double.valueOf(constraint.min()),
          Double.valueOf(constraint.max()), this.parameterContainer);
    }
  }
}
