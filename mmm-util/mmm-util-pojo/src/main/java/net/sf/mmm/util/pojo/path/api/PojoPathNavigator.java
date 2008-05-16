/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the interface for the navigator used to traverse the object-web
 * spanned by an initial {@link net.sf.mmm.util.pojo.api.Pojo} reflectively
 * according to a given {@link PojoPath}.
 * 
 * <h3>Design</h3>
 * The {@link PojoPathNavigator} is designed to be thread-safe and extendable.
 * Therefore the state is externalized to a {@link PojoPathContext context} that
 * is provided as argument to the methods of this interface.
 * 
 * @see PojoPath
 * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder
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
   * intermediate objects have changed
   * {@link #set(Object, String, PojoPathMode, PojoPathContext, Object) outside this navigator}
   * in the meantime.
   * 
   * @param pojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} to operate
   *        on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        with <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @return the result of the navigation of the given <code>pojoPath</code>
   *         starting at the given <code>pojo</code>. It may be
   *         <code>null</code> according to the given
   *         {@link PojoPathMode mode}.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate
   *         {@link net.sf.mmm.util.pojo.api.Pojo} was <code>null</code> and
   *         the given <code>mode</code> disallows this.
   * @throws InstantiationFailedException if an intermediate
   *         {@link net.sf.mmm.util.pojo.api.Pojo} was <code>null</code> and
   *         should be created but the instantiation failed.
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
   * intermediate objects have changed
   * {@link #set(Object, String, PojoPathMode, PojoPathContext, Object) outside this navigator}
   * in the meantime.
   * 
   * @param <TYPE> is the generic type of the result.
   * @param pojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} to operate
   *        on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        with <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param targetClass is the required result-type.
   * @return the result of the navigation of the given <code>pojoPath</code>
   *         starting at the given <code>pojo</code>. It may be
   *         <code>null</code> according to the given
   *         {@link PojoPathMode mode}.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate
   *         {@link net.sf.mmm.util.pojo.api.Pojo} was <code>null</code> and
   *         the given <code>mode</code> disallows this.
   * @throws InstantiationFailedException if an intermediate
   *         {@link net.sf.mmm.util.pojo.api.Pojo} was <code>null</code> and
   *         should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   * @throws PojoPathConversionException if the result is NOT compatible with
   *         the given <code>targetClass</code> and could NOT be converted.
   */
  <TYPE> TYPE get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context,
      Class<TYPE> targetClass) throws PojoPathException, IllegalPojoPathException,
      PojoPathSegmentIsNullException, InstantiationFailedException, PojoPathConversionException;

  /**
   * This method determines the result-type for the given
   * <code>{@link PojoPath pojoPath}</code> starting at the given
   * <code>pojoType</code> using the given <code>context</code>.<br>
   * In other words if
   * {@link #get(Object, String, PojoPathMode, PojoPathContext) get} is invoked
   * on this navigator with an instance of <code>pojoType</code> the result
   * will be an instance of the type returned by this method.<br>
   * Please note that the given {@link PojoPath pojoPath} may be <em>unsafe</em>,
   * what means that it has a {@link PojoPath#getSegment() segment} that points
   * to a property that does NOT exist for the {@link Class} determined for the
   * according {@link net.sf.mmm.util.pojo.api.Pojo}. In other words a
   * {@link PojoPath} is unsafe if it can NOT be written as native Java method
   * cascade without using casts in order to be compiled. It may be illegal
   * depending on the initial {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param pojoType is the type of the initial
   *        {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param failOnUnsafePath determines how to deal with <em>unsafe</em>
   *        {@link PojoPath}s. If <code>true</code> and the given
   *        <code>pojoPath</code> is unsafe for the given
   *        <code>pojoType</code> an {@link PojoPathUnsafeException} is thrown
   *        and if <code>false</code> this method returns <code>null</code>
   *        in such case.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @return the guaranteed result-type of the navigation of the given
   *         <code>pojoPath</code> starting at the given <code>pojoType</code>.
   *         It may be <code>Object.class</code> e.g. in case an untyped
   *         {@link java.util.Collection} is hit. It will be <code>null</code>
   *         if the given <code>pojoPath</code> is <em>unsafe</em> and the
   *         given <code>mode</code> is {@link PojoPathMode#RETURN_IF_NULL}.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathUnsafeException if the given <code>pojoPath</code> is
   *         unsafe for the given <code>pojoType</code> and this is disallowed
   *         by <code>mode</code>.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  GenericType<?> getType(GenericType<?> pojoType, String pojoPath, boolean failOnUnsafePath,
      PojoPathContext context) throws PojoPathException, IllegalPojoPathException,
      PojoPathUnsafeException;

  /**
   * This method determines the result-type for the given
   * <code>{@link PojoPath pojoPath}</code> starting at the given
   * <code>pojoType</code> using the given <code>context</code>.<br>
   * In other words if
   * {@link #get(Object, String, PojoPathMode, PojoPathContext) get} is invoked
   * on this navigator with an instance of <code>pojoType</code> the result
   * will be an instance of the type returned by this method.<br>
   * Please note that the given {@link PojoPath pojoPath} may be <em>unsafe</em>,
   * what means that it has a {@link PojoPath#getSegment() segment} that points
   * to a property that does NOT exist for the {@link Class} determined for the
   * according {@link net.sf.mmm.util.pojo.api.Pojo}. In other words a
   * {@link PojoPath} is unsafe if it can NOT be written as native Java method
   * cascade without using casts in order to be compiled. It may be illegal
   * depending on the initial {@link net.sf.mmm.util.pojo.api.Pojo}.
   * 
   * @param pojoType is the type of the initial
   *        {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param failOnUnsafePath determines how to deal with <em>unsafe</em>
   *        {@link PojoPath}s. If <code>true</code> and the given
   *        <code>pojoPath</code> is unsafe for the given
   *        <code>pojoType</code> an {@link PojoPathUnsafeException} is thrown
   *        and if <code>false</code> this method returns <code>null</code>
   *        in such case.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @return the guaranteed result-type of the navigation of the given
   *         <code>pojoPath</code> starting at the given <code>pojoType</code>.
   *         It may be <code>Object.class</code> e.g. in case an untyped
   *         {@link java.util.Collection} is hit. It will be <code>null</code>
   *         if the given <code>pojoPath</code> is <em>unsafe</em> and the
   *         given <code>mode</code> is {@link PojoPathMode#RETURN_IF_NULL}.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathUnsafeException if the given <code>pojoPath</code> is
   *         unsafe for the given <code>pojoType</code> and this is disallowed
   *         by <code>mode</code>.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  GenericType<?> getType(Type pojoType, String pojoPath, boolean failOnUnsafePath,
      PojoPathContext context) throws PojoPathException, IllegalPojoPathException,
      PojoPathUnsafeException;

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
   * @param pojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} to operate
   *        on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        with <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param value is the value to set. It may be <code>null</code>.
   * @return the result of the <code>set</code> operation.
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate
   *         {@link net.sf.mmm.util.pojo.api.Pojo} was <code>null</code> and
   *         the given <code>mode</code> disallows this.
   * @throws InstantiationFailedException if an intermediate
   *         {@link net.sf.mmm.util.pojo.api.Pojo} was <code>null</code> and
   *         should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   * @throws PojoPathConversionException if the given <code>pojo</code> is NOT
   *         compatible with the type required for the given
   *         <code>pojoPath</code> and could NOT be converted.
   */
  Object set(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context, Object value)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException,
      InstantiationFailedException, PojoPathConversionException;

}
