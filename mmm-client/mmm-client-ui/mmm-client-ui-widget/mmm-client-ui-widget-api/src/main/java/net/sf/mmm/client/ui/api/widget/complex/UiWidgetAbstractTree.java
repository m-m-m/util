/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;
import net.sf.mmm.util.lang.api.function.Consumer;

/**
 * This is the interface for a {@link UiWidgetAbstractDataSet data set widget} that represents a <em>tree</em>
 * widget. Such widget represents a tree-structure showing tree-nodes that can be collapsed and expanded. It
 * has the following features:
 * <ul>
 * <li>Configured via {@link #setTreeModel(UiTreeModel) tree model}</li>
 * <li></li>
 * </ul>
 *
 * @param <NODE> is the generic type of the tree-nodes. E.g. {@link net.sf.mmm.util.collection.api.TreeNode}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAbstractTree<NODE> extends UiWidgetAbstractDataSet<NODE>, UiWidgetWithValue<NODE> {

  /** The {@link #hasStyle(String) style} of this tree widget. */
  String STYLE_TREE = CssStyles.TREE;

  /**
   * This method gets the {@link UiTreeModel} adapting from {@literal <NODE>} to this tree-widget.
   *
   * @return the {@link UiTreeModel}. May be <code>null</code> if NOT set.
   */
  UiTreeModel<NODE> getTreeModel();

  /**
   * This method sets the {@link UiTreeModel} adapting from {@literal <NODE>} to this tree-widget.
   *
   * @param model is the {@link UiTreeModel} to set.
   */
  void setTreeModel(UiTreeModel<NODE> model);

  /**
   * This method sets the {@link UiTreeNodeRenderer} used to render the {@link UiWidgetRegular widgets} for an
   * individual {@literal <NODE>}.
   *
   * @param renderer is the {@link UiTreeNodeRenderer}.
   */
  void setTreeNodeRenderer(UiTreeNodeRenderer<NODE, ?> renderer);

  /**
   * This method gets the {@link UiWidgetTreeNode} for the given {@literal <NODE>}. It contains a
   * {@link UiWidgetTreeNode#getNodeWidget() node widget} that was created by the {@link UiTreeNodeRenderer}.
   * Further you can {@link UiWidgetTreeNode#setCollapsed(boolean) collapse or expand} the node.
   *
   * @see #setTreeNodeRenderer(UiTreeNodeRenderer)
   *
   * @param node is the {@literal <NODE>}.
   * @return the according {@link UiWidgetTreeNode} or <code>null</code> if the given <code>node</code> is NOT
   *         in this tree.
   */
  UiWidgetTreeNode<NODE> getTreeNodeWidget(NODE node);

  /**
   * This method collapses all nodes.
   */
  void collapseAllNodes();

  /**
   * This method expands all nodes that have already been
   * {@link UiTreeModel#getChildrenAsync(Object, Consumer) loaded}.
   */
  void expandNodes();

  /**
   * This is the interface for the model (adapter) for a
   * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree}. If the tree structure should be editable
   * for the end-user you need to implement {@link UiWidgetAbstractTree.UiTreeModelMutable}. However,
   * modification of the tree node data is still possible if the tree is
   * {@link UiWidgetAbstractTree#isEditable() editable}.
   *
   * @param <NODE> is the generic type of the tree-nodes of this model.
   */
  interface UiTreeModel<NODE> {

    /**
     * This method gets the children of the given tree-node.
     *
     * @param node the tree-node for which the children are requested.
     * @return the {@link List} of child-nodes or <code>null</code> if NOT available and
     *         {@link #getChildrenAsync(Object, Consumer) asynchronous loading} is required.
     */
    List<NODE> getChildren(NODE node);

    /**
     * This method gets the children of the given tree-node asynchronously.
     *
     * @param node the tree-node for which the children are requested.
     * @param callback is the {@link Consumer} that will be {@link Consumer#accept(Object) called} when the
     *        children are available. Maybe <code>null</code> if only loading shall be triggered.
     */
    void getChildrenAsync(NODE node, Consumer<List<NODE>> callback);

  }

  /**
   * Extends {@link UiWidgetAbstractTree.UiTreeModel} with features for editing the tree structure. It
   * represents the current state of the tree for a tree widget. Modifications may still be cancelled and
   * reverted.<br/>
   * <b>ATTENTION:</b><br/>
   * Implementing a mutable tree model is not always easy. The suggested approach is to create a copy of each
   * tree node that can be modified by the tree widget without causing harm. Central caching of tree nodes
   * from the server can be done based on a central and immutable {@link UiWidgetAbstractTree.UiTreeModel}
   * used by the according mutable tree models.
   *
   * @param <NODE> is the generic type of the tree-nodes of this model.
   */
  @SuppressWarnings("unchecked")
  interface UiTreeModelMutable<NODE> extends UiTreeModel<NODE> {

    /**
     * Adds the given <code>child</code> {@literal <NODE>} to the given <code>parent</code> at the specified
     * <code>index</code>.
     *
     * @param parent is the parent {@literal <NODE>} where to add the given <code>child</code>.
     * @param child is the {@literal <NODE>} to add to the {@link #getChildren(Object) children} of
     *        <code>parent</code>. This method will remove the <code>child</code> from any previous parent.
     *        The instance of the child may later be modified. To prevent undesired modifications create a
     *        deep-copy of the node before calling this method.
     * @param index is the index where to add the child.
     */
    void addChild(NODE parent, NODE child, int index);

    /**
     * Convenience method variant of {@link #addChild(Object, Object, int)} to add a child at the end.
     *
     * @see #addChild(Object, Object, int)
     *
     * @param parent is the parent {@literal <NODE>} where to add the given <code>child</code>.
     * @param child is the {@literal <NODE>} to add to the {@link #getChildren(Object) children} of
     *        <code>parent</code>.
     */
    void addChild(NODE parent, NODE child);

    /**
     * Removes the given <code>children</code> from the <code>parent</code>.
     *
     * @param parent is the parent {@literal <NODE>} where to remove the given <code>children</code>.
     * @param children are the children to remove.
     */
    void removeChilden(NODE parent, NODE... children);

  }

  /**
   * This is the interface for the main widget representing a node of the tree.
   *
   * @param <NODE> is the generic type of the {@link #getValue() node data}.
   */
  interface UiWidgetTreeNode<NODE> extends UiWidget, AttributeWriteCollapsed, AttributeReadValue<NODE> {

    /**
     * Please note that you may need to cast to the {@link UiWidgetRegular}-type according to your
     * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer}.
     *
     * @see UiWidgetAbstractTree#setTreeNodeRenderer(UiTreeNodeRenderer)
     *
     * @return the {@link UiWidgetRegular regular widget} used to render the node data.
     */
    UiWidgetRegular getNodeWidget();

  }

  /**
   * The renderer responsible to {@link #create(net.sf.mmm.client.ui.api.UiContext) create} widgets and
   * {@link #assignNodeToWidget(Object, UiWidgetRegular) assign} a {@literal <NODE>} to them.
   *
   * @param <NODE> is the generic type of the tree-nodes.
   * @param <WIDGET> is the generic type of the {@link UiWidgetRegular} used to render a {@literal <NODE>}.
   */
  interface UiTreeNodeRenderer<NODE, WIDGET extends UiWidgetRegular> extends UiSingleWidgetFactory<WIDGET> {

    /**
     * This method assigns the given {@literal <NODE>} to the given {@link UiWidgetRegular widget}.<br/>
     * As a simple example {@literal <WIDGET>} may be
     * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel} and {@literal <NODE>} may be {@link String}.
     * Then this method would simply do <code>widget.setLabel(node)</code>.<br/>
     * <b>ATTENTION:</b><br/>
     * The {@link UiWidgetRegular} may be reused for optimal performance if the {@literal <NODE>} has changed
     * or been removed. Therefore this method needs to reset and update the entire state of the
     * {@link UiWidgetRegular}.
     *
     * @param node is the {@literal <NODE>} to assign.
     * @param widget is the {@link UiWidgetRegular} that is to be assigned. It has initially been created via
     *        {@link #create(net.sf.mmm.client.ui.api.UiContext)}.
     */
    void assignNodeToWidget(NODE node, WIDGET widget);

  }

  /**
   * This is the default implementation of
   * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer}.
   *
   * @param <NODE> is the generic type of the tree-nodes.
   */
  class UiTreeNodeRendererDefault<NODE> implements UiTreeNodeRenderer<NODE, UiWidgetLabel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLabel create(UiContext context) {

      return context.getWidgetFactory().create(UiWidgetLabel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignNodeToWidget(NODE node, UiWidgetLabel widget) {

      String label;
      if (node == null) {
        label = "-";
      } else {
        label = node.toString();
      }
      widget.setLabel(label);
    }
  }

}
