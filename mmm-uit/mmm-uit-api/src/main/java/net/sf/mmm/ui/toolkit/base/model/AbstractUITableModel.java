/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.event.UITableModelListener;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.base.event.UITableModelEvent;
import net.sf.mmm.util.event.ChangeEvent.Type;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModel} interface.
 * 
 * @param <E> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractUITableModel<E> implements UITableModel<E> {

  /** the listeners of the model */
  private final List<UITableModelListener> listeners;

  /**
   * The constructor.
   */
  public AbstractUITableModel() {

    super();
    this.listeners = new ArrayList<UITableModelListener>();
  }

  /**
   * {@inheritDoc}
   */
  public void addListener(UITableModelListener listener) {

    this.listeners.add(listener);
  }

  /**
   * {@inheritDoc}
   */
  public void removeListener(UITableModelListener listener) {

    this.listeners.remove(listener);
  }

  /**
   * {@inheritDoc}
   */
  public String getCellValueAsString(int rowIndex, int columnIndex) {

    E value = getCellValue(rowIndex, columnIndex);
    if (value == null) {
      return "";
    } else {
      return value.toString();
    }
  }

  /**
   * This method creates and {@link #fireChangeEvent(UITableModelEvent) sends}
   * an event that informs of the change of a single row.
   * 
   * @param type is the type change.
   * @param rowIndex is the index of the row that has changed.
   */
  protected void fireRowChangeEvent(Type type, int rowIndex) {

    fireChangeEvent(new UITableModelEvent(type, rowIndex, rowIndex, -1));
  }

  /**
   * This method creates and {@link #fireChangeEvent(UITableModelEvent) sends}
   * an event that informs of the change of a range of rows.
   * 
   * @param type is the type change.
   * @param rowStartIndex is the index of the first row that has changed.
   * @param rowEndIndex is the index of the last row that has changed.
   */
  protected void fireRowChangeEvent(Type type, int rowStartIndex, int rowEndIndex) {

    fireChangeEvent(new UITableModelEvent(type, rowStartIndex, rowEndIndex, -1));
  }

  /**
   * This method creates and {@link #fireChangeEvent(UITableModelEvent) sends}
   * an event that informs of the change of a single column.
   * 
   * @param type is the type change.
   * @param columnIndex is the index of the column that has changed.
   */
  protected void fireColumnChangeEvent(Type type, int columnIndex) {

    fireChangeEvent(new UITableModelEvent(type, 0, getRowCount() - 1, columnIndex));
  }

  /**
   * This method creates and {@link #fireChangeEvent(UITableModelEvent) sends}
   * an event that informs of the change of a range of rows.
   * 
   * @param type is the type change.
   * @param rowStartIndex is the index of the first row that has changed.
   * @param rowEndIndex is the index of the last row that has changed.
   * @param columnIndex is the index of the column that has changed.
   */
  protected void fireChangeEvent(Type type, int rowStartIndex, int rowEndIndex, int columnIndex) {

    fireChangeEvent(new UITableModelEvent(type, rowStartIndex, rowEndIndex, columnIndex));
  }

  /**
   * This method sends the given event to all
   * {@link UITableModel#addListener(UITableModelListener) registered}
   * {@link UITableModelListener listeners} of this model.
   * 
   * @param event is the event to send.
   */
  protected void fireChangeEvent(UITableModelEvent event) {

    for (int i = 0; i < this.listeners.size(); i++) {
      UITableModelListener listener = this.listeners.get(i);
      try {
        listener.tableModelChanged(event);
      } catch (Throwable t) {
        handleListenerException(listener, t);
      }
    }
  }

  /**
   * This method is called by the {@link #fireChangeEvent(UITableModelEvent)}
   * method if a listener caused an exception or error.
   * 
   * @param listener is the listener that threw the exception or error.
   * @param t is the exception or error.
   */
  protected abstract void handleListenerException(UITableModelListener listener, Throwable t);

}
