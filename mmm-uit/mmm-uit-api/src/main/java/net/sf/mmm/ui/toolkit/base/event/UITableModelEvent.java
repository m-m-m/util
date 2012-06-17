/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.event;

import net.sf.mmm.ui.toolkit.api.event.UIModelEvent;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This class represents the event sent by the
 * {@link net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel table-model} to
 * its {@link net.sf.mmm.ui.toolkit.api.event.UITableModelListener listeners} in
 * order to notify about changes of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTable table}.<br>
 * Be aware that only {@link net.sf.mmm.util.event.api.ChangeType#UPDATE update}
 * events may apply to incomplete columns (single column but
 * {@link #getRowStartIndex() row-start} is not <code>0</code> or
 * {@link #getRowEndIndex() row-end} is not
 * <code>{@link net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel#getRowCount() rowCount} -
 * 1</code>). Other events must apply to complete column(s) or row(s).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITableModelEvent extends UIModelEvent {

  /** the start index of the changed rows */
  private final int rowStart;

  /** the end index of the changed rows */
  private final int rowEnd;

  /** the index of the changed column */
  private final int column;

  /**
   * The constructor.
   * 
   * @param eventType is the type for the new event.
   * @param rowStartIndex is the index of the first row that has changed.
   * @param rowEndIndex is the index of the last row that has changed.
   * @param columnIndex is the index of the column that has changed.
   */
  public UITableModelEvent(ChangeType eventType, int rowStartIndex, int rowEndIndex, int columnIndex) {

    super(eventType);
    this.rowStart = rowStartIndex;
    this.rowEnd = rowEndIndex;
    this.column = columnIndex;
  }

  /**
   * This method gets the index of the first row that has changed.
   * 
   * @see net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel#getRowCount()
   * 
   * @return the start index of the rows that changed or <code>-1</code> if one
   *         or multiple
   *         {@link net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel#getColumnName(int)
   *         column-name}(s) changed.
   */
  public int getRowStartIndex() {

    return this.rowStart;
  }

  /**
   * This method gets the index of the last row that has changed.
   * 
   * @see net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel#getRowCount()
   * 
   * @return the end index of the rows that changed.
   */
  public int getRowEndIndex() {

    return this.rowEnd;
  }

  /**
   * This method gets the index of the column that has changed.
   * 
   * @see net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel#getColumnCount()
   * 
   * @return the index of the column that changed or <code>-1</code> if all
   *         columns changed.
   */
  public int getColumnIndex() {

    return this.column;
  }

}
