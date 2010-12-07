/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.impl;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * This is an implementation of the {@link TypeVariable} interface.
 * 
 * @param <DECLARATION> the type of generic declaration that declared the
 *        underlying type variable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class TypeVariableImpl<DECLARATION extends GenericDeclaration> implements
    TypeVariable<DECLARATION> {

  /** @see #getName() */
  private final String name;

  /** @see #getBounds() */
  private final Type[] bounds;

  /** @see #getGenericDeclaration() */
  private final DECLARATION genericDeclaration;

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name}.
   * @param declaration is the {@link #getGenericDeclaration() declaring
   *        element} (e.g. Class or Method).
   * @param bounds are the {@link #getBounds()}.
   */
  public TypeVariableImpl(String name, DECLARATION declaration, Type[] bounds) {

    super();
    this.name = name;
    this.genericDeclaration = declaration;
    this.bounds = bounds;
  }

  /**
   * TODO: unclear how we build the bounds!
   * 
   * {@inheritDoc}
   */
  public Type[] getBounds() {

    return this.bounds;
  }

  /**
   * {@inheritDoc}
   */
  public DECLARATION getGenericDeclaration() {

    return this.genericDeclaration;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {

    if (other == this) {
      return true;
    }
    if (other instanceof TypeVariable<?>) {
      TypeVariable<?> otherVariable = (TypeVariable<?>) other;
      return (this.name.equals(otherVariable.getName()) && this.genericDeclaration
          .equals(otherVariable.getGenericDeclaration()));
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return this.genericDeclaration.hashCode() ^ this.name.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.name;
  }

}
