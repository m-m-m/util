/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValueValidator;
import net.sf.mmm.util.validation.base.ValidationFailureComposer;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} {@link #validate(Map, Object) validating}
 * {@link Map#values() all values} of a given {@link Map} using the {@link #ValidatorMapValues(AbstractValidator...)
 * given} validators.
 *
 * @param <V> the generic type of the {@link Map#values() values} in the collection.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorMapValues<V> extends AbstractValidator<Map<?, V>>
    implements ComposedValueValidator<Map<?, V>> {

  /** The child validators. */
  private final AbstractValidator<? super V>[] validators;

  /**
   * The constructor.
   *
   * @param validators the {@link AbstractValidator}s used to {@link AbstractValidator#validate(Object, Object)
   *        validate} each element.
   */
  @SafeVarargs
  public ValidatorMapValues(AbstractValidator<? super V>... validators) {

    super();
    this.validators = validators;
  }

  /**
   * The constructor.
   *
   * @param validators the {@link AbstractValidator}s used to {@link AbstractValidator#validate(Object, Object)
   *        validate} each element.
   */
  public ValidatorMapValues(List<AbstractValidator<? super V>> validators) {
    this(validators.toArray(new AbstractValidator[validators.size()]));
  }

  @Override
  public AbstractValidator<?> getValidator(int index) {

    return this.validators[index];
  }

  @Override
  public int getValidatorCount() {

    return this.validators.length;
  }

  @Override
  public ValidationFailure validate(Map<?, V> map, Object valueSource) {

    ValidationFailureComposer composer = new ValidationFailureComposer();
    if (map != null) {
      int i = 0;
      for (V value : map.values()) {
        for (AbstractValidator<? super V> validator : this.validators) {
          Object source;
          if (valueSource == null) {
            source = Integer.toString(i);
          } else {
            source = valueSource.toString() + "." + i;
          }
          ValidationFailure failure = validator.validate(value, source);
          composer.add(failure);
        }
        i++;
      }
    }
    return composer.get(valueSource);
  }

  @Override
  public int hashCode() {

    return 8643;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ValidatorMapValues<?> other = (ValidatorMapValues<?>) obj;
    if (!Objects.equals(this.validators, other.validators)) {
      return false;
    }
    return true;
  }

}
