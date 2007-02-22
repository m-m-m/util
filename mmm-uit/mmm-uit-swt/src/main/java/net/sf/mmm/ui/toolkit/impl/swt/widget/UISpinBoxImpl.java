/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UISpinBox;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMySpinnerAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UISpinBox} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the list-elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UISpinBoxImpl<E> extends AbstractUIWidget implements UISpinBox<E> {

  /** the native SWT widget */
  private final SyncMySpinnerAccess syncAccess;

  /** the model */
  private UIListModel<E> model;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param listModel
   *        is the model for the elements to select.
   */
  public UISpinBoxImpl(UIFactorySwt uiFactory, UISwtNode parentObject, UIListModel<E> listModel) {

    super(uiFactory, parentObject);
    this.syncAccess = new SyncMySpinnerAccess(uiFactory, SWT.NONE, listModel);
    this.model = listModel;
    // this.spinBox.setMinimum(this.model.getMinWidthimumValue());
    // this.spinBox.setMaximum(this.model.getMaximumValue());
    // this.spinBox.setSelection(this.model.getMinimumValue());
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UISpinBox#getModel()
   */
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   */
  public void setSelectedIndex(int newIndex) {

    this.syncAccess.setSelectedIndex(newIndex);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {

    return this.syncAccess.getSelectedIndex();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   */
  public void setSelectedValue(E newValue) {

    int index = this.model.getIndexOf(newValue);
    if (index > -1) {
      setSelectedIndex(index);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   */
  public E getSelectedValue() {

    int i = getSelectedIndex();
    if (i >= 0) {
      return this.model.getElement(i);
    }
    return null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   */
  public void setModel(UIListModel<E> newModel) {

    this.syncAccess.setModel(newModel);
    this.model = newModel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable()
   */
  public boolean isEditable() {

    return this.syncAccess.isEditable();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#setEditable(boolean)
   */
  public void setEditable(boolean editableFlag) {

    this.syncAccess.setEditable(editableFlag);
  }

}
