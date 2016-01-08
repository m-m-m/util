/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.StringProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderString;

/**
 * This is the implementation of {@link StringProperty}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class StringPropertyImpl extends GenericPropertyImpl<String> implements StringProperty {

  private static final GenericType<String> TYPE = new SimpleGenericTypeImpl<>(String.class);

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public StringPropertyImpl(String name, Bean bean) {
    super(name, TYPE, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public StringPropertyImpl(String name, Bean bean, AbstractValidator<? super String> validator) {
    super(name, TYPE, bean, validator);
  }

  @Override
  public GenericPropertyImpl<String> copy(String newName, Bean newBean,
      AbstractValidator<? super String> newValidator) {

    return new StringPropertyImpl(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderString<PropertyBuilder<StringPropertyImpl>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderString<>(x));
  }

  @Override
  protected boolean useEqualsInternal() {

    return true;
  }

}
