/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isTitleVisible() titleVisible} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteTitleVisible extends AttributeReadTitleVisible {

  /**
   * This method sets the {@link #isTitleVisible() titleVisible flag} of this object.
   * 
   * @param titleVisible is the new value for {@link #isTitleVisible()}.
   */
  void setTitleVisible(boolean titleVisible);

}
