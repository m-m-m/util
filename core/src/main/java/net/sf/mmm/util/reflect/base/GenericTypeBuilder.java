/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.RuntimeGenericType;

/**
 * This class allows to build instances of {@link GenericType} with custom binding for {@link TypeVariable}s at runtime.
 * The following example illustrates the usage:
 *
 * <pre>
 * public static GenericType&lt;{@link java.util.List}&lt;{@link javax.inject.Provider}&lt;T>>> typeOfListOfProviderOf(GenericType&ltT> type) {
 *   return new {@link GenericTypeBuilder}&lt;{@link java.util.List}&lt;{@link javax.inject.Provider}&lt;T>>>() {}
 *              .{@link #with(GenericTypeVariable, GenericType) with}(new GenericTypeVariable&lt;T>() {}, type)
 *              .{@link #build()};
 * }
 * </pre>
 *
 * @param <T> the generic type of the {@link GenericType} to {@link #build()} after
 *        {@link #with(GenericTypeVariable, GenericType) type variables have been bound}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class GenericTypeBuilder<T> extends GenericTypeCapture<T> {

  private final Type typeArgument;

  private final Map<GenericTypeVariable<?>, Type> variable2typeMap;

  /**
   * The constructor.
   */
  public GenericTypeBuilder() {
    super();
    this.typeArgument = resolve();
    assert !(this.typeArgument instanceof TypeVariable);
    this.variable2typeMap = new HashMap<>();
  }

  /**
   * @param <V> the generic type of the {@link TypeVariable} to bind.
   * @param variable the captured {@link TypeVariable}.
   * @param type the {@link GenericType} to bind to the {@link TypeVariable}.
   * @return this instance for fluent API.
   */
  public <V> GenericTypeBuilder<T> with(GenericTypeVariable<V> variable, GenericType<V> type) {

    return withType(variable, type.getType());
  }

  /**
   * @param <V> the generic type of the {@link TypeVariable} to bind.
   * @param variable the captured {@link TypeVariable}.
   * @param type the {@link Class} to bind to the {@link TypeVariable}.
   * @return this instance for fluent API.
   */
  public <V> GenericTypeBuilder<T> with(GenericTypeVariable<V> variable, Class<V> type) {

    return withType(variable, type);
  }

  /**
   * @param <V> the generic type of the {@link TypeVariable} to bind.
   * @param variable the captured {@link TypeVariable}.
   * @param type the {@link Type} to bind to the {@link TypeVariable}.
   * @return this instance for fluent API.
   */
  public <V> GenericTypeBuilder<T> withType(GenericTypeVariable<V> variable, Type type) {

    this.variable2typeMap.put(variable, type);
    return this;
  }

  /**
   * @return the new {@link GenericType} created by this builder.
   */
  public GenericType<T> build() {

    return new RuntimeGenericType<>(this.typeArgument, this.variable2typeMap);
  }

}
