/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.util.lang.api.Builder;

/**
 * This is the base class to create instances of {@link AbstractValidator} using the builder pattern.
 *
 * @param <V> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class ObjectValidatorBuilder<V, PARENT, SELF extends ObjectValidatorBuilder<V, PARENT, SELF>>
    implements Builder<AbstractValidator<? super V>> {

  private final PARENT parent;

  private final List<AbstractValidator<? super V>> validators;

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ObjectValidatorBuilder(PARENT parent) {
    super();
    this.validators = new ArrayList<>();
    this.parent = parent;
  }

  /**
   * @param <T> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
   * @param builder the {@link ObjectValidatorBuilder}.
   * @return the {@link List} of validators.
   */
  protected <T> List<AbstractValidator<? super T>> getValidators(ObjectValidatorBuilder<T, ?, ?> builder) {

    return builder.validators;
  }

  /**
   * @return the parent {@link Builder} or {@code null} if {@literal <PARENT>} is void.
   */
  public PARENT and() {

    return this.parent;
  }

  /**
   * @return this build instance for fluent API calls.
   */
  @SuppressWarnings("unchecked")
  protected SELF self() {

    return (SELF) this;
  }

  /**
   * @param validator the {@link AbstractValidator} to add to this builder.
   * @return this build instance for fluent API calls.
   */
  public SELF add(AbstractValidator<? super V> validator) {

    this.validators.add(validator);
    return self();
  }

  /**
   * Value is {@link ValidatorMandatory mandatory}.
   *
   * @return this build instance for fluent API calls.
   */
  public SELF mandatory() {

    return add(ValidatorMandatory.getInstance());
  }

  /**
   * This method allows to define a range in a generic way. If you have a properly typed builder please use more
   * specific methods such as {@link CompareableValidatorBuilder#range(net.sf.mmm.util.value.api.Range)} instead.
   *
   * @param min the minimum value allowed or {@code null} for no lower bound.
   * @param max the maximum value allowed or {@code null} for no upper bound.
   * @return this build instance for fluent API calls.
   */
  public abstract SELF range(String min, String max);

  /**
   * @return the {@link AbstractValidator}
   */
  @Override
  public AbstractValidator<? super V> build() {

    int size = this.validators.size();
    if (size == 0) {
      return ValidatorNone.getInstance();
    } else if (size == 1) {
      return this.validators.get(0);
    } else {
      AbstractValidator<? super V>[] array = this.validators.toArray(new AbstractValidator[size]);
      return new ComposedValidator<>(array);
    }
  }

}
