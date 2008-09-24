/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Type;

/**
 * This is the interface of a generic type and allows simple and powerful access
 * to the complex generic type-system introduced in Java5.<br>
 * It represents a {@link Type} (available via {@link #getType()}) but allows
 * easy access to resolve the actual {@link Class} for {@link #getLowerBound()
 * assignment} and {@link #getUpperBound() retrieval}. This includes resolving
 * {@link java.lang.reflect.TypeVariable}s as far as possible.<br>
 * Have a look at the following example:<br>
 * 
 * <pre>
 * public class Foo&lt;A, B&gt; { 
 *   A getA() { ... } 
 *   B getB() { ... } 
 * }
 * public class Bar&lt;X&gt; extends Foo&lt;X, String&gt; { 
 *   ... 
 * }
 * public class Some extends Bar&lt;Long&gt; { 
 *   ... 
 * }
 * </pre>
 * 
 * If you want to determine the type of <code>Some.getA()</code> reflectively,
 * you will have to dive into the deepest and trickiest part of the reflection
 * API and might step into one of the many pitfalls on this way. Or you simply
 * use the features offered via this API.<br>
 * 
 * @see net.sf.mmm.util.reflect.api.ReflectionUtil#createGenericType(Type)
 * @see net.sf.mmm.util.reflect.api.ReflectionUtil#createGenericType(Type,
 *      GenericType)
 * 
 * @param <T> is the templated type of the {@link #getUpperBound() upper bound}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface GenericType<T> {

  /** An empty {@link GenericType}-array. */
  GenericType<?>[] NO_TYPES = new GenericType[0];

  /**
   * This method gets the {@link Class} that reflects the lower bound of the
   * {@link #getType() value-type} represented by this {@link GenericType}.<br>
   * It is the {@link Class} to be used for assignment (as parameter) of a value
   * of this {@link GenericType}. It will only differ from the
   * {@link #getUpperBound() upper bound} if this {@link GenericType} includes a
   * {@link java.lang.reflect.WildcardType}.<br>
   * E.g. for <code>&lt;? super Integer&gt;</code> this method will return
   * <code>{@link Integer}</code>.<br>
   * The {@link #getLowerBound() lower bound} is always equal or more specific
   * to the {@link #getUpperBound() upper bound}.
   * 
   * @return the {@link Class} that is the lower bound.
   */
  Class<? extends T> getLowerBound();

  /**
   * This method gets the {@link Class} that reflects the upper bound of the
   * {@link #getType() value-type} represented by this {@link GenericType}.<br>
   * It is the {@link Class} to be used for retrieval (the return-type) of a
   * value of this {@link GenericType}. It will only differ from the
   * {@link #getLowerBound() lower bound} if this {@link GenericType} includes a
   * {@link java.lang.reflect.WildcardType}.<br>
   * E.g. for <code>&lt;? super Integer&gt;</code> this method will return
   * <code>{@link Object}</code> and for <code>&lt;? extends Number&gt;</code>
   * this method will return {@link Number}.<br>
   * The {@link #getUpperBound() upper bound} is always
   * {@link Class#isAssignableFrom(Class) assignable from} the
   * {@link #getLowerBound() lower bound}.
   * 
   * @return the {@link Class} that is the upper bound.
   */
  Class<T> getUpperBound();

  /**
   * This method gets the {@link Type} represented by this {@link GenericType}.
   * 
   * @see Class#getGenericSuperclass()
   * @see Class#getGenericInterfaces()
   * @see java.lang.reflect.Method#getGenericReturnType()
   * @see java.lang.reflect.Method#getGenericParameterTypes()
   * @see java.lang.reflect.Field#getGenericType()
   * @see java.lang.reflect.Constructor#getGenericParameterTypes()
   * 
   * @return the value-type.
   */
  Type getType();

  /**
   * This method gets the component-type of this {@link GenericType} if it
   * represents an array, {@link java.util.Collection} or {@link java.util.Map}.<br>
   * Here are some examples:<br>
   * <table border="1">
   * <tr>
   * <th>type</th>
   * <th>{@link #getComponentType()}</th>
   * </tr>
   * <tr>
   * <td><code>List&lt;Map&lt;String, Long&gt;&gt;</code></td>
   * <td><code>Map&lt;String, Long&gt;</code></td>
   * </tr>
   * <tr>
   * <td><code>List</code></td>
   * <td><code>Object</code></td>
   * </tr>
   * <tr>
   * <td><code>Foo[]</code></td>
   * <td><code>Foo</code></td>
   * </tr>
   * <tr>
   * <td><code>Foo&lt;Bar&gt;[]</code></td>
   * <td><code>Foo&lt;Bar&gt;</code></td>
   * </tr>
   * <tr>
   * <td><code>Foo&lt;Bar&gt;</code></td>
   * <td><code>null</code></td>
   * </tr>
   * <tr>
   * <td><code>Map&lt;String, Long&gt;</code></td>
   * <td><code>Long</code></td>
   * </tr>
   * </table>
   * 
   * @return the component-type of this {@link GenericType} or <code>null</code>
   *         if this {@link GenericType} does NOT represent an array,
   *         {@link java.util.Collection} or {@link java.util.Map}.
   */
  GenericType<?> getComponentType();

  /**
   * This method gets the number of {@link #getTypeArgument(int) type-arguments}
   * .
   * 
   * @return the type-argument count.
   */
  int getTypeArgumentCount();

  /**
   * This method gets the type-argument at the given <code>index</code>.<br>
   * E.g. for the {@link GenericType} representing
   * <code>Map&lt;String, List&lt;Integer&gt;&gt;</code> this method would
   * return <code>String</code> for an <code>index</code> of <code>0</code> and
   * <code>List&lt;Integer&gt;</code> for an <code>index</code> of
   * <code>1</code>.
   * 
   * @see #getTypeArgumentCount()
   * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
   * 
   * @param index is the position of the requested type-argument. It has to be
   *        in the range from <code>0</code> to
   *        <code>{@link #getTypeArgumentCount()} - 1</code>.
   * @return the type-argument at the given <code>index</code>.
   */
  GenericType<?> getTypeArgument(int index);

  /**
   * This method determines if this {@link GenericType} is equal to or a
   * super-type of the given <code>subType</code>.
   * 
   * @see Class#isAssignableFrom(Class)
   * 
   * @param subType is the potential sub-type of this {@link GenericType}.
   * @return <code>true</code> if objects of the type <code>subType</code> can
   *         be assigned to this {@link GenericType}.
   */
  boolean isAssignableFrom(GenericType<?> subType);

  /**
   * This method gets the string representation of this {@link GenericType}. In
   * case the underlying {@link #getType() value-type} is a regular
   * {@link Class}, this method will return its {@link Class#getName() qualified
   * name} otherwise it will return the string representation of the generic
   * type information (e.g.
   * <code>java.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.Integer&gt;&gt;[]</code>
   * ).
   * 
   * @return this {@link GenericType} as string.
   */
  String toString();

}
