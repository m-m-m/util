/* $Id$ */
package net.sf.mmm.ui.toolkit.api.model;

import net.sf.mmm.ui.toolkit.api.event.UITableModelListener;

/**
 * This is the interface for the model of a table widget.
 * 
 * @see net.sf.mmm.ui.toolkit.api.widget.UITable
 * 
 * @param <C>
 *        is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITableModel<C> {

  /**
   * This method registers the given change listener to this model.
   * 
   * @param listener
   *        is the change listener to add.
   */
  void addListener(UITableModelListener listener);

  /**
   * This method unregisters the given change listener from this model. The
   * listener should have been registered via the addChangeListener method
   * before.
   * 
   * @param listener
   *        is the change listener to remove.
   */
  void removeListener(UITableModelListener listener);

  /**
   * This method gets the number of columns of the table.
   * 
   * @return the column count.
   */
  int getColumnCount();

  /**
   * This method gets the number of rows of the table.
   * 
   * @return the row count.
   */
  int getRowCount();

  /**
   * This method gets the value of the specified cell.
   * 
   * @param rowIndex
   *        is the row index of the requested cell value.
   * @param columnIndex
   *        is the column index of the requested cell value.
   * @return the value at the specified cell. For an empty cell
   *         <code>null</code> can be returned.
   */
  C getCellValue(int rowIndex, int columnIndex);

  /**
   * This method gets the value of the specified cell as string.
   * 
   * @see #getCellValue(int, int)
   * 
   * @param rowIndex
   *        is the row index of the requested cell value.
   * @param columnIndex
   *        is the column index of the requested cell value.
   * @return the value at the specified cell. For an empty cell
   *         <code>null</code> can be returned.
   */
  String getCellValueAsString(int rowIndex, int columnIndex);

  /**
   * This method gets the name of the specified column.
   * 
   * @param columnIndex
   * @return the name of the column at the given index.
   */
  String getColumnName(int columnIndex);

}
