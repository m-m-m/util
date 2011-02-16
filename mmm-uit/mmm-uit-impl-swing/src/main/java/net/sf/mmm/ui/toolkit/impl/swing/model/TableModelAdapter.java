/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import net.sf.mmm.ui.toolkit.api.event.UITableModelListener;
import net.sf.mmm.ui.toolkit.api.model.data.UiMutableTableMvcModel;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.base.event.UITableModelEvent;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel} to a
 * swing {@link javax.swing.table.TableModel}.
 * 
 * @param <C> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TableModelAdapter<C> extends AbstractTableModel implements UITableModelListener {

  /** UID for serialization. */
  private static final long serialVersionUID = -7846849727632188732L;

  /** the model to adapt */
  private final UiTableMvcModel<C> model;

  /**
   * The constructor.
   * 
   * @param tableModel is the model to adapt.
   */
  public TableModelAdapter(UiTableMvcModel<C> tableModel) {

    super();
    this.model = tableModel;
    this.model.addListener(this);
  }

  /**
   * This method gets the adapted model.
   * 
   * @return the model.
   */
  public UiTableMvcModel<C> getModel() {

    return this.model;
  }

  /**
   * {@inheritDoc}
   */
  public int getRowCount() {

    if (this.model == null) {
      return 0;
    } else {
      return this.model.getRowCount();
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getColumnCount() {

    if (this.model == null) {
      return 0;
    } else {
      return this.model.getColumnCount();
    }
  }

  /**
   * {@inheritDoc}
   */
  public Object getValueAt(int rowIndex, int columnIndex) {

    if (this.model == null) {
      return null;
    } else {
      return this.model.getCellValue(rowIndex, columnIndex);
    }
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    if ((this.model != null) && (this.model instanceof UiMutableTableMvcModel)) {
      UiMutableTableMvcModel mutableModel = (UiMutableTableMvcModel) this.model;
      mutableModel.setCellValue(rowIndex, columnIndex, aValue);
    }
  }

}
