/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.Instant;
import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.time.ValidatorBuilderInstant;

/**
 * This is the implementation of {@link WritableInstantProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class InstantProperty extends AbstractRegularProperty<Instant> implements WritableInstantProperty {

  private Instant value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public InstantProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public InstantProperty(String name, Bean bean, AbstractValidator<? super Instant> validator) {
    super(name, bean, validator);
  }

  @Override
  protected Instant doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(Instant newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  @Override
  public ValidatorBuilderInstant<PropertyBuilder<InstantProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderInstant<>(x));
  }

}