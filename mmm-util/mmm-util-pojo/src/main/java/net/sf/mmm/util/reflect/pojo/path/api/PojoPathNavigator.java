/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import net.sf.mmm.util.reflect.InstantiationFailedException;

/**
 * This is the interface for the navigator used to traverse the object-web
 * spanned by an initial {@link net.sf.mmm.util.reflect.pojo.api.Pojo}
 * reflectively according to a given {@link PojoPath}.
 * 
 * <h3>Design</h3>
 * The {@link PojoPathNavigator} is designed to be thread-safe and extendable.
 * Therefore the state is externalized to a {@link PojoPathContext context} that
 * is provided as argument to the methods of this interface.
 * 
 * @see PojoPath
 * @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptorBuilder
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPathNavigator {

  /**
   * This method evaluates the given <code>pojoPath</code> for the given
   * <code>pojo</code> using the given <code>mode</code> and
   * <code>context</code>. It returns the result of the evaluation.<br>
   * <b>ATTENTION:</b><br>
   * If you use {@link PojoPathContext#getCache() caching} for repetitive calls
   * on the same initial <code>pojo</code>, you might get wrong results if
   * intermediate objects have changed in the meantime.
   * 
   * @param pojo is the initial {@link net.sf.mmm.util.reflect.pojo.api.Pojo} to
   *        operate on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @return the result of the navigation of the given <code>pojoPath</code>
   *         starting at the given <code>pojo</code>. It may be
   *         <code>null</code> according to the given
   *         {@link PojoPathMode mode}.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.api.Pojo} was <code>null</code>
   *         and the given <code>mode</code> disallows this.
   * @throws InstantiationFailedException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.api.Pojo} was <code>null</code>
   *         and should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  Object get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException,
      InstantiationFailedException;

  /**
   * This method evaluates the given <code>pojoPath</code> for the given
   * <code>pojo</code> using the given <code>mode</code> and
   * <code>context</code>. It returns the result of the evaluation.<br>
   * <b>ATTENTION:</b><br>
   * If you use {@link PojoPathContext#getCache() caching} for repetitive calls
   * on the same initial <code>pojo</code>, you might get wrong results if
   * intermediate objects have changed in the meantime.
   * 
   * @param <TYPE> is the generic type of the result.
   * @param pojo is the initial {@link net.sf.mmm.util.reflect.pojo.api.Pojo} to
   *        operate on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param targetClass is the required result-type.
   * @return the result of the navigation of the given <code>pojoPath</code>
   *         starting at the given <code>pojo</code>. It may be
   *         <code>null</code> according to the given
   *         {@link PojoPathMode mode}.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.api.Pojo} was <code>null</code>
   *         and the given <code>mode</code> disallows this.
   * @throws InstantiationFailedException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.api.Pojo} was <code>null</code>
   *         and should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   * @throws PojoPathConversionException if the result is NOT compatible with
   *         the given <code>targetClass</code> and could NOT be converted.
   */
  <TYPE> TYPE get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context,
      Class<TYPE> targetClass) throws PojoPathException, IllegalPojoPathException,
      PojoPathSegmentIsNullException, InstantiationFailedException, PojoPathConversionException;

  /**
   * This method sets the given <code>value</code> for the given
   * <code>pojoPath</code> in the given <code>pojo</code> using the given
   * <code>mode</code> and <code>context</code>. It acts like a
   * {@link #get(Object, String, PojoPathMode, PojoPathContext) get} on the
   * {@link PojoPath#getParentPath() parent-path} and then setting the
   * <code>value</code> for the remaining
   * {@link PojoPath#getSegment() segment} on the result.<br>
   * The result of this method is defined as following:<br>
   * <ul>
   * <li>If the last {@link PojoPath#getSegment() segment} points to a
   * {@link PojoPathFunction} the result of its
   * {@link PojoPathFunction#set(Object, String, Object, PojoPathContext) set}-method
   * is returned.</li>
   * <li>Otherwise if {@link PojoPath#getSegment() segment} points to an index,</li>
   * <li>the depends on the result of the
   * {@link PojoPath#getParentPath() parent-path} is a {@link java.util.List},
   * {@link java.util.Map} or array, this will be the old
   * {@link #get(Object, String, PojoPathMode, PojoPathContext) value} of the
   * given <code>pojoPath</code> that has been replaced by <code>value</code>.</li>
   * <li></li>
   * <li></li>
   * <li></li>
   * <li></li>
   * </ul>
   * If <code>pojo</code> is This will typically be the value that replaced
   * value. It may be <code>null</code>.
   * 
   * @param pojo is the initial {@link net.sf.mmm.util.reflect.pojo.api.Pojo} to
   *        operate on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param value is the value to set. It may be <code>null</code>.
   * @return the result of the <code>set</code> operation.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.api.Pojo} was <code>null</code>
   *         and the given <code>mode</code> disallows this.
   * @throws InstantiationFailedException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.api.Pojo} was <code>null</code>
   *         and should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   * @throws PojoPathConversionException if the given <code>pojo</code> is NOT
   *         compatible with the type required for the given
   *         <code>pojoPath</code> and could NOT be converted.
   */
  Object set(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context, Object value)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException,
      InstantiationFailedException, PojoPathConversionException;

}
