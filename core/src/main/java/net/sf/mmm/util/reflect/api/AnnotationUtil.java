/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Method;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a collection of utility functions to deal with {@link Annotation annotations}.
 *
 * @see ReflectionUtil
 * @see net.sf.mmm.util.reflect.base.AnnotationUtilImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface AnnotationUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.reflect.api.AnnotationUtil";

  /** an empty element-type array */
  ElementType[] NO_TARGET = new ElementType[0];

  /**
   * This method determines if the given {@code annotationType} represents an {@link Annotation} that has the
   * {@link java.lang.annotation.Retention} {@link java.lang.annotation.RetentionPolicy#RUNTIME runtime} and can
   * therefore be resolved at runtime.
   *
   * @param <A> is the type of the annotation to check.
   * @param annotationType is the type of the annotation to check.
   * @return {@code true} if the given {@code annotationType} can be resolved at runtime.
   */
  <A extends Annotation> boolean isRuntimeAnnotation(Class<A> annotationType);

  /**
   * This method determines if the given {@code annotationType} represents an {@link Annotation} that has a
   * {@link java.lang.annotation.Target} compatible with the given {@code targetType}. <br>
   *
   * @param <A> is the type of the annotation to check.
   * @param annotationType is the type of the annotation to check.
   * @param targetType is the expected target-type to check.
   * @return {@code true} if the given {@code annotationType} can be used to annotate elements of the given
   *         {@code targetType}.
   */
  <A extends Annotation> boolean isAnnotationForType(Class<A> annotationType, ElementType targetType);

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of the type given by {@code annotation} in
   * the class {@link Class#getSuperclass() hierarchy} of the given {@code annotatedClass}. <br>
   * <b>INFORMATION:</b><br>
   * This method is only useful if the given {@code annotation} is a {@link #isRuntimeAnnotation(Class) runtime
   * annotation} that is {@link #isAnnotationForType(Class, ElementType) applicable} for {@link ElementType#TYPE
   * classes}. If the {@code annotation} is {@link java.lang.annotation.Inherited inherited} you may want to directly
   * use {@link Class#getAnnotation(Class)} instead.
   *
   * @see #getTypeAnnotation(Class, Class)
   *
   * @param <A> is the type of the requested annotation.
   * @param annotatedClass is the class potentially annotated with the given {@code annotation}. This should NOT be an
   *        {@link Class#isInterface() interface}, {@link Class#isPrimitive() primitive}, {@link Class#isArray() array},
   *        {@link Class#isEnum() enum}, or {@link Class#isAnnotation() annotation}.
   * @param annotationClass is the type of the requested annotation.
   * @return the requested annotation or {@code null} if neither the {@code annotatedClass} nor one of its
   *         {@link Class#getSuperclass() super-classes} are {@link Class#getAnnotation(Class) annotated} with the given
   *         {@code annotation}.
   * @throws IllegalArgumentException if the given annotation is no {@link #isRuntimeAnnotation(Class) runtime
   *         annotation} or is NOT {@link #isAnnotationForType(Class, ElementType) applicable} for
   *         {@link ElementType#TYPE classes}.
   */
  <A extends Annotation> A getClassAnnotation(Class<?> annotatedClass, Class<A> annotationClass)
      throws IllegalArgumentException;

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of the type given by {@code annotation} in
   * the declaration of the given {@code annotatedType}. <br>
   * Instead of {@link #getClassAnnotation(Class, Class)} this method will also scan implemented interfaces for the
   * given {@code annotation}. <br>
   * This method is only useful if the given {@code annotation} is a {@link java.lang.annotation.RetentionPolicy#RUNTIME
   * runtime} annotation.
   *
   * @param <A> is the type of the requested annotation.
   * @param annotatedType is the class or interface potentially annotated with the given {@code annotation}. This should
   *        NOT be an {@link Class#isPrimitive() primitive}, {@link Class#isArray() array} , {@link Class#isEnum() enum}
   *        , or {@link Class#isAnnotation() annotation}.
   * @param annotationClass is the type of the requested annotation.
   * @return the requested annotation or {@code null} if neither the {@code annotatedType} nor one of its
   *         {@link Class#getSuperclass() super-classes}, or any implemented {@link Class#getInterfaces() interfaces}
   *         (no matter if implemented directly or indirectly) are {@link Class#getAnnotation(Class) annotated} with the
   *         given {@code annotation}.
   */
  <A extends Annotation> A getTypeAnnotation(Class<?> annotatedType, Class<A> annotationClass);

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of the type given by {@code annotation} in
   * the {@link ReflectionUtil#getParentMethod(Method) hierarchy} of the given {@link Method method}. <br>
   * This method is only useful if the given {@code annotation} is a {@link java.lang.annotation.RetentionPolicy#RUNTIME
   * runtime} annotation.
   *
   * @param <A> is the type of the requested annotation.
   * @param annotatedMethod is the method potentially annotated with the given {@code annotation}.
   * @param annotationClass is the type of the requested annotation.
   * @return the requested annotation or {@code null} if neither the {@code annotatedMethod} nor one of its
   *         {@link ReflectionUtil#getParentMethod(Method) parent methods} are {@link Method#getAnnotation(Class)
   *         annotated} with the given {@code annotation}.
   */
  <A extends Annotation> A getMethodAnnotation(Method annotatedMethod, Class<A> annotationClass);

}
