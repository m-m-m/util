/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UiSwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.ComboBoxModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncComboAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIComboBoxImpl<E> extends AbstractUIWidget implements UiComboBox<E> {

  /** the synchron access to the combo */
  private final SyncComboAccess syncAccess;

  /** the listener */
  private final ComboBoxModelAdapter modelAdapter;

  /** the model */
  private UiListMvcModel<E> model;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param editableFlag is the (initial) value of the
   *        {@link net.sf.mmm.ui.toolkit.api.attribute.UiWriteEditable#isEditable() editable-flag}.
   * @param listModel is the model defining the the selectable elements.
   */
  public UIComboBoxImpl(UiFactorySwt uiFactory, UiSwtNode parentObject, boolean editableFlag,
      UiListMvcModel<E> listModel) {

    super(uiFactory, parentObject);
    int style = SWT.DEFAULT;
    if (editableFlag) {
      style = style ^ SWT.READ_ONLY;
    }
    this.syncAccess = new SyncComboAccess(uiFactory, style);
    this.model = listModel;
    this.modelAdapter = new ComboBoxModelAdapter(this.syncAccess, this.model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncComboAccess getSyncAccess() {

    return this.syncAccess;
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
  @Override
  public void create() {

    super.create();
    this.modelAdapter.initialize();
  }

  /**
   * {@inheritDoc}
   */
  public UiListMvcModel<E> getModel() {

    return this.model;
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return this.syncAccess.getSelection();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.syncAccess.setText(this.modelAdapter.getModel().getElementAsString(newIndex));
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiListMvcModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
    this.model = newModel;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.syncAccess.hasStyle(SWT.READ_ONLY);
  }

  /**
   * {@inheritDoc}
   */
  public void setEditable(boolean editableFlag) {

  // not supported - maybe throw away comboBox and create new-one?
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.syncAccess.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedValue(E newValue) {

    int index = this.model.getIndexOf(newValue);
    if (index >= 0) {
      setSelectedIndex(index);
    }
  }

  /**
   * {@inheritDoc}
   */
  public E getSelectedValue() {

    int index = getSelectedIndex();
    if (index >= 0) {
      return this.model.getElement(index);
    }
    return null;
  }

}
