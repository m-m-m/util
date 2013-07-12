/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.sf.mmm.client.ui.api.widget.model.UiTreeModel;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTree;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Focusable;
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
public class UiWidgetAdapterGwtTree<NODE> extends UiWidgetAdapterGwtWidgetActive<Tree> implements
    UiWidgetAdapterTree<NODE> {

  /** @see #setRootNode(Object) */
  private NODE rootNode;

  /** @see #setTreeModel(UiTreeModel) */
  private UiTreeModel<NODE> treeModel;

  /** Maps from {@literal <NODE>} to {@link TreeNodeAdapter}. */
  private Map<NODE, TreeNodeAdapter> nodeMap;

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
  public void setRootNode(NODE node) {

    if (this.rootNode != null) {
      TreeNodeAdapter treeNodeAdapter = this.nodeMap.get(this.rootNode);
      if (treeNodeAdapter != null) {
        treeNodeAdapter.remove();
      }
      this.nodeMap.clear();
    }
    this.rootNode = node;
    TreeNodeAdapter rootNodeAdapter = new TreeNodeAdapter(this.rootNode);
    this.nodeMap.put(this.rootNode, rootNodeAdapter);
    rootNodeAdapter.loadChildren();
    getToplevelWidget().addItem(rootNodeAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // getToplevelWidget().setEnabled(enabled);
  }

  private void onOpenEvent(OpenEvent<TreeItem> event) {

    TreeNodeAdapter treeNodeAdapter = (TreeNodeAdapter) event.getTarget();
    treeNodeAdapter.loadChildren();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Tree createToplevelWidget() {

    Tree tree = new Tree();
    OpenHandler<TreeItem> openHandler = new OpenHandler<TreeItem>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onOpen(OpenEvent<TreeItem> event) {

        onOpenEvent(event);
      }
    };
    tree.addOpenHandler(openHandler);
    tree.setWidth("300");
    tree.setHeight("300");
    return tree;
  }

  /**
   * This inner class adapts from {@literal <NODE>} to {@link TreeItem}.
   */
  protected class TreeNodeAdapter extends TreeItem implements Consumer<List<NODE>> {

    /** @see #getNode() */
    private final NODE node;

    /**
     * The constructor.
     * 
     * @param node is the unwrapped node (business object).
     */
    public TreeNodeAdapter(NODE node) {

      super();
      this.node = node;
      updateNode();
    }

    /**
     * Updates the UI of this node.
     */
    protected void updateNode() {

      // TODO: use column for that...
      String title = this.node.toString();
      setText(SafeHtmlUtils.htmlEscape(title));
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

      for (NODE childNode : value) {
        TreeNodeAdapter childNodeAdapter = new TreeNodeAdapter(childNode);
        UiWidgetAdapterGwtTree.this.nodeMap.put(childNode, childNodeAdapter);
        getToplevelWidget().addItem(childNodeAdapter);
      }
    }
  }
}
