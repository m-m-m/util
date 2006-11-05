/* $Id$ */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.UITreeModelListener;

/**
 * This is the default implementation of the UIListModel interface.
 * 
 * @param <T>
 *        is the templated type of the user data in the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DefaultUITreeModel<T> extends AbstractUITreeModel<UITreeNodeIF<T>> {

  /** the root node of the tree */
  private DefaultUITreeNode<T> rootNode;

  /**
   * The constructor.
   */
  public DefaultUITreeModel() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param rootNodeData
   *        is the data object for the root node.
   */
  public DefaultUITreeModel(T rootNodeData) {

    super();
    this.rootNode = new DefaultUITreeNode<T>(this, null, rootNodeData);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.model.AbstractUITreeModel#handleListenerException(net.sf.mmm.ui.toolkit.api.event.UITreeModelListener,
   *      java.lang.Throwable)
   */
  @Override
  protected void handleListenerException(UITreeModelListener listener, Throwable t) {

  // we ignore this...
  }

  /**
   * This method returns the same object as the getRootNode method. It only
   * exists to avoid casting and to specify the used node type.
   * 
   * @see DefaultUITreeModel#getRootNode()
   * 
   * @return the root node.
   */
  public DefaultUITreeNode<T> getRoot() {

    return this.rootNode;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITreeModel#getRootNode()
   */
  public UITreeNodeIF<T> getRootNode() {

    return this.rootNode;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITreeModel#getChildCount(java.lang.Object)
   */
  public int getChildCount(UITreeNodeIF<T> node) {

    return node.getChildNodeCount();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITreeModel#getChildNode(java.lang.Object,
   *      int)
   */
  public UITreeNodeIF<T> getChildNode(UITreeNodeIF<T> node, int index) {

    return node.getChildNode(index);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.model.UITreeModel#getParent(java.lang.Object)
   */
  public UITreeNodeIF<T> getParent(UITreeNodeIF<T> node) {

    return node.getParentNode();
  }

}
