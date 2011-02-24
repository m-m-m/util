/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Table;

import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Table}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncTableAccess extends AbstractSyncControlAccess {

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.Table#getSelection() selection} of the
   * table.
   */
  private static final String OPERATION_GET_SELECTION = "getSelection";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Table#setSelection(int) selection} of the
   * table.
   */
  private static final String OPERATION_SET_SELECTION = "getSelection";

  /**
   * operation to {@link org.eclipse.swt.widgets.Table#removeAll() "remove all"}
   * items of the table.
   */
  private static final String OPERATION_REMOVE_ALL = "removeAll";

  /** the table to access */
  private Table table;

  /** the selection value */
  private int selection;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the
   *        table.
   */
  public SyncTableAccess(UiFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.table = null;
    this.selection = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Table getSwtObject() {

    return this.table;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SELECTION) {
      this.selection = this.table.getSelectionIndex();
    } else if (operation == OPERATION_SET_SELECTION) {
      this.table.setSelection(this.selection);
    } else if (operation == OPERATION_REMOVE_ALL) {
      this.table.removeAll();
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.table = new Table(getParent(), getStyle());
    this.table.setHeaderVisible(true);
    this.table.setLinesVisible(true);
    super.createSynchron();
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Table#getSelection() selection} of the
   * table.
   * 
   * @return the selection.
   */
  public int getSelection() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTION);
    return this.selection;
  }

  /**
   * This method sets the selection of the table.
   * 
   * @param newSelection is the new selection to set.
   */
  public void setSelection(int newSelection) {

    assert (checkReady());
    this.selection = newSelection;
    invoke(OPERATION_SET_SELECTION);
  }

  /**
   * This method {@link org.eclipse.swt.widgets.Table#removeAll() "removes all"}
   * items from the table.
   */
  public void removeAll() {

    assert (checkReady());
    invoke(OPERATION_REMOVE_ALL);
  }

}
