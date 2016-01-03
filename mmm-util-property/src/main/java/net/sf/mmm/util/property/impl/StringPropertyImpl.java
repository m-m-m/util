/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.StringProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class StringPropertyImpl extends GenericPropertyImpl<String> implements StringProperty {

  private static final GenericType<String> TYPE = new SimpleGenericTypeImpl<>(String.class);

  /**
   * The constructor.
   *
   * @param name
   * @param type
   */
  public StringPropertyImpl(String name) {
    super(name, TYPE);
  }

  /**
   * The constructor.
   *
   * @param name
   * @param type
   * @param bean
   */
  public StringPropertyImpl(String name, Bean bean) {
    super(name, TYPE, bean);
  }

  /**
   * The constructor.
   *
   * @param name
   * @param type
   * @param validator
   */
  public StringPropertyImpl(String name, AbstractValidator<? super String> validator) {
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
  public StringPropertyImpl(String name, Bean bean, AbstractValidator<? super String> validator) {
    super(name, TYPE, bean, validator);
  }

  @Override
  public StringPropertyImpl createFor(Bean newBean) {

    return new StringPropertyImpl(getName(), newBean, getValidator());
  }

}
