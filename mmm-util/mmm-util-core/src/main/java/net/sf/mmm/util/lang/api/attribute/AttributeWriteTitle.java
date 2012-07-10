/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read and write access to the {@link AttributeReadTitle#getTitle() title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 * @param <TITLE> is the generic type of the {@link #getTitle() title}.
 */
public abstract interface AttributeWriteTitle<TITLE> extends AttributeReadTitle<TITLE> {

  /**
   * This method sets the {@link #getTitle() title} of this object.
   * 
   * @param newTitle is the new {@link #getTitle() title} to set.
   */
  void setTitle(TITLE newTitle);

}
