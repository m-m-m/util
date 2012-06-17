/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;

/**
 * This is the interface for additional layout information of a cell.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface GridCellInfo {

  /**
   * This method sets the {@link #getColumnSpan() column-span} for the
   * represented cell.
   * 
   * @param spanCount is the number of cells to combine horizontally. It has to
   *        be greater than <code>0</code>.
   */
  void setColumnSpan(int spanCount);

  /**
   * This method gets the column-span for the represented cell. Like in HTML a
   * column-span defines the number of cells to join horizontal along the
   * columns including the starting cell itself.<br/>
   * The default value is 1. E.g. a value of 2 combines the specified cell with
   * the next cell to the right.<br/>
   * 
   * @return the column-span.
   */
  int getColumnSpan();

  /**
   * This method sets the {@link #getRowSpan() row-span} for the represented
   * cell.
   * 
   * @param rowCount is the number of cells to combine vertically. It has to be
   *        greater than <code>0</code>.
   */
  void setRowSpan(int rowCount);

  /**
   * This method gets the row-span for the represented cell. Like in HTML a
   * row-span defines the number of cells to join vertically along the rows
   * including the starting cell itself.<br/>
   * The default value is 1. E.g. a value of 2 combines the specified cell with
   * the next cell to the right.<br/>
   * 
   * @return the column-span.
   */
  int getRowSpan();

}
