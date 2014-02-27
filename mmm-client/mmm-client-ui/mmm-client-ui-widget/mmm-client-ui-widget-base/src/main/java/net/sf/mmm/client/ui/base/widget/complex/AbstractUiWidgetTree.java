/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.Collection;
import java.util.Collections;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTree;

/**
 * This is the abstract base implementation of
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <NODE> is the generic type of the tree-nodes. E.g. {@link net.sf.mmm.util.collection.api.TreeNode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetTree<ADAPTER extends UiWidgetAdapterTree<NODE>, NODE> extends
    AbstractUiWidgetAbstractDataSet<ADAPTER, NODE, NODE, NODE> implements UiWidgetTree<NODE> {

  /** The default {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer}. */
  private static final UiTreeNodeRenderer<Object, UiWidgetLabel> DEFAULT_RENDERER = new UiTreeNodeRendererDefault<Object>();

  /** @see #getTreeModel() */
  private UiTreeModel<NODE> treeModel;

  /** @see #setTreeNodeRenderer(net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer) */
  private UiTreeNodeRenderer<NODE, ?> treeNodeRenderer;

  /** @see #getTitle() */
  private String title;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public AbstractUiWidgetTree(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.treeNodeRenderer = (UiTreeNodeRenderer) DEFAULT_RENDERER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.title != null) {
      adapter.setTitle(this.title);
    }
    if (this.treeModel != null) {
      adapter.setTreeModel(this.treeModel);
    }
    if (this.treeNodeRenderer != null) {
      adapter.setTreeNodeRenderer(this.treeNodeRenderer);
    }
    NODE rootNode = getOriginalValue();
    if (rootNode != null) {
      adapter.setRootNode(rootNode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeModel(UiTreeModel<NODE> model) {

    this.treeModel = model;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTreeModel(model);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTreeNodeRenderer(UiTreeNodeRenderer<NODE, ?> renderer) {

    this.treeNodeRenderer = renderer;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTreeNodeRenderer(renderer);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTreeNode<NODE> getTreeNodeWidget(NODE node) {

    if (hasWidgetAdapter()) {
      return getWidgetAdapter().getTreeNodeWidget(node);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void collapseAllNodes() {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().collapseAllNodes();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void expandNodes() {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().expandNodes();
    }
  }

  // /**
  // * {@inheritDoc}
  // */
  // @SuppressWarnings({ "unchecked", "rawtypes" })
  // @Override
  // protected Class<NODE> getValueClass() {
  //
  // NODE rootNode = getRecentValue();
  // if (rootNode == null) {
  // return null;
  // }
  // return (Class) rootNode.getClass();
  // }

  /**
   * @return the treeModel
   */
  @Override
  public UiTreeModel<NODE> getTreeModel() {

    return this.treeModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    this.title = title;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTitle(title);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(NODE value, boolean forUser) {

    super.doSetValue(value, forUser);
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setRootNode(value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetSelected(NODE container, boolean selected) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<NODE> getAllAvailableItems() {

    // TODO Auto-generated method stub
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NODE getLastAvailableItem() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NODE getItemContainer(NODE item) {

    // TODO Auto-generated method stub
    return item;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NODE getItem(NODE container) {

    // TODO Auto-generated method stub
    return container;
  }

  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public NODE getSelectedValue() {
  //
  // if (hasWidgetAdapter()) {
  // return getWidgetAdapter().getSelectedValue();
  // } else {
  // if ((this.selectedValues == null) || (this.selectedValues.isEmpty())) {
  // return null;
  // }
  // return this.selectedValues.get(0);
  // }
  // }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public List<NODE> getSelectedValues() {
  //
  // if (hasWidgetAdapter()) {
  // return getWidgetAdapter().getSelectedValues();
  // } else {
  // return this.selectedValues;
  // }
  // }

}
