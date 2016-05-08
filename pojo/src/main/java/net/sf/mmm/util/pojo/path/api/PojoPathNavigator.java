/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import java.lang.reflect.Type;
import java.util.Map;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is the interface for the navigator used to traverse the object-web spanned by an initial
 * {@link net.sf.mmm.util.pojo.api.Pojo} reflectively according to a given {@link PojoPath}.
 *
 * <h3>Design</h3> The {@link PojoPathNavigator} is designed to be thread-safe and extensible. Therefore the
 * state is externalized to a {@link PojoPathContext context} that is provided as argument to the methods of
 * this interface.
 *
 * @see PojoPath
 * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@ComponentSpecification
public interface PojoPathNavigator {

  /**
   * This method evaluates the given {@code pojoPath} for the given {@code pojo} using the given {@code mode}
   * and {@code context}. It returns the result of the evaluation. <br>
   * <b>ATTENTION:</b><br>
   * If you use {@link PojoPathContext#getCache() caching} for repetitive calls on the same initial
   * {@code pojo}, you might get wrong results if intermediate objects have changed
   * {@link #set(Object, String, PojoPathMode, PojoPathContext, Object) outside this navigator} in the
   * meantime.
   *
   * @param pojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} to operate on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal with {@code null} values.
   * @param context is the {@link PojoPathContext} for this operation.
   * @return the result of the navigation of the given {@code pojoPath} starting at the given {@code pojo}. It
   *         may be {@code null} according to the given {@link PojoPathMode mode}.
   * @throws IllegalPojoPathException if the given {@code pojoPath} is illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} was
   *         {@code null} and the given {@code mode} disallows this.
   * @throws InstantiationFailedException if an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} was
   *         {@code null} and should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  Object get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException, InstantiationFailedException;

  /**
   * This method evaluates the given {@code pojoPath} for the given {@code pojo} using the given {@code mode}
   * and {@code context}. It returns the result of the evaluation. <br>
   * <b>ATTENTION:</b><br>
   * If you use {@link PojoPathContext#getCache() caching} for repetitive calls on the same initial
   * {@code pojo}, you might get wrong results if intermediate objects have changed
   * {@link #set(Object, String, PojoPathMode, PojoPathContext, Object) outside this navigator} in the
   * meantime.
   *
   * @param <TYPE> is the generic type of the result.
   * @param pojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} to operate on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal with {@code null} values.
   * @param context is the {@link PojoPathContext} for this operation.
   * @param targetClass is the required result-type.
   * @return the result of the navigation of the given {@code pojoPath} starting at the given {@code pojo}. It
   *         may be {@code null} according to the given {@link PojoPathMode mode}.
   * @throws IllegalPojoPathException if the given {@code pojoPath} is illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} was
   *         {@code null} and the given {@code mode} disallows this.
   * @throws InstantiationFailedException if an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} was
   *         {@code null} and should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   * @throws PojoPathConversionException if the result is NOT compatible with the given {@code targetClass}
   *         and could NOT be converted.
   */
  <TYPE> TYPE get(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context, Class<TYPE> targetClass)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException, InstantiationFailedException,
      PojoPathConversionException;

  /**
   * This method determines the result-type for the given {@link PojoPath pojoPath} starting at the given
   * {@code pojoType} using the given {@code context}. <br>
   * In other words if {@link #get(Object, String, PojoPathMode, PojoPathContext) get} is invoked on this
   * navigator with an instance of {@code pojoType} the result will be an instance of the type returned by
   * this method. <br>
   * Please note that the given {@link PojoPath pojoPath} may be <em>unsafe</em> , what means that it has a
   * {@link PojoPath#getSegment() segment} that points to a property that does NOT exist for the {@link Class}
   * determined for the according {@link net.sf.mmm.util.pojo.api.Pojo}. In other words a {@link PojoPath} is
   * unsafe if it can NOT be written as native Java method cascade without using casts in order to be
   * compiled. It may be illegal depending on the initial {@link net.sf.mmm.util.pojo.api.Pojo}.
   *
   * @param pojoType is the type of the initial {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param failOnUnsafePath determines how to deal with <em>unsafe</em> {@link PojoPath}s. If {@code true}
   *        and the given {@code pojoPath} is unsafe for the given {@code pojoType} an
   *        {@link PojoPathUnsafeException} is thrown and if {@code false} this method returns {@code null} in
   *        such case.
   * @param context is the {@link PojoPathContext} for this operation.
   * @return the guaranteed result-type of the navigation of the given {@code pojoPath} starting at the given
   *         {@code pojoType}. It may be {@code Object.class} e.g. in case an untyped
   *         {@link java.util.Collection} is hit. It will be {@code null} if the given {@code pojoPath} is
   *         <em>unsafe</em> and the given {@code mode} is {@link PojoPathMode#RETURN_IF_NULL}.
   * @throws IllegalPojoPathException if the given {@code pojoPath} is illegal.
   * @throws PojoPathUnsafeException if the given {@code pojoPath} is unsafe for the given {@code pojoType}
   *         and this is disallowed by {@code mode}.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  GenericType<?> getType(GenericType<?> pojoType, String pojoPath, boolean failOnUnsafePath, PojoPathContext context)
      throws PojoPathException, IllegalPojoPathException, PojoPathUnsafeException;

  /**
   * This method determines the result-type for the given {@link PojoPath pojoPath} starting at the given
   * {@code pojoType} using the given {@code context}. <br>
   * In other words if {@link #get(Object, String, PojoPathMode, PojoPathContext) get} is invoked on this
   * navigator with an instance of {@code pojoType} the result will be an instance of the type returned by
   * this method. <br>
   * Please note that the given {@link PojoPath pojoPath} may be <em>unsafe</em> , what means that it has a
   * {@link PojoPath#getSegment() segment} that points to a property that does NOT exist for the {@link Class}
   * determined for the according {@link net.sf.mmm.util.pojo.api.Pojo}. In other words a {@link PojoPath} is
   * unsafe if it can NOT be written as native Java method cascade without using casts in order to be
   * compiled. It may be illegal depending on the initial {@link net.sf.mmm.util.pojo.api.Pojo}.
   *
   * @param pojoType is the type of the initial {@link net.sf.mmm.util.pojo.api.Pojo}.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param failOnUnsafePath determines how to deal with <em>unsafe</em> {@link PojoPath}s. If {@code true}
   *        and the given {@code pojoPath} is unsafe for the given {@code pojoType} an
   *        {@link PojoPathUnsafeException} is thrown and if {@code false} this method returns {@code null} in
   *        such case.
   * @param context is the {@link PojoPathContext} for this operation.
   * @return the guaranteed result-type of the navigation of the given {@code pojoPath} starting at the given
   *         {@code pojoType}. It may be {@code Object.class} e.g. in case an untyped
   *         {@link java.util.Collection} is hit. It will be {@code null} if the given {@code pojoPath} is
   *         <em>unsafe</em> and the given {@code mode} is {@link PojoPathMode#RETURN_IF_NULL}.
   * @throws IllegalPojoPathException if the given {@code pojoPath} is illegal.
   * @throws PojoPathUnsafeException if the given {@code pojoPath} is unsafe for the given {@code pojoType}
   *         and this is disallowed by {@code mode}.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   */
  GenericType<?> getType(Type pojoType, String pojoPath, boolean failOnUnsafePath, PojoPathContext context)
      throws PojoPathException, IllegalPojoPathException, PojoPathUnsafeException;

  /**
   * This method sets the given {@code value} for the given {@code pojoPath} in the given {@code pojo} using
   * the given {@code mode} and {@code context}. It acts like a
   * {@link #get(Object, String, PojoPathMode, PojoPathContext) get} on the {@link PojoPath#getParentPath()
   * parent-path} and then setting the {@code value} for the remaining {@link PojoPath#getSegment() segment}
   * on the result. <br>
   * The result of this method is defined as following:<br>
   * <ul>
   * <li>If the last {@link PojoPath#getSegment() segment} points to a {@link PojoPathFunction} the result of
   * its {@link PojoPathFunction#set(Object, String, Object, PojoPathContext) set} -method is returned.</li>
   * <li>Otherwise if {@link PojoPath#getSegment() segment} points to an index,</li>
   * <li>the depends on the result of the {@link PojoPath#getParentPath() parent-path} is a
   * {@link java.util.List}, {@link java.util.Map} or array, this will be the old
   * {@link #get(Object, String, PojoPathMode, PojoPathContext) value} of the given {@code pojoPath} that has
   * been replaced by {@code value}.</li>
   * <li></li>
   * <li></li>
   * <li></li>
   * <li></li>
   * </ul>
   * If {@code pojo} is This will typically be the value that replaced value. It may be {@code null} .
   *
   * @param pojo is the initial {@link net.sf.mmm.util.pojo.api.Pojo} to operate on.
   * @param pojoPath is the {@link PojoPath} to navigate.
   * @param mode is the {@link PojoPathMode mode} that determines how to deal with {@code null} values.
   * @param context is the {@link PojoPathContext} for this operation.
   * @param value is the value to set. It may be {@code null}.
   * @return the result of the {@code set} operation.
   * @throws IllegalPojoPathException if the given {@code pojoPath} is illegal.
   * @throws PojoPathSegmentIsNullException if an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} was
   *         {@code null} and the given {@code mode} disallows this.
   * @throws InstantiationFailedException if an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} was
   *         {@code null} and should be created but the instantiation failed.
   * @throws PojoPathException if the operation failed for arbitrary reasons.
   * @throws PojoPathConversionException if the given {@code pojo} is NOT compatible with the type required
   *         for the given {@code pojoPath} and could NOT be converted.
   */
  Object set(Object pojo, String pojoPath, PojoPathMode mode, PojoPathContext context, Object value)
      throws PojoPathException, IllegalPojoPathException, PojoPathSegmentIsNullException, InstantiationFailedException,
      PojoPathConversionException;

  /**
   * This method creates a lazy, immutable {@link Map} reflecting the given
   * {@link net.sf.mmm.util.pojo.api.Pojo}. <br>
   * <b>ATTENTION:</b><br>
   * The {@link Map} will be {@link net.sf.mmm.util.collection.base.AbstractSimpleMap simple} and will NOT
   * support {@link Map#size() size} or {@link Map#keySet() iteration}.
   *
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder#pojo2Map(Object)
   * @since 1.1.1
   *
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} to convert.
   * @return the {@link Map} reflecting the given {@link net.sf.mmm.util.pojo.api.Pojo}.
   */
  Map<String, Object> pojo2Map(Object pojo);

  /**
   * This method creates a lazy, immutable {@link Map} reflecting the given
   * {@link net.sf.mmm.util.pojo.api.Pojo}. <br>
   * <b>ATTENTION:</b><br>
   * The {@link Map} will be {@link net.sf.mmm.util.collection.base.AbstractSimpleMap simple} and will NOT
   * support {@link Map#size() size} or {@link Map#keySet() iteration}.
   *
   * @see net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder#pojo2Map(Object)
   * @since 1.1.1
   *
   * @param pojo is the {@link net.sf.mmm.util.pojo.api.Pojo} to convert.
   * @param context is the {@link PojoPathContext} for this operation.
   * @return the {@link Map} reflecting the given {@link net.sf.mmm.util.pojo.api.Pojo}.
   */
  Map<String, Object> pojo2Map(Object pojo, PojoPathContext context);

}
