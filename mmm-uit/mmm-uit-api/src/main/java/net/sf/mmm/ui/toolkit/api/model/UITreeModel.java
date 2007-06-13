/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.model;

import net.sf.mmm.ui.toolkit.api.event.UITreeModelListener;

/**
 * This is the interface for the model of a tree widget.
 * 
 * @see net.sf.mmm.ui.toolkit.api.widget.UITree
 * 
 * @param <N> is the templated type of the tree nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UITreeModel<N> {

  /**
   * This method registers the given change listener to this model.
   * 
   * @param listener is the change listener to add.
   */
  void addListener(UITreeModelListener<N> listener);

  /**
   * This method unregisters the given change listener from this model. The
   * listener should have been registered via the addChangeListener method
   * before.
   * 
   * @param listener is the change listener to remove.
   */
  void removeListener(UITreeModelListener<N> listener);

  /**
   * This method gets the root node object. That is the top-level node of the
   * tree.
   * 
   * @return the root node.
   */
  N getRootNode();

  /**
   * This method gets the class reflecting the type of the generic node type.
   * 
   * @return the node-type.
   */
  Class<? extends N> getNodeType();

  /**
   * This method determines the number of children of the given node.
   * 
   * @param node is a node retrieved from this model via the method
   *        <code>getRootNode</code> or <code>getChildNode</code>.
   * @return the number of children of the given node.
   */
  int getChildCount(N node);

  /**
   * This method gets the child node of the given node at the given position.
   * 
   * @param node is a node retrieved from this model via the method
   *        <code>getRootNode</code> or <code>getChildNode</code>.
   * @param index is the position of the requested child node. The value must be
   *        greater or equal to zero and less than
   *        {@link #getChildCount(Object)}.
   * @return the child node of the given node at the given index.
   */
  N getChildNode(N node, int index);

  /**
   * This method gets the parent node of the given node.
   * 
   * @param node is node whose parent is requested.
   * @return the parent node of the given node or <code>null</code> if the
   *         given node is the root node.
   */
  N getParent(N node);

  /**
   * This method gets the string representation of the given node.
   * 
   * @param node is the node to convert.
   * @return the string representation of the given node.
   */
  String toString(N node);

}
