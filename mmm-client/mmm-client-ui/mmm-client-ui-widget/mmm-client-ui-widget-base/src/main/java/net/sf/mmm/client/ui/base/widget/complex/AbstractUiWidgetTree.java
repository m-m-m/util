/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.Arrays;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.api.widget.model.UiTreeModel;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTree;
import net.sf.mmm.util.validation.api.ValidationState;

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

  /** @see #getTreeModel() */
  private UiTreeModel<NODE> treeModel;

  /** @see #getSelectedValues() */
  private List<NODE> selectedValues;

  /** @see #getSelectionMode() */
  private SelectionMode selectionMode;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetTree(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.treeModel != null) {
      adapter.setTreeModel(this.treeModel);
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
  protected Class<NODE> getValueClass() {

    return (Class) this.treeModel.getRootNode().getClass();
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
  @SuppressWarnings("unchecked")
  @Override
  public boolean setSelectedValue(NODE selectedValue) {

    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setSelectedValue(selectedValue);
    } else {
      this.selectedValues = Arrays.asList(selectedValue);
      // return isContained(selectedValue);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValues(List<NODE> selection) {

    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setSelectedValues(selection);
    } else {
      this.selectedValues = selection;
      // return isContained(selection);
    }
    return false;
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
      // getWidgetAdapter().setSelectionMode(selectionMode);
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

    // TODO Auto-generated method stub
    super.doSetValue(value, forUser);
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setRootNode(value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected NODE doGetValue(NODE template, ValidationState state) throws RuntimeException {

    // TODO Auto-generated method stub
    return super.doGetValue(template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    if (hasWidgetAdapter()) {
      // return getWidgetAdapter().hasSelectedValue();
      return true;
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
      // return getWidgetAdapter().getSelectedValue();
      return null;
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
      // getWidgetAdapter().getSelectedValues();
      return null;
    } else {
      return this.selectedValues;
    }
  }

}
