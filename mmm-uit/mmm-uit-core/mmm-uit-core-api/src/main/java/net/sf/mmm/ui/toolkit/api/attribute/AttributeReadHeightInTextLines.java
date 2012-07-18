/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #getHeightInTextLines() height in text lines} attribute of
 * an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadHeightInTextLines {

  /**
   * This method gets the <em>height in text lines</em> of this object. This is the number of textual lines
   * that are visible at a time. If the text has more lines than this height a scrollbar will automatically
   * appear.
   * 
   * @return the height in text lines.
   */
  int getHeightInTextLines();

}
