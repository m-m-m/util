/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.Collection;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;

/**
 * The abstract base for a {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Collection}
 * values.
 *
 * @param <E> the generic type of the {@link Collection#contains(Object) elements contained} in the {@link Collection}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 * @param <SUB> the generic type of the {@link #with() sub-builder}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class CollectionValidatorBuilder<E, PARENT, SELF extends CollectionValidatorBuilder<E, PARENT, SELF, SUB>, SUB extends ObjectValidatorBuilder<E, SELF, ?>>
    extends AbstractCollectionValidatorBuilder<E, Collection<E>, PARENT, SELF> {

  private SUB subBuilder;

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public CollectionValidatorBuilder(PARENT parent) {
    super(parent);
  }

  /**
   * Creates a new {@link ObjectValidatorBuilder builder} for the {@link AbstractValidator validators} to invoke for
   * each {@link Collection#contains(Object) elements contained} in the {@link Collection}.<br/>
   * Use {@link #and()} to return to this builder after the sub-builder is complete.
   *
   * @return the new sub-builder.
   */
  public SUB with() {

    if (this.subBuilder != null) {
      throw new IllegalStateException("subBuilder already exists!");
    }
    this.subBuilder = createSubBuilder();
    return this.subBuilder;
  }

  /**
   * @return a new instance of the {@link #with() sub-builder}.
   */
  protected abstract SUB createSubBuilder();

  @Override
  public AbstractValidator<? super Collection<E>> build() {

    if (this.subBuilder == null) {
      add(new ValidatorCollectionElements<>(getValidators(this.subBuilder)));
    }
    return super.build();
  }

}
