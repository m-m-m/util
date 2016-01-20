/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableBooleanProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ValidatorBuilderBoolean;

/**
 * This is the implementation of {@link BooleanProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BooleanProperty extends AbstractRegularProperty<Boolean> implements WritableBooleanProperty {

  private static final GenericType<Boolean> TYPE = new SimpleGenericTypeImpl<>(Boolean.class);

  private Boolean value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public BooleanProperty(String name, Bean bean) {
    super(name, TYPE, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public BooleanProperty(String name, Bean bean, AbstractValidator<? super Boolean> validator) {
    super(name, TYPE, bean, validator);
  }

  @Override
  protected Boolean doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(Boolean newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  @Override
  public BooleanProperty copy(String newName, Bean newBean, AbstractValidator<? super Boolean> newValidator) {

    return new BooleanProperty(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderBoolean<PropertyBuilder<BooleanProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderBoolean<>(x));
  }

}
