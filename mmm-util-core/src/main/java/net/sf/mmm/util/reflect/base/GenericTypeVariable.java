/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Objects;

/**
 * This class acts a a generic base to capture a {@link TypeVariable} that can then be bound at runtime.
 *
 * @param <T> the generic {@link TypeVariable}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class GenericTypeVariable<T> extends GenericTypeCapture<T> {

  private final TypeVariable<?> typeVariable;

  /**
   * The constructor.
   */
  public GenericTypeVariable() {
    Type typeArgument = resolve();
    assert (typeArgument instanceof TypeVariable);
    this.typeVariable = (TypeVariable<?>) typeArgument;
  }

  /**
   * The constructor.
   */
  GenericTypeVariable(TypeVariable<?> typeVariable) {
    this.typeVariable = typeVariable;
  }

  @Override
  public final int hashCode() {

    return Objects.hash(this.typeVariable.getName(), this.typeVariable.getGenericDeclaration());
  }

  @Override
  public final boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof GenericTypeVariable)) {
      return false;
    }
    GenericTypeVariable<?> other = (GenericTypeVariable<?>) obj;
    if (!this.typeVariable.getName().equals(other.typeVariable.getName())) {
      return false;
    }
    if (!this.typeVariable.getGenericDeclaration().equals(other.typeVariable.getGenericDeclaration())) {
      return false;
    }
    return true;
  }

  /**
   * @return the {@link TypeVariable}.
   */
  public TypeVariable<?> get() {

    return this.typeVariable;
  }

}
