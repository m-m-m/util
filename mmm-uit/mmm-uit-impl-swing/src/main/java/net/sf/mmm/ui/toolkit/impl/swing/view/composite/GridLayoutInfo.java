/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GridLayoutInfo {

  /** @see #getTotalHeight() */
  private int totalHeight;

  /** @see #getCurrentY() */
  private int currentY;

  /** The list of columns. */
  private final List<ColumnLayoutInfo> columns;

  /**
   * The constructor.
   */
  public GridLayoutInfo() {

    super();
    this.columns = new ArrayList<ColumnLayoutInfo>();
  }

  /**
   * The constructor.
   * 
   * @param columnCount is the number of columns.
   */
  public GridLayoutInfo(int columnCount) {

    super();
    this.columns = new ArrayList<ColumnLayoutInfo>(columnCount);
    for (int i = 0; i < columnCount; i++) {
      this.columns.add(new ColumnLayoutInfo());
    }
  }

  /**
   * This method gets the {@link ColumnLayoutInfo} for the given
   * <code>index</code>.
   * 
   * @param index is the index of the requested column.
   * @return the {@link ColumnLayoutInfo} for the given <code>index</code>.
   */
  public ColumnLayoutInfo getColumnInfo(int index) {

    return this.columns.get(index);
  }

  /**
   * This method gets {@link #getColumnInfo(int) column-info} or creates it if
   * it does not yet exist.
   * 
   * @param index is the index of the requested column.
   * @return the {@link ColumnLayoutInfo} for the given <code>index</code>.
   */
  public ColumnLayoutInfo getOrCreateColumnInfo(int index) {

    for (int i = this.columns.size(); i <= index; i++) {
      this.columns.add(new ColumnLayoutInfo());
    }
    return this.columns.get(index);
  }

  /**
   * This method gets the current number of columns.
   * 
   * @return the column count.
   */
  public int getColumnCount() {

    return this.columns.size();
  }

  /**
   * This method sets the {@link #getColumnCount() column count}. TODO If the
   * given <code>count</code> is greater than column...
   * 
   * @param count is the new column count.
   */
  public void setColumnCount(int count) {

    int size = this.columns.size();
    int delta = count - size;
    if (delta > 0) {
      for (int i = 0; i < delta; i++) {
        this.columns.add(new ColumnLayoutInfo());
      }
    } else if (delta < 0) {
      for (int i = size - 1; i >= count; i--) {
        this.columns.remove(i);
      }
    }
  }

  /**
   * This method gets the total width of the grid.
   * 
   * @return the total width of the grid.
   */
  public int getTotalWidth() {

    int totalWidth = 0;
    for (ColumnLayoutInfo columnInfo : this.columns) {
      totalWidth = totalWidth + columnInfo.width;
    }
    return totalWidth;
  }

  /**
   * This method gets the width of the <code>columnSpan</code> number of columns
   * starting at the given <code>column</code>.
   * 
   * @param column is the index of the column to start with.
   * @param columnSpan is the number of columns to include. See
   *        {@link net.sf.mmm.ui.toolkit.api.common.GridCellInfo#getColumnSpan()}
   *        for further details.
   * @return the width in pixels.
   */
  public int getWidth(int column, int columnSpan) {

    int width = 0;
    int stop = column + columnSpan;
    if (stop > this.columns.size()) {
      // error?
      stop = this.columns.size();
    }
    for (int i = column; i < stop; i++) {
      width = width + this.columns.get(i).width;
    }
    return width;
  }

  /**
   * This method gets the total height of the grid.
   * 
   * @return the total height of the grid.
   */
  public int getTotalHeight() {

    return this.totalHeight;
  }

  /**
   * This method sets the {@link #getTotalHeight()}.
   * 
   * @param totalHeight is the totalHeight to set.
   */
  public void setTotalHeight(int totalHeight) {

    this.totalHeight = totalHeight;
  }

  /**
   * This method resets the calculated values for width and height.
   */
  public void reset() {

    this.currentY = 0;
    this.totalHeight = 0;
    for (ColumnLayoutInfo columnInfo : this.columns) {
      columnInfo.width = 0;
    }
  }

  /**
   * This method gets the {@link int}.
   * 
   * @return the {@link int}.
   */
  public int getCurrentY() {

    return this.currentY;
  }

  /**
   * @param currentY is the currentY to set
   */
  public void setCurrentY(int currentY) {

    this.currentY = currentY;
  }

  /**
   * This inner class represents the layout information for a column of the
   * grid.
   */
  public static class ColumnLayoutInfo {

    /** @see #getWidth() */
    private int width;

    /**
     * The constructor.
     */
    public ColumnLayoutInfo() {

      super();
    }

    /**
     * This method gets the width in pixel.
     * 
     * @return the width.
     */
    public int getWidth() {

      return this.width;
    }

    /**
     * @param width is the width to set
     */
    public void setWidth(int width) {

      this.width = width;
    }

  }

}
