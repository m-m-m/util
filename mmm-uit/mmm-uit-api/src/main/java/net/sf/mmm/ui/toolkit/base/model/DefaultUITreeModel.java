/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

import net.sf.mmm.ui.toolkit.api.event.UITreeModelListener;

/**
 * This is the default implementation of the UIListModel interface.
 * 
 * @param <T> is the templated type of the user data in the tree nodes.
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
   * @param rootNodeData is the data object for the root node.
   */
  public DefaultUITreeModel(T rootNodeData) {

    super();
    this.rootNode = new DefaultUITreeNode<T>(this, null, rootNodeData);
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  public UITreeNodeIF<T> getRootNode() {

    return this.rootNode;
  }

  /**
   * {@inheritDoc}
   */
  public int getChildCount(UITreeNodeIF<T> node) {

    return node.getChildNodeCount();
  }

  /**
   * {@inheritDoc}
   */
  public UITreeNodeIF<T> getChildNode(UITreeNodeIF<T> node, int index) {

    return node.getChildNode(index);
  }

  /**
   * {@inheritDoc}
   */
  public UITreeNodeIF<T> getParent(UITreeNodeIF<T> node) {

    return node.getParentNode();
  }

}
