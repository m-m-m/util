/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeModel;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTree;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * This is the implementation of {@link UiWidgetAdapterTree} using GWT based on {@link Tree}.
 * 
 * @param <NODE> is the generic type of the tree-nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTree<NODE> extends UiWidgetAdapterGwtWidgetActive<FlowPanel> implements
    UiWidgetAdapterTree<NODE> {

  /** @see #setRootNode(Object) */
  private NODE rootNode;

  /** @see #setTreeModel(UiTreeModel) */
  private UiTreeModel<NODE> treeModel;

  /** @see #setTreeNodeRenderer(UiTreeNodeRenderer) */
  private UiTreeNodeRenderer<NODE, ?> treeNodeRenderer;

  /** Maps from {@literal <NODE>} to {@link TreeNodeAdapter}. */
  private Map<NODE, TreeNodeAdapter> nodeMap;

  /** @see #getGwtTree() */
  private Tree tree;

  /** @see #getScrollPanel() */
  private ScrollPanel scrollPanel;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTree() {

    super();
    this.nodeMap = new HashMap<NODE, TreeNodeAdapter>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeModel(UiTreeModel<NODE> treeModel) {

    this.treeModel = treeModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeNodeRenderer(UiTreeNodeRenderer<NODE, ?> renderer) {

    this.treeNodeRenderer = renderer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    // not directly supported... emulate or switch to CellTree?
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValue(NODE selectedValue) {

    TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(selectedValue);
    if (treeNodeAdapter != null) {
      getGwtTree().setSelectedItem(treeNodeAdapter);
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValues(List<NODE> selectedValues) {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    return (getGwtTree().getSelectedItem() != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NODE getSelectedValue() {

    TreeNodeAdapter selectedItem = (TreeNodeAdapter) getGwtTree().getSelectedItem();
    if (selectedItem != null) {
      return selectedItem.node;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<NODE> getSelectedValues() {

    // TODO Auto-generated method stub
    return Collections.EMPTY_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getTreeNodeWidget(NODE node) {

    TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(node);
    if (treeNodeAdapter != null) {
      return treeNodeAdapter.widget;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRootNode(NODE node) {

    if (this.rootNode != null) {
      TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(this.rootNode);
      if (treeNodeAdapter != null) {
        treeNodeAdapter.remove();
      }
      this.nodeMap.clear();
    }
    this.rootNode = node;
    TreeNodeAdapter rootNodeAdapter = createTreeNodeAdapter(this.rootNode);
    this.nodeMap.put(this.rootNode, rootNodeAdapter);
    rootNodeAdapter.loadChildren();
    getGwtTree().addItem(rootNodeAdapter);
  }

  private TreeNodeAdapter createTreeNodeAdapter(NODE node) {

    UiWidget widget = this.treeNodeRenderer.create(getUiWidget().getContext());
    TreeNodeAdapter rootNodeAdapter = new TreeNodeAdapter(node, widget);
    return rootNodeAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return getGwtTree();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getGwtTree();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getGwtTree();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    if (enabled) {
      getGwtTree().removeStyleName("Disabled");
    } else {
      getGwtTree().addStyleName("Disabled");
    }
  }

  private void onOpenEvent(OpenEvent<TreeItem> event) {

    TreeNodeAdapter treeNodeAdapter = (TreeNodeAdapter) event.getTarget();
    if (treeNodeAdapter.getChildCount() == 0) {
      treeNodeAdapter.loadChildren();
    }
  }

  /**
   * @return the raw GWT {@link Tree}.
   */
  public Tree getGwtTree() {

    if (this.tree == null) {
      this.tree = new Tree();
      OpenHandler<TreeItem> openHandler = new OpenHandler<TreeItem>() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onOpen(OpenEvent<TreeItem> event) {

          onOpenEvent(event);
        }
      };
      this.tree.addOpenHandler(openHandler);
    }
    return this.tree;
  }

  /**
   * @return the {@link ScrollPanel} containing the {@link #getGwtTree() tree}.
   */
  public ScrollPanel getScrollPanel() {

    if (this.scrollPanel == null) {
      this.scrollPanel = new ScrollPanel(getGwtTree());
      // temporary hack for testing...
      this.scrollPanel.setSize("300px", "300px");
    }
    return this.scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FlowPanel createToplevelWidget() {

    FlowPanel toplevelWidget = new FlowPanel();
    toplevelWidget.add(getScrollPanel());
    toplevelWidget.setStylePrimaryName(UiWidgetTree.PRIMARY_STYLE);
    return toplevelWidget;
  }

  /**
   * This inner class adapts from {@literal <NODE>} to {@link TreeItem}.
   */
  protected class TreeNodeAdapter extends TreeItem implements Consumer<List<NODE>> {

    /** @see #getNode() */
    private final NODE node;

    /** @see #getWidget() */
    private final UiWidget widget;

    /**
     * The constructor.
     * 
     * @param node is the unwrapped node (business object).
     * @param widget is the {@link UiWidget} rendering this node.
     */
    public TreeNodeAdapter(NODE node, UiWidget widget) {

      super(getToplevelWidget(widget));
      this.widget = widget;
      this.node = node;
      updateNode();
    }

    /**
     * Updates the UI of this node.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void updateNode() {

      ((UiTreeNodeRenderer) UiWidgetAdapterGwtTree.this.treeNodeRenderer).assignNodeToWidget(this.node, this.widget);
    }

    /**
     * @return the node
     */
    public NODE getNode() {

      return this.node;
    }

    /**
     * Loads the children of this node asynchronously.
     */
    protected void loadChildren() {

      UiWidgetAdapterGwtTree.this.treeModel.getChildrenAsync(this.node, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(List<NODE> value) {

      clearChildren();
      for (NODE childNode : value) {
        TreeNodeAdapter childNodeAdapter = createTreeNodeAdapter(childNode);
        UiWidgetAdapterGwtTree.this.nodeMap.put(childNode, childNodeAdapter);
        addItem(childNodeAdapter);
      }
    }

    private void clearChildren() {

      int childCount = getChildCount();
      for (int i = 0; i < childCount; i++) {
        TreeNodeAdapter child = (TreeNodeAdapter) getChild(i);
        TreeNodeAdapter oldNode = UiWidgetAdapterGwtTree.this.nodeMap.remove(child.node);
        assert (oldNode == child);
      }
      removeItems();
    }
  }
}
