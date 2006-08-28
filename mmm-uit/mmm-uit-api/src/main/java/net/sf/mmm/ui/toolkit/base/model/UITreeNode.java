package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.ui.toolkit.api.event.EventType;

/**
 * This inner class is the implementation of the {@link UITreeNodeIF} interface.
 * 
 * @param <T>
 *        is the templated type of the user data in the tree nodes.
 */
public class UITreeNode<T> implements UITreeNodeIF<T> {

    /** the owning tree-model */
    private final UIDefaultTreeModel<T> model;

    /** the parent node of this node */
    private UITreeNode<T> parent;

    /** the node data */
    T data;

    /** the children of this node */
    private List<UITreeNodeIF<T>> children;

    /**
     * The constructor.
     * 
     * @param treeModel
     *        is the tree-model owning this node.
     * @param parentNode
     *        is the parent node of the node to create.
     * @param nodeData
     *        is the data of the node to create.
     */
    protected UITreeNode(UIDefaultTreeModel<T> treeModel, UITreeNode<T> parentNode, T nodeData) {

        super();
        this.model = treeModel;
        this.parent = parentNode;
        this.children = new Vector<UITreeNodeIF<T>>();
        this.data = nodeData;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getParentNode()
     * {@inheritDoc}
     */
    public UITreeNodeIF<T> getParentNode() {

        return this.parent;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getChildNode(int)
     * {@inheritDoc}
     */
    public UITreeNodeIF<T> getChildNode(int index) {

        return this.children.get(index);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getChildNodeCount()
     * {@inheritDoc}
     */
    public int getChildNodeCount() {

        return this.children.size();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getIndexOfChildNode(net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF)
     * {@inheritDoc}
     */
    public int getIndexOfChildNode(UITreeNodeIF childNode) {

        return this.children.indexOf(childNode);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getData()
     * {@inheritDoc}
     */
    public T getData() {

        return this.data;
    }

    /**
     * This method gets the data of this node.
     * 
     * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getData()
     * 
     * @param newData
     *        is the new node data.
     */
    public void setData(T newData) {

        this.data = newData;
        // fireChangeEvent(UITreeModelEvent.createUpdateEvent(this));
    }

    /**
     * This method creates a child node of this node.
     * 
     * @param childData
     *        is the data object of this node. See
     *        {@link UITreeNodeIF#getData()}.
     * @return the created node.
     */
    public UITreeNode<T> createChildNode(T childData) {

        UITreeNode<T> childNode = new UITreeNode<T>(this.model, this, childData);
        this.children.add(childNode);
        this.model.fireChangeEvent(EventType.ADD, childNode);
        return childNode;
    }

    /**
     * This method removes this node from its parent.
     */
    public void removeFromParent() {

        if (this.parent != null) {
            this.parent.children.remove(this);
            this.model.fireChangeEvent(EventType.REMOVE, this);            
        }
    }
    
    /**
     * @see java.lang.Object#toString()
     * {@inheritDoc}
     */
    public String toString() {

        if (this.data == null) {
            return "";
        } else {
            return this.data.toString();
        }
    }

}