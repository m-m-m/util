/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.AbstractGenericType;
import net.sf.mmm.util.reflect.base.GenericTypeBuilder;
import net.sf.mmm.util.reflect.base.GenericTypeVariable;

/**
 * This class extends {@link GenericTypeImpl} with the ability to bind and resolve {@link TypeVariable}s at runtime.
 *
 * @see GenericTypeBuilder#build()
 *
 * @param <T> the generic type of the {@link #getRetrievalClass() upper bound}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class RuntimeGenericType<T> extends GenericTypeImpl<T> {

  private final Map<GenericTypeVariable<?>, Type> variable2typeMap;

  /**
   * The constructor.
   *
   * @param type is the {@link #getType() type}.
   * @param variable2typeMap a {@link Map} mapping the runtime bound {@link TypeVariable}s.
   */
  public RuntimeGenericType(Type type, Map<GenericTypeVariable<?>, Type> variable2typeMap) {
    this(type, null, variable2typeMap);
  }

  /**
   * The constructor.
   *
   * @param valueType is the {@link #getType() value-type}.
   * @param definingType is the {@link #getDefiningType() defining-type}.
   * @param variable2typeMap a {@link Map} mapping the runtime bound {@link TypeVariable}s.
   */
  public RuntimeGenericType(Type valueType, GenericType<?> definingType,
      Map<GenericTypeVariable<?>, Type> variable2typeMap) {
    super(valueType, definingType, false);
    this.variable2typeMap = variable2typeMap;
    init();
  }

  @Override
  protected AbstractGenericType<T> create(Type genericType, GenericType<?> genericDefiningType) {

    Type type = genericType;
    if (genericType instanceof TypeVariable) {
      type = resolveBoundTypeVariable((TypeVariable<?>) genericType, genericType);
    }
    return new RuntimeGenericType<>(type, genericDefiningType, this.variable2typeMap);
  }

  private Type resolveBoundTypeVariable(TypeVariable<?> typeVariable, Type fallback) {

    GenericTypeVariable<?> key = wrap(typeVariable);
    Type type = this.variable2typeMap.get(key);
    if (type != null) {
      return type;
    }
    return fallback;
  }

}
