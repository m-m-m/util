/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;

import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.TableModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTable} interface using Swing
 * as the UI toolkit.
 * 
 * @param <C> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTableImpl<C> extends AbstractUiWidgetSwingComposed<JTable, JScrollPane> implements
    UiTable<C> {

  /** the table model adapter */
  private TableModelAdapter<C> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   */
  public UiTableImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JTable(), new JScrollPane());
    JTable table = getDelegate();
    table.setShowGrid(true);
    table.setColumnModel(new DefaultTableColumnModel());
    JScrollPane scrollPanel = getAdapter().getToplevelDelegate();
    scrollPanel.setViewportView(table);
    scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
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
  public UiTableMvcModel<C> getModel() {

    if (this.modelAdapter == null) {
      return null;
    } else {
      return this.modelAdapter.getModel();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setModel(UiTableMvcModel<C> newModel) {

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new TableModelAdapter<C>(newModel);
    JTable table = getDelegate();
    table.setModel(this.modelAdapter);
    table.createDefaultColumnsFromModel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    getDelegate().getSelectionModel().addListSelectionListener(getAdapter());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return getDelegate().getSelectedRow();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    JTable table = getDelegate();
    if (newIndex >= 0) {
      table.setRowSelectionInterval(newIndex, newIndex);
    } else if (newIndex == -1) {
      int rowCount = table.getRowCount();
      if (rowCount > 0) {
        table.removeRowSelectionInterval(0, table.getRowCount() - 1);
      }
    } else {
      throw new IllegalArgumentException("illegal selection index: " + newIndex);
    }
  }

}
