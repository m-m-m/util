/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the abstract base implementation of a simple {@link Datatype}.
 *
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractSimpleDatatypeBase<V> extends AbstractDatatype implements SimpleDatatype<V> {

  private static final long serialVersionUID = -2351955533173401204L;

  /**
   * The constructor.
   */
  public AbstractSimpleDatatypeBase() {

    super();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    V value = getValue();
    if (value == this) {
      return false;
    }
    V otherValue = ((AbstractSimpleDatatypeBase<V>) obj).getValue();
    if (value == null) {
      return (otherValue == null);
    } else {
      return value.equals(otherValue);
    }
  }

  @Override
  public int hashCode() {

    V value = getValue();
    if (value == this) {
      return super.hashCode();
    }
    if (value == null) {
      return 0;
    } else {
      return value.hashCode();
    }
  }

  @Override
  public String toString() {

    V value = getValue();
    if (value == null) {
      return null;
    } else {
      return value.toString();
    }
  }

  /**
   * @param <V> is the generic type of the contained {@link #getValue() value}.
   * @param datatype is the {@link SimpleDatatype} to get the {@link #getValue() value} from.
   * @return the {@link #getValue() value} of the given {@code datatype} or {@code null} if {@code datatype} is
   *         {@code null}.
   * @since 7.0.0
   */
  public static <V> V getValue(SimpleDatatype<V> datatype) {

    if (datatype == null) {
      return null;
    }
    return datatype.getValue();
  }

}
