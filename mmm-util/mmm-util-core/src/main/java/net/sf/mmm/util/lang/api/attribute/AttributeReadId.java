/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This is the abstract interface for an object that has an {@link #getId() ID}.
 * 
 * @param <T> is the generic type of the {@link #getId() ID}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract interface AttributeReadId<T> {

  /**
   * This method gets the ID used to identify this object. An ID should be unique.
   * 
   * @return the object's ID.
   */
  T getId();

}
