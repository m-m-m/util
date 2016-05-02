/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is a very simple implementation of {@link GenericType} that is GWT compatible and therefore limited to core
 * features.
 *
 * @param <T> is the generic type of the {@link #getRetrievalClass() retrieval class}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class SimpleGenericTypeLimited<T> implements GenericType<T> {

  /** The {@link GenericType} for {@link Object}. */
  public static final GenericType<Object> TYPE_OBJECT = new SimpleGenericTypeLimited<>(Object.class);

  /** The {@link GenericType} for {@link Void}. */
  public static final GenericType<Void> TYPE_VOID = new SimpleGenericTypeLimited<>(Void.class);

  /** @see #getType() */
  private final Class<T> type;

  /** @see #getComponentType() */
  private GenericType<?> componentType;

  /** @see #getKeyType() */
  private GenericType<?> keyType;

  /**
   * The constructor.
   *
   * @param type is the {@link #getType() type} to represent.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public SimpleGenericTypeLimited(Class<T> type) {

    super();
    NlsNullPointerException.checkNotNull(Class.class, type);
    this.type = type;
    if (type.isArray()) {
      this.componentType = new SimpleGenericTypeLimited(type.getComponentType());
      this.keyType = null;
    } else if (isCollection(type)) {
      this.componentType = TYPE_OBJECT;
      this.keyType = null;
    } else if (isMap(type)) {
      this.componentType = TYPE_OBJECT;
      this.keyType = TYPE_OBJECT;
    } else {
      this.componentType = null;
      this.keyType = null;
    }
  }

  /**
   * This method determines if the given type is a {@link List}.
   *
   * @param rawType is the {@link Class} to check.
   * @return {@code true} if the given type is assignable to {@link List}.
   */
  private boolean isCollection(Class<?> rawType) {

    // this sucks... is there really no way to work around this in GWT?
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=4663
    if (rawType == Collection.class) {
      return true;
    }
    if (rawType == List.class) {
      return true;
    }
    if (rawType == Set.class) {
      return true;
    }
    Class<?> superType = rawType;
    while (superType != null) {
      if (superType == AbstractCollection.class) {
        return true;
      }
      superType = superType.getSuperclass();
    }
    return false;
  }

  /**
   * This method determines if the given type is a {@link Map}.
   *
   * @param rawType is the {@link Class} to check.
   * @return {@code true} if the given type is assignable to {@link Map}.
   */
  private boolean isMap(Class<?> rawType) {

    // this sucks... is there really no way to work around this in GWT?
    // http://code.google.com/p/google-web-toolkit/issues/detail?id=4663
    if (rawType == Map.class) {
      return true;
    }
    Class<?> superType = rawType;
    while (superType != null) {
      if (superType == AbstractMap.class) {
        return true;
      }
      superType = superType.getSuperclass();
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getComponentType() {

    return this.componentType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getKeyType() {

    return this.keyType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getType() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<T> getAssignmentClass() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<T> getRetrievalClass() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getTypeArgument(int index) {

    throw new IndexOutOfBoundsException(Integer.toString(index));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTypeArgumentCount() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAssignableFrom(GenericType<?> subType) {

    throw new NlsUnsupportedOperationException("GWT:isAssignableFrom");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return ~this.type.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SimpleGenericTypeLimited<?> other = (SimpleGenericTypeLimited<?>) obj;
    return this.type.equals(other.type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.type.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toStringSimple() {

    return this.type.getSimpleName();
  }

}
