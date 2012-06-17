/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is an implementation of the {@link WildcardType} interface for the unbounded wildcard (<code>?</code>
 * ).
 * 
 * @see ReflectionUtil#toType(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public final class UnboundedWildcardType implements WildcardType {

  /** The prefix/value of the {@link #toString() string representation}. */
  public static final String PREFIX = "?";

  /** The singleton instance. */
  public static final WildcardType INSTANCE = new UnboundedWildcardType();

  /**
   * The constructor.
   */
  private UnboundedWildcardType() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getLowerBounds() {

    return ReflectionUtil.NO_TYPES;
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getUpperBounds() {

    return ReflectionUtil.NO_TYPES;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {

    if (other == this) {
      return true;
    }
    if ((other != null) && (other instanceof WildcardType)) {
      WildcardType otherWildcard = (WildcardType) other;
      if ((otherWildcard.getLowerBounds().length == 0) && (otherWildcard.getUpperBounds().length == 0)) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    // hardcoded hash code of the unbounded wildcard as
    // sun.reflect.generics.reflectiveObjects.WildcardTypeImpl
    return 22474412;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return PREFIX;
  }

}
