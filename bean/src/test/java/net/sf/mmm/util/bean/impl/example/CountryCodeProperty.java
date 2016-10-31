/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import java.util.regex.Pattern;

import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValidator;
import net.sf.mmm.util.validation.base.ValidatorMandatory;
import net.sf.mmm.util.validation.base.text.ValidatorPattern;

/**
 * This is a {@link StringProperty} for a country code.
 *
 * @author hohwille
 */
public class CountryCodeProperty extends StringProperty {

  private static final Pattern PATTERN = Pattern.compile("[A-Z]{2}");

  private static final AbstractValidator<String> VALIDATOR = new ComposedValidator<>(ValidatorMandatory.getInstance(), new ValidatorPattern(PATTERN));

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public CountryCodeProperty(String name, Object bean) {
    super(name, bean, VALIDATOR);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  protected CountryCodeProperty(String name, Object bean, AbstractValidator<? super String> validator) {
    super(name, bean, validator);
  }

}
