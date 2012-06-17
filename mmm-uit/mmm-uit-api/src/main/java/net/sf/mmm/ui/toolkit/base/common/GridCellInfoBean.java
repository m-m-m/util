/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.common;

import net.sf.mmm.ui.toolkit.api.common.GridCellInfo;

/**
 * This is the implementation of {@link GridCellInfo} as simple java bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GridCellInfoBean implements GridCellInfo {

  /** @see #getColumnSpan() */
  private int columnSpan;

  /** @see #getRowSpan() */
  private int rowSpan;

  /**
   * The constructor.
   */
  public GridCellInfoBean() {

    super();
    this.columnSpan = 1;
    this.rowSpan = 1;
  }

  /**
   * {@inheritDoc}
   */
  public void setColumnSpan(int columnSpan) {

    if (columnSpan < 1) {
      throw new IllegalArgumentException(Integer.toString(columnSpan));
    }
    this.columnSpan = columnSpan;
  }

  /**
   * {@inheritDoc}
   */
  public int getColumnSpan() {

    return this.columnSpan;
  }

  /**
   * {@inheritDoc}
   */
  public void setRowSpan(int rowSpan) {

    if (rowSpan < 1) {
      throw new IllegalArgumentException(Integer.toString(rowSpan));
    }
    this.rowSpan = rowSpan;
  }

  /**
   * {@inheritDoc}
   */
  public int getRowSpan() {

    return this.rowSpan;
  }

}
