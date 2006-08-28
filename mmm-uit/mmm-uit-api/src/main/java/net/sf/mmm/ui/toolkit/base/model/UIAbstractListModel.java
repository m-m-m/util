/* $Id: UIAbstractListModel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} interface.
 * 
 * @param <E>
 *        is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractListModel<E> implements UIListModelIF<E> {

    /** the listeners of the model */
    private List<UIListModelListenerIF> listeners;

    /**
     * The constructor.
     */
    public UIAbstractListModel() {

        super();
        this.listeners = new Vector<UIListModelListenerIF>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#addListener(net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF)
     * {@inheritDoc}
     */
    public void addListener(UIListModelListenerIF listener) {

        this.listeners.add(listener);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#removeListener(net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF)
     * {@inheritDoc}
     */
    public void removeListener(UIListModelListenerIF listener) {

        this.listeners.remove(listener);
    }

    /**
     * This method sends the given event to all registered listeners of this
     * model.
     * 
     * @param event
     *        is the event to send.
     */
    protected void fireChangeEvent(UIListModelEvent event) {

        for (int i = 0; i < this.listeners.size(); i++) {
            UIListModelListenerIF listener = this.listeners.get(i);
            try {
                listener.listModelChanged(event);
            } catch (Throwable t) {
                handleListenerException(listener, t);
            }
        }
    }

    /**
     * This method is called by the <code>fireChangeEvent</code> method if a
     * listener caused an exception or error.
     * 
     * @param listener
     *        is the listener that threw the exception or error.
     * @param t
     *        is the exception or error.
     */
    protected abstract void handleListenerException(UIListModelListenerIF listener, Throwable t);

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getElementAsString(int)
     * {@inheritDoc}
     */
    public String getElementAsString(int index) {

        E value = getElement(index);
        if (value == null) {
            // this is an error...
            return "NULL";
        } else {
            return value.toString();
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getIndexOf(Object)
     * {@inheritDoc}
     */
    public int getIndexOf(E element) {

        return -1;
    }

}
