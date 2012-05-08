/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * The mutable variant of {@link AttributeReadTitle}.
 * 
 * @param <T> is the generic type of the {@link #getTitle() title}. See {@link AttributeReadTitle}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface AttributeWriteTitle<T> extends AttributeReadTitle<T> {

  /**
   * This method sets the title of this object.
   * 
   * @param newTitle is the new title to set.
   */
  void setTitle(T newTitle);

}
