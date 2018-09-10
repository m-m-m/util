/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.pojo.api.TypedProperty;

/**
 * This is the abstract base implementation of {@link ValidatorBuilder}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractValidatorBuilder implements ValidatorBuilder {

  /**
   * The constructor.
   */
  public AbstractValidatorBuilder() {

    super();
  }

  @Override
  public AbstractValidator<?> newValidator(Class<?> pojoType, String property) {

    return newValidator(pojoType, property, null);
  }

  @Override
  public <T> AbstractValidator<T> newValidator(Class<?> pojoType, TypedProperty<T> property) {

    return newValidator(pojoType, property, property.getPropertyType());
  }

}
