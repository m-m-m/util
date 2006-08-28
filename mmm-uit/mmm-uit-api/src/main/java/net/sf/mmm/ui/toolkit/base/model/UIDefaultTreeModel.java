/* $Id: UIDefaultTreeModel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.model;


import net.sf.mmm.ui.toolkit.api.event.UITreeModelListenerIF;

/**
 * This is the default implementation of the UIListModelIF interface.
 * 
 * @param <T>
 *        is the templated type of the user data in the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDefaultTreeModel<T> extends UIAbstractTreeModel<UITreeNodeIF<T>> {

    /** the root node of the tree */
    private UITreeNode<T> rootNode;

    /**
     * The constructor.
     */
    public UIDefaultTreeModel() {

        this(null);
    }

    /**
     * The constructor.
     * 
     * @param rootNodeData
     *        is the data object for the root node.
     */
    public UIDefaultTreeModel(T rootNodeData) {

        super();
        this.rootNode = new UITreeNode<T>(this, null, rootNodeData);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UIAbstractTreeModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UITreeModelListenerIF,
     *      java.lang.Throwable)
     * {@inheritDoc}
     */
    protected void handleListenerException(UITreeModelListenerIF listener, Throwable t) {

    // we ignore this...
    }

    /**
     * This method returns the same object as the getRootNode method. It only
     * exists to avoid casting and to specify the used node type.
     * 
     * @see UIDefaultTreeModel#getRootNode()
     * 
     * @return the root node.
     */
    public UITreeNode<T> getRoot() {

        return this.rootNode;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#getRootNode()
     * {@inheritDoc}
     */
    public UITreeNodeIF<T> getRootNode() {

        return this.rootNode;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#getChildCount(java.lang.Object)
     * {@inheritDoc}
     */
    public int getChildCount(UITreeNodeIF<T> node) {

        return node.getChildNodeCount();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#getChildNode(java.lang.Object,
     *      int)
     * {@inheritDoc}
     */
    public UITreeNodeIF<T> getChildNode(UITreeNodeIF<T> node, int index) {

        return node.getChildNode(index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.model.UITreeModelIF#getParent(java.lang.Object)
     * {@inheritDoc}
     */
    public UITreeNodeIF<T> getParent(UITreeNodeIF<T> node) {

        return node.getParentNode();
    }

}