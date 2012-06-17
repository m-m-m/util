/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.Dimension;
import java.lang.reflect.Array;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.TreeModelAdapter;

/**
 * This class is the implementation of the UITree interface using Swing as the
 * UI toolkit.
 * 
 * @param <N> is the templated type of the tree-nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTreeImpl<N> extends AbstractUiWidgetSwingComposed<JTree, JScrollPane> implements
    UiTree<N> {

  /** the tree model adapter */
  private TreeModelAdapter<N> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiTreeImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JTree(), new JScrollPane());
    JTree tree = getDelegate();
    tree.setExpandsSelectedPaths(true);
    tree.setShowsRootHandles(true);
    JScrollPane scrollPanel = getAdapter().getToplevelDelegate();
    scrollPanel.setViewportView(tree);
    scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public UiTreeMvcModel<N> getModel() {

    if (this.modelAdapter == null) {
      return null;
    } else {
      return this.modelAdapter.getModel();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiTreeMvcModel<N> newModel) {

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new TreeModelAdapter<N>(newModel);
    getDelegate().setModel(this.modelAdapter);
  }

  /**
   * This method sets the selection mode of this tree.
   * 
   * @param multiSelection - if <code>true</code> the user can select multiple
   *        items, else ony one.
   */
  public void setMultiSelection(boolean multiSelection) {

    int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
    if (multiSelection) {
      mode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
    }
    getDelegate().getSelectionModel().setSelectionMode(mode);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMultiSelection() {

    int mode = getDelegate().getSelectionModel().getSelectionMode();
    return (mode == TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public N getSelection() {

    TreePath selection = getDelegate().getSelectionPath();
    if (selection != null) {
      return (N) selection.getLastPathComponent();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public N[] getSelections() {

    TreePath[] selections = getDelegate().getSelectionPaths();
    int len = 0;
    if (selections != null) {
      len = selections.length;
    }
    N[] result = (N[]) Array.newInstance(getModel().getNodeType(), len);
    for (int i = 0; i < len; i++) {
      result[i] = (N) selections[i].getLastPathComponent();
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    getDelegate().addTreeSelectionListener(getAdapter());
    return true;
  }

}
