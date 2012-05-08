/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This is the abstract interface for an object that has a {@link #getValue() value}.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public abstract interface AttributeReadValue<V> {

  /**
   * This method gets the value of this object.
   * 
   * @return the value of this object. May be <code>null</code> unless otherwise stated.
   */
  V getValue();

}
