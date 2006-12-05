/* $Id$ */
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
 * @param <C> 
 *        is the templated type of the objects in the table cells.
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
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param multiSelection -
   *        if <code>true</code> the user can select multiple, else ony one.
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
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public SyncTableAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#create()
   */
  @Override
  public void create() {

    super.create();
    this.modelAdapter.initialize();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITable#getModel()
   */
  public UITableModel<C> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITable#setModel(net.sf.mmm.ui.toolkit.api.model.UITableModel)
   */
  public void setModel(UITableModel<C> newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   */
  public int getSelectedIndex() {
  
    return this.syncAccess.getSelection();
  }
  
  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   */
  public void setSelectedIndex(int newIndex) {
  
    this.syncAccess.setSelection(newIndex);
  }
  
}
