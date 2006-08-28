/* $Id$ */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UITreeModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UITreeModelListenerIF;
import net.sf.mmm.ui.toolkit.api.model.UITreeModelIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} interface.
 * 
 * @param <N>
 *        is the templated type of the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractTreeModel<N> implements UITreeModelIF<N> {

    /** the listeners of the model */
    private List<UITreeModelListenerIF<N>> listeners;

    /**
     * The constructor.
     */
    public UIAbstractTreeModel() {

        super();
        this.listeners = new Vector<UITreeModelListenerIF<N>>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#addListener(net.sf.mmm.ui.toolkit.api.event.UITreeModelListenerIF)
     * {@inheritDoc}
     */
    public void addListener(UITreeModelListenerIF<N> listener) {

        this.listeners.add(listener);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#removeListener(net.sf.mmm.ui.toolkit.api.event.UITreeModelListenerIF)
     * {@inheritDoc}
     */
    public void removeListener(UITreeModelListenerIF<N> listener) {

        this.listeners.remove(listener);
    }

    /**
     * This method sends the given event to all registered listeners of this
     * model.
     * 
     * @param event
     *        is the event to send.
     */
    protected void fireChangeEvent(UITreeModelEvent<N> event) {

        for (int i = 0; i < this.listeners.size(); i++) {
            UITreeModelListenerIF<N> listener = this.listeners.get(i);
            try {
                listener.treeModelChanged(event);
            } catch (Throwable t) {
                handleListenerException(listener, t);
            }
        }
    }

    /**
     * This method creates an event for the given change and sends it to all
     * registered listeners of this model.
     * 
     * @param type
     *        is the type change.
     * @param node
     *        is the node that changed.
     */
    protected void fireChangeEvent(EventType type, N node) {

        fireChangeEvent(new UITreeModelEvent<N>(type, node));
    }

    /**
     * This method is called by the {@link #fireChangeEvent(UITreeModelEvent)}
     * method if a listener caused an exception or error.
     * 
     * @param listener
     *        is the listener that threw the exception or error.
     * @param t
     *        is the exception or error.
     */
    protected abstract void handleListenerException(UITreeModelListenerIF listener, Throwable t);

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#toString(Object)
     * {@inheritDoc}
     */
    public String toString(N node) {

        if (node == null) {
            // this is an error...
            return "NULL";
        } else {
            return node.toString();
        }
    }

}
