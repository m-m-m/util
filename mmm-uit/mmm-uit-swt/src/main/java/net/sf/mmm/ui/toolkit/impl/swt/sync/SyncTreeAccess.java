/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchron access on a SWT
 * {@link org.eclipse.swt.widgets.Tree}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncTreeAccess extends AbstractSyncControlAccess {

  /** the empty selection */
  private static final Object[] EMPTY_SELECTION = new Object[0];

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.Tree#getSelection() selection} of the tree.
   */
  private static final String OPERATION_GET_SELECTION = "getSelection";

  /**
   * operation to {@link org.eclipse.swt.widgets.Tree#removeAll() "remove all"}
   * items of the tree.
   */
  private static final String OPERATION_REMOVE_ALL = "removeAll";

  /** the tree to access */
  private Tree tree;

  /** the selection value */
  private Object[] selection;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the tree.
   */
  public SyncTreeAccess(UIFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.tree = null;
    this.selection = EMPTY_SELECTION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Tree getSwtObject() {

    return this.tree;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SELECTION) {
      TreeItem[] selectedItems = this.tree.getSelection();
      if (selectedItems.length > 0) {
        this.selection = new Object[selectedItems.length];
        for (int i = 0; i < this.selection.length; i++) {
          this.selection[i] = selectedItems[i].getData();
        }
      } else {
        this.selection = EMPTY_SELECTION;
      }
    } else if (operation == OPERATION_REMOVE_ALL) {
      this.tree.removeAll();
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.tree = new Tree(getParent(), getStyle());
    super.createSynchron();
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Tree#getSelection() selection} of the tree.
   * 
   * @return the selection.
   */
  public Object[] getSelection() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTION);
    return this.selection;
  }

  /**
   * This method {@link org.eclipse.swt.widgets.Tree#removeAll() "removes all"}
   * items from the tree.
   */
  public void removeAll() {

    assert (checkReady());
    invoke(OPERATION_REMOVE_ALL);
  }

}
