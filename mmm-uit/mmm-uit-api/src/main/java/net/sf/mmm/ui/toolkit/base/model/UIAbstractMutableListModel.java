/* $Id$ */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.EventType;
import net.sf.mmm.ui.toolkit.api.event.UIListModelEvent;
import net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF} interface.
 * 
 * @param <E>
 *        is the templated type of the elements in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractMutableListModel<E> extends UIAbstractListModel<E> implements
        UIMutableListModelIF<E> {

    /**
     * The constructor.
     */
    public UIAbstractMutableListModel() {

        super();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UIMutableListModelIF#removeAll()
     * 
     */
    public void removeAll() {

        if (getElementCount() > 0) {
            int lastIndex;
            synchronized (this) {
                lastIndex = getElementCount() - 1;
                for (int i = lastIndex; i >= 0; i--) {
                    removeElement(i);
                }
            }
            fireChangeEvent(new UIListModelEvent(EventType.REMOVE, 0, lastIndex));
        }
    }

}
