/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
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
    AbstractUiWidgetActive<ADAPTER, NODE> implements UiWidgetTree<NODE> {

  /** The default {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer}. */
  private static final UiTreeNodeRenderer<Object, UiWidgetLabel> DEFAULT_RENDERER = new UiTreeNodeRendererDefault<Object>();

  /** @see #getTreeModel() */
  private UiTreeModel<NODE> treeModel;

  /** @see #setTreeNodeRenderer(net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractTree.UiTreeNodeRenderer) */
  private UiTreeNodeRenderer<NODE, ?> treeNodeRenderer;

  /** @see #getSelectedValues() */
  private List<NODE> selectedValues;

  /** @see #getSelectionMode() */
  private SelectionMode selectionMode;

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
    this.selectionMode = SelectionMode.SINGLE_SELECTION;
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
    if (this.selectionMode != null) {
      adapter.setSelectionMode(this.selectionMode);
    }
    if (getOriginalValue() != null) {
      adapter.setRootNode(getOriginalValue());
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

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected Class<NODE> getValueClass() {

    NODE rootNode = this.treeModel.getRootNode();
    return (Class) rootNode.getClass();
  }

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
  public void setSelectedValue(NODE selectedValue) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSelectedValue(selectedValue);
    } else {
      this.selectedValues = Arrays.asList(selectedValue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedValues(List<NODE> selection) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSelectedValues(selection);
    } else {
      this.selectedValues = selection;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SelectionMode getSelectionMode() {

    return this.selectionMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    this.selectionMode = selectionMode;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSelectionMode(selectionMode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSelectionHandler(UiHandlerEventSelection<NODE> handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeSelectionHandler(UiHandlerEventSelection<NODE> handler) {

    return removeEventHandler(handler);
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
  public boolean hasSelectedValue() {

    if (hasWidgetAdapter()) {
      return getWidgetAdapter().hasSelectedValue();
    } else {
      return ((this.selectedValues != null) && (!this.selectedValues.isEmpty()));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NODE getSelectedValue() {

    if (hasWidgetAdapter()) {
      return getWidgetAdapter().getSelectedValue();
    } else {
      if ((this.selectedValues == null) || (this.selectedValues.isEmpty())) {
        return null;
      }
      return this.selectedValues.get(0);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<NODE> getSelectedValues() {

    if (hasWidgetAdapter()) {
      return getWidgetAdapter().getSelectedValues();
    } else {
      return this.selectedValues;
    }
  }

}
