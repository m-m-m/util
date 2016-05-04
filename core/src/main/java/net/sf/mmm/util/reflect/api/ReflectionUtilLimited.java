/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

/**
 * This is a limited subset of {@link ReflectionUtil} that is GWT compatible.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface ReflectionUtilLimited extends GenericTypeFactory {

  /** an empty class array */
  Class<?>[] NO_PARAMETERS = new Class[0];

  /** an empty {@link Object}-array */
  Object[] NO_ARGUMENTS = new Object[0];

  /**
   * This method gets the according non-{@link Class#isPrimitive() primitive} type for the class given by {@code type}.
   * <br>
   * E.g. {@link #getNonPrimitiveType(Class) getNonPrimitiveType}{@code (int.class)} will return {@code Integer.class}.
   *
   *
   * @see Class#isPrimitive()
   *
   * @param <T> is the generic type of the given {@code type} {@link Class}.
   * @param type is the (potentially) {@link Class#isPrimitive() primitive} type.
   * @return the according object-type for the given {@code type}. This will be the given {@code type} itself if it is
   *         NOT {@link Class#isPrimitive() primitive}.
   * @since 1.0.2
   */
  <T> Class<T> getNonPrimitiveType(Class<T> type);

}
