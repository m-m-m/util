/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.model;

/**
 * This is the interface for a tree node of the default tree model.
 * 
 * @see net.sf.mmm.ui.toolkit.base.model.DefaultUITreeModel
 * 
 * @param <T> is the templated type of the user data in the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiTreeNode<T> {

  /**
   * This method gets the parent node of this node.
   * 
   * @return the parent node of this node or <code>null</code> if this is the
   *         root node of the tree.
   */
  UiTreeNode<T> getParentNode();

  /**
   * This method gets the child node of this node at the given position.
   * 
   * @param index is the position of the requested child node.
   * @return the child node at the given index.
   */
  UiTreeNode<T> getChildNode(int index);

  /**
   * This method gets the number of child nodes of this node.
   * 
   * @return the child node count.
   */
  int getChildNodeCount();

  /**
   * This method gets the index of the given child node.
   * 
   * @param childNode is the child node whos index is requested.
   * @return the index position of the requested child node or -1 if the given
   *         node is no child node of this node.
   */
  int getIndexOfChildNode(UiTreeNode childNode);

  /**
   * This method gets the data object of this node. The node itself only
   * contains the tree structure. The data is stored as generic object and is
   * used to create the display name of the tree node. That is done in the
   * toString method of the tree node implementation. By default the call is
   * delegated to the toString method of the data object.<br/> In the most
   * simple approach the data may be only a String that represents the display
   * name of this node.
   * 
   * @return the data object of this node.
   */
  T getData();

}
