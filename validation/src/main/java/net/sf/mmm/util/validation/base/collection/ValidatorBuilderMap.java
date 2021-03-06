/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.Map;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.value.api.Range;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for a {@link Map}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class ValidatorBuilderMap<K, V, PARENT> extends AbstractMapValidatorBuilder<K, V, Map<K, V>, PARENT, ValidatorBuilderMap<K, V, PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderMap(PARENT parent) {
    super(parent);
  }

  @Override
  public ValidatorBuilderMap<K, V, PARENT> range(String min, String max) {

    Integer iMin = null;
    if (min != null) {
      iMin = Integer.valueOf(min);
    }
    Integer iMax = null;
    if (max != null) {
      iMax = Integer.valueOf(max);
    }
    add(new ValidatorMapSize(new Range<>(iMin, iMax)));
    return self();
  }

}
