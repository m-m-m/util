/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.custom.MySpinner;

/**
 * This class is used for synchron access on
 * {@link net.sf.mmm.ui.toolkit.impl.swt.custom.MySpinner}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncMySpinnerAccess extends AbstractSyncCompositeAccess {

  /**
   * operation to get the {@link MySpinner#getSelectedIndex() selection-index}
   * of the spinner.
   */
  private static final String OPERATION_GET_SELECTED_INDEX = "getSelectedIndex";

  /**
   * operation to set the
   * {@link MySpinner#setSelectedIndex(int) selection-index} of the spinner.
   */
  private static final String OPERATION_SET_SELECTED_INDEX = "setSelectedIndex";

  /**
   * operation to set the
   * {@link MySpinner#setEditable(boolean) "editable flag"} of the spinner.
   */
  private static final String OPERATION_SET_EDITABLE = "setEditable";

  /**
   * operation to set the {@link MySpinner#setModel(UIListModel) model} of
   * the spinner.
   */
  private static final String OPERATION_SET_MODEL = "setModel";

  /** the spinner to access */
  private MySpinner spinner;

  /** the model */
  private UIListModel model;

  /** the editable flag */
  private boolean editable;

  /** the selection index */
  private int index;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchonization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the composite.
   * @param listModel
   *        is the list model containing the elements of the spinner.
   */
  public SyncMySpinnerAccess(UIFactorySwt uiFactory, int swtStyle, UIListModel listModel) {

    super(uiFactory, swtStyle);
    this.spinner = null;
    this.model = listModel;
    this.editable = false;
    this.index = 0;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#createSynchron()
   */
  @Override
  protected void createSynchron() {

    this.spinner = new MySpinner(getParent(), getStyle(), this.model);
    this.spinner.setEditable(this.editable);
    super.createSynchron();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncObjectAccess#performSynchron(java.lang.String)
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
   * @see net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncCompositeAccess#getSwtObject()
   */
  @Override
  public MySpinner getSwtObject() {

    return this.spinner;
  }

  /**
   * This method sets the model of the spinner.
   * 
   * @param listModel
   *        is the model to set.
   */
  public void setModel(UIListModel listModel) {

    assert (checkReady());
    this.model = listModel;
    invoke(OPERATION_SET_MODEL);
  }

  /**
   * This method sets the
   * {@link MySpinner#setEditable(boolean) "editable flag"} of the spinner.
   * 
   * @param editableFlag
   *        is the new status of the editable-flag.
   */
  public void setEditable(boolean editableFlag) {

    assert (checkReady());
    this.editable = editableFlag;
    invoke(OPERATION_SET_MODEL);
  }

  /**
   * This method gets the {@link MySpinner#isEditable() "editable flag"} of
   * the spinner.
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
   * This method sets the
   * {@link MySpinner#setSelectedIndex(int) selection-index} of the spinner.
   * 
   * @param selectedIndex
   *        is the index of the item to select.
   */
  public void setSelectedIndex(int selectedIndex) {

    assert (checkReady());
    this.index = selectedIndex;
    invoke(OPERATION_SET_SELECTED_INDEX);
  }

}
