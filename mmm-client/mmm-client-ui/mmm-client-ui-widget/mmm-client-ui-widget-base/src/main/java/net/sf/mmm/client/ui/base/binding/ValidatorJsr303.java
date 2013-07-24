/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValidationFailure;
import net.sf.mmm.util.validation.base.SimpleValidationFailure;

/**
 * This is an implementation of {@link net.sf.mmm.util.validation.api.ValueValidator} that adapts to
 * {@link javax.validation} (JSR 303).
 * 
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ValidatorJsr303<V> extends AbstractValidator<V> {

  /** @see #validate(Object, Object) */
  private final Validator validator;

  /** @see #validate(Object, Object) */
  private final PojoDescriptor descriptor;

  /** @see #validate(Object, Object) */
  private final String property;

  /** @see #validate(Object, Object) */
  private final Class<?>[] groups;

  /**
   * The constructor.
   * 
   * @param validator is the {@link Validator} instance.
   */
  public ValidatorJsr303(Validator validator, PojoDescriptor<?> descriptor) {

    this(validator, descriptor, (String) null);
  }

  /**
   * The constructor.
   * 
   * @param validator is the {@link Validator} instance.
   * @param groups are the {@link Validator#validate(Object, Class...) groups to use for validation}.
   */
  public ValidatorJsr303(Validator validator, PojoDescriptor<?> descriptor, Class<?>... groups) {

    this(validator, descriptor, null, groups);
  }

  /**
   * The constructor.
   * 
   * @param validator is the {@link Validator} instance.
   * @param property is the property to validate or <code>null</code> to validate the entire object.
   */
  public ValidatorJsr303(Validator validator, PojoDescriptor<?> descriptor, String property) {

    this(validator, descriptor, property, Default.class);
  }

  /**
   * The constructor.
   * 
   * @param validator is the {@link Validator} instance.
   * @param property is the property to validate or <code>null</code> to validate the entire object.
   * @param groups are the {@link Validator#validate(Object, Class...) groups to use for validation}.
   */
  public ValidatorJsr303(Validator validator, PojoDescriptor<?> descriptor, String property, Class<?>... groups) {

    super();
    this.validator = validator;
    this.descriptor = descriptor;
    this.property = property;
    this.groups = groups;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationFailure validate(V value, Object valueSource) {

    Set set;
    if (this.property == null) {
      if (value == null) {
        return null;
      }
      set = this.validator.validate(value, this.groups);
    } else {
      Object pojo = this.descriptor.newInstance();
      this.descriptor.setProperty(pojo, this.property, value);
      set = this.validator.validateProperty(pojo, this.property, this.groups);
    }
    Set<ConstraintViolation<?>> violationSet = set;
    int size = violationSet.size();
    if (size == 1) {
      ConstraintViolation<?> violation = violationSet.iterator().next();
      return createValidationFailure(violation, valueSource);
    } else if (size > 1) {
      ValidationFailure[] failures = new ValidationFailure[size];
      int i = 0;
      for (ConstraintViolation<?> violation : violationSet) {
        failures[i++] = createValidationFailure(violation, valueSource);
      }
      return new ComposedValidationFailure(getCode(), valueSource, failures);
    }
    return null;
  }

  /**
   * Creates a {@link ValidationFailure} for the given {@link ConstraintViolation}.
   * 
   * @param violation is the {@link ConstraintViolation}.
   * @param valueSource is the source of the value. May be <code>null</code>.
   * @return the created {@link ValidationFailure}.
   */
  protected ValidationFailure createValidationFailure(ConstraintViolation<?> violation, Object valueSource) {

    return new SimpleValidationFailure(violation.getConstraintDescriptor().toString(), valueSource,
        violation.getMessage());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getCode() {

    return "Jsr303";
  }
}
