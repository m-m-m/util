/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.validation.base.collection.ValidatorBuilderCollection;
import net.sf.mmm.util.validation.base.collection.ValidatorBuilderMap;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderCharSequence;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderString;

/**
 * This is the interface for instances of {@link ObjectValidatorBuilder}.
 *
 * @param <PARENT> the generic type of the {@link #getParent() parent}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ObjectValidatorBuilderFactory<PARENT> {

  private static final BuilderFactory INSTANCE = new BuilderFactory();

  private final PARENT parent;

  /**
   * The constructor.
   *
   * @param parent - see {@link #getParent()}.
   */
  public ObjectValidatorBuilderFactory(PARENT parent) {
    super();
    this.parent = parent;
  }

  /**
   * @return the parent builder or {@code null} if {@link Void}.
   */
  protected PARENT getParent() {

    return this.parent;
  }

  /**
   * @param v the value for type inference. Will be ignored and may be {@code null}.
   * @return the {@link ValidatorBuilderCharSequence}.
   */
  public ValidatorBuilderCharSequence<PARENT> create(CharSequence v) {

    return new ValidatorBuilderCharSequence<>(getParent());
  }

  /**
   * @param v the value for type inference. Will be ignored and may be {@code null}.
   * @return the {@link ValidatorBuilderString}.
   */
  public ValidatorBuilderString<PARENT> create(String v) {

    return new ValidatorBuilderString<>(getParent());
  }

  /**
   * @param v the value for type inference. Will be ignored and may be {@code null}.
   * @return the {@link ValidatorBuilderBoolean}.
   */
  public ValidatorBuilderBoolean<PARENT> create(Boolean v) {

    return new ValidatorBuilderBoolean<>(getParent());
  }

  /**
   * @param <E> the generic type of the {@link Collection#contains(Object) elements contained} in the {@link Collection}
   *        to validate.
   * @param v the value for type inference. Will be ignored and may be {@code null}.
   * @return the {@link ValidatorBuilderCollection}.
   */
  public <E> ValidatorBuilderCollection<E, PARENT> create(Collection<E> v) {

    return new ValidatorBuilderCollection<>(getParent());
  }

  /**
   * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
   * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
   * @param v the value for type inference. Will be ignored and may be {@code null}.
   * @return the {@link ValidatorBuilderCollection}.
   */
  public <K, V> ValidatorBuilderMap<K, V, PARENT> create(Map<K, V> v) {

    return new ValidatorBuilderMap<>(getParent());
  }

  /**
   * @param <E> the generic type of the {@link Collection#contains(Object) elements contained} in the {@link Collection}
   *        to validate.
   * @param v the value for type inference. Will be ignored and may be {@code null}.
   * @return the {@link ValidatorBuilderCollection}.
   */
  public <E> ValidatorBuilderCollection<E, PARENT> createCollection(E v) {

    return new ValidatorBuilderCollection<>(getParent());
  }

  /**
   * @return the signleton instance of this factory without {@link #getParent() parent}.
   */
  public static BuilderFactory getInstance() {

    return INSTANCE;
  }

  /**
   * Implementation of {@link ObjectValidatorBuilderFactory} without {@link #getParent() parent}.
   */
  public static final class BuilderFactory extends ObjectValidatorBuilderFactory<Void> {

    /**
     * The constructor.
     */
    BuilderFactory() {
      super(null);
    }

  }

}
