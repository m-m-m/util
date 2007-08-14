/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * This class is a collection of utility functions for dealing with
 * {@link Annotation annotations}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class AnnotationUtil {

  /** an empty element-type array */
  public static final ElementType[] NO_TARGET = new ElementType[0];

  /**
   * The forbidden constructor.
   */
  private AnnotationUtil() {

  }

  /**
   * This method determines if the given <code>annotationType</code>
   * represents an {@link Annotation} that has the {@link Retention}
   * {@link RetentionPolicy#RUNTIME "runtime"} and can therefore be resolved at
   * runtime.
   * 
   * @param <A> is the type of the annotation to check.
   * @param annotationType is the type of the annotation to check.
   * @return <code>true</code> if the given <code>annotationType</code> can
   *         be resolved at runtime.
   */
  public static <A extends Annotation> boolean isRuntimeAnnotation(Class<A> annotationType) {

    Retention retention = annotationType.getAnnotation(Retention.class);
    if (retention != null) {
      return (retention.value() == RetentionPolicy.RUNTIME);
    }
    return false;
  }

  /**
   * This method determines if the given <code>annotationType</code>
   * represents an {@link Annotation} that has a {@link Target} compatible with
   * the given <code>targetType</code>.<br>
   * 
   * @param <A> is the type of the annotation to check.
   * @param annotationType is the type of the annotation to check.
   * @param targetType is the expected target-type to check.
   * @return <code>true</code> if the given <code>annotationType</code> can
   *         be used to annotate elements of the given <code>targetType</code>.
   */
  public static <A extends Annotation> boolean isAnnotationForType(Class<A> annotationType,
      ElementType targetType) {

    Target target = annotationType.getAnnotation(Target.class);
    if (target != null) {
      ElementType[] types = target.value();
      for (int i = 0; i < types.length; i++) {
        if (types[i] == targetType) {
          return true;
        }
      }
      return false;
    }
    return true;
  }

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of
   * the type given by <code>annotation</code> in the class
   * {@link Class#getSuperclass() hierarchy} of the given
   * <code>annotatedClass</code>.<br>
   * <b>INFORMATION:</b><br>
   * This method is only useful if the given <code>annotation</code> is a
   * {@link #isRuntimeAnnotation(Class) runtime annotation} that is
   * {@link #isAnnotationForType(Class, ElementType) applicable} for
   * {@link ElementType#TYPE classes}. If the <code>annotation</code> is
   * {@link java.lang.annotation.Inherited inherited} you may want to directly
   * use {@link Class#getAnnotation(Class)} instead.
   * 
   * @see #getTypeAnnotation(Class, Class)
   * 
   * @param <A> is the type of the requested annotation.
   * @param annotatedClass is the class potentially annotated with the given
   *        <code>annotation</code>. This should NOT be an
   *        {@link Class#isInterface() interface},
   *        {@link Class#isPrimitive() primitive},
   *        {@link Class#isArray() array}, {@link Class#isEnum() enum}, or
   *        {@link Class#isAnnotation() annotation}.
   * @param annotation is the type of the requested annotation.
   * @return the requested annotation or <code>null</code> if neither the
   *         <code>annotatedClass</code> nor one of its
   *         {@link Class#getSuperclass() super-classes} are
   *         {@link Class#getAnnotation(Class) annotated} with the given
   *         <code>annotation</code>.
   * @throws IllegalArgumentException if the given annotation is no
   *         {@link #isRuntimeAnnotation(Class) runtime annotation} or is NOT
   *         {@link #isAnnotationForType(Class, ElementType) applicable} for
   *         {@link ElementType#TYPE classes}.
   */
  public static <A extends Annotation> A getClassAnnotation(Class<?> annotatedClass,
      Class<A> annotation) throws IllegalArgumentException {

    if (!isRuntimeAnnotation(annotation)) {
      throw new IllegalArgumentException("Given annotation (" + annotation
          + ") can NOT be resolved at runtime!");
    }
    if (!isAnnotationForType(annotation, ElementType.TYPE)) {
      throw new IllegalArgumentException("Given annotation (" + annotation
          + ") can NOT annotate classes!");
    }
    A result = annotatedClass.getAnnotation(annotation);
    while (result == null) {
      annotatedClass = annotatedClass.getSuperclass();
      if (annotatedClass == null) {
        return null;
      } else {
        result = annotatedClass.getAnnotation(annotation);
      }
    }
    return result;
  }

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of
   * the type given by <code>annotation</code> in the
   * {@link Class#getInterfaces() hierarchy} of the given
   * <code>annotatedInterface</code>.<br>
   * This method is only useful if the given <code>annotation</code> is a
   * {@link #isRuntimeAnnotation(Class) runtime annotation} that is
   * {@link #isAnnotationForType(Class, ElementType) applicable} for
   * {@link ElementType#TYPE classes}.
   * 
   * @param <A> is the type of the requested annotation.
   * @param annotatedType is the type potentially implementing an interface
   *        annotated with the given <code>annotation</code>. This should NOT
   *        be an {@link Class#isPrimitive() primitive},
   *        {@link Class#isArray() array}, {@link Class#isEnum() enum}, or
   *        {@link Class#isAnnotation() annotation}.
   * @param annotation is the type of the requested annotation.
   * @return the requested annotation or <code>null</code> if neither the
   *         <code>annotatedInterface</code> nor one of its
   *         {@link Class#getInterfaces() super-interfaces} are
   *         {@link Class#getAnnotation(Class) annotated} with the given
   *         <code>annotation</code>.
   */
  private static <A extends Annotation> A getInterfacesAnnotation(Class<?> annotatedType,
      Class<A> annotation) {

    Class<?>[] interfaces = annotatedType.getInterfaces();
    for (int i = 0; i < interfaces.length; i++) {
      A result = interfaces[i].getAnnotation(annotation);
      if (result != null) {
        return result;
      }
    }
    for (int i = 0; i < interfaces.length; i++) {
      A result = getInterfacesAnnotation(interfaces[i], annotation);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of
   * the type given by <code>annotation</code> in the declaration of the given
   * <code>annotatedType</code>.<br>
   * This method is only useful if the given <code>annotation</code> is a
   * {@link RetentionPolicy#RUNTIME runtime}.
   * 
   * @param <A> is the type of the requested annotation.
   * @param annotatedType is the class or interface potentially annotated with
   *        the given <code>annotation</code>. This should NOT be an
   *        {@link Class#isPrimitive() primitive},
   *        {@link Class#isArray() array}, {@link Class#isEnum() enum}, or
   *        {@link Class#isAnnotation() annotation}.
   * @param annotation is the type of the requested annotation.
   * @return the requested annotation or <code>null</code> if neither the
   *         <code>annotatedType</code> nor one of its
   *         {@link Class#getSuperclass() super-classes}, or any implemented
   *         {@link Class#getInterfaces() interfaces} (no matter if implemented
   *         directly or indirectly) are
   *         {@link Class#getAnnotation(Class) annotated} with the given
   *         <code>annotation</code>.
   */
  public static <A extends Annotation> A getTypeAnnotation(Class<?> annotatedType,
      Class<A> annotation) {

    A result = getClassAnnotation(annotatedType, annotation);
    while (result == null) {
      result = getInterfacesAnnotation(annotatedType, annotation);
      annotatedType = annotatedType.getSuperclass();
      if (annotatedType == null) {
        break;
      }
    }
    return result;
  }

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of
   * the type given by <code>annotation</code> in the
   * {@link ReflectionUtil#getParentMethod(Method) hierarchy} of the given
   * {@link Method method}.<br>
   * This method is only useful if the given <code>annotation</code> is a
   * {@link RetentionPolicy#RUNTIME runtime} annotation.
   * 
   * @param <A> is the type of the requested annotation.
   * @param annotatedMethod is the method potentially annotated with the given
   *        <code>annotation</code>.
   * @param annotation is the type of the requested annotation.
   * @return the requested annotation or <code>null</code> if neither the
   *         <code>annotatedMethod</code> nor one of its
   *         {@link ReflectionUtil#getParentMethod(Method) parent methods} are
   *         {@link Method#getAnnotation(Class) annotated} with the given
   *         <code>annotation</code>.
   */
  public static <A extends Annotation> A getMethodAnnotation(Method annotatedMethod,
      Class<A> annotation) {

    if (!isRuntimeAnnotation(annotation)) {
      throw new IllegalArgumentException("Given annotation (" + annotation
          + ") can NOT be resolved at runtime!");
    }
    if (!isAnnotationForType(annotation, ElementType.METHOD)) {
      throw new IllegalArgumentException("Given annotation (" + annotation
          + ") can NOT annotate methods!");
    }
    A result = annotatedMethod.getAnnotation(annotation);
    if (result == null) {
      String methodName = annotatedMethod.getName();
      Class<?>[] parameterTypes = annotatedMethod.getParameterTypes();
      Class<?> inheritingClass = annotatedMethod.getDeclaringClass();
      while (result == null) {
        annotatedMethod = ReflectionUtil.getParentMethod(inheritingClass, methodName,
            parameterTypes);
        if (annotatedMethod == null) {
          return null;
        } else {
          result = annotatedMethod.getAnnotation(annotation);
          inheritingClass = annotatedMethod.getDeclaringClass();
        }
      }
    }
    return result;
  }

}
