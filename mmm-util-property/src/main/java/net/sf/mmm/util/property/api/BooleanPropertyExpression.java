/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import net.sf.mmm.util.bean.api.Bean;
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
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public BooleanPropertyExpression(String name, Bean bean, AbstractValidator<? super Boolean> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public BooleanPropertyExpression(String name, Bean bean) {
    super(name, bean);
  }

  @Override
  public ValidatorBuilderBoolean<PropertyBuilder<BooleanProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderBoolean<>(x));
  }
}
