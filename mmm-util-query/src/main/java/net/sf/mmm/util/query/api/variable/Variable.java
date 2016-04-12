/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.variable;

import java.util.Objects;

import net.sf.mmm.util.lang.api.attribute.AttributeReadName;
import net.sf.mmm.util.property.api.path.PropertyPath;

/**
 * This class represents a simple variable to be used in query {@link net.sf.mmm.util.query.api.statement.Statement}s.
 *
 * @param <V> the generic type of the value represented by this variable.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class Variable<V> implements AttributeReadName {

  private final String name;

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name} of this {@link Variable}.
   */
  protected Variable(String name) {
    super();
    this.name = name;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public int hashCode() {

    return ~this.name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == this) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    Variable<?> other = (Variable<?>) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return super.equals(obj);
  }

  @Override
  public String toString() {

    return "$" + this.name;
  }

  /**
   * @param <V> the generic type of the value represented by this variable.
   * @param name the {@link #getName() name} of the variable.
   * @return the new {@link Variable}.
   */
  public static <V> Variable<V> valueOf(String name) {

    return new Variable<>(name);
  }

  /**
   * @param <V> the generic type of the value represented by this variable.
   * @param name the {@link #getName() name} of the variable.
   * @param path the {@link PropertyPath} this variable stands for.
   * @return the new {@link Variable}.
   */
  public static <V> Variable<V> valueOf(String name, PropertyPath<V> path) {

    return new Variable<>(name);
  }
}
