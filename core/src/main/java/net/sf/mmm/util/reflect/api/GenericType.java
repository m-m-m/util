/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Type;

/**
 * This is the interface of a generic type and allows simple and powerful access to the complex generic type-system
 * introduced in Java5. <br>
 * It represents a {@link Type} (available via {@link #getType()}) but allows easy access to resolve the actual erasure
 * {@link Class} for {@link #getAssignmentClass() assignment} and {@link #getRetrievalClass() retrieval}. This includes
 * resolving {@link java.lang.reflect.TypeVariable}s as far as possible. <br>
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
 * If you want to determine the type of {@code Some.getA()} reflectively, you will have to dive into the deepest and
 * trickiest part of the reflection API and might step into one of the many pitfalls on this way. All this is solved for
 * you, if you use what is offered via this API. <br>
 * <b>LIMITATIONS:</b><br>
 * This solution will only support one upper and one lower bound but NOT multiple bounds of the same kind. However this
 * is more a feature than a limitation as it makes the usage simple and IMHO using multiple bounds is a quite uncommon
 * feature that should be avoided.
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
   * This method gets the {@link Class} to be used for assignment (as parameter) of a value of this {@link GenericType}.
   * <br>
   * It will only differ from the {@link #getRetrievalClass() retrieval-class} if this {@link GenericType} is a
   * {@link java.lang.reflect.WildcardType}. <br>
   * Unlike the {@link java.lang.reflect.WildcardType#getLowerBounds() lower-bound}, the assignment-class is never
   * {@code null}. If there is no {@link java.lang.reflect.WildcardType#getLowerBounds() lower-bound}, the
   * {@link #getAssignmentClass() assignment-class} is the same as the {@link #getRetrievalClass() retrieval-class}.
   * Therefore the {@link #getAssignmentClass() assignment-class} is always equal or more specific to the
   * {@link #getRetrievalClass() retrieval-class}. <br>
   * Here are some examples:
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>{@link #getAssignmentClass()}</th>
   * </tr>
   * <tr>
   * <td>{@literal <? super Integer>}</td>
   * <td>{@link Integer}</td>
   * </tr>
   * <tr>
   * <td>{@literal <? extends CharSequence>}</td>
   * <td>{@link CharSequence}</td>
   * </tr>
   * <tr>
   * <td>{@code String}</td>
   * <td>{@link String}</td>
   * </tr>
   * </table>
   *
   * @return the {@link Class} that is the lower bound.
   */
  Class<? extends T> getAssignmentClass();

  /**
   * This method gets the {@link Class} to be used for retrieval (the return-type) of a value of this
   * {@link GenericType}. <br>
   * It will only differ from the {@link #getAssignmentClass() assignment-class} if this {@link GenericType} is a
   * {@link java.lang.reflect.WildcardType}. <br>
   * The {@link #getRetrievalClass() retrieval-class} is the {@link java.lang.reflect.WildcardType#getUpperBounds()
   * upper-bound}, however for usability and simplicity only one bound is supported. <br>
   * Here are some examples:
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>{@link #getAssignmentClass()}</th>
   * </tr>
   * <tr>
   * <td>{@literal <? super Integer>}</td>
   * <td>{@link Object}</td>
   * </tr>
   * <tr>
   * <td>{@literal <? extends CharSequence>}</td>
   * <td>{@link CharSequence}</td>
   * </tr>
   * <tr>
   * <td>{@code String}</td>
   * <td>{@link String}</td>
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
   * {@link java.util.Collection} or {@link java.util.Map}. <br>
   * Here are some examples:<br>
   * <table border="1">
   * <tr>
   * <th>type</th>
   * <th>{@link #getComponentType()}</th>
   * </tr>
   * <tr>
   * <td>{@literal List<Map<String, Long>>}</td>
   * <td>{@literal Map<String, Long>}</td>
   * </tr>
   * <tr>
   * <td>{@code List}</td>
   * <td>{@code Object}</td>
   * </tr>
   * <tr>
   * <td>{@code Foo[]}</td>
   * <td>{@code Foo}</td>
   * </tr>
   * <tr>
   * <td>{@literal Foo<Bar>[]}</td>
   * <td>{@literal Foo<Bar>}</td>
   * </tr>
   * <tr>
   * <td>{@literal Foo<Bar>}</td>
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>{@literal Map<String, Long>}</td>
   * <td>{@code Long}</td>
   * </tr>
   * </table>
   *
   * @return the component-type of this {@link GenericType} or {@code null} if this {@link GenericType} does NOT
   *         represent an array, {@link java.util.Collection} or {@link java.util.Map}.
   */
  GenericType<?> getComponentType();

  /**
   * This method gets the key-type of this {@link GenericType} if it represents a {@link java.util.Map}. <br>
   * Here are some examples:<br>
   * <table border="1">
   * <tr>
   * <th>type</th>
   * <th>{@link #getComponentType()}</th>
   * </tr>
   * <tr>
   * <td>{@literal List<Map<String, Long>>}</td>
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>{@code Map}</td>
   * <td>{@code Object}</td>
   * </tr>
   * <tr>
   * <td>{@code Foo[]}</td>
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>{@literal Foo<K,V>}</td>
   * <td>{@code null}</td>
   * </tr>
   * <tr>
   * <td>{@literal Map<String, Long>}</td>
   * <td>{@code String}</td>
   * </tr>
   * </table>
   *
   * @since 2.0.0
   * @return the key-type of this {@link GenericType} or {@code null} if this {@link GenericType} does NOT represent a
   *         {@link java.util.Map}.
   */
  GenericType<?> getKeyType();

  /**
   * This method gets the number of {@link #getTypeArgument(int) type-arguments} .
   *
   * @return the type-argument count.
   */
  int getTypeArgumentCount();

  /**
   * This method gets the type-argument at the given {@code index}. <br>
   * E.g. for the {@link GenericType} representing {@literal Map<String, List<Integer>>} this method would return
   * {@code String} for an {@code index} of {@code 0} and {@literal List<Integer>} for an {@code index} of {@code 1}.
   *
   * @see #getTypeArgumentCount()
   * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
   *
   * @param index is the position of the requested type-argument. It has to be in the range from {@code 0} to <code>
   *        {@link #getTypeArgumentCount()} - 1</code>.
   * @return the type-argument at the given {@code index}.
   */
  GenericType<?> getTypeArgument(int index);

  /**
   * This method determines if this {@link GenericType} is equal to or a super-type of the given {@code subType}. <br>
   * If {@code X.isAssignableFrom(Y)} is {@code true}, then an instance of {@code Y} can be casted to {@code X}. <br>
   * <b>NOTE:</b><br>
   * In case of strange and deeply cascaded generic constructs this can be an expensive operation with many recursive
   * invocations.
   *
   * @see Class#isAssignableFrom(Class)
   *
   * @param subType is the potential sub-type of this {@link GenericType}.
   * @return {@code true} if objects of the type {@code subType} can be assigned to this {@link GenericType}.
   */
  boolean isAssignableFrom(GenericType<?> subType);

  /**
   * This method gets the string representation of this {@link GenericType}. In case the underlying {@link #getType()
   * value-type} is a regular {@link Class}, this method will return its {@link Class#getName() qualified name}
   * otherwise it will return the string representation of the generic type information (e.g.
   * {@literal java.util.Map<java.lang.String, java.util.List<java.lang.Integer>>[]}).
   *
   * @return this {@link GenericType} as string.
   */
  @Override
  String toString();

  /**
   * @see ReflectionUtil#toStringSimple(Type)
   *
   * @return a compact string representation with only {@link Class#getSimpleName() simple names} for {@link Class}es.
   */
  String toStringSimple();

}
