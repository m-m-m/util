/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This is an implementation of the {@link ParameterizedType} interface.
 * 
 * @see ReflectionUtilImpl#toType(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ParameterizedTypeImpl implements ParameterizedType {

  /** @see #getRawType() */
  private final Class<?> rawType;

  /** @see #getOwnerType() */
  private final Type ownerType;

  /** @see #getActualTypeArguments() */
  private final Type[] actualTypeArguments;

  /**
   * The constructor.
   * 
   * @param rawType is the {@link #getRawType() raw type}.
   * @param typeArguments are the {@link #getActualTypeArguments()}.
   * @param ownerType is the {@link #getOwnerType() owner type} (may be <code>null</code>).
   */
  public ParameterizedTypeImpl(Class<?> rawType, Type[] typeArguments, Type ownerType) {

    super();
    this.rawType = rawType;
    this.ownerType = ownerType;
    this.actualTypeArguments = typeArguments;
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getActualTypeArguments() {

    return this.actualTypeArguments.clone();
  }

  /**
   * {@inheritDoc}
   */
  public Type getOwnerType() {

    return this.ownerType;
  }

  /**
   * {@inheritDoc}
   */
  public Type getRawType() {

    return this.rawType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {

    if (other instanceof ParameterizedType) {
      ParameterizedType otherType = (ParameterizedType) other;
      if (this == otherType) {
        return true;
      } else {
        Type otherOwner = otherType.getOwnerType();
        boolean result;
        if (this.ownerType == null) {
          result = (otherOwner == null);
        } else {
          result = this.ownerType.equals(otherOwner);
        }
        result = result && (this.rawType.equals(otherType.getRawType()))
            && Arrays.equals(this.actualTypeArguments, otherType.getActualTypeArguments());
        return result;
      }
    } else {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    int hash = Arrays.hashCode(this.actualTypeArguments);
    if (this.rawType != null) {
      hash = hash ^ this.rawType.hashCode();
    }
    if (this.ownerType != null) {
      hash = hash ^ this.ownerType.hashCode();
    }
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();
    if (this.ownerType != null) {
      result.append(ReflectionUtilImpl.getInstance().toString(this.ownerType));
      result.append('.');
      // TODO: this is NOT as easy!
      result.append(this.rawType.getName());
    } else {
      result.append(this.rawType.getName());
    }
    if (this.actualTypeArguments.length > 0) {
      result.append('<');
      for (int i = 0; i < this.actualTypeArguments.length; i++) {
        Type typeArgument = this.actualTypeArguments[i];
        if (i > 0) {
          result.append(", ");
        }
        if (typeArgument instanceof Class<?>) {
          result.append(((Class<?>) typeArgument).getName());
        } else {
          result.append(typeArgument.toString());
        }
      }
      result.append('>');
    }
    return result.toString();
  }

}
