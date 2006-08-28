/* $Id$ */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UITableModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UITableModelListenerIF;
import net.sf.mmm.ui.toolkit.api.model.UITableModelIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} interface.
 * 
 * @param <E>
 *        is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractTableModel<E> implements UITableModelIF<E> {

    /** the listeners of the model */
    private final List<UITableModelListenerIF> listeners;

    /**
     * The constructor.
     */
    public UIAbstractTableModel() {

        super();
        this.listeners = new ArrayList<UITableModelListenerIF>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModelIF#addListener(net.sf.mmm.ui.toolkit.api.event.UITableModelListenerIF)
     * {@inheritDoc}
     */
    public void addListener(UITableModelListenerIF listener) {

        this.listeners.add(listener);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModelIF#removeListener(net.sf.mmm.ui.toolkit.api.event.UITableModelListenerIF)
     * {@inheritDoc}
     */
    public void removeListener(UITableModelListenerIF listener) {

        this.listeners.remove(listener);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITableModelIF#getCellValueAsString(int,
     *      int)
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
     * @param type
     *        is the type change.
     * @param rowIndex
     *        is the index of the row that has changed.
     */
    protected void fireRowChangeEvent(EventType type, int rowIndex) {

        fireChangeEvent(new UITableModelEvent(type, rowIndex, rowIndex, -1));
    }

    /**
     * This method creates and {@link #fireChangeEvent(UITableModelEvent) sends}
     * an event that informs of the change of a range of rows.
     * 
     * @param type
     *        is the type change.
     * @param rowStartIndex
     *        is the index of the first row that has changed.
     * @param rowEndIndex
     *        is the index of the last row that has changed.
     */
    protected void fireRowChangeEvent(EventType type, int rowStartIndex, int rowEndIndex) {

        fireChangeEvent(new UITableModelEvent(type, rowStartIndex, rowEndIndex, -1));
    }

    /**
     * This method creates and {@link #fireChangeEvent(UITableModelEvent) sends}
     * an event that informs of the change of a single column.
     * 
     * @param type
     *        is the type change.
     * @param columnIndex
     *        is the index of the column that has changed.
     */
    protected void fireColumnChangeEvent(EventType type, int columnIndex) {

        fireChangeEvent(new UITableModelEvent(type, 0, getRowCount() - 1, columnIndex));
    }

    /**
     * This method creates and {@link #fireChangeEvent(UITableModelEvent) sends}
     * an event that informs of the change of a range of rows.
     * 
     * @param type
     *        is the type change.
     * @param rowStartIndex
     *        is the index of the first row that has changed.
     * @param rowEndIndex
     *        is the index of the last row that has changed.
     * @param columnIndex
     *        is the index of the column that has changed.
     */
    protected void fireChangeEvent(EventType type, int rowStartIndex, int rowEndIndex, int columnIndex) {

        fireChangeEvent(new UITableModelEvent(type, rowStartIndex, rowEndIndex, columnIndex));
    }

    /**
     * This method sends the given event to all
     * {@link UITableModelIF#addListener(UITableModelListenerIF) registered}
     * {@link UITableModelListenerIF listeners} of this model.
     * 
     * @param event
     *        is the event to send.
     */
    protected void fireChangeEvent(UITableModelEvent event) {

        for (int i = 0; i < this.listeners.size(); i++) {
            UITableModelListenerIF listener = this.listeners.get(i);
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
     * @param listener
     *        is the listener that threw the exception or error.
     * @param t
     *        is the exception or error.
     */
    protected abstract void handleListenerException(UITableModelListenerIF listener, Throwable t);

}
