/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This (functional) interface allows to implement an external function to calculate the
 * {@link Object#hashCode() hash code} of an object of the type {@literal <VALUE>}.
 * 
 * @param <VALUE> is the generic type of the objects to {@link #hashCode(Object) hash}. May also be
 *        {@link Object} for generic implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public interface HashCodeFunction<VALUE> {

  /**
   * @param value is the object to hash.
   * @return the custom {@link Object#hashCode() hash code} of the given {@code value}. The default
   *         implementation will simply return {@code value.hashCode()}.
   */
  int hashCode(VALUE value);

}
