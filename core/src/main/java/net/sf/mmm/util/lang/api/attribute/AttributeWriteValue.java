/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read and write access to the {@link #getValue() value} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface AttributeWriteValue<VALUE> extends AttributeReadValue<VALUE> {

  /**
   * This method sets the {@link #getValue() value}.
   * 
   * @param value is the new {@link #getValue() value}. May be {@code null} unless otherwise stated.
   */
  void setValue(VALUE value);

}
