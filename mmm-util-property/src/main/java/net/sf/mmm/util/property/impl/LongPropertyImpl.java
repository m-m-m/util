/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.LongProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderLong;

/**
 * This is the implementation of {@link LongProperty}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class LongPropertyImpl extends NumberPropertyImpl implements LongProperty {

  /**
   * The constructor.
   */
  public LongPropertyImpl() {
    super();
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public LongPropertyImpl(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public LongPropertyImpl(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  @Override
  public void setValue(Number value) {

    if ((value == null) || (value instanceof Long)) {
      super.setValue(value);
    } else {
      super.setValue(Long.valueOf(value.longValue()));
    }
  }

  @Override
  public LongPropertyImpl copy(String newName, Bean newBean, AbstractValidator<? super Number> newValidator) {

    return new LongPropertyImpl(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderLong<PropertyBuilder<LongPropertyImpl>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderLong<>(x));
  }

}
