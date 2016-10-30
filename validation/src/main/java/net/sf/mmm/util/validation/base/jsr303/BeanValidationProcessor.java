/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.ValidatorRegistry;

/**
 * This is the interface for a decorator that
 * {@link #processConstraints(AnnotatedElement, ValidatorRegistry, GenericType) processes} JSR303 (Bean Validation)
 * {@link Annotation}s and creates according {@link net.sf.mmm.util.validation.api.ValueValidator validators}. Please
 * note that it currently only bridges the common JSR 303 {@link Annotation}s but will not support the entire JSR303
 * standard.
 *
 * @author hohwille
 * @since 7.4.0
 */
public interface BeanValidationProcessor {

  /**
   * Processes the JSR303 (Bean Validation) {@link Annotation}s and creates according
   * {@link net.sf.mmm.util.validation.api.ValueValidator validators}.
   *
   * @param annotatedElement the {@link AnnotatedElement} to process. Typically a {@link java.lang.reflect.Method} or
   *        {@link java.lang.reflect.Field}.
   * @param validatorRegistry the {@link ValidatorRegistry} where to
   *        {@link ValidatorRegistry#add(net.sf.mmm.util.validation.base.AbstractValidator) add}
   *        {@link net.sf.mmm.util.validation.api.ValueValidator validators} for annotated constraints.
   * @param valueType the {@link GenericType} of the value that should be validated. Can be used for more precise or
   *        optimized processing. May be {@code null} if not available what can reduce quality of the result.
   */
  void processConstraints(AnnotatedElement annotatedElement, ValidatorRegistry<?, ?> validatorRegistry, GenericType<?> valueType);

}
