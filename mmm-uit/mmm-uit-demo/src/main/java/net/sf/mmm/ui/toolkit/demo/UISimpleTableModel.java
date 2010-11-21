/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.demo;

import net.sf.mmm.ui.toolkit.api.event.UITableModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIMutableTableModel;
import net.sf.mmm.ui.toolkit.base.model.AbstractUITableModel;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISimpleTableModel extends AbstractUITableModel<String> implements
    UIMutableTableModel<String> {

  /** */
  private final String[][] cells;

  /** */
  private final String[] columnNames;

  /**
   * The constructor.
   * 
   * @param rowCount
   * @param columnCount
   */
  public UISimpleTableModel(int rowCount, int columnCount) {

    super();
    this.cells = new String[rowCount][columnCount];
    this.columnNames = new String[columnCount];
  }

  /**
   * {@inheritDoc}
   */
  public int getColumnCount() {

    return this.columnNames.length;
  }

  /**
   * {@inheritDoc}
   */
  public int getRowCount() {

    return this.cells.length;
  }

  /**
   * {@inheritDoc}
   */
  public String getCellValue(int rowIndex, int columnIndex) {

    return this.cells[rowIndex][columnIndex];
  }

  /**
   * {@inheritDoc}
   */
  public String getColumnName(int columnIndex) {

    return this.columnNames[columnIndex];
  }

  /**
   * 
   */
  public void initCells() {

    for (int col = 0; col < this.columnNames.length; col++) {
      String s = getColumnName(col);
      if (s == null) {
        s = "" + (char) ('A' + col);
      }
      for (int row = 0; row < this.cells.length; row++) {
        this.cells[row][col] = s + row;
      }
    }
  }

  /**
   * 
   */
  public void initColumnNames() {

    for (int col = 0; col < this.columnNames.length; col++) {
      String letter = "" + (char) ('A' + col);
      setColumnName(col, letter);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setCellValue(int rowIndex, int columnIndex, String value) {

    this.cells[rowIndex][columnIndex] = value;
    fireChangeEvent(ChangeType.UPDATE, rowIndex, rowIndex, columnIndex);
  }

  /**
   * {@inheritDoc}
   */
  public void setColumnName(int columnIndex, String name) {

    this.columnNames[columnIndex] = name;
    fireChangeEvent(ChangeType.UPDATE, -1, 0, columnIndex);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void handleListenerException(UITableModelListener listener, Throwable t) {

    t.printStackTrace();
  }
}
