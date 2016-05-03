/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Method;

import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;

/**
 * This class represents a {@link java.lang.reflect.Method#getParameterTypes() "method signature"}. It is a container
 * for a {@link java.lang.Class} array and can be used as {@link java.util.Map#get(java.lang.Object) hash-key}.
 *
 * @see #isApplicable(Signature)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class Signature {

  /** the void signature for a non-arg method */
  public static final Signature VOID = new Signature(ReflectionUtilLimited.NO_PARAMETERS);

  /** the wrapped signature */
  private final Class<?>[] signature;

  /** bleeding edge performance hack */
  private final int hash;

  /**
   * The constructor.
   *
   * @param theSignature is the signature to wrap.
   */
  public Signature(Class<?>... theSignature) {

    this(0, theSignature);
  }

  /**
   * The constructor.
   *
   * @param hash the base value for {@link #hashCode()}.
   * @param theSignature is the signature to wrap.
   */
  protected Signature(int hash, Class<?>... theSignature) {

    super();
    this.signature = theSignature;
    int hashcode = hash;
    for (int i = 0; i < this.signature.length; i++) {
      if (this.signature[i] != null) {
        hashcode = (31 * hashcode) + this.signature[i].hashCode();
      }
    }
    hashcode = hashcode << 2;
    hashcode = hashcode | this.signature.length;
    this.hash = hashcode;
  }

  /**
   * The constructor.
   *
   * @param method is the {@link Method} whose signature should be wrapped.
   */
  @SuppressWarnings("all")
  public Signature(Method method) {

    this(method.getParameterTypes());
  }

  /**
   * The constructor.
   *
   * @param arguments is a specific argument list to create a signature from.
   */
  public Signature(Object... arguments) {

    this(ReflectionUtilImpl.getInstance().getClasses(arguments));
  }

  /**
   * This method determines if the given signature is applicable for this signature. Here applicable means that if this
   * is the signature of a method, that method could be called with arguments of the given signature {@code s}.
   * <br>
   * Only call this method if this signature does NOT {@link #getType(int) contain} {@code null}.
   *
   * @param s is the signature to test.
   * @return {@code true} if the given signature is applicable for this signature, {@code false} otherwise.
   */
  public boolean isApplicable(Signature s) {

    // to make sure what the isAssignableFrom method means:
    // Object.class.isAssignableFrom(String.class) == true

    if (this.signature.length == s.signature.length) {
      for (int i = 0; i < this.signature.length; i++) {
        Class<?> type = s.signature[i];
        // an argument of null is always applicable
        if (type != null) {
          if (!this.signature[i].isAssignableFrom(type)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  /**
   * This method gets the number of {@link Class types} in this signature.
   *
   * @return the type count.
   */
  public int getTypeCount() {

    return this.signature.length;
  }

  /**
   * This method gets the {@link Class type} of this signature at the given position.
   *
   * @param position is the index of the requested type. This value must be in the range from {@code 0} to <code>{@link #getTypeCount()} - 1</code>.
   * @return the {@link Class type} at the given index.
   */
  public Class<?> getType(int position) {

    return this.signature[position];
  }

  @Override
  public int hashCode() {

    return this.hash;
  }

  @Override
  public boolean equals(Object other) {

    if (other == this) {
      return true;
    }
    if ((other != null) && (getClass() == other.getClass())) {
      Signature s = (Signature) other;
      if (this.signature.length == s.signature.length) {
        for (int i = 0; i < this.signature.length; i++) {
          if (this.signature[i] != s.signature[i]) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder();
    toString(buffer);
    return buffer.toString();
  }

  /**
   * @see #toString()
   *
   * @param buffer the {@link StringBuilder} to {@link StringBuilder#append(String) to}.
   */
  protected void toString(StringBuilder buffer) {

    buffer.append('(');
    if (this.signature.length > 0) {
      buffer.append(this.signature[0]);
    }
    for (int i = 1; i < this.signature.length; i++) {
      buffer.append(", ");
      buffer.append(this.signature[i]);
    }
    buffer.append(')');
  }

}
