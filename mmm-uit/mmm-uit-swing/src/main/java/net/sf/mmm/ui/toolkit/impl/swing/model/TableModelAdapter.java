/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import net.sf.mmm.ui.toolkit.api.event.UITableModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UITableModelListener;
import net.sf.mmm.ui.toolkit.api.model.UIMutableTableModel;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UITableModel} to
 * a swing {@link javax.swing.table.TableModel}.
 * 
 * @param <C>
 *        is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TableModelAdapter<C> extends AbstractTableModel implements UITableModelListener {

  /** UID for serialization */
  private static final long serialVersionUID = -7846849727632188732L;

  /** the model to adapt */
  private final UITableModel<C> model;

  /**
   * The constructor.
   * 
   * @param tableModel
   *        is the model to adapt.
   */
  public TableModelAdapter(UITableModel<C> tableModel) {

    super();
    this.model = tableModel;
    this.model.addListener(this);
  }

  /**
   * This method gets the adapted model.
   * 
   * @return the model.
   */
  public UITableModel<C> getModel() {

    return this.model;
  }

  /**
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount() {

    if (this.model == null) {
      return 0;
    } else {
      return this.model.getRowCount();
    }
  }

  /**
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount() {

    if (this.model == null) {
      return 0;
    } else {
      return this.model.getColumnCount();
    }
  }

  /**
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt(int rowIndex, int columnIndex) {

    if (this.model == null) {
      return null;
    } else {
      return this.model.getCellValue(rowIndex, columnIndex);
    }
  }

  /**
   * @see javax.swing.table.AbstractTableModel#getColumnName(int)
   */
  @Override
  public String getColumnName(int columnIndex) {

    return this.model.getColumnName(columnIndex);
  }

  /**
   * This method disposes this model adapter. It is called when the adapter is
   * NOT used anymore and can be eaten by the garbarge-collector. The
   * implementation of this method can tidy-up (e.g. remove registered
   * listeners).
   */
  public void dispose() {

    this.model.removeListener(this);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.event.UITableModelListener#tableModelChanged(net.sf.mmm.ui.toolkit.api.event.UITableModelEvent)
   */
  public void tableModelChanged(UITableModelEvent event) {

    if (event.getRowStartIndex() == -1) {
      fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    } else {
      int type;
      switch (event.getType()) {
        case ADD:
          type = TableModelEvent.INSERT;
          break;
        case REMOVE:
          type = TableModelEvent.DELETE;
          break;
        case UPDATE:
          type = TableModelEvent.UPDATE;
          break;
        default :
          throw new IllegalStateException("Unknown event type " + event.getType());
      }
      fireTableChanged(new TableModelEvent(this, event.getRowStartIndex(), event.getRowEndIndex(),
          event.getColumnIndex(), type));
    }

  }

  /**
   * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int,
   *      int)
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    if ((this.model != null) && (this.model instanceof UIMutableTableModel)) {
      UIMutableTableModel mutableModel = (UIMutableTableModel) this.model;
      mutableModel.setCellValue(rowIndex, columnIndex, aValue);
    }
  }

}
