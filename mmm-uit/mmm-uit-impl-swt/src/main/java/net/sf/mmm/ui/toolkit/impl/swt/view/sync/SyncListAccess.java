/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

import org.eclipse.swt.widgets.List;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.List}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncListAccess extends AbstractSyncControlAccess<List> {

  /**
   * operation to set the {@link org.eclipse.swt.widgets.List#setSelection(int)
   * selection} of the list.
   */
  private static final String OPERATION_SET_SELECTION = "setSelection";

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.List#getSelectionIndex() selection} of the
   * list.
   */
  private static final String OPERATION_GET_SELECTION_INDEX = "getSelectionIndex";

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.List#getSelectionIndices() selections} of
   * the list.
   */
  private static final String OPERATION_GET_SELECTION_INDICES = "getSelectionIndices";

  /**
   * operation to {@link org.eclipse.swt.widgets.List#removeAll() "remove all"}
   * items of the list.
   */
  private static final String OPERATION_REMOVE_ALL = "removeAll";

  /**
   * operation to {@link org.eclipse.swt.widgets.List#add(java.lang.String) add}
   * an item to the list.
   */
  private static final String OPERATION_ADD = "add";

  /** the SWT list to access */
  private List list;

  /** the selection */
  private int selection;

  /** the selection list */
  private int[] selectionList;

  /** an item to add */
  private String item;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the control.
   */
  public SyncListAccess(UiFactorySwt uiFactory, UiList<?> node, int swtStyle) {

    super(uiFactory, node, swtStyle);
    this.list = null;
    this.selection = -1;
    this.selectionList = new int[0];
    this.item = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.list = new List(getParent(), getStyle());
    super.createSynchron();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SELECTION_INDEX) {
      this.selection = this.list.getSelectionIndex();
    } else if (operation == OPERATION_SET_SELECTION) {
      this.list.setSelection(this.selection);
    } else if (operation == OPERATION_GET_SELECTION_INDICES) {
      this.selectionList = this.list.getSelectionIndices();
    } else if (operation == OPERATION_REMOVE_ALL) {
      this.list.removeAll();
    } else if (operation == OPERATION_ADD) {
      this.list.add(this.item);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  public List getDelegate() {

    return this.list;
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.List#getSelectionIndices() indices} of the
   * selected list-items.
   * 
   * @return the indices of the selected items (empty array for no selection).
   */
  public int[] getSelections() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTION_INDICES);
    return this.selectionList;
  }

  /**
   * This method gets the index of the selected item in the list.
   * 
   * @return the selection or <code>-1</code> if no item is selected.
   */
  public int getSelection() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTION_INDEX);
    return this.selection;
  }

  /**
   * This method selects the item with the given index.
   * 
   * @param selectionIndex is the index of the item to select.
   */
  public void setSelection(int selectionIndex) {

    assert (checkReady());
    this.selection = selectionIndex;
    invoke(OPERATION_SET_SELECTION);
  }

  /**
   * This method {@link org.eclipse.swt.widgets.List#removeAll() "removes all"}
   * items from the list.
   */
  public void removeAll() {

    assert (checkReady());
    invoke(OPERATION_REMOVE_ALL);
  }

  /**
   * This method {@link org.eclipse.swt.widgets.List#add(java.lang.String) adds}
   * an item to the list at the end of the item-list.
   * 
   * @param itemText is the display text of the item.
   */
  public void addItem(String itemText) {

    assert (checkReady());
    this.item = itemText;
    invoke(OPERATION_ADD);
  }

}
