/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectedValue;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModel;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiWidgetTreeNode;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable}.
 * 
 * @param <NODE> is the generic type of the nodes of the tree.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterTree<NODE> extends UiWidgetAdapterActive, AttributeWriteSelectedValue<NODE>,
    AttributeWriteSelectionMode, AttributeWriteStringTitle {

  /**
   * @param node is the root node.
   */
  void setRootNode(NODE node);

  /**
   * @param model is the {@link UiTreeModel}.
   */
  void setTreeModel(UiTreeModel<NODE> model);

  /**
   * @see UiWidgetAdapterTree#setTreeNodeRenderer(UiTreeNodeRenderer)
   * @param renderer is the {@link UiTreeNodeRenderer}.
   */
  void setTreeNodeRenderer(UiTreeNodeRenderer<NODE, ?> renderer);

  /**
   * @see UiWidgetAdapterTree#getTreeNodeWidget(Object)
   * @param node is the {@literal <NODE>}.
   * @return is the {@link UiWidgetTreeNode} or <code>null</code>.
   */
  UiWidgetTreeNode<NODE> getTreeNodeWidget(NODE node);

  /**
   * @see UiWidgetAdapterTree#collapseAllNodes()
   */
  void collapseAllNodes();

  /**
   * @see UiWidgetAdapterTree#expandNodes()
   */
  void expandNodes();

}
