/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.value.api.NumberRange;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ConstraintContext extends AbstractLoggableObject {

  private final ObjectValidatorBuilder<?, ?, ?> validatorBuilder;

  private Number minimum;

  private Number maximum;

  /**
   * The constructor.
   *
   * @param validatorBuilder the {@link ObjectValidatorBuilder}.
   */
  public ConstraintContext(ObjectValidatorBuilder<?, ?, ?> validatorBuilder) {
    super();
    this.validatorBuilder = validatorBuilder;
  }

  /**
   * @return the validatorBuilder
   */
  public ObjectValidatorBuilder<?, ?, ?> getValidatorBuilder() {

    return this.validatorBuilder;
  }

  /**
   * @param minimum the min value allowed.
   */
  public void setMinimum(Number minimum) {

    this.minimum = minimum;
  }

  /**
   * @param maximum the max value allowed.
   */
  public void setMaximum(Number maximum) {

    this.maximum = maximum;
  }

  private Number getOrReplaceNumber(Number current, Number value, boolean minNotMax, Method method, Annotation annotation) {

    if (current != null) {
      getLogger().warn("Replacing {}-value {} with {} for {} due to annotation {}", minNotMax ? "min" : "max", current, value, method, annotation);
    }
    return value;
  }

  /**
   * Has to be called at the end to complete the processing.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  void complete() {

    if ((this.minimum != null) || (this.maximum != null)) {
      NumberRange range = new NumberRange(this.minimum, this.maximum);
      ValidatorRange validator = new ValidatorRange<>(range);
      this.validatorBuilder.add(validator);
    }

  }

}
