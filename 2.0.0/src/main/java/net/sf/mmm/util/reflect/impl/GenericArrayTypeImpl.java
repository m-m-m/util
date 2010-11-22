/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * This is an implementation of the {@link GenericArrayType} interface.
 * 
 * @see net.sf.mmm.util.reflect.base.ReflectionUtilImpl#toType(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class GenericArrayTypeImpl implements GenericArrayType {

  /** @see #getGenericComponentType() */
  private final Type genericComponentType;

  /**
   * The constructor.
   * 
   * @param componentType is the {@link #getGenericComponentType() generic
   *        component-type}.
   */
  public GenericArrayTypeImpl(Type componentType) {

    super();
    this.genericComponentType = componentType;
  }

  /**
   * {@inheritDoc}
   */
  public Type getGenericComponentType() {

    return this.genericComponentType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.genericComponentType + "[]";
  }

}
