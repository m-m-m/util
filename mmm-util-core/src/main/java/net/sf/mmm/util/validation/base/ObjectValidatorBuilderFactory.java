/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.base.text.ValidatorBuilderCharSequence;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderString;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class ObjectValidatorBuilderFactory<PARENT> {

  private static final BuilderFactory INSTANCE = new BuilderFactory();

  private final PARENT parent;

  /**
   * The constructor.
   */
  public ObjectValidatorBuilderFactory(PARENT parent) {
    super();
    this.parent = parent;
  }

  /**
   * @return the singleton instance of {@link BuilderFactory}.
   */
  public static BuilderFactory getInstance() {

    return INSTANCE;
  }

  /**
   * @return the parent builder or <code>null</code> if <code>Void</code>.
   */
  protected PARENT getParent() {

    return this.parent;
  }

  public ValidatorBuilderCharSequence<PARENT> create(CharSequence v) {

    return new ValidatorBuilderCharSequence<>(getParent());
  }

  public ValidatorBuilderString<PARENT> create(String v) {

    return new ValidatorBuilderString<>(getParent());
  }

  // public <E, SELF extends CollectionValidatorBuilder<E, PARENT, SELF, SUB>, SUB extends ObjectValidatorBuilder<E,
  // SELF, ?>> CollectionValidatorBuilder<E, PARENT, SELF, SUB> create(
  // Collection<? extends E> v) {
  //
  // return new CollectionValidatorBuilder<>(getParent());
  // }

  public static final class BuilderFactory extends ObjectValidatorBuilderFactory<Void> {

    /**
     * The constructor.
     */
    BuilderFactory() {
      super(null);
    }

  }

}
