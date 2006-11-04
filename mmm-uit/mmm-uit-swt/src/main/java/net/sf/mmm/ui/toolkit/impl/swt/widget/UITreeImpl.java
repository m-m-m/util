/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import net.sf.mmm.ui.toolkit.api.model.UITreeModel;
import net.sf.mmm.ui.toolkit.api.widget.UITree;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.TreeModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTreeAccess;

import org.eclipse.swt.SWT;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITree} interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITreeImpl extends AbstractUIWidget implements UITree {

  /** the unwrapped swt tree */
  private final SyncTreeAccess syncAccess;

  /** the model adapter */
  private final TreeModelAdapter modelAdapter;

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
  public UITreeImpl(UIFactorySwt uiFactory, UISwtNode parentObject, boolean multiSelection) {

    super(uiFactory, parentObject);
    int style = SWT.BORDER;
    if (multiSelection) {
      style |= SWT.MULTI;
    } else {
      style |= SWT.SINGLE;
    }
    this.syncAccess = new SyncTreeAccess(uiFactory, style);
    this.modelAdapter = new TreeModelAdapter(this.syncAccess);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   * 
   */
  @Override
  public SyncTreeAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#create()
   * 
   */
  @Override
  public void create() {

    super.create();
    this.modelAdapter.initialize();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getModel()
   * 
   */
  @SuppressWarnings("unchecked")
  public UITreeModel getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#setModel(net.sf.mmm.ui.toolkit.api.model.UITreeModel)
   * 
   */
  @SuppressWarnings("unchecked")
  public void setModel(UITreeModel newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlag#isMultiSelection()
   * 
   */
  public boolean isMultiSelection() {

    return this.syncAccess.hasStyle(SWT.MULTI);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getSelection()
   * 
   */
  public Object getSelection() {

    Object[] selection = this.syncAccess.getSelection();
    if (selection.length > 0) {
      return selection[0];
    }
    return null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getSelections()
   * 
   */
  public Object[] getSelections() {

    return this.syncAccess.getSelection();
  }

}
