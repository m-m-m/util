/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl.example;

import java.util.regex.Pattern;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.impl.StringPropertyImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValidator;
import net.sf.mmm.util.validation.base.ValidatorMandatory;
import net.sf.mmm.util.validation.base.ValidatorPattern;

/**
 * This is a {@link StringPropertyImpl} for a country code.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class CountryCodeProperty extends StringPropertyImpl {

  private static final Pattern PATTERN = Pattern.compile("[A-Z]{2}");

  private static final AbstractValidator<String> VALIDATOR = new ComposedValidator<>(
      ValidatorMandatory.getInstance(), new ValidatorPattern(PATTERN));

  /**
   * The constructor.
   *
   * @param name
   */
  public CountryCodeProperty(String name) {
    this(name, null);
  }

  /**
   * The constructor.
   *
   * @param name
   * @param bean
   */
  public CountryCodeProperty(String name, Bean bean) {
    super(name, bean, VALIDATOR);
  }

  @Override
  public StringPropertyImpl createFor(Bean newBean) {

    return new CountryCodeProperty(getName(), newBean);
  }

}
