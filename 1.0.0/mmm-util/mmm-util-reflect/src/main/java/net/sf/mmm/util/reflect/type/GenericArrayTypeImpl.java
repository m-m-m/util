/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is an implementation of the {@link GenericArrayType} interface.
 * 
 * @see ReflectionUtil#toType(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class GenericArrayTypeImpl implements GenericArrayType {

  /** @see #getGenericComponentType() */
  private final Type genericComponentType;

  /**
   * The constructor.
   * 
   * @param componentType is the
   *        {@link #getGenericComponentType() generic component-type}.
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

}
