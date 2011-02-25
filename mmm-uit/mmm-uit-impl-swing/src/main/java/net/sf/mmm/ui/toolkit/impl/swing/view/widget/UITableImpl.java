/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.model.data.UiTableMvcModel;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTable;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.TableModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTable} interface using Swing as the
 * UI toolkit.
 * 
 * @param <C> is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITableImpl<C> extends AbstractUIWidget implements UiTable<C> {

  /** the native Swing table */
  private final JTable table;

  /** the swing scroll pane */
  private final JScrollPane scrollPanel;

  /** the table model adapter */
  private TableModelAdapter<C> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UITableImpl(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
    this.table = new JTable();
    this.table.setShowGrid(true);
    this.table.setColumnModel(new DefaultTableColumnModel());
    this.scrollPanel = new JScrollPane(this.table);
    this.scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.scrollPanel;
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
    this.table.setModel(this.modelAdapter);
    this.table.createDefaultColumnsFromModel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent e) {

        // has the mouse button already been released?
        if (!e.getValueIsAdjusting()) {
          if (getSelectedIndex() >= 0) {
            invoke(ActionType.SELECT);
          } else {
            invoke(ActionType.DESELECT);
          }
        }
      }
    });
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JComponent getActiveSwingComponent() {

    return this.table;
  }

  /**
   * {@inheritDoc}
   */
  public int getSelectedIndex() {

    return this.table.getSelectedRow();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectedIndex(int newIndex) {

    if (newIndex >= 0) {
      this.table.setRowSelectionInterval(newIndex, newIndex);
    } else if (newIndex == -1) {
      int rowCount = this.table.getRowCount();
      if (rowCount > 0) {
        this.table.removeRowSelectionInterval(0, this.table.getRowCount() - 1);
      }
    } else {
      throw new IllegalArgumentException("illegal selection index: " + newIndex);
    }
  }

}
