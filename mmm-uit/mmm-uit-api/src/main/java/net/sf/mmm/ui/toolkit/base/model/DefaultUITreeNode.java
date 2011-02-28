package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.util.event.api.ChangeType;

/**
 * This inner class is the implementation of the {@link UiTreeNode} interface.
 * 
 * @param <T> is the templated type of the user data in the tree nodes.
 */
public class DefaultUITreeNode<T> implements UiTreeNode<T> {

  /** the owning tree-model */
  private final DefaultUITreeModel<T> model;

  /** the parent node of this node */
  private DefaultUITreeNode<T> parent;

  /** the node data */
  private T data;

  /** the children of this node */
  private List<UiTreeNode<T>> children;

  /**
   * The constructor.
   * 
   * @param treeModel is the tree-model owning this node.
   * @param parentNode is the parent node of the node to create.
   * @param nodeData is the data of the node to create.
   */
  protected DefaultUITreeNode(DefaultUITreeModel<T> treeModel, DefaultUITreeNode<T> parentNode,
      T nodeData) {

    super();
    this.model = treeModel;
    this.parent = parentNode;
    this.children = new Vector<UiTreeNode<T>>();
    this.data = nodeData;
  }

  /**
   * {@inheritDoc}
   */
  public UiTreeNode<T> getParentNode() {

    return this.parent;
  }

  /**
   * {@inheritDoc}
   */
  public UiTreeNode<T> getChildNode(int index) {

    return this.children.get(index);
  }

  /**
   * {@inheritDoc}
   */
  public int getChildNodeCount() {

    return this.children.size();
  }

  /**
   * {@inheritDoc}
   */
  public int getIndexOfChildNode(UiTreeNode childNode) {

    return this.children.indexOf(childNode);
  }

  /**
   * {@inheritDoc}
   */
  public T getData() {

    return this.data;
  }

  /**
   * This method gets the data of this node.
   * 
   * @see net.sf.mmm.ui.toolkit.base.model.UiTreeNode#getData()
   * 
   * @param newData is the new node data.
   */
  public void setData(T newData) {

    this.data = newData;
    // fireChangeEvent(UITreeModelEvent.createUpdateEvent(this));
  }

  /**
   * This method creates a child node of this node.
   * 
   * @param childData is the data object of this node. See
   *        {@link UiTreeNode#getData()}.
   * @return the created node.
   */
  public DefaultUITreeNode<T> createChildNode(T childData) {

    DefaultUITreeNode<T> childNode = new DefaultUITreeNode<T>(this.model, this, childData);
    this.children.add(childNode);
    this.model.fireChangeEvent(ChangeType.ADD, childNode);
    return childNode;
  }

  /**
   * This method removes this node from its parent.
   */
  public void removeFromParent() {

    if (this.parent != null) {
      this.parent.children.remove(this);
      this.model.fireChangeEvent(ChangeType.REMOVE, this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.data == null) {
      return "";
    } else {
      return this.data.toString();
    }
  }

}
