/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * {@link Enum} with the available types of {@link Bean} methods.
 *
 * @see BeanMethod
 * @see BeanPrototypeOperation
 *
 * @author hohwille
 * @since 8.0.0
 */
public enum BeanMethodType {

  /**
   * Getter for {@link WriteableProperty#getValue() property value}.
   *
   * @see BeanPrototypeOperationGet
   */
  GET,

  /**
   * Setter for {@link WriteableProperty#setValue(Object) property value}.
   *
   * @see BeanPrototypeOperationSet
   */
  SET,

  /**
   * Getter for {@link WriteableProperty property}.
   *
   * @see BeanPrototypeOperationProperty
   */
  PROPERTY,

  /**
   * {@link Bean#access()}.
   *
   * @see BeanPrototypeOperationAccess
   */
  ACCESS,

  /**
   * {@link Bean#equals(Object)}.
   *
   * @see BeanPrototypeOperationEquals
   */
  EQUALS,

  /**
   * {@link Bean#hashCode()}.
   *
   * @see BeanPrototypeOperationHashCode
   */
  HASH_CODE,

  /**
   * {@link Bean#toString()}.
   *
   * @see BeanPrototypeOperationToString
   */
  TO_STRING,

  /**
   * Unmapped {@link Method#isDefault() default method}.
   *
   * @see BeanPrototypeOperationDefaultMethod
   */
  DEFAULT_METHOD

}
