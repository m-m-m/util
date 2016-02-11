/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValueValidator;
import net.sf.mmm.util.validation.base.ValidationFailureComposer;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} {@link #validate(Collection, Object) validating}
 * {@link Collection#iterator() all elements} of a given {@link Collection} using the
 * {@link #ValidatorCollectionElements(AbstractValidator...) given} validators.
 *
 * @param <E> the generic type of the {@link Collection#contains(Object) elements contained} in the collection.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorCollectionElements<E> extends AbstractValidator<Collection<E>>
    implements ComposedValueValidator<Collection<E>> {

  /** The child validators. */
  private final AbstractValidator<? super E>[] validators;

  /**
   * The constructor.
   *
   * @param validators the {@link AbstractValidator}s used to {@link AbstractValidator#validate(Object, Object)
   *        validate} each element.
   */
  @SafeVarargs
  public ValidatorCollectionElements(AbstractValidator<? super E>... validators) {

    super();
    this.validators = validators;
  }

  /**
   * The constructor.
   *
   * @param validators the {@link AbstractValidator}s used to {@link AbstractValidator#validate(Object, Object)
   *        validate} each element.
   */
  public ValidatorCollectionElements(List<AbstractValidator<? super E>> validators) {
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
  public ValidationFailure validate(Collection<E> value, Object valueSource) {

    ValidationFailureComposer composer = new ValidationFailureComposer();
    if (value != null) {
      int i = 0;
      for (E element : value) {
        for (AbstractValidator<? super E> validator : this.validators) {
          Object source;
          if (valueSource == null) {
            source = Integer.toString(i);
          } else {
            source = valueSource.toString() + "." + i;
          }
          ValidationFailure failure = validator.validate(element, source);
          composer.add(failure);
        }
        i++;
      }
    }
    return composer.get(valueSource);
  }

  @Override
  public int hashCode() {

    return 8420;
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
    ValidatorCollectionElements<?> other = (ValidatorCollectionElements<?>) obj;
    if (!Objects.equals(this.validators, other.validators)) {
      return false;
    }
    return true;
  }

}
