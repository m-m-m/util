/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.Map;
import java.util.function.BiFunction;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComplexValidatorBuilder;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilderFactory;
import net.sf.mmm.util.value.api.Range;

/**
 * The abstract base for a {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Map} values.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 * @param <M> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractMapValidatorBuilder<K, V, M extends Map<K, V>, PARENT, SELF extends AbstractMapValidatorBuilder<K, V, M, PARENT, SELF>>
    extends ComplexValidatorBuilder<M, PARENT, SELF> {

  private ObjectValidatorBuilder<K, ? extends SELF, ?> keySubBuilder;

  private ObjectValidatorBuilder<V, ? extends SELF, ?> valueSubBuilder;

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public AbstractMapValidatorBuilder(PARENT parent) {
    super(parent);
  }

  /**
   * @see ValidatorCollectionSize
   *
   * @param range the {@link Range} to limit the {@link Map#size() size} of the {@link Map}.
   * @return this build instance for fluent API calls.
   */
  public SELF size(Range<Number> range) {

    return add(new ValidatorMapSize(range));
  }

  /**
   * @see #size(Range)
   *
   * @param min the minimum {@link Map#size() size} allowed.
   * @param max the maximum {@link Map#size() size} allowed.
   * @return this build instance for fluent API calls.
   */
  public SELF size(int min, int max) {

    return size(new Range<>(Integer.valueOf(min), Integer.valueOf(max)));
  }

  /**
   * @see #size(Range)
   *
   * @param max the maximum {@link Map#size() size} allowed.
   * @return this build instance for fluent API calls.
   */
  public SELF max(int max) {

    return size(0, max);
  }

  /**
   * Creates a new {@link ObjectValidatorBuilder builder} for the {@link AbstractValidator validators} to invoke for
   * each {@link Map#keySet() key} in the {@link Map}.<br/>
   * Use {@link #and()} to return to this builder after the sub-builder is complete.<br/>
   * A typical usage looks like this:
   *
   * <pre>
   * [...].withKeys((f, v) -> f.create(v)).[...].and().[...].build()
   * </pre>
   *
   * @param <SUB> the generic type of the returned sub-builder.
   * @param factory lambda function used to create the returned sub-builder by calling the according {@code create}
   *        method on the supplied {@link ObjectValidatorBuilderFactory} with the given dummy element.
   * @return the new sub-builder.
   */
  public <SUB extends ObjectValidatorBuilder<K, ? extends SELF, ?>> SUB withKeys(BiFunction<ObjectValidatorBuilderFactory<SELF>, K, SUB> factory) {

    if (this.keySubBuilder != null) {
      throw new IllegalStateException("keySubBuilder already exists!");
    }
    SUB sub = factory.apply(getSubFactory(), null);
    this.keySubBuilder = sub;
    return sub;
  }

  /**
   * Creates a new {@link ObjectValidatorBuilder builder} for the {@link AbstractValidator validators} to invoke for
   * each {@link Map#values() values} in the {@link Map}.<br/>
   * Use {@link #and()} to return to this builder after the sub-builder is complete.<br/>
   * A typical usage looks like this:
   *
   * <pre>
   * [...].withValues((f, v) -> f.create(v)).[...].and().[...].build()
   * </pre>
   *
   * @param <SUB> the generic type of the returned sub-builder.
   * @param factory lambda function used to create the returned sub-builder by calling the according {@code create}
   *        method on the supplied {@link ObjectValidatorBuilderFactory} with the given dummy element.
   * @return the new sub-builder.
   */
  public <SUB extends ObjectValidatorBuilder<V, ? extends SELF, ?>> SUB withValues(BiFunction<ObjectValidatorBuilderFactory<SELF>, V, SUB> factory) {

    if (this.valueSubBuilder != null) {
      throw new IllegalStateException("valueSubBuilder already exists!");
    }
    SUB sub = factory.apply(getSubFactory(), null);
    this.valueSubBuilder = sub;
    return sub;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public AbstractValidator<? super M> build() {

    if (this.keySubBuilder != null) {
      add(new ValidatorMapKeys(getValidators(this.keySubBuilder)));
    }
    if (this.valueSubBuilder != null) {
      add(new ValidatorMapKeys(getValidators(this.valueSubBuilder)));
    }
    return super.build();
  }

}
