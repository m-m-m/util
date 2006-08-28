/* $Id: UIDefaultListModel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF} interface.
 * 
 * @param <E>
 *        is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDefaultListModel<E> extends UIAbstractMutableListModel<E> {

    /** the items of this list */
    private List<E> elements;

    /**
     * The constructor.
     */
    public UIDefaultListModel() {

        super();
        this.elements = new Vector<E>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getElementCount()
     * {@inheritDoc}
     */
    public int getElementCount() {

        return this.elements.size();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getElement(int)
     * {@inheritDoc}
     */
    public E getElement(int index) {

        return this.elements.get(index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UIAbstractListModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UIListModelListenerIF,
     *      java.lang.Throwable)
     * {@inheritDoc}
     */
    protected void handleListenerException(UIListModelListenerIF listener, Throwable t) {

    // we ignore this...
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF#addElement(Object)
     * {@inheritDoc}
     */
    public void addElement(E element) {

        this.elements.add(element);
        fireChangeEvent(new UIListModelEvent(EventType.ADD, this.elements.size() - 1));
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF#setElement(Object,
     *      int)
     * {@inheritDoc}
     */
    public void setElement(E newItem, int index) {

        this.elements.set(index, newItem);
        fireChangeEvent(new UIListModelEvent(EventType.UPDATE, index));
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF#addElement(Object,
     *      int)
     * {@inheritDoc}
     */
    public void addElement(E item, int index) {

        this.elements.add(index, item);
        fireChangeEvent(new UIListModelEvent(EventType.ADD, index));
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF#removeElement(int)
     * {@inheritDoc}
     */
    public void removeElement(int index) {

        this.elements.remove(index);
        fireChangeEvent(new UIListModelEvent(EventType.REMOVE, index));
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF#removeElement(Object)
     * {@inheritDoc}
     */
    public boolean removeElement(E element) {

        int index = this.elements.indexOf(element);
        if (index >= 0) {
            removeElement(element);
            return true;
        }
        return false;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF#removeAll()
     * {@inheritDoc}
     */
    @Override
    public void removeAll() {

        int count = this.elements.size();
        if (count > 0) {
            this.elements.clear();
            fireChangeEvent(new UIListModelEvent(EventType.REMOVE, 0, count - 1));
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getIndexOf(Object)
     * {@inheritDoc}
     */
    public int getIndexOf(E element) {

        return this.elements.indexOf(element);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF#getIndexOfString(java.lang.String)
     * {@inheritDoc}
     */
    public int getIndexOfString(String element) {

        int len = this.elements.size();
        for (int i = 0; i < len; i++) {
            if (element.equals(getElementAsString(i))) {
                return i;
            }
        }
        return -1;
    }

}