/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #getValueType() value type} of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @param <VALUE> is the generic type of the {@link #getValueType() value type}.
 */
public abstract interface AttributeReadValueType<VALUE> {

  /**
   * This method gets the value type of this object. This is the class reflecting a particular value such as
   * {@link AttributeReadValue#getValue()}.
   *
   * @return the value type of this object.
   */
  Class<VALUE> getValueType();

}
