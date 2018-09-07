/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.pojo.api.TypedProperty;

/**
 * This is a fallback implementation of {@link ValidatorBuilder} that may be used if JSR 303 is not available (missing
 * on classpath, configured wrongly, or the like).
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorBuilderNone extends AbstractValidatorBuilder {

  /**
   * The constructor.
   */
  public ValidatorBuilderNone() {

    super();
  }

  @Override
  public <V> AbstractValidator<V> newValidator(Class<V> pojoType) {

    return ValidatorNone.getInstance();
  }

  @Override
  public AbstractValidator<?> newValidator(Class<?> pojoType, String property, Class<?> propertyType) {

    return ValidatorNone.getInstance();
  }

  @Override
  public <T> AbstractValidator<T> newValidator(Class<?> pojoType, TypedProperty<T> property,
      Class<T> propertyType) {

    return ValidatorNone.getInstance();
  }

}
