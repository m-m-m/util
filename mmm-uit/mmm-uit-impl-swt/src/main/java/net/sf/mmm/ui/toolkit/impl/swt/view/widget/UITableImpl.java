/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.model.TableModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTableAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTable} interface using SWT as
 * the UI toolkit.
 * 
 * @param <C> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITableImpl<C> extends AbstractUiWidgetSwt<Table> implements UiTable<C> {

  /** @see #getAdapter() */
  private final SyncTableAccess adapter;

  /** the model adapter */
  private final TableModelAdapter<C> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param multiSelection - if <code>true</code> the user can select multiple,
   *        else ony one.
   */
  public UITableImpl(UiFactorySwt uiFactory, boolean multiSelection) {

    super(uiFactory);
    int style;
    if (multiSelection) {
      style = SWT.MULTI | SWT.FULL_SELECTION;
    } else {
      style = SWT.SINGLE;
    }
    style |= SWT.VIRTUAL;
    this.adapter = new SyncTableAccess(uiFactory, this, style);
    this.modelAdapter = new TableModelAdapter<C>(this.adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncTableAccess getAdapter() {

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
  public UiTableMvcModel<C> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiTableMvcModel<C> newModel) {

    this.modelAdapter.setModel(newModel);
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
  public int getSelectedIndex() {

    return this.adapter.getSelection();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.adapter.setSelection(newIndex);
  }

}
