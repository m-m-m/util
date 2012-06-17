/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import java.lang.reflect.Array;

import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.model.TreeModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTreeAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTree} interface using SWT as
 * the UI toolkit.
 * 
 * @param <N> is the templated type of the tree-nodes that can be
 *        {@link #getSelection() selected} with this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITreeImpl<N> extends AbstractUiWidgetSwt<Tree> implements UiTree<N> {

  /** the unwrapped swt tree */
  private final SyncTreeAccess syncAccess;

  /** the model adapter */
  private final TreeModelAdapter<N> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param multiSelection - if <code>true</code> the user can select multiple,
   *        else ony one.
   */
  public UITreeImpl(UiFactorySwt uiFactory, boolean multiSelection) {

    super(uiFactory);
    int style = SWT.BORDER;
    if (multiSelection) {
      style |= SWT.MULTI;
    } else {
      style |= SWT.SINGLE;
    }
    this.syncAccess = new SyncTreeAccess(uiFactory, this, style);
    this.modelAdapter = new TreeModelAdapter<N>(this.syncAccess);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncTreeAccess getAdapter() {

    return this.syncAccess;
  }

  /**
   * {@inheritDoc}
   */
  public void create() {

    // super.create();
    this.modelAdapter.initialize();
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

    return this.modelAdapter.getModel();
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiTreeMvcModel<N> newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMultiSelection() {

    return this.syncAccess.hasStyle(SWT.MULTI);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public N getSelection() {

    Object[] selection = this.syncAccess.getSelection();
    if (selection.length > 0) {
      return (N) selection[0];
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public N[] getSelections() {

    Object[] selection = this.syncAccess.getSelection();
    N[] result = (N[]) Array.newInstance(getModel().getNodeType(), selection.length);
    System.arraycopy(selection, 0, result, 0, selection.length);
    return result;
  }

}
