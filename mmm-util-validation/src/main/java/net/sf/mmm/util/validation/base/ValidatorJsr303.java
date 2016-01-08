/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is an implementation of {@link net.sf.mmm.util.validation.api.ValueValidator} that adapts to
 * <code>javax.validation</code> (JSR 303).
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorJsr303<V> extends AbstractValidator<V> {

  /** @see #validate(Object, Object) */
  private final Validator validator;

  /** @see #validate(Object, Object) */
  private final Class<?> pojoType;

  /** @see #validate(Object, Object) */
  private final String property;

  /** @see #validate(Object, Object) */
  private final Class<?> propertyType;

  /** @see #validate(Object, Object) */
  private final Class<?>[] groups;

  /** @see #isMandatory() */
  private final boolean mandatory;

  /**
   * The constructor.
   *
   * @param validator is the {@link Validator} instance.
   * @param pojoType is the type of the {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   */
  public ValidatorJsr303(Validator validator, Class<V> pojoType) {

    this(validator, pojoType, (String) null);
  }

  /**
   * The constructor.
   *
   * @param validator is the {@link Validator} instance.
   * @param pojoType is the type of the {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param groups are the {@link Validator#validate(Object, Class...) groups to use for validation}.
   */
  public ValidatorJsr303(Validator validator, Class<V> pojoType, Class<?>... groups) {

    this(validator, pojoType, null, null, groups);
  }

  /**
   * The constructor.
   *
   * @param validator is the {@link Validator} instance.
   * @param pojoType is the type of the {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param property is the property to validate or <code>null</code> to validate the entire object.
   */
  public ValidatorJsr303(Validator validator, Class<?> pojoType, String property) {

    this(validator, pojoType, property, null);
  }

  /**
   * The constructor.
   *
   * @param validator is the {@link Validator} instance.
   * @param pojoType is the type of the {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param property is the property to validate or <code>null</code> to validate the entire object.
   * @param propertyType is the {@link #getPropertyType() property type}.
   */
  public ValidatorJsr303(Validator validator, Class<?> pojoType, String property, Class<?> propertyType) {

    this(validator, pojoType, property, propertyType, Default.class);
  }

  /**
   * The constructor.
   *
   * @param validator is the {@link Validator} instance.
   * @param pojoType is the type of the {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param property is the property to validate or <code>null</code> to validate the entire object.
   * @param propertyType is the {@link #getPropertyType() property type}.
   * @param groups are the {@link Validator#validate(Object, Class...) groups to use for validation}.
   */
  public ValidatorJsr303(Validator validator, Class<?> pojoType, String property, Class<?> propertyType,
      Class<?>... groups) {

    super();
    NlsNullPointerException.checkNotNull(Validator.class, validator);
    NlsNullPointerException.checkNotNull("pojoType", pojoType);
    NlsNullPointerException.checkNotNull("groups", groups);
    if ((property == null) && (propertyType != null)) {
      throw new NlsIllegalArgumentException(propertyType, "propertyType (property=null)");
    }
    this.validator = validator;
    this.pojoType = pojoType;
    this.property = property;
    this.propertyType = propertyType;
    this.groups = groups;
    this.mandatory = calculateMandatoryFlag();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <P> P getProperty(TypedProperty<P> typedProperty) {

    if (typedProperty == PROPERTY_MANDATORY) {
      if (this.mandatory) {
        return (P) Boolean.TRUE;
      }
      return null;
    }
    return super.getProperty(typedProperty);
  }

  /**
   * @return the calculated value for {@link #isMandatory()}.
   */
  private boolean calculateMandatoryFlag() {

    // This is quite a stupid way to figure this out...
    if (this.property != null) {
      if ((this.propertyType != null) && this.propertyType.isPrimitive()) {
        return true;
      }
      BeanDescriptor beanDescriptor = this.validator.getConstraintsForClass(this.pojoType);
      PropertyDescriptor propertyDescriptor = beanDescriptor.getConstraintsForProperty(this.property);
      if (propertyDescriptor == null) {
        // not annotated
        return false;
      }
      Set<ConstraintDescriptor<?>> constraintDescriptors = propertyDescriptor.getConstraintDescriptors();
      for (ConstraintDescriptor<?> descriptor : constraintDescriptors) {
        Class<? extends Annotation> annotationType = descriptor.getAnnotation().annotationType();
        if (Mandatory.class == annotationType) {
          return true;
        } else if (NotNull.class == annotationType) {
          return true;
        }
      }
      // Set<ConstraintViolation<?>> violationSet = validateJsr303(null);
      // if (!violationSet.isEmpty()) {
      // return true;
      // }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationFailure validate(V value, Object valueSource) {

    if ((value == null) && (this.propertyType != null) && this.propertyType.isPrimitive()) {
      return new ValidationFailureImpl(Mandatory.class.getSimpleName(), valueSource,
          createBundle(NlsBundleUtilCoreRoot.class).errorMandatory());
    }
    Set<ConstraintViolation<?>> violationSet = validateJsr303(value);
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
   * Validates the given <code>value</code>.
   *
   * @param value is the value to validate.
   * @return the {@link Set} of {@link ConstraintViolation}s. Will be empty if the given <code>value</code> is valid.
   */
  private Set<ConstraintViolation<?>> validateJsr303(V value) {

    @SuppressWarnings("rawtypes")
    Set set;
    if (this.property == null) {
      if (value == null) {
        return null;
      }
      set = this.validator.validate(value, this.groups);
    } else {
      set = this.validator.validateValue(this.pojoType, this.property, value, this.groups);
    }
    @SuppressWarnings("unchecked")
    Set<ConstraintViolation<?>> violationSet = set;
    return violationSet;
  }

  /**
   * Creates a {@link ValidationFailure} for the given {@link ConstraintViolation}.
   *
   * @param violation is the {@link ConstraintViolation}.
   * @param valueSource is the source of the value. May be <code>null</code>.
   * @return the created {@link ValidationFailure}.
   */
  protected ValidationFailure createValidationFailure(ConstraintViolation<?> violation, Object valueSource) {

    String code = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
    return new ValidationFailureImpl(code, valueSource, violation.getMessage());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getCode() {

    StringBuilder buffer = new StringBuilder("JSR303@");
    buffer.append(this.pojoType.getSimpleName());
    if (this.property != null) {
      buffer.append('.');
      buffer.append(this.property);
    }
    return buffer.toString();
  }

  /**
   * @return the {@link Class} reflecting the {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   */
  public Class<?> getPojoType() {

    return this.pojoType;
  }

  /**
   * @return the name of the property to validate or <code>null</code> to validate the entire {@link #getPojoType()
   *         POJO}.
   */
  public String getProperty() {

    return this.property;
  }

  /**
   * @return the {@link Class} reflecting the type of the {@link #getProperty() property} to validate or
   *         <code>null</code> if undefined. May be used for additional support currently missing in JSR 303 such as
   *         primitive types that are implicitly mandatory but implementations of JSR 303 cause
   *         {@link NullPointerException} or similar effects on property validation.
   */
  public Class<?> getPropertyType() {

    return this.propertyType;
  }
}
