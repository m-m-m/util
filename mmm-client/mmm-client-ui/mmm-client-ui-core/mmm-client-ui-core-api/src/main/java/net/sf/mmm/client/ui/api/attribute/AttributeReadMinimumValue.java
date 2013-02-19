/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getMinimumValue() minimum value} attribute of an object.
 * 
 * @param <VALUE> is the generic type of the {@link #getMinimumValue() minimum value}. This is typically a
 *        {@link Number} but may potentially also be something else (e.g. a {@link java.util.Date}). Should be
 *        the same type as {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue()}.
 * 
 * @see AttributeReadMaximumValue
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadMinimumValue<VALUE> {

  /**
   * This method gets the <em>minimum</em>
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value} of this object. The
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value} of this object has to be
   * greater or equal to this <code>minimum</code>.
   * 
   * @return the minimum allowed for {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue()
   *         value}.
   */
  VALUE getMinimumValue();

}
