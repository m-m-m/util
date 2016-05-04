/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read and write access to the {@link #getMaximumValue() maximum value} of an object.
 *
 * @param <VALUE> is the generic type of the {@link #getMaximumValue() maximum value}. This is typically a
 *        {@link Number} but may potentially also be something else (e.g. a {@link java.util.Date}). Should be the same
 *        type as {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract interface AttributeWriteMaximumValue<VALUE> extends AttributeReadMaximumValue<VALUE> {

  /**
   * This method sets the {@link #getMaximumValue() maximum value} of this object. <br>
   * <b>ATTENTION:</b><br>
   * Changing the maximum value dynamically can invalidate the current
   * {@link net.sf.mmm.util.lang.api.attribute.AttributeReadValue#getValue() value}. This should be avoided as it may
   * have undesired effects.
   *
   * @param maximum is the new {@link #getMaximumValue() maximum value} to set. Use e.g. {@link Integer#MAX_VALUE} to
   *        unset.
   */
  void setMaximumValue(VALUE maximum);

}
