/* $Id$ */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

/**
 * This is the interface of a tree model listener. Such a listener gets notified
 * about any change of {@link net.sf.mmm.ui.toolkit.api.widget.UITreeIF tree}
 * {@link net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#getChildNode(Object, int) nodes}
 * from the {@link net.sf.mmm.ui.toolkit.api.model.UITreeModelIF tree-model}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF
 * 
 * @param <N>
 *        is the templated type of the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITreeModelListenerIF<N> extends EventListener {

    /**
     * This method is called by the
     * {@link net.sf.mmm.ui.toolkit.api.model.UITreeModelIF tree-model} if it
     * changed (nodes have been updated, inserted or removed).
     * 
     * @param event
     *        notifies about the change.
     */
    void treeModelChanged(UITreeModelEvent<N> event);

}
