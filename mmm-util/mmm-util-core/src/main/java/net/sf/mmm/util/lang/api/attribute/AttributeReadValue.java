/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #getValue() value} of an object.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract interface AttributeReadValue<VALUE> {

  /**
   * This method gets the value of this object.
   * 
   * @return the value of this object. May be <code>null</code> unless otherwise stated.
   */
  VALUE getValue();

}
