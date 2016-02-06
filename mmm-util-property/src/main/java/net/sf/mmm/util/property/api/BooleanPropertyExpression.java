/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ValidatorBuilderBoolean;

/**
 * Abstract base implementation of {@link AbstractRegularPropertyExpression} for {@link WritableBooleanProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class BooleanPropertyExpression extends AbstractRegularPropertyExpression<Boolean>
    implements WritableBooleanProperty {

  /**
   * The constructor.
   */
  public BooleanPropertyExpression() {
    super();
  }

  /**
   * The constructor.
   *
   * @param validator - see {@link #validate()}.
   */
  public BooleanPropertyExpression(AbstractValidator<? super Boolean> validator) {
    super(validator);
  }

  @Override
  public ValidatorBuilderBoolean<PropertyBuilder<BooleanProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderBoolean<>(x));
  }
}
