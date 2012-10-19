/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeReadMultiSelection;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;

/**
 * This is the interface for a tree. A tree is a widget used to display a hierachical structure of nodes
 * (items of the tree). The user can open and close the nodes of the tree that are no leaves. Further he can
 * select one or multiple nodes (depending on selection type).
 * 
 * @param <N> is the templated type of the tree-nodes that can be {@link #getSelection() selected} with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiTree<N> extends UiWidget, AttributeReadMultiSelection {

  /** the type of this object */
  String TYPE = "Tree";

  /**
   * This method gets the model of this tree.
   * 
   * @return the tree model.
   */
  UiTreeMvcModel<N> getModel();

  /**
   * This method sets the model of this tree.
   * 
   * @param newModel is the new tree model to set.
   */
  void setModel(UiTreeMvcModel<N> newModel);

  /**
   * This method gets the selected {@link UiTreeMvcModel#getChildNode(Object, int) node}.
   * 
   * @return the selected node or <code>null</code> if no node is selected.
   */
  N getSelection();

  /**
   * This method gets all selected nodes.
   * 
   * @see AttributeReadMultiSelection#isMultiSelection()
   * 
   * @return an array containing all selected nodes. The array will have a <code>length</code> of zero if no
   *         node is selected.
   */
  N[] getSelections();

}
