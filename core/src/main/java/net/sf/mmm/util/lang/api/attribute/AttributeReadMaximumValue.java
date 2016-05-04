/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #getMaximumValue() maximum value} attribute of an object.
 *
 * @param <VALUE> is the generic type of the {@link #getMaximumValue() maximum value}. This is typically a
 *        {@link Number} but may potentially also be something else (e.g. a {@link java.util.Date}). Should be the same
 *        type as {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract interface AttributeReadMaximumValue<VALUE> {

  /**
   * This method gets the <em>maximum</em> {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue()
   * value} of this object. A legal {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value} has
   * to be less or equal to this {@code maximum}.
   *
   * @return the maximum allowed for {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value} or
   *         {@code null} if unbounded.
   */
  VALUE getMaximumValue();

}
