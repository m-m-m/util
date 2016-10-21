/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;

/**
 * The (default) implementation of {@link BeanValidationProcessor}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanValidationProcessorImpl implements BeanValidationProcessor {

  private final Map<Class<? extends Annotation>, ConstraintProcessor> annotation2ProcessorMap;

  /**
   * The constructor.
   */
  public BeanValidationProcessorImpl() {
    super();
    this.annotation2ProcessorMap = new HashMap<>();
    registerProcessors();
  }

  /**
   * @param annotation the constraint {@link Annotation} to handle.
   * @param processor the {@link ConstraintProcessor} handling the {@link Annotation}.
   */
  protected final void registerProcessor(Class<? extends Annotation> annotation, ConstraintProcessor processor) {

    ConstraintProcessor duplicate = this.annotation2ProcessorMap.put(annotation, processor);
    if (duplicate != null) {
      throw new DuplicateObjectException(processor, annotation, duplicate);
    }
  }

  /**
   *
   */
  protected void registerProcessors() {

    /*
     * String annotationName = annotationType.getName(); if (MANDATORY_ANNOTATIONS.contains(annotationName)) {
     * valdidatorBuilder.mandatory(); } else if (Size.class == annotationType) { Size size = (Size) annotation; minimum
     * = getOrReplaceNumber(minimum, Integer.valueOf(size.min()), true, method, annotation); maximum =
     * getOrReplaceNumber(maximum, Integer.valueOf(size.max()), false, method, annotation); } else if (Min.class ==
     * annotationType) { Min min = (Min) annotation; minimum = getOrReplaceNumber(minimum, Long.valueOf(min.value()),
     * true, method, annotation); } else if (Max.class == annotationType) { Max max = (Max) annotation; maximum =
     * getOrReplaceNumber(maximum, Long.valueOf(max.value()), true, method, annotation); } else if (DecimalMax.class ==
     * annotationType) {
     * 
     * }
     */
  }

  @Override
  public void processConstraints(AnnotatedElement annotatedElement, ObjectValidatorBuilder<?, ?, ?> validatorBuilder) {

    ConstraintContext context = null;
    for (Annotation annotation : annotatedElement.getAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();
      ConstraintProcessor processor = this.annotation2ProcessorMap.get(annotationType);
      if (processor != null) {
        if (context == null) {
          context = new ConstraintContext(validatorBuilder);
        }
        processor.process(annotation, context);
      }
    }
    if (context != null) {
      context.complete();
    }
  }

}
