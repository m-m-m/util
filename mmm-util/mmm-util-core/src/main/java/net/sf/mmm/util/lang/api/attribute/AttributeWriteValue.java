/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * The mutable variant of {@link AttributeReadValue}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract interface AttributeWriteValue<V> extends AttributeReadValue<V> {

  /**
   * This method sets the {@link #getValue() value}.
   * 
   * @param value is the new {@link #getValue() value}. May be <code>null</code> unless otherwise stated.
   */
  void setValue(V value);

}
