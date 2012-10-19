/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteTitle;

/**
 * This interface gives read and write access to the {@link #getTitle() title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteStringTitle extends AttributeReadStringTitle, AttributeWriteTitle<String> {

  /**
   * This method sets the title of this object.
   * 
   * @see #getTitle()
   * 
   * @param title is the new title.
   */
  void setTitle(String title);

}
