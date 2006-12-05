/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.event.ActionType;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.TableModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITable} interface using Swing as the
 * UI toolkit.
 * 
 * @param <C>
 *        is the templated type of the objects in the table cells.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITableImpl<C> extends AbstractUIWidget implements UITable<C> {

  /** the native Swing table */
  private final JTable table;

  /** the swing scroll pane */
  private final JScrollPane scrollPanel;

  /** the table model adapter */
  private TableModelAdapter<C> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UITableImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.table = new JTable();
    this.table.setShowGrid(true);
    this.table.setColumnModel(new DefaultTableColumnModel());
    this.scrollPanel = new JScrollPane(this.table);
    this.scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  @Override
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITable#getModel()
   */
  public UITableModel<C> getModel() {

    if (this.modelAdapter == null) {
      return null;
    } else {
      return this.modelAdapter.getModel();
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITable#setModel(net.sf.mmm.ui.toolkit.api.model.UITableModel)
   */
  public void setModel(UITableModel<C> newModel) {

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new TableModelAdapter<C>(newModel);
    this.table.setModel(this.modelAdapter);
    this.table.createDefaultColumnsFromModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.AbstractUINode#doInitializeListener()
   */
  @Override
  protected boolean doInitializeListener() {

    this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent e) {

        // has the mouse button already been released?
        if (!e.getValueIsAdjusting()) {
          invoke(ActionType.SELECT);
        }
      }
    });
    return true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getActiveSwingComponent()
   */
  @Override
  protected JComponent getActiveSwingComponent() {

    return this.table;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {

    return this.table.getSelectedRow();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
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
