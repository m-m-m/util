/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.BooleanProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BooleanPropertyImpl extends GenericPropertyImpl<Boolean> implements BooleanProperty {

  private static final GenericType<Boolean> TYPE = new SimpleGenericTypeImpl<>(Boolean.class);

  /**
   * The constructor.
   *
   * @param name
   * @param type
   */
  public BooleanPropertyImpl(String name) {
    super(name, TYPE);
  }

  /**
   * The constructor.
   *
   * @param name
   * @param type
   * @param bean
   */
  public BooleanPropertyImpl(String name, Bean bean) {
    super(name, TYPE, bean);
  }

  /**
   * The constructor.
   *
   * @param name
   * @param type
   * @param validator
   */
  public BooleanPropertyImpl(String name, AbstractValidator<? super Boolean> validator) {
    super(name, TYPE, validator);
  }

  /**
   * The constructor.
   *
   * @param name
   * @param type
   * @param bean
   * @param validator
   */
  public BooleanPropertyImpl(String name, Bean bean, AbstractValidator<? super Boolean> validator) {
    super(name, TYPE, bean, validator);
  }

  @Override
  public BooleanPropertyImpl createFor(Bean newBean) {

    return new BooleanPropertyImpl(getName(), newBean, getValidator());
  }

}
