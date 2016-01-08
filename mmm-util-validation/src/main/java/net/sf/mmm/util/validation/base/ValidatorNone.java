/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is an implementation of {@link net.sf.mmm.util.validation.api.ValueValidator} that always validates
 * successfully.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorNone extends AbstractValidator<Object> {

  /** @see #getInstance() */
  private static final ValidatorNone INSTANCE = new ValidatorNone();

  /**
   * The constructor.
   */
  public ValidatorNone() {

    super();
  }

  /**
   * @param <VALUE> is the generic type of the value to validate.
   * @return the instance of this validator that always validates successfully.
   */
  @SuppressWarnings({ "unchecked" })
  public static <VALUE> AbstractValidator<VALUE> getInstance() {

    return (AbstractValidator<VALUE>) INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationFailure validate(Object value, Object valueSource) {

    return null;
  }

}
