/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.model.UITableModel;
import net.sf.mmm.ui.toolkit.api.widget.UITable;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.TableModelAdapter;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITable} interface using Swing as
 * the UI toolkit.
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
  private TableModelAdapter modelAdapter;

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
    this.modelAdapter = new TableModelAdapter(newModel);
    this.table.setModel(this.modelAdapter);
    this.table.createDefaultColumnsFromModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getActiveSwingComponent()
   */
  @Override
  protected JComponent getActiveSwingComponent() {

    return this.table;
  }

}
