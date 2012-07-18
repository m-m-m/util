/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getHeightInTextLines() height in text lines} of
 * an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteHeightInTextLines extends AttributeReadHeightInTextLines {

  /**
   * This method sets the {@link #getHeightInTextLines() height in text lines} of this object.
   * 
   * @param lines is the new value for {@link #getHeightInTextLines() height in text lines} to set.
   */
  void setHeightInTextLines(int lines);

}
