/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getRowSpan() rowSpan} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadRowSpan {

  /** The attribute name of {@link #getRowSpan()}. */
  String ATTRIBUTE_ROWSPAN = "rowspan";

  /**
   * This method gets the number of rows spanned by this object (cell). This cell expands to that number of
   * cells vertically so a value greater than <code>1</code> indicates that the next cells to the bottom are
   * connected.
   * 
   * @return the row span. By default <code>1</code>.
   */
  int getRowSpan();

}
