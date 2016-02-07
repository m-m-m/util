/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.List;
import java.util.Map;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValueValidator;
import net.sf.mmm.util.validation.base.ValidationFailureComposer;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} {@link #validate(Map, Object) validating}
 * {@link Map#keySet() all keys} of a given {@link Map} using the {@link #ValidatorMapKeys(AbstractValidator...) given}
 * validators.
 *
 * @param <K> the generic type of the {@link Map#keySet() keys} in the collection.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorMapKeys<K> extends AbstractValidator<Map<K, ?>>
    implements ComposedValueValidator<Map<K, ?>> {

  /** The child validators. */
  private final AbstractValidator<? super K>[] validators;

  /**
   * The constructor.
   *
   * @param validators the {@link AbstractValidator}s used to {@link AbstractValidator#validate(Object, Object)
   *        validate} each element.
   */
  @SafeVarargs
  public ValidatorMapKeys(AbstractValidator<? super K>... validators) {

    super();
    this.validators = validators;
  }

  /**
   * The constructor.
   *
   * @param validators the {@link AbstractValidator}s used to {@link AbstractValidator#validate(Object, Object)
   *        validate} each element.
   */
  public ValidatorMapKeys(List<AbstractValidator<? super K>> validators) {
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
  public ValidationFailure validate(Map<K, ?> map, Object valueSource) {

    ValidationFailureComposer composer = new ValidationFailureComposer();
    if (map != null) {
      int i = 0;
      for (K key : map.keySet()) {
        for (AbstractValidator<? super K> validator : this.validators) {
          Object source;
          if (valueSource == null) {
            source = Integer.toString(i);
          } else {
            source = valueSource.toString() + "." + i;
          }
          ValidationFailure failure = validator.validate(key, source);
          composer.add(failure);
        }
        i++;
      }
    }
    return composer.get(valueSource);
  }

}
