/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.AbstractGenericType;

/**
 * This is an implementation of the {@link GenericType} interface for a simple {@link Class}.
 * 
 * @param <T> is the templated type of the {@link #getRetrievalClass() upper bound}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class SimpleGenericTypeImpl<T> extends AbstractGenericType<T> {

  /** The {@link GenericType} for {@link Object}. */
  public static final GenericType<Object> TYPE_OBJECT = new SimpleGenericTypeImpl<Object>(Object.class);

  /** The {@link GenericType} for <code>void</code>. */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final GenericType<?> TYPE_VOID = new SimpleGenericTypeImpl(void.class);

  /** The {@link GenericType} for <code>int</code>. */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static final GenericType<?> TYPE_INT = new SimpleGenericTypeImpl(int.class);

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
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public SimpleGenericTypeImpl(Class<T> type) {

    super();
    this.type = type;
    if (type.isArray()) {
      this.componentType = new SimpleGenericTypeImpl(type.getComponentType());
      this.keyType = null;
    } else if (Collection.class.isAssignableFrom(type)) {
      Type resolvedType = resolveTypeVariable(CommonTypeVariables.TYPE_VARIABLE_COLLECTION_ELEMENT, this);
      this.componentType = new GenericTypeImpl(resolvedType);
      this.keyType = null;
    } else if (Map.class.isAssignableFrom(type)) {
      Type resolvedType = resolveTypeVariable(CommonTypeVariables.TYPE_VARIABLE_MAP_VALUE, this);
      this.componentType = new GenericTypeImpl(resolvedType);
      resolvedType = resolveTypeVariable(CommonTypeVariables.TYPE_VARIABLE_MAP_KEY, this);
      this.keyType = new GenericTypeImpl(resolvedType);
    } else {
      this.componentType = null;
      this.keyType = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericType<?> create(Type genericType) {

    return new GenericTypeImpl<Object>(genericType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getDefiningType() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getComponentType() {

    return this.componentType;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getKeyType() {

    return this.keyType;
  }

  /**
   * {@inheritDoc}
   */
  public Type getType() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public Class<T> getAssignmentClass() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public Class<T> getRetrievalClass() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getTypeArgument(int index) {

    throw new IndexOutOfBoundsException(Integer.toString(index));
  }

  /**
   * {@inheritDoc}
   */
  public int getTypeArgumentCount() {

    return 0;
  }

}
