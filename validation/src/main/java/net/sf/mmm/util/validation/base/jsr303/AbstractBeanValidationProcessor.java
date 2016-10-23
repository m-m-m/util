/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.base.MathUtilImpl;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.ValidatorRegistry;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessor;
import net.sf.mmm.util.validation.base.jsr303.constraints.TypedConstraintProcessor;

/**
 * The (default) implementation of {@link BeanValidationProcessor}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractBeanValidationProcessor extends AbstractLoggableComponent implements BeanValidationProcessor {

  private final Map<String, ConstraintProcessor<?>> annotation2ProcessorMap;

  private MathUtil mathUtil;

  /**
   * The constructor.
   */
  public AbstractBeanValidationProcessor() {
    super();
    this.annotation2ProcessorMap = new HashMap<>();
  }

  /**
   * @param mathUtil the {@link MathUtil} to {@link Inject}.
   */
  @Inject
  public void setMathUtil(MathUtil mathUtil) {

    this.mathUtil = mathUtil;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.mathUtil == null) {
      this.mathUtil = MathUtilImpl.getInstance();
    }
  }

  /**
   * @param annotationFqn the {@link Class#getName() qualified name} of the constraint {@link Annotation} to handle.
   * @param processor the {@link ConstraintProcessor} handling the {@link Annotation}.
   */
  protected final void registerProcessor(String annotationFqn, ConstraintProcessor<?> processor) {

    ConstraintProcessor<?> duplicate = this.annotation2ProcessorMap.put(annotationFqn, processor);
    if (duplicate != null) {
      throw new DuplicateObjectException(processor, annotationFqn, duplicate);
    }
  }

  /**
   * @param annotationType the constraint {@link Annotation} to handle.
   * @param processor the {@link ConstraintProcessor} handling the {@link Annotation}.
   */
  protected final void registerProcessor(Class<? extends Annotation> annotationType, ConstraintProcessor<?> processor) {

    registerProcessor(annotationType.getName(), processor);
  }

  /**
   * @param processor the {@link TypedConstraintProcessor}.
   */
  protected final void registerProcessor(TypedConstraintProcessor<?> processor) {

    registerProcessor(processor.getType(), processor);
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void processConstraints(AnnotatedElement annotatedElement, ValidatorRegistry<?, ?> validatorRegistry, GenericType<?> valueType) {

    ConstraintContext context = null;
    for (Annotation annotation : annotatedElement.getAnnotations()) {
      Class<? extends Annotation> annotationType = annotation.annotationType();
      ConstraintProcessor<Annotation> processor = (ConstraintProcessor) this.annotation2ProcessorMap.get(annotationType.getName());
      if (processor != null) {
        if (context == null) {
          context = new ConstraintContext(validatorRegistry, annotatedElement, valueType, this.mathUtil);
        }
        context.setConstraint(annotation);
        processor.process(annotation, context);
      }
    }
    if (context != null) {
      context.complete();
    }
  }

}
