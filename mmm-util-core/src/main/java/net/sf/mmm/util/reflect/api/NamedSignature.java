/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Method;
import java.util.Objects;

import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This class extends {@link Signature} with a {@link #getName() name} honored by {@link #equals(Object)},
 * {@link #hashCode()} and {@link #toString()}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class NamedSignature extends Signature {

  private String name;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param theSignature is the signature to wrap.
   */
  public NamedSignature(String name, Class<?>... theSignature) {
    super(name.hashCode(), theSignature);
    this.name = name;
  }

  /**
   * The constructor.
   *
   * @param method is the {@link Method} whose signature should be wrapped.
   */
  public NamedSignature(Method method) {
    this(method.getName(), method.getParameterTypes());
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param arguments is a specific argument list to create a signature from.
   */
  public NamedSignature(String name, Object... arguments) {
    this(name, ReflectionUtilImpl.getInstance().getClasses(arguments));
  }

  /**
   * @return the name
   */
  public String getName() {

    return this.name;
  }

  @Override
  public boolean equals(Object other) {

    if (this == other) {
      return true;
    }
    if (!super.equals(other)) {
      return false;
    }
    NamedSignature otherSignature = (NamedSignature) other;
    if (!Objects.equals(this.name, otherSignature.name)) {
      return false;
    }
    return true;
  }

  @Override
  protected void toString(StringBuilder buffer) {

    buffer.append(this.name);
    super.toString(buffer);
  }
}
