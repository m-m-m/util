/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.sync;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiSpinBox;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.custom.MySpinner;

/**
 * This class is used for synchronous access on
 * {@link net.sf.mmm.ui.toolkit.impl.swt.custom.MySpinner}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncMySpinnerAccess extends AbstractSyncCompositeAccess<MySpinner> {

  /**
   * operation to get the {@link MySpinner#getSelectedIndex() selection-index}
   * of the spinner.
   */
  private static final String OPERATION_GET_SELECTED_INDEX = "getSelectedIndex";

  /**
   * operation to set the {@link MySpinner#setSelectedIndex(int)
   * selection-index} of the spinner.
   */
  private static final String OPERATION_SET_SELECTED_INDEX = "setSelectedIndex";

  /**
   * operation to set the {@link MySpinner#setEditable(boolean) "editable flag"}
   * of the spinner.
   */
  private static final String OPERATION_SET_EDITABLE = "setEditable";

  /**
   * operation to set the {@link MySpinner#setModel(UiListMvcModel) model} of
   * the spinner.
   */
  private static final String OPERATION_SET_MODEL = "setModel";

  /** the spinner to access */
  private MySpinner spinner;

  /** the model */
  private UiListMvcModel<?> model;

  /** the editable flag */
  private boolean editable;

  /** the selection index */
  private int index;

  /**
   * The constructor.
   * 
   * @param uiFactory is used to do the synchronization.
   * @param node is the owning {@link #getNode() node}.
   * @param swtStyle is the {@link org.eclipse.swt.widgets.Widget#getStyle()
   *        style} of the composite.
   * @param listModel is the list model containing the elements of the spinner.
   */
  public SyncMySpinnerAccess(UiFactorySwt uiFactory, UiSpinBox<?> node, int swtStyle,
      UiListMvcModel<?> listModel) {

    super(uiFactory, node, swtStyle);
    this.spinner = null;
    this.model = listModel;
    this.editable = false;
    this.index = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createSynchron() {

    this.spinner = new MySpinner(getParent(), getStyle(), this.model);
    this.spinner.setEditable(this.editable);
    super.createSynchron();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_GET_SELECTED_INDEX) {
      this.index = this.spinner.getSelectedIndex();
    } else if (operation == OPERATION_SET_SELECTED_INDEX) {
      this.spinner.setSelectedIndex(this.index);
    } else if (operation == OPERATION_SET_EDITABLE) {
      this.spinner.setEditable(this.editable);
    } else if (operation == OPERATION_SET_MODEL) {
      this.spinner.setModel(this.model);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  public MySpinner getDelegate() {

    return this.spinner;
  }

  /**
   * This method sets the model of the spinner.
   * 
   * @param listModel is the model to set.
   */
  public void setModel(UiListMvcModel<?> listModel) {

    assert (checkReady());
    this.model = listModel;
    invoke(OPERATION_SET_MODEL);
  }

  /**
   * This method sets the {@link MySpinner#setEditable(boolean) "editable flag"}
   * of the spinner.
   * 
   * @param editableFlag is the new status of the editable-flag.
   */
  public void setEditable(boolean editableFlag) {

    assert (checkReady());
    this.editable = editableFlag;
    invoke(OPERATION_SET_MODEL);
  }

  /**
   * This method gets the {@link MySpinner#isEditable() "editable flag"} of the
   * spinner.
   * 
   * @return <code>true</code> if the spinner is editable.
   */
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * This method gets the {@link MySpinner#getSelectedIndex() selection-index}
   * of the spinner.
   * 
   * @return the index of the current selection.
   */
  public int getSelectedIndex() {

    assert (checkReady());
    invoke(OPERATION_GET_SELECTED_INDEX);
    return this.index;
  }

  /**
   * This method sets the {@link MySpinner#setSelectedIndex(int)
   * selection-index} of the spinner.
   * 
   * @param selectedIndex is the index of the item to select.
   */
  public void setSelectedIndex(int selectedIndex) {

    assert (checkReady());
    this.index = selectedIndex;
    invoke(OPERATION_SET_SELECTED_INDEX);
  }

}
