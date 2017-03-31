/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.Collection;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.value.api.Range;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for a {@link Collection} of {@link Object}s.
 *
 * @param <E> the generic type of the {@link Collection#contains(Object) elements contained} in the {@link Collection}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class ValidatorBuilderCollection<E, PARENT> extends AbstractCollectionValidatorBuilder<E, Collection<E>, PARENT, ValidatorBuilderCollection<E, PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderCollection(PARENT parent) {
    super(parent);
  }

  @Override
  public ValidatorBuilderCollection<E, PARENT> range(String min, String max) {

    Integer iMin = null;
    if (min != null) {
      iMin = Integer.valueOf(min);
    }
    Integer iMax = null;
    if (max != null) {
      iMax = Integer.valueOf(max);
    }
    add(new ValidatorCollectionSize(new Range<>(iMin, iMax)));
    return self();
  }

}
