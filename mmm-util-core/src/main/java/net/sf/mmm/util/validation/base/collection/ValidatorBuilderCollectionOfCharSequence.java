/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.Collection;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderCharSequence;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Collection} of {@link String}s.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorBuilderCollectionOfCharSequence<PARENT> extends
    CollectionValidatorBuilder<CharSequence, PARENT, ValidatorBuilderCollectionOfCharSequence<PARENT>, ValidatorBuilderCharSequence<ValidatorBuilderCollectionOfCharSequence<PARENT>>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderCollectionOfCharSequence(PARENT parent) {
    super(parent);
  }

  @Override
  protected ValidatorBuilderCharSequence<ValidatorBuilderCollectionOfCharSequence<PARENT>> createSubBuilder() {

    return new ValidatorBuilderCharSequence<>(this);
  }

}
