/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.model.data.UiListMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiList;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.model.ListModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncListAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiList} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIListImpl<E> extends AbstractUiWidgetSwt<List> implements UiList<E> {

  /** the model adapter */
  private final ListModelAdapter<E> modelAdapter;

  /** @see #getAdapter() */
  private final SyncListAccess adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param multiSelection - if <code>true</code> the user can select multiple,
   *        else ony one.
   * @param model is the model.
   */
  public UIListImpl(UiFactorySwt uiFactory, boolean multiSelection, UiListMvcModel<E> model) {

    super(uiFactory);
    int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
    if (multiSelection) {
      style |= SWT.MULTI;
    } else {
      style |= SWT.SINGLE;
    }
    this.adapter = new SyncListAccess(uiFactory, this, style);
    this.modelAdapter = new ListModelAdapter<E>(this.adapter, model);
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
  public SyncListAccess getAdapter() {

    return this.adapter;
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
  public boolean isMultiSelection() {

    return this.adapter.hasStyle(SWT.MULTI);
  }

  /**
   * {@inheritDoc}
   */
  public UiListMvcModel<E> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiListMvcModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
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

    this.adapter.setSelection(newIndex);
  }

  /**
   * {@inheritDoc}
   */
  public int[] getSelectedIndices() {

    return this.adapter.getSelections();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedValue(E newValue) {

    int index = this.modelAdapter.getModel().getIndexOf(newValue);
    if (index != -1) {
      this.adapter.setSelection(index);
    }
  }

  /**
   * {@inheritDoc}
   */
  public E getSelectedValue() {

    int index = this.adapter.getSelection();
    if (index == -1) {
      return null;
    }
    return this.modelAdapter.getModel().getElement(index);
  }

}
