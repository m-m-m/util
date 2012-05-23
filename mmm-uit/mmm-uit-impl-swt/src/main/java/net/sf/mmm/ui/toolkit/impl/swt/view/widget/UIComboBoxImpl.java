/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.model.ComboBoxModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncComboAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiComboBox} interface using SWT
 * as the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIComboBoxImpl<E> extends AbstractUiWidgetSwt<Combo> implements UiComboBox<E> {

  /** @see #getAdapter() */
  private final SyncComboAccess adapter;

  /** the listener */
  private final ComboBoxModelAdapter modelAdapter;

  /** the model */
  private UiListMvcModel<E> model;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param editableFlag is the (initial) value of the
   *        {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEditable#isEditable()
   *        editable-flag}.
   * @param listModel is the model defining the the selectable elements.
   */
  public UIComboBoxImpl(UiFactorySwt uiFactory, boolean editableFlag, UiListMvcModel<E> listModel) {

    super(uiFactory);
    int style = SWT.DEFAULT;
    if (editableFlag) {
      style = style ^ SWT.READ_ONLY;
    }
    this.adapter = new SyncComboAccess(uiFactory, this, style);
    this.model = listModel;
    this.modelAdapter = new ComboBoxModelAdapter(this.adapter, this.model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncComboAccess getAdapter() {

    return this.adapter;
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
  public void create() {

    // super.create();
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

    return this.adapter.getSelection();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.adapter.setText(this.modelAdapter.getModel().getElementAsString(newIndex));
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

    return this.adapter.hasStyle(SWT.READ_ONLY);
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

    return this.adapter.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.adapter.setText(text);
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
