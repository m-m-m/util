/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is a builder to create multiple instances of {@link ValidatorJsr303}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorBuilderJsr303 extends AbstractValidatorBuilder {

  /** @see #newValidator(Class, String) */
  private final Validator validator;

  /** @see #newValidator(Class, String) */
  private Class<?>[] groups;

  /**
   * The constructor.
   */
  public ValidatorBuilderJsr303() {

    this(Validation.buildDefaultValidatorFactory().getValidator());
  }

  /**
   * The constructor.
   *
   * @param validator is the {@link Validator} instance to use.
   */
  public ValidatorBuilderJsr303(Validator validator) {

    super();
    this.validator = validator;
    this.groups = new Class<?>[] { Default.class };
  }

  /**
   * @return the {@link Validator} instance.
   */
  public Validator getValidator() {

    return this.validator;
  }

  /**
   * @return the current groups. Initially only containing {@link Default}.
   */
  public Class<?>[] getGroups() {

    return this.groups;
  }

  /**
   * @param groups are the {@link #getGroups() groups} to set.
   */
  public void setGroups(Class<?>[] groups) {

    this.groups = groups;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <V> AbstractValidator<V> newValidator(Class<V> pojoType) {

    return new ValidatorJsr303<>(this.validator, pojoType, this.groups);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public AbstractValidator<?> newValidator(Class<?> pojoType, String property, Class<?> propertyType) {

    return new ValidatorJsr303(this.validator, pojoType, property, propertyType, this.groups);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> AbstractValidator<T> newValidator(Class<?> pojoType, TypedProperty<T> property, Class<T> propertyType) {

    return new ValidatorJsr303<>(this.validator, pojoType, property.getPojoPath(), propertyType, this.groups);
  }
}
