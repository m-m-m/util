/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import net.sf.mmm.util.reflect.InstantiationFailedException;

/**
 * This is the interface for the navigator used to traverse the object-web
 * spanned by an initial {@link net.sf.mmm.util.reflect.pojo.Pojo} reflectively
 * according to a given {@link PojoPath}.
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
   * The character that separates the &#171;Segment&#187;s of a
   * <em>POJO-path</em>.
   */
  char SEPARATOR = '.';

  /**
   * This method evaluates the given <code>pojoPath</code> for the given
   * <code>pojo</code> using the given <code>mode</code> and
   * <code>context</code>. It returns the result of the evaluation.
   * 
   * @param pojo is the initial POJO to operate on.
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
   * @throws PojoPathSegmentIsNullException if an intermediate POJO was
   *         <code>null</code> and the given <code>mode</code> disallows
   *         this.
   * @throws InstantiationFailedException if an intermediate POJO was
   *         <code>null</code> and should be created but the instantiation
   *         failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  Object get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException,
      InstantiationFailedException;

  /**
   * This method sets the given <code>value</code> for the given
   * <code>pojoPath</code> in the given <code>pojo</code> using the given
   * <code>mode</code> and <code>context</code>. It acts like a
   * {@link #get(Object, String, PojoPathMode, PojoPathContext) get} on the
   * {@link PojoPath#getParentPath() parent-path} and then setting the
   * <code>value</code> for the remaining
   * {@link PojoPath#getSegment() segment} on the result.
   * 
   * @param pojo is the initial {@link net.sf.mmm.util.reflect.pojo.Pojo} to
   *        operate on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal
   *        <code>null</code> values.
   * @param context is the {@link PojoPathContext context} for this operation.
   * @param value is the value to set. It may be <code>null</code>.
   * @return the value that has actually been set. This will typically be the
   *         given <code>value</code> itself. However in specific situations
   *         the value may have been converted. The result will be
   *         <code>null</code> if the value has NOT been set (because an
   *         intermediate POJO was <code>null</code>).
   * @throws IllegalPojoPathException if the given <code>pojoPath</code> is
   *         illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.Pojo} was <code>null</code>
   *         and the given <code>mode</code> disallows this.
   * @throws InstantiationFailedException if an intermediate
   *         {@link net.sf.mmm.util.reflect.pojo.Pojo} was <code>null</code>
   *         and should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  Object set(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context, Object value)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException,
      InstantiationFailedException;

}
