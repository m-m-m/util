/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidatorRange;
import net.sf.mmm.util.validation.base.ValidatorArrayLength;
import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.validation.base.ValidatorRangeGeneric;
import net.sf.mmm.util.validation.base.ValidatorRegistry;
import net.sf.mmm.util.validation.base.collection.ValidatorCollectionSize;
import net.sf.mmm.util.validation.base.collection.ValidatorMapSize;
import net.sf.mmm.util.validation.base.jsr303.constraints.ConstraintProcessor;
import net.sf.mmm.util.value.api.NumberRange;

/**
 * This is the context for building and collecting the {@link net.sf.mmm.util.validation.base.AbstractValidator
 * validators}. It is used as state to communicate between {@link BeanValidationProcessorImpl} and
 * {@link ConstraintProcessor}s.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class ConstraintContext extends AbstractLoggableObject {

  private final ValidatorRegistry<?, ?> validatorRegistry;

  private final AnnotatedElement annotatedElement;

  private final GenericType<?> valueType;

  private final MathUtil mathUtil;

  private Annotation constraint;

  private NumberRange range;

  /**
   * The constructor.
   *
   * @param validatorRegistry - see {@link #getValidatorRegistry()}.
   * @param annotatedElement the {@link AnnotatedElement} to process.
   * @param valueType - see {@link #getValueType()}.
   * @param mathUtil the {@link MathUtil}.
   */
  ConstraintContext(ValidatorRegistry<?, ?> validatorRegistry, AnnotatedElement annotatedElement, GenericType<?> valueType, MathUtil mathUtil) {
    super();
    this.validatorRegistry = validatorRegistry;
    this.annotatedElement = annotatedElement;
    this.valueType = valueType;
    this.mathUtil = mathUtil;
  }

  /**
   * @return the {@link ValidatorRegistry} where to
   *         {@link ValidatorRegistry#add(net.sf.mmm.util.validation.base.AbstractValidator) add} the
   *         {@link net.sf.mmm.util.validation.base.AbstractValidator validators}.
   */
  @SuppressWarnings("rawtypes")
  public ValidatorRegistry getValidatorRegistry() {

    return this.validatorRegistry;
  }

  /**
   * @return the {@link GenericType} of the value to validate. Can be used for advanced processing but may be not
   *         available ({@code null}).
   */
  public GenericType<?> getValueType() {

    return this.valueType;
  }

  /**
   * @param constraint the current constraint {@link Annotation}.
   */
  void setConstraint(Annotation constraint) {

    this.constraint = constraint;
  }

  /**
   * @param minimum the min value allowed.
   */
  public void setMinimum(Number minimum) {

    if (this.range == null) {
      this.range = new NumberRange(minimum, null);
    } else {
      updateRange(this.range.withMin(minimum));
    }
  }

  /**
   * @param maximum the max value allowed.
   */
  public void setMaximum(Number maximum) {

    if (this.range == null) {
      this.range = new NumberRange(null, maximum);
    } else {
      updateRange(this.range.withMax(maximum));
    }
  }

  private void updateRange(NumberRange newRange) {

    if (this.range != newRange) {
      Number min = this.range.getMin();
      Number max = this.range.getMax();
      if (((min != null) && (newRange.getMin() != min)) || (max != null) && (newRange.getMax() != max)) {
        getLogger().warn("Replacing range {} with {} for constraint {} of {}.", this.range, newRange, this.constraint, this.annotatedElement);
      }
      this.range = newRange;
    }
  }

  /**
   * Has to be called at the end to complete the processing.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  void complete() {

    if (this.range != null) {
      AbstractValidatorRange validator = null;
      if (this.valueType != null) {
        Class<?> valueClass = this.valueType.getAssignmentClass();
        if (this.valueType.isCollection()) {
          validator = new ValidatorCollectionSize(this.range);
        } else if (this.valueType.isMap()) {
          validator = new ValidatorMapSize(this.range);
        } else if (this.valueType.getComponentType() != null) {
          validator = new ValidatorArrayLength(this.range);
        } else if (this.mathUtil.getNumberType(valueClass) != null) {
          validator = new ValidatorRange<>(this.range);
        }
      }
      if (validator == null) {
        validator = new ValidatorRangeGeneric<>(this.range);
      }
      this.validatorRegistry.add(validator);
    }
  }

}
