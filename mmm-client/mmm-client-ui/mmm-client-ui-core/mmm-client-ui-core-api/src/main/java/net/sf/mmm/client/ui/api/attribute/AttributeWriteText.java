/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getText() text} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteText extends AttributeReadText {

  /**
   * This method sets the {@link #getText() text} of this object.
   * 
   * @param text is the new {@link #getText() text} to set.
   */
  void setText(String text);

}
