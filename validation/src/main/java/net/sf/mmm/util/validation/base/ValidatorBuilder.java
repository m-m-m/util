/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is the interface for a builder of {@link net.sf.mmm.util.validation.api.ValueValidator} instances from
 * annotations (JSR 303).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface ValidatorBuilder {

  /**
   * Creates a new instance of {@link net.sf.mmm.util.validation.api.ValueValidator} validating the specified
   * {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param <V> is the generic type of <code>pojoType</code>.
   * @param pojoType is the {@link Class} reflecting to {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @return the new {@link net.sf.mmm.util.validation.api.ValueValidator} instance.
   */
  <V> AbstractValidator<V> newValidator(Class<V> pojoType);

  /**
   * Creates a new instance of {@link net.sf.mmm.util.validation.api.ValueValidator} validating the given
   * <code>property</code> of the specified {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param pojoType is the {@link Class} reflecting to {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param property is the property to validate.
   * @return the new {@link net.sf.mmm.util.validation.api.ValueValidator} instance.
   */
  AbstractValidator<?> newValidator(Class<?> pojoType, String property);

  /**
   * Creates a new instance of {@link net.sf.mmm.util.validation.api.ValueValidator} validating the given
   * <code>property</code> of the specified {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param pojoType is the {@link Class} reflecting to {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param property is the property to validate.
   * @param propertyType is the {@link ValidatorJsr303#getPropertyType() property type}.
   * @return the new {@link net.sf.mmm.util.validation.api.ValueValidator} instance.
   */
  AbstractValidator<?> newValidator(Class<?> pojoType, String property, Class<?> propertyType);

  /**
   * Creates a new instance of {@link net.sf.mmm.util.validation.api.ValueValidator} validating the given
   * <code>property</code> of the specified {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param <T> is the generic {@link TypedProperty#getPropertyType() type of the property}.
   * @param pojoType is the {@link Class} reflecting to {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param property is the {@link TypedProperty} to validate.
   * @return the new {@link net.sf.mmm.util.validation.api.ValueValidator} instance.
   */
  <T> AbstractValidator<T> newValidator(Class<?> pojoType, TypedProperty<T> property);

  /**
   * Creates a new instance of {@link net.sf.mmm.util.validation.api.ValueValidator} validating the given
   * <code>property</code> of the specified {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param <T> is the generic {@link TypedProperty#getPropertyType() type of the property}.
   * @param pojoType is the {@link Class} reflecting to {@link net.sf.mmm.util.pojo.api.Pojo} to validate.
   * @param property is the {@link TypedProperty} to validate.
   * @param propertyType is the {@link ValidatorJsr303#getPropertyType() property type}.
   * @return the new {@link net.sf.mmm.util.validation.api.ValueValidator} instance.
   */
  <T> AbstractValidator<T> newValidator(Class<?> pojoType, TypedProperty<T> property, Class<T> propertyType);

}
