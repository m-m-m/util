/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

/**
 * This is a limited subset of {@link ReflectionUtil} that is GWT compatible.
 * 
 * @author hohwille
 * @since 3.0.0
 */
public interface ReflectionUtilLimited {

  /**
   * This method gets the according non-{@link Class#isPrimitive() primitive} type for the class given by
   * <code>type</code>.<br>
   * E.g. <code>{@link #getNonPrimitiveType(Class) getNonPrimitiveType}(int.class)</code> will return
   * <code>Integer.class</code>.
   * 
   * @see Class#isPrimitive()
   * 
   * @param type is the (potentially) {@link Class#isPrimitive() primitive} type.
   * @return the according object-type for the given <code>type</code>. This will be the given
   *         <code>type</code> itself if it is NOT {@link Class#isPrimitive() primitive}.
   * @since 1.0.2
   */
  Class<?> getNonPrimitiveType(Class<?> type);

}
