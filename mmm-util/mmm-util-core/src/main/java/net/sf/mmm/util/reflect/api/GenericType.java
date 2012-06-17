/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Type;

/**
 * This is the interface of a generic type and allows simple and powerful access to the complex generic
 * type-system introduced in Java5.<br>
 * It represents a {@link Type} (available via {@link #getType()}) but allows easy access to resolve the
 * actual erasure {@link Class} for {@link #getAssignmentClass() assignment} and {@link #getRetrievalClass()
 * retrieval}. This includes resolving {@link java.lang.reflect.TypeVariable}s as far as possible.<br>
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
 * If you want to determine the type of <code>Some.getA()</code> reflectively, you will have to dive into the
 * deepest and trickiest part of the reflection API and might step into one of the many pitfalls on this way.
 * All this is solved for you, if you use what is offered via this API.<br>
 * <b>LIMITATIONS:</b><br>
 * This solution will only support one upper and one lower bound but NOT multiple bounds of the same kind.
 * However this is more a feature than a limitation as it makes the usage simple and IMHO using multiple
 * bounds is a quite uncommon feature that should be avoided.
 * 
 * @see net.sf.mmm.util.reflect.api.ReflectionUtil#createGenericType(Type)
 * @see net.sf.mmm.util.reflect.api.ReflectionUtil#createGenericType(Type, GenericType)
 * 
 * @param <T> is the generic type of the {@link #getRetrievalClass() retrieval class}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface GenericType<T> extends Type {

  /** An empty {@link GenericType}-array. */
  GenericType<?>[] NO_TYPES = new GenericType[0];

  /**
   * This method gets the {@link Class} to be used for assignment (as parameter) of a value of this
   * {@link GenericType}.<br>
   * It will only differ from the {@link #getRetrievalClass() retrieval-class} if this {@link GenericType} is
   * a {@link java.lang.reflect.WildcardType}.<br>
   * Unlike the {@link java.lang.reflect.WildcardType#getLowerBounds() lower-bound}, the assignment-class is
   * never <code>null</code>. If there is no {@link java.lang.reflect.WildcardType#getLowerBounds()
   * lower-bound}, the {@link #getAssignmentClass() assignment-class} is the same as the
   * {@link #getRetrievalClass() retrieval-class}. Therefore the {@link #getAssignmentClass()
   * assignment-class} is always equal or more specific to the {@link #getRetrievalClass() retrieval-class}. <br>
   * Here are some examples:
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>{@link #getAssignmentClass()}</th>
   * </tr>
   * <tr>
   * <td><code>&lt;? super Integer&gt;</code></td>
   * <td><code>{@link Integer}</code></td>
   * </tr>
   * <tr>
   * <td><code>&lt;? extends CharSequence&gt;</code></td>
   * <td><code>{@link CharSequence}</code></td>
   * </tr>
   * <tr>
   * <td><code>String</code></td>
   * <td><code>{@link String}</code></td>
   * </tr>
   * </table>
   * 
   * @return the {@link Class} that is the lower bound.
   */
  Class<? extends T> getAssignmentClass();

  /**
   * This method gets the {@link Class} to be used for retrieval (the return-type) of a value of this
   * {@link GenericType}. <br>
   * It will only differ from the {@link #getAssignmentClass() assignment-class} if this {@link GenericType}
   * is a {@link java.lang.reflect.WildcardType}.<br>
   * The {@link #getRetrievalClass() retrieval-class} is the
   * {@link java.lang.reflect.WildcardType#getUpperBounds() upper-bound}, however for usability and simplicity
   * only one bound is supported.<br>
   * Here are some examples:
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>{@link #getAssignmentClass()}</th>
   * </tr>
   * <tr>
   * <td><code>&lt;? super Integer&gt;</code></td>
   * <td><code>{@link Object}</code></td>
   * </tr>
   * <tr>
   * <td><code>&lt;? extends CharSequence&gt;</code></td>
   * <td><code>{@link CharSequence}</code></td>
   * </tr>
   * <tr>
   * <td><code>String</code></td>
   * <td><code>{@link String}</code></td>
   * </tr>
   * </table>
   * 
   * @return the {@link Class} that is the upper bound.
   */
  Class<T> getRetrievalClass();

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
   * This method gets the component-type of this {@link GenericType} if it represents an array,
   * {@link java.util.Collection} or {@link java.util.Map}.<br>
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
   * @return the component-type of this {@link GenericType} or <code>null</code> if this {@link GenericType}
   *         does NOT represent an array, {@link java.util.Collection} or {@link java.util.Map}.
   */
  GenericType<?> getComponentType();

  /**
   * This method gets the key-type of this {@link GenericType} if it represents a {@link java.util.Map}.<br>
   * Here are some examples:<br>
   * <table border="1">
   * <tr>
   * <th>type</th>
   * <th>{@link #getComponentType()}</th>
   * </tr>
   * <tr>
   * <td><code>List&lt;Map&lt;String, Long&gt;&gt;</code></td>
   * <td><code>null</code></td>
   * </tr>
   * <tr>
   * <td><code>Map</code></td>
   * <td><code>Object</code></td>
   * </tr>
   * <tr>
   * <td><code>Foo[]</code></td>
   * <td><code>null</code></td>
   * </tr>
   * <tr>
   * <td><code>Foo&lt;K,V&gt;</code></td>
   * <td><code>null</code></td>
   * </tr>
   * <tr>
   * <td><code>Map&lt;String, Long&gt;</code></td>
   * <td><code>String</code></td>
   * </tr>
   * </table>
   * 
   * @since 2.0.0
   * @return the key-type of this {@link GenericType} or <code>null</code> if this {@link GenericType} does
   *         NOT represent a {@link java.util.Map} .
   */
  GenericType<?> getKeyType();

  /**
   * This method gets the number of {@link #getTypeArgument(int) type-arguments} .
   * 
   * @return the type-argument count.
   */
  int getTypeArgumentCount();

  /**
   * This method gets the type-argument at the given <code>index</code>.<br>
   * E.g. for the {@link GenericType} representing <code>Map&lt;String, List&lt;Integer&gt;&gt;</code> this
   * method would return <code>String</code> for an <code>index</code> of <code>0</code> and
   * <code>List&lt;Integer&gt;</code> for an <code>index</code> of <code>1</code>.
   * 
   * @see #getTypeArgumentCount()
   * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
   * 
   * @param index is the position of the requested type-argument. It has to be in the range from
   *        <code>0</code> to <code>{@link #getTypeArgumentCount()} - 1</code>.
   * @return the type-argument at the given <code>index</code>.
   */
  GenericType<?> getTypeArgument(int index);

  /**
   * This method determines if this {@link GenericType} is equal to or a super-type of the given
   * <code>subType</code>.<br>
   * If <code>X.isAssignableFrom(Y)</code> is <code>true</code>, then an instance of <code>Y</code> can be
   * casted to <code>X</code>.<br>
   * <b>NOTE:</b><br>
   * In case of strange and deeply cascaded generic constructs this can be an expensive operation with many
   * recursive invocations.
   * 
   * @see Class#isAssignableFrom(Class)
   * 
   * @param subType is the potential sub-type of this {@link GenericType}.
   * @return <code>true</code> if objects of the type <code>subType</code> can be assigned to this
   *         {@link GenericType}.
   */
  boolean isAssignableFrom(GenericType<?> subType);

  /**
   * This method gets the string representation of this {@link GenericType}. In case the underlying
   * {@link #getType() value-type} is a regular {@link Class}, this method will return its
   * {@link Class#getName() qualified name} otherwise it will return the string representation of the generic
   * type information (e.g.
   * <code>java.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.Integer&gt;&gt;[]</code> ).
   * 
   * @return this {@link GenericType} as string.
   */
  String toString();

}
