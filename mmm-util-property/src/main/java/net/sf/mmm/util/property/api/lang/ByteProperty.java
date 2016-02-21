/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderInteger;

/**
 * This is the implementation of {@link WritableByteProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ByteProperty extends NumberProperty<Byte> implements WritableByteProperty {

  private Byte value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public ByteProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public ByteProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  @Override
  public void setValue(Number value) {

    if ((value == null) || (value instanceof Byte)) {
      super.setValue(value);
    } else {
      super.setValue(Byte.valueOf(value.byteValue()));
    }
  }

  @Override
  protected Byte doGetValue() {

    return this.value;
  }

  @Override
  protected void doSetNumber(Byte newValue) {

    this.value = newValue;
  }

  @Override
  public ValidatorBuilderInteger<PropertyBuilder<ByteProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderInteger<>(x));
  }

}