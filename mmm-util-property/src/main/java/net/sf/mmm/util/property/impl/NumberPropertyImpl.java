/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.NumberProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link NumberProperty}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class NumberPropertyImpl extends GenericPropertyImpl<Number> implements NumberProperty {

  private static final GenericType<Number> TYPE = new SimpleGenericTypeImpl<>(Number.class);

  /**
   * The constructor.
   */
  public NumberPropertyImpl() {
    this(null, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public NumberPropertyImpl(String name, Bean bean) {
    super(name, TYPE, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public NumberPropertyImpl(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, TYPE, bean, validator);
  }

  @Override
  public NumberPropertyImpl copy(String newName, Bean newBean, AbstractValidator<? super Number> newValidator) {

    return new NumberPropertyImpl(newName, newBean, newValidator);
  }

  @Override
  protected final boolean useEqualsInternal() {

    return true;
  }

}
