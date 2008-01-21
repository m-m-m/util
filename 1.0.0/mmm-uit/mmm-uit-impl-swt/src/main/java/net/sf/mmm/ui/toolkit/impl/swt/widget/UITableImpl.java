/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.TableModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTableAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITable} interface using SWT as the
 * UI toolkit.
 * 
 * @param <C> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITableImpl<C> extends AbstractUIWidget implements UITable<C> {

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
  public UITableImpl(UIFactorySwt uiFactory, UISwtNode parentObject, boolean multiSelection) {

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
  public UITableModel<C> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UITableModel<C> newModel) {

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
