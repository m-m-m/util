/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Collection;

/**
 * The abstract base for a {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Collection}
 * values.
 *
 * @param <V> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class ComplexValidatorBuilder<V, PARENT, SELF extends ComplexValidatorBuilder<V, PARENT, SELF>>
    extends ObjectValidatorBuilder<V, PARENT, SELF> {

  private ObjectValidatorBuilderFactory<SELF> subFactory;

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ComplexValidatorBuilder(PARENT parent) {
    super(parent);
  }

  /**
   * @return the sub-{@link ObjectValidatorBuilderFactory factory}.
   */
  public ObjectValidatorBuilderFactory<SELF> getSubFactory() {

    if (this.subFactory == null) {
      this.subFactory = new ObjectValidatorBuilderFactory<>(self());
    }
    return this.subFactory;
  }

}
