/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UIList;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.ListModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncListAccess;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIList} interface using SWT as the
 * UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIListImpl<E> extends AbstractUIWidget implements UIList<E> {

  /** the model adapter */
  private final ListModelAdapter<E> modelAdapter;

  /** the sync access to the SWT list */
  private final SyncListAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple, else ony one.
   * @param model
   *        is the model.
   */
  public UIListImpl(UIFactorySwt uiFactory, UISwtNode parentObject, boolean multiSelection,
      UIListModel<E> model) {

    super(uiFactory, parentObject);
    int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
    if (multiSelection) {
      style |= SWT.MULTI;
    } else {
      style |= SWT.SINGLE;
    }
    this.syncAccess = new SyncListAccess(uiFactory, style);
    this.modelAdapter = new ListModelAdapter<E>(this.syncAccess, model);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   * 
   */
  @Override
  public SyncListAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#create()
   * 
   */
  @Override
  public void create() {

    super.create();
    this.modelAdapter.initialize();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlag#isMultiSelection()
   * 
   */
  public boolean isMultiSelection() {

    return this.syncAccess.hasStyle(SWT.MULTI);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIList#getModel()
   * 
   */
  public UIListModel<E> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIList#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   * 
   */
  public void setModel(UIListModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   * 
   */
  public int getSelectedIndex() {

    return this.syncAccess.getSelection();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   * 
   */
  public void setSelectedIndex(int newIndex) {

    this.syncAccess.setSelection(newIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIList#getSelectedIndices()
   * 
   */
  public int[] getSelectedIndices() {

    return this.syncAccess.getSelections();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   * 
   */
  public void setSelectedValue(E newValue) {

    int index = this.modelAdapter.getModel().getIndexOf(newValue);
    if (index != -1) {
      this.syncAccess.setSelection(index);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   * 
   */
  public E getSelectedValue() {

    int index = this.syncAccess.getSelection();
    if (index == -1) {
      return null;
    }
    return this.modelAdapter.getModel().getElement(index);
  }

}
