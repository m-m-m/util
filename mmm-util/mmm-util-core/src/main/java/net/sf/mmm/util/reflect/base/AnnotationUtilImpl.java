/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.reflect.api.AnnotationNotForTargetException;
import net.sf.mmm.util.reflect.api.AnnotationNotRuntimeException;
import net.sf.mmm.util.reflect.api.AnnotationUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This class is a collection of utility functions for dealing with {@link Annotation annotations}.
 * 
 * @see #getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named
public class AnnotationUtilImpl implements AnnotationUtil {

  /** @see #getInstance() */
  private static AnnotationUtilImpl instance;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   */
  protected AnnotationUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link AnnotationUtilImpl}.<br>
   * This design is the best compromise between easy access (via this indirection you have direct, static
   * access to all offered functionality) and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and construct new instances via
   * the container-framework of your choice (like plexus, pico, springframework, etc.). To wire up the
   * dependent components everything is properly annotated using common-annotations (JSR-250). If your
   * container does NOT support this, you should consider using a better one.
   * 
   * @return the singleton instance.
   */
  public static AnnotationUtilImpl getInstance() {

    if (instance == null) {
      synchronized (AnnotationUtilImpl.class) {
        if (instance == null) {
          AnnotationUtilImpl util = new AnnotationUtilImpl();
          util.setReflectionUtil(ReflectionUtilImpl.getInstance());
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * This method gets the {@link ReflectionUtilImpl} used by this {@link AnnotationUtilImpl} instance.
   * 
   * @return the {@link ReflectionUtilImpl} to use.
   */
  protected ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil the reflectionUtil to set
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    if (this.reflectionUtil != null) {
      throw new AlreadyInitializedException();
    }
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  public <A extends Annotation> boolean isRuntimeAnnotation(Class<A> annotationType) {

    Retention retention = annotationType.getAnnotation(Retention.class);
    if (retention != null) {
      return (retention.value() == RetentionPolicy.RUNTIME);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public <A extends Annotation> boolean isAnnotationForType(Class<A> annotationType, ElementType targetType) {

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
   * {@inheritDoc}
   */
  public <A extends Annotation> A getClassAnnotation(Class<?> annotatedClass, Class<A> annotation)
      throws IllegalArgumentException {

    if (!isRuntimeAnnotation(annotation)) {
      throw new AnnotationNotRuntimeException(annotation);
    }
    if (!isAnnotationForType(annotation, ElementType.TYPE)) {
      throw new AnnotationNotForTargetException(annotation, ElementType.TYPE);
    }
    A result = annotatedClass.getAnnotation(annotation);
    Class<?> currentClass = annotatedClass;
    while (result == null) {
      currentClass = currentClass.getSuperclass();
      if (currentClass == null) {
        return null;
      } else {
        result = currentClass.getAnnotation(annotation);
      }
    }
    return result;
  }

  /**
   * This method gets the first {@link Class#getAnnotation(Class) annotation} of the type given by
   * <code>annotation</code> in the {@link Class#getInterfaces() hierarchy} of the given
   * <code>annotatedInterface</code>.<br>
   * This method is only useful if the given <code>annotation</code> is a {@link #isRuntimeAnnotation(Class)
   * runtime annotation} that is {@link #isAnnotationForType(Class, ElementType) applicable} for
   * {@link ElementType#TYPE classes}.
   * 
   * @param <A> is the type of the requested annotation.
   * @param annotatedType is the type potentially implementing an interface annotated with the given
   *        <code>annotation</code>. This should NOT be an {@link Class#isPrimitive() primitive},
   *        {@link Class#isArray() array}, {@link Class#isEnum() enum}, or {@link Class#isAnnotation()
   *        annotation}.
   * @param annotation is the type of the requested annotation.
   * @return the requested annotation or <code>null</code> if neither the <code>annotatedInterface</code> nor
   *         one of its {@link Class#getInterfaces() super-interfaces} are {@link Class#getAnnotation(Class)
   *         annotated} with the given <code>annotation</code>.
   */
  private <A extends Annotation> A getInterfacesAnnotation(Class<?> annotatedType, Class<A> annotation) {

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
   * {@inheritDoc}
   */
  public <A extends Annotation> A getTypeAnnotation(Class<?> annotatedType, Class<A> annotation) {

    A result = getClassAnnotation(annotatedType, annotation);
    Class<?> currentClass = annotatedType;
    while (result == null) {
      result = getInterfacesAnnotation(currentClass, annotation);
      currentClass = currentClass.getSuperclass();
      if (currentClass == null) {
        break;
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public <A extends Annotation> A getMethodAnnotation(Method annotatedMethod, Class<A> annotation) {

    if (!isRuntimeAnnotation(annotation)) {
      throw new AnnotationNotRuntimeException(annotation);
    }
    if (!isAnnotationForType(annotation, ElementType.METHOD)) {
      throw new AnnotationNotForTargetException(annotation, ElementType.METHOD);
    }
    A result = annotatedMethod.getAnnotation(annotation);
    if (result == null) {
      String methodName = annotatedMethod.getName();
      Class<?>[] parameterTypes = annotatedMethod.getParameterTypes();
      Class<?> inheritingClass = annotatedMethod.getDeclaringClass();
      while (result == null) {
        Method currentMethod = getReflectionUtil().getParentMethod(inheritingClass, methodName, parameterTypes);
        if (currentMethod == null) {
          return null;
        } else {
          result = currentMethod.getAnnotation(annotation);
          inheritingClass = currentMethod.getDeclaringClass();
        }
      }
    }
    return result;
  }

}
