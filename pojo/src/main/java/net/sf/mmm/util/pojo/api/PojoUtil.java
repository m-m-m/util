/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

import java.util.Set;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a collection of utility functions to deal with {@link Pojo POJOs}.
 *
 * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder
 * @see net.sf.mmm.util.reflect.api.ReflectionUtil
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.1.0
 */
@ComponentSpecification
public interface PojoUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.pojo.api.PojoUti";

  /**
   * This method recursively traverses all objects starting from the given {@link Object} via all properties and
   * contents. It simply delegates to {@link #visitObjectRecursive(Object, Filter, boolean)} using {@code true} for
   * {@code loopProtection}.
   *
   * @param object is the {@link Object} to traverse recursively.
   * @param visitor is the {@link Filter} {@link Filter#accept(Object) invoked} for all traversed {@link Object}s. If an
   *        {@link Object} is not {@link Filter#accept(Object) accepted} by this {@link Filter} the recursion stops at
   *        this point.
   */
  void visitObjectRecursive(Object object, Filter<Object> visitor);

  /**
   * This method recursively traverses all objects starting from the given {@link Object} via all properties and
   * contents. It supports {@link java.util.Collection}s, {@link java.util.Map}s, {@link Object} {@link Class#isArray()
   * arrays}, and {@link net.sf.mmm.util.pojo.api.Pojo}s. <br>
   * <b>ATTENTION:</b><br>
   * This method will NOT traverse into {@link Class#isPrimitive() primitive} {@link Class#isArray() arrays} for
   * performance reasons. Simply realize this inside your {@code visitor} if you need it.
   *
   * @param object is the {@link Object} to traverse recursively.
   * @param visitor is the {@link Filter} {@link Filter#accept(Object) invoked} for all traversed {@link Object}s. If an
   *        {@link Object} is not {@link Filter#accept(Object) accepted} by this {@link Filter} the recursion stops at
   *        this point.
   * @param loopProtection - {@code true} to collect all visited {@link Object}s in a {@link Set} in order to
   *        prevent infinity loops, {@code false} otherwise (to save performance if no loops are possible for the
   *        given {@link Object}).
   */
  void visitObjectRecursive(Object object, Filter<Object> visitor, boolean loopProtection);

}
