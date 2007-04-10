/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.Combo}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncComboAccess extends AbstractSyncControlAccess {

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Combo#setText(String) text} of the combo.
   */
  private static final String OPERATION_SET_TEXT = "setText";

  /**
   * operation to get the {@link org.eclipse.swt.widgets.Combo#getText() text}
   * of the combo.
   */
  private static final String OPERATION_GET_TEXT = "getText";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.Combo#setTextLimit(int) text-limit} of the
   * combo.
   */
  private static final String OPERATION_SET_TEXT_LIMIT = "setTextLimit";

  /**
   * operation to get the
   * {@link org.eclipse.swt.widgets.Combo#getSelection() selection} of the
   * combo.
   */
  private static final String OPERATION_GET_SELECTION = "getSelection";

  /**
   * operation to
   * {@link org.eclipse.swt.widgets.Combo#removeAll() "remove all"} items of
   * the combo.
   */
  private static final String OPERATION_REMOVE_ALL = "removeAll";

  /**
   * operation to
   * {@link org.eclipse.swt.widgets.Combo#add(java.lang.String) add} an item
   * to the combo.
   */
  private static final String OPERATION_ADD = "add";

  /**
   * operation to
   * {@link org.eclipse.swt.widgets.Combo#add(java.lang.String, int) add} an
   * item at a given index to the combo.
   */
  private static final String OPERATION_ADD_AT = "addAt";

  /**
   * operation to {@link org.eclipse.swt.widgets.Combo#remove(int) remove} the
   * item at a given index from the combo.
   */
  private static final String OPERATION_REMOVE_AT = "removeAt";

  /**
   * operation to
   * {@link org.eclipse.swt.widgets.Combo#setItem(int, java.lang.String) set}
   * the item at a given index in the combo.
   */
  private static final String OPERATION_SET_ITEM = "setItem";

  /** the combo to access */
  private Combo combo;

  /** the text of this combo */
  private String text;

  /** the selection value */
  private int selection;

  /** the maximum character count for the text */
  private int textLimit;

  /** the item to add */
  private String item;

  /** the index position of the item */
  private int index;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchronization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the combo.
   */
  public SyncComboAccess(UIFactorySwt uiFactory, int swtStyle) {

    super(uiFactory, swtStyle);
    this.combo = null;
    this.selection = 0;
    this.textLimit = Combo.LIMIT;
    this.text = "";
    this.item = null;
    this.index = -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Combo getSwtObject() {

    return this.combo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SELECTION) {
      this.selection = this.combo.getSelectionIndex();
    } else if (operation == OPERATION_SET_TEXT) {
      this.combo.setText(this.text);
    } else if (operation == OPERATION_GET_TEXT) {
      this.text = this.combo.getText();
    } else if (operation == OPERATION_SET_TEXT_LIMIT) {
      this.combo.setTextLimit(this.textLimit);
    } else if (operation == OPERATION_REMOVE_ALL) {
      this.combo.removeAll();
    } else if (operation == OPERATION_ADD_AT) {
      this.combo.add(this.item, this.index);
    } else if (operation == OPERATION_REMOVE_AT) {
      this.combo.remove(this.index);
    } else if (operation == OPERATION_SET_ITEM) {
      this.combo.setItem(this.index, this.item);
    } else if (operation == OPERATION_ADD) {
      this.combo.add(this.item);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.combo = new Combo(getParent(), getStyle());
    if ((this.text != null) && (this.text.length() > 0)) {
      this.combo.setText(this.text);
    }
    this.combo.setTextLimit(this.textLimit);
    super.createSynchron();
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Combo#getSelection() selection} of the
   * combo.
   * 
   * @return the selection.
   */
  public int getSelection() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTION);
    return this.selection;
  }

  /**
   * This method gets the
   * {@link org.eclipse.swt.widgets.Combo#getTextLimit() text-limit} of the
   * combo.
   * 
   * @return the maximum character count for the combo.
   */
  public int getTextLimit() {

    return this.textLimit;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Combo#setTextLimit(int) text-limit} of the
   * combo.
   * 
   * @param limit
   *        is the maximum character count for the combo.
   */
  public void setTextLimit(int limit) {

    assert (checkReady());
    this.textLimit = limit;
    invoke(OPERATION_SET_TEXT_LIMIT);
  }

  /**
   * This method
   * {@link org.eclipse.swt.widgets.Combo#add(java.lang.String) adds} an item
   * to the combo at the end of the item-list.
   * 
   * @param itemText
   *        is the display text of the item.
   */
  public void addItem(String itemText) {

    assert (checkReady());
    this.item = itemText;
    invoke(OPERATION_ADD);
  }

  /**
   * This method
   * {@link org.eclipse.swt.widgets.Combo#add(java.lang.String, int) adds} an
   * item to the combo at the given <code>itemIndex</code>.
   * 
   * @param itemText
   *        is the display text of the item.
   * @param itemIndex
   *        is the position where to add the new item.
   */
  public void addItem(String itemText, int itemIndex) {

    assert (checkReady());
    this.item = itemText;
    this.index = itemIndex;
    invoke(OPERATION_ADD_AT);
  }

  /**
   * This method
   * {@link org.eclipse.swt.widgets.Combo#setItem(int, java.lang.String) sets}
   * the item of the combo at the given <code>itemIndex</code>.
   * 
   * @param itemText
   *        is the display text of the item.
   * @param itemIndex
   *        is the position of the item to set (update).
   */
  public void setItem(String itemText, int itemIndex) {

    assert (checkReady());
    this.item = itemText;
    this.index = itemIndex;
    invoke(OPERATION_SET_ITEM);
  }

  /**
   * This method {@link org.eclipse.swt.widgets.Combo#remove(int) removes} the
   * item of the combo at the given <code>itemIndex</code>.
   * 
   * @param itemIndex
   *        is the position of the item to remove.
   */
  public void removeItem(int itemIndex) {

    assert (checkReady());
    this.index = itemIndex;
    invoke(OPERATION_REMOVE_AT);
  }

  /**
   * This method
   * {@link org.eclipse.swt.widgets.Combo#removeAll() "removes all"} items
   * from the combo.
   */
  public void removeAll() {

    assert (checkReady());
    invoke(OPERATION_REMOVE_ALL);
  }

  /**
   * This method gets the {@link org.eclipse.swt.widgets.Combo#getText() text}
   * of the combo.
   * 
   * @return the text
   */
  public String getText() {

    assert (checkReady());
    invoke(OPERATION_GET_TEXT);
    return this.text;
  }

  /**
   * This method sets the
   * {@link org.eclipse.swt.widgets.Combo#setText(String) text} of the combo.
   * 
   * @param newText
   *        is the new text to set.
   */
  public void setText(String newText) {

    assert (checkReady());
    this.text = newText;
    invoke(OPERATION_SET_TEXT);
  }

}
