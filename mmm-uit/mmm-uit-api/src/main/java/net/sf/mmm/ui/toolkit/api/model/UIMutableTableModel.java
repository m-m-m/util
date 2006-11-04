/* $Id$ */
package net.sf.mmm.ui.toolkit.api.model;

/**
 * This is the interface of a
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} that can be modified.
 * 
 * @param <E>
 *        is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIMutableTableModel<E> extends UITableModel<E> {

  /**
   * This method sets the value of the specified cell.
   * 
   * @param rowIndex
   *        is the row index of the cell to set.
   * @param columnIndex
   *        is the column index of the cell to set.
   * @param value
   *        is the new value to set.
   */
  void setCellValue(int rowIndex, int columnIndex, E value);

  /**
   * This method sets the {@link UITableModel#getColumnName(int) name} of
   * the column at the given index.
   * 
   * @param columnIndex
   *        is the index of the according column.
   * @param name
   *        is the new name of the specified column.
   */
  void setColumnName(int columnIndex, String name);

}
