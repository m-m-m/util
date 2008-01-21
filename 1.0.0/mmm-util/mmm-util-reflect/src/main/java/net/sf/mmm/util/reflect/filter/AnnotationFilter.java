/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.filter;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.filter.Filter;
import net.sf.mmm.util.reflect.AnnotationUtil;

/**
 * This is a filter that only {@link #accept(Class) accepts} {@link Class types}
 * that are {@link Class#isAnnotationPresent(Class) annotated} with an
 * annotation given at {@link #AnnotationFilter(Class) construction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AnnotationFilter implements Filter<Class<?>> {

  /** The required annotation. */
  private final Class<? extends Annotation> annotationType;

  /** */
  private final boolean forceInheritence;

  /**
   * The constructor.
   * 
   * @param annotationType is the annotation that has to be
   *        {@link Class#isAnnotationPresent(Class) present} if a type should be
   *        {@link #accept(Class) accepted}.
   * @throws IllegalArgumentException if the given <code>annotationType</code>
   *         is NO
   *         {@link AnnotationUtil#isRuntimeAnnotation(Class) runtime annotation}.
   */
  public AnnotationFilter(Class<? extends Annotation> annotationType)
      throws IllegalArgumentException {

    this(annotationType, false);
  }

  /**
   * The constructor.
   * 
   * @param annotationType is the annotation that has to be
   *        {@link Class#isAnnotationPresent(Class) present} if a type should be
   *        {@link #accept(Class) accepted}.
   * @param forceInheritence if <code>true</code> then also types are accepted
   *        if one of their super-types (including implemented interfaces) is
   *        properly annotated (see
   *        {@link AnnotationUtil#getTypeAnnotation(Class, Class)}).
   * @throws IllegalArgumentException if the given <code>annotationType</code>
   *         is NO
   *         {@link AnnotationUtil#isRuntimeAnnotation(Class) runtime annotation}.
   */
  public AnnotationFilter(Class<? extends Annotation> annotationType, boolean forceInheritence)
      throws IllegalArgumentException {

    super();
    if (!AnnotationUtil.INSTANCE.isRuntimeAnnotation(annotationType)) {
      throw new IllegalArgumentException("Given annotation (" + annotationType
          + ") can NOT be resolved at runtime!");
    }
    this.annotationType = annotationType;
    this.forceInheritence = forceInheritence;
  }

  /**
   * {@inheritDoc}
   * 
   * @return <code>true</code> if the specified
   *         {@link Class#isAnnotationPresent(Class) annotation is present} for
   *         the given <code>type</code>, <code>false</code> otherwise.
   */
  public boolean accept(Class<?> type) {

    if (type != null) {
      if (this.forceInheritence) {
        Annotation annotation = AnnotationUtil.INSTANCE
            .getTypeAnnotation(type, this.annotationType);
        return (annotation != null);
      } else {
        return type.isAnnotationPresent(this.annotationType);
      }

    }
    return false;
  }

}
