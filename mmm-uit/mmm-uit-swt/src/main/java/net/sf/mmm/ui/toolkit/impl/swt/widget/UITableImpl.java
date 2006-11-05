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
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITableImpl extends AbstractUIWidget implements UITable {

  /** the sync access to the native SWT table */
  private final SyncTableAccess syncAccess;

  /** the model adapter */
  private final TableModelAdapter modelAdapter;

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
    this.modelAdapter = new TableModelAdapter(this.syncAccess);
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
  public UITableModel getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITable#setModel(net.sf.mmm.ui.toolkit.api.model.UITableModel)
   */
  public void setModel(UITableModel newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

}
