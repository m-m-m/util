/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValueValidator;

/**
 * This is an implementation of {@link ValueValidator} that is composed out of a set of individual
 * {@link #getValidator(int) validators} given at {@link #ComposedValidator(AbstractValidator...)
 * construction}. It will always invoke <em>all</em> {@link #getValidator(int) contained validators} when a
 * {@link #validate(Object, Object) validation} is performed.
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ComposedValidator<V> extends AbstractValidator<V> {

  /** @see #getCode() */
  private static final String CODE = "ComposedValidator";

  /** The child validators. */
  private final AbstractValidator<? super V>[] validators;

  /**
   * The constructor.
   *
   * @param validators are the {@link #getValidator(int) validators} to compose.
   */
  @SafeVarargs
  public ComposedValidator(AbstractValidator<? super V>... validators) {

    super();
    this.validators = validators;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMandatory() {

    for (AbstractValidator<? super V> validator : this.validators) {
      if (validator.isMandatory()) {
        return true;
      }
    }
    return super.isMandatory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getCode() {

    return CODE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationFailure validate(V value, Object valueSource) {

    ValidationFailure result = null;
    List<ValidationFailure> failureList = null;
    for (ValueValidator<? super V> validator : this.validators) {
      ValidationFailure failure = validator.validate(value, valueSource);
      if (failure != null) {
        if (failureList == null) {
          failureList = new ArrayList<ValidationFailure>();
        }
        failureList.add(failure);
      }
    }
    if (failureList != null) {
      if (failureList.size() == 1) {
        result = failureList.get(0);
      } else {
        result = new ComposedValidationFailure(getCode(), valueSource,
            failureList.toArray(new ValidationFailure[failureList.size()]));
      }
    }
    return result;
  }

  /**
   * @see #getValidator(int)
   * @see java.util.Collection#size()
   *
   * @return the number of {@link #getValidator(int) validators}.
   */
  public int getValidatorCount() {

    return this.validators.length;
  }

  /**
   * Gets the {@link ValueValidator} at the given <code>index</code>.
   *
   * @see java.util.List#get(int)
   *
   * @param index is the index of the {@link ValueValidator} to get.
   * @return the requested {@link ValueValidator}.
   */
  public ValueValidator<? super V> getValidator(int index) {

    return this.validators[index];
  }
}
