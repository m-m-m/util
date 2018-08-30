/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

/**
 * A {@link AnnotationNotForTargetException} is thrown if an {@link java.lang.annotation.Annotation} should be
 * resolved for a specific {@link ElementType} that it is NOT {@link java.lang.annotation.Target targeted}
 * for.
 *
 * @see AnnotationUtil#isRuntimeAnnotation(Class)
 * @see AnnotationUtil#getTypeAnnotation(Class, Class)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class AnnotationNotForTargetException extends ReflectionException {

  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "AnnotationNotForTarget";

  /**
   * The constructor.
   *
   * @param annotation is the {@link Class} reflecting the {@link Annotation}.
   * @param target is the {@link ElementType} not {@link java.lang.annotation.Target targeted} by the given
   *        {@code annotation}.
   */
  public AnnotationNotForTargetException(Class<? extends Annotation> annotation, ElementType target) {

    super("The given annotation '" + annotation + "' can NOT annotate the target '" + target + "'!", null);
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
