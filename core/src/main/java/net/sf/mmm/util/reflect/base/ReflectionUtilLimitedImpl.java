/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;

/**
 * This is the implementation of the {@link ReflectionUtilLimited} interface. It is strictly GWT compatible.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ReflectionUtilLimitedImpl extends AbstractLoggableComponent implements ReflectionUtilLimited {

  /** @see #getInstance() */
  private static final ReflectionUtilLimited INSTANCE = new ReflectionUtilLimitedImpl();

  /**
   * The constructor.
   */
  public ReflectionUtilLimitedImpl() {

    super();
  }

  /**
   * @return the instance
   */
  public static ReflectionUtilLimited getInstance() {

    return INSTANCE;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> Class<T> getNonPrimitiveType(Class<T> type) {

    Class<?> result = type;
    if (type.isPrimitive()) {
      if (int.class == type) {
        result = Integer.class;
      } else if (long.class == type) {
        result = Long.class;
      } else if (double.class == type) {
        result = Double.class;
      } else if (boolean.class == type) {
        result = Boolean.class;
      } else if (float.class == type) {
        result = Float.class;
      } else if (char.class == type) {
        result = Character.class;
      } else if (byte.class == type) {
        result = Byte.class;
      } else if (short.class == type) {
        result = Short.class;
      } else if (void.class == type) {
        result = Void.class;
      } else {
        throw new IllegalStateException("Class-loader isolation trap!");
      }
    }
    return (Class<T>) result;
  }

  @Override
  public <T> GenericType<T> createGenericType(Class<T> type) {

    return new SimpleGenericTypeLimited<>(type);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public GenericType<?> createGenericType(Type type) {

    NlsNullPointerException.checkNotNull(Type.class, type);
    if (type instanceof Class) {
      return new SimpleGenericTypeLimited((Class) type);
    } else if (type instanceof GenericType) {
      return (GenericType<?>) type;
    } else {
      throw new IllegalCaseException(type.toString());
    }
  }

  @Override
  public GenericType<?> createGenericType(Type type, GenericType<?> definingType) {

    return createGenericType(type);
  }

  @Override
  public GenericType<?> createGenericType(Type type, Class<?> definingType) {

    return createGenericType(type);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <E> GenericType<List<E>> createGenericTypeOfList(GenericType<E> elementType) {

    return (GenericType) createGenericType(List.class);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <K, V> GenericType<Map<K, V>> createGenericTypeOfMap(GenericType<K> keyType, GenericType<V> valueType) {

    return (GenericType) createGenericType(Map.class);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <E> GenericType<Set<E>> createGenericTypeOfSet(GenericType<E> elementType) {

    return (GenericType) createGenericType(Set.class);
  }

}
