/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.model.UITreeModelIF;
import net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlagIF;

/**
 * This is the interface for a tree. A tree is a widget used to display a
 * hierachical structure of nodes (items of the tree). The user can open and
 * close the nodes of the tree that are no leaves. Further he can select one or
 * multiple nodes (depending on selection type).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITreeIF extends UIWidgetIF, UIReadMultiSelectionFlagIF {

    /** the type of this object */
    String TYPE = "Tree";

    /**
     * This method gets the model of this tree.
     * 
     * @return the tree model.
     */
    UITreeModelIF getModel();

    /**
     * This method sets the model of this tree.
     * 
     * @param newModel
     *        is the new tree model to set.
     */
    void setModel(UITreeModelIF newModel);

    /**
     * This method gets the selected
     * {@link UITreeModelIF#getChildNode(Object, int) node}.
     * 
     * @return the selected node or <code>null</code> if no node is selected.
     */
    Object getSelection();

    /**
     * This method gets all selected nodes.
     * 
     * @see UIReadMultiSelectionFlagIF#isMultiSelection()
     * 
     * @return an array containing all selected nodes. The array will have a
     *         <code>length</code> of zero if no node is selected.
     */
    Object[] getSelections();

}