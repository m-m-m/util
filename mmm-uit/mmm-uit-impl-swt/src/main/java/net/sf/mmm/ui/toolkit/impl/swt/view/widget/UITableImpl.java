/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UiSwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.TableModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncTableAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTable} interface using SWT as the
 * UI toolkit.
 * 
 * @param <C> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITableImpl<C> extends AbstractUIWidget implements UiTable<C> {

  /** the sync access to the native SWT table */
  private final SyncTableAccess syncAccess;

  /** the model adapter */
  private final TableModelAdapter<C> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param multiSelection - if <code>true</code> the user can select
   *        multiple, else ony one.
   */
  public UITableImpl(UiFactorySwt uiFactory, UiSwtNode parentObject, boolean multiSelection) {

    super(uiFactory, parentObject);
    int style;
    if (multiSelection) {
      style = SWT.MULTI | SWT.FULL_SELECTION;
    } else {
      style = SWT.SINGLE;
    }
    style |= SWT.VIRTUAL;
    this.syncAccess = new SyncTableAccess(uiFactory, style);
    this.modelAdapter = new TableModelAdapter<C>(this.syncAccess);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncTableAccess getSyncAccess() {

    return this.syncAccess;
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

    return this.syncAccess.getSelection();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    this.syncAccess.setSelection(newIndex);
  }

}
