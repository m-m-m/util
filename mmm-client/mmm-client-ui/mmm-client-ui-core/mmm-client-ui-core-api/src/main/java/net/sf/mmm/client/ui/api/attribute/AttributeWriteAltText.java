/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getAltText() alternative text} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAltText extends AttributeReadAltText {

  /**
   * This method sets the {@link #getAltText() alternative text} of this object.
   * 
   * @see #getAltText()
   * @see net.sf.mmm.util.lang.api.concern.Accessibility
   * 
   * @param altText is the new {@link #getAltText() alternative text}.
   */
  void setAltText(String altText);

}
