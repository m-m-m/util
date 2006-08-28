/* $Id: ListModelAdapter.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.model;

import javax.swing.AbstractListModel;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF;
import net.sf.mmm.ui.toolkit.api.model.UIListModelIF;

/**
 * This class adapts a {@link net.sf.mmm.ui.toolkit.api.model.UIListModelIF} to
 * a swing {@link javax.swing.ListModel}.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ListModelAdapter<E> extends AbstractListModel implements
        UIListModelListenerIF {

    /** uid for serialization */
    private static final long serialVersionUID = -6062581165914420738L;

    /** the original list model */
    protected final UIListModelIF<E> model;

    /**
     * The constructor.
     * 
     * @param listModel
     *        is the list model to adapt to swing.
     */
    public ListModelAdapter(UIListModelIF<E> listModel) {

        super();
        this.model = listModel;
        this.model.addListener(this);
    }

    /**
     * @see javax.swing.ListModel#getElementAt(int)
     * {@inheritDoc}
     */
    public Object getElementAt(int index) {

        return this.model.getElement(index);
    }

    /**
     * @see javax.swing.ListModel#getSize()
     * {@inheritDoc}
     */
    public int getSize() {

        return this.model.getElementCount();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF#listModelChanged(net.sf.mmm.ui.toolkit.api.event.UIListModelEvent)
     * {@inheritDoc}
     */
    public void listModelChanged(UIListModelEvent event) {

        if (event.getType() == EventType.ADD) {
            fireIntervalAdded(this, event.getStartIndex(), event.getEndIndex());
        } else if (event.getType() == EventType.REMOVE) {
            fireIntervalRemoved(this, event.getStartIndex(), event.getEndIndex());
            System.out.println(event.getStartIndex() + "-" + event.getEndIndex());
        } else if (event.getType() == EventType.UPDATE) {
            fireContentsChanged(this, event.getStartIndex(), event.getEndIndex());
        }
    }

    /**
     * This method gets the list-model that is adapted.
     * 
     * @return the actual list-model.
     */
    public UIListModelIF<E> getModel() {
        
        return this.model;
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
    
}
