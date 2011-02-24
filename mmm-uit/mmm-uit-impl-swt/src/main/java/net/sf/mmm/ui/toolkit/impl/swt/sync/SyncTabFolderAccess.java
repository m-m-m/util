/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.TabFolder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncTabFolderAccess extends AbstractSyncCompositeAccess {

  /**
   * operation to set the {@link TabFolder#setSelection(int) selection-index} of
   * the tab-folder.
   */
  private static final String OPERATION_SET_SELECTION = "setSelection";

  /**
   * operation to get the {@link TabFolder#getSelectionIndex() selection-index}
   * of the tab-folder.
   */
  private static final String OPERATION_GET_SELECTION = "getSelectionIndex";

  /**
   * operation to create a
   * {@link TabItem#TabItem(org.eclipse.swt.widgets.TabFolder, int, int) tab-item}
   * in the tab-folder.
   */
  private static final String OPERATION_CREATE_TAB_ITEM = "createTabItem";

  /** the tab-folder */
  private TabFolder tabFolder;

  /** the weights to set */
  private int selectionIndex;

  /** a tab-item */
  private TabItem tabItem;

  /** the title for the tab-item */
  private String tabItemText;

  /** the index where to insert the new tab-item or <code>-1</code> for end */
  private int tabItemIndex;

  /** the control of the tab-item */
  private Control tabItemControl;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the
   *        tab-folder.
   */
  public SyncTabFolderAccess(UiFactorySwt uiFactory, int swtStyle) {

    this(uiFactory, swtStyle, null);
  }

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param swtStyle is the
   *        {@link org.eclipse.swt.widgets.Widget#getStyle() style} of the
   *        tab-folder.
   * @param swtTabFolder is the widget to access.
   */
  public SyncTabFolderAccess(UiFactorySwt uiFactory, int swtStyle, TabFolder swtTabFolder) {

    super(uiFactory, swtStyle);
    this.tabFolder = swtTabFolder;
    this.selectionIndex = 0;
    this.tabItem = null;
    this.tabItemControl = null;
    this.tabItemText = null;
    this.tabItemIndex = -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TabFolder getSwtObject() {

    return this.tabFolder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_SET_SELECTION) {
      this.tabFolder.setSelection(this.selectionIndex);
    } else if (operation == OPERATION_GET_SELECTION) {
      this.selectionIndex = this.tabFolder.getSelectionIndex();
    } else if (operation == OPERATION_CREATE_TAB_ITEM) {
      if (this.tabItemIndex == -1) {
        this.tabItem = new TabItem(this.tabFolder, SWT.NONE);
      } else {
        this.tabItem = new TabItem(this.tabFolder, SWT.NONE, this.tabItemIndex);
      }
      this.tabItem.setText(this.tabItemText);
      this.tabItem.setControl(this.tabItemControl);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.tabFolder = new TabFolder(getParent(), getStyle());
    super.createSynchron();
  }

  /**
   * This method {@link TabFolder#setSelection(int) selects} the tab with the
   * given <code>selectionIndex</code>.
   * 
   * @param selection is the index of the tab to select.
   */
  public void setSelectionIndex(int selection) {

    assert (checkReady());
    this.selectionIndex = selection;
    invoke(OPERATION_SET_SELECTION);
  }

  /**
   * This method gets the {@link TabFolder#getSelectionIndex() index} of the
   * selected tab.
   * 
   * @return the selection index.
   */
  public int getSelectionIndex() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTION);
    return this.selectionIndex;
  }

  /**
   * This method creates a new tab-item with the given <code>title</code>.
   * 
   * @param control is the control to display in the new tab.
   * @param title is the title for the new tab-item.
   * @param index is the index where to insert the new tab-item or
   *        <code>-1</code> to add the new item to the end.
   * @return the created tab-item.
   */
  public TabItem createTabItem(Control control, String title, int index) {

    assert (checkReady());
    this.tabItemText = title;
    this.tabItemIndex = index;
    this.tabItemControl = control;
    invoke(OPERATION_CREATE_TAB_ITEM);
    return this.tabItem;
  }

}
