/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.annotation.Annotation;

import net.sf.mmm.util.reflect.NlsBundleUtilReflectRoot;

/**
 * A {@link AnnotationNotRuntimeException} is thrown if an {@link java.lang.annotation.Annotation} should be resolved at
 * runtime but has NOT the {@link java.lang.annotation.Retention} {@link java.lang.annotation.RetentionPolicy#RUNTIME}.
 *
 * @see AnnotationUtil#isRuntimeAnnotation(Class)
 * @see AnnotationUtil#getTypeAnnotation(Class, Class)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class AnnotationNotRuntimeException extends ReflectionException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5375096243963460300L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "AnnotationNotRuntime";

  /**
   * The constructor.
   *
   * @param annotation is the {@link Class} reflecting the {@link Annotation}.
   */
  public AnnotationNotRuntimeException(Class<? extends Annotation> annotation) {

    super(createBundle(NlsBundleUtilReflectRoot.class).errorAnnotationNotRuntime(annotation));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
