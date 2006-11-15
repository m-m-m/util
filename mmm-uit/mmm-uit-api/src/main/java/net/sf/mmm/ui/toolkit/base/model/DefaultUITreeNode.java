package net.sf.mmm.ui.toolkit.base.model;

import java.util.List;
import java.util.Vector;

import net.sf.mmm.util.event.ChangeEvent.Type;

/**
 * This inner class is the implementation of the {@link UITreeNodeIF} interface.
 * 
 * @param <T>
 *        is the templated type of the user data in the tree nodes.
 */
public class DefaultUITreeNode<T> implements UITreeNodeIF<T> {

  /** the owning tree-model */
  private final DefaultUITreeModel<T> model;

  /** the parent node of this node */
  private DefaultUITreeNode<T> parent;

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
  protected DefaultUITreeNode(DefaultUITreeModel<T> treeModel, DefaultUITreeNode<T> parentNode, T nodeData) {

    super();
    this.model = treeModel;
    this.parent = parentNode;
    this.children = new Vector<UITreeNodeIF<T>>();
    this.data = nodeData;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getParentNode()
   */
  public UITreeNodeIF<T> getParentNode() {

    return this.parent;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getChildNode(int)
   */
  public UITreeNodeIF<T> getChildNode(int index) {

    return this.children.get(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getChildNodeCount()
   */
  public int getChildNodeCount() {

    return this.children.size();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getIndexOfChildNode(net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF)
   */
  public int getIndexOfChildNode(UITreeNodeIF childNode) {

    return this.children.indexOf(childNode);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.UITreeNodeIF#getData()
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
  public DefaultUITreeNode<T> createChildNode(T childData) {

    DefaultUITreeNode<T> childNode = new DefaultUITreeNode<T>(this.model, this, childData);
    this.children.add(childNode);
    this.model.fireChangeEvent(Type.ADD, childNode);
    return childNode;
  }

  /**
   * This method removes this node from its parent.
   */
  public void removeFromParent() {

    if (this.parent != null) {
      this.parent.children.remove(this);
      this.model.fireChangeEvent(Type.REMOVE, this);
    }
  }

  /**
   * @see java.lang.Object#toString()
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
