/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.validation.base.collection.ValidatorCollectionSize;
import net.sf.mmm.util.validation.base.collection.ValidatorMapSize;
import net.sf.mmm.util.value.api.Range;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that validates that a given value is within a
 * specific {@link Range}. In advance to {@link ValidatorRange} this implementation can handle arbitrary input types
 * such as arrays, {@link Collection}s or {@link Map}s. It is therefore a generic combination of all validators such as
 * {@link ValidatorRange}, {@link ValidatorArrayLength}, {@link ValidatorCollectionSize}, {@link ValidatorMapSize}, etc.
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 * @param <R> the generic type of the {@link Range}-bounds.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.4.0
 */
public class ValidatorRangeGeneric<V, R> extends AbstractValidatorRange<V, R> {

  /**
   * The constructor.
   *
   * @param range is the {@link Range} the value has to be within.
   */
  public ValidatorRangeGeneric(Range<R> range) {

    super(range);
  }

  /**
   * The constructor.
   *
   * @param min is the {@link Range#getMin() minimum} value allowed.
   * @param max is the {@link Range#getMax() maximum} value allowed.
   */
  public ValidatorRangeGeneric(R min, R max) {

    this(new Range<>(min, max));
  }

  @SuppressWarnings("unchecked")
  @Override
  protected R convertValue(V value) {

    Object result;
    if (value instanceof Collection) {
      int size = ((Collection<?>) value).size();
      result = Integer.valueOf(size);
    } else if (value instanceof Map) {
      int size = ((Map<?, ?>) value).size();
      result = Integer.valueOf(size);
    } else if (value instanceof CharSequence) {
      int size = ((CharSequence) value).length();
      result = Integer.valueOf(size);
    } else if (value.getClass().isArray()) {
      int size = Array.getLength(value);
      result = Integer.valueOf(size);
    } else {
      result = value;
    }
    return (R) result;
  }

}
