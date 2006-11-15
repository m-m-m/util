/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import java.lang.reflect.Array;

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
public class UITreeImpl<N> extends AbstractUIWidget implements UITree<N> {

  /** the unwrapped swt tree */
  private final SyncTreeAccess syncAccess;

  /** the model adapter */
  private final TreeModelAdapter<N> modelAdapter;

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
    this.modelAdapter = new TreeModelAdapter<N>(this.syncAccess);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public SyncTreeAccess getSyncAccess() {

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
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getModel()
   */
  @SuppressWarnings("unchecked")
  public UITreeModel<N> getModel() {

    return this.modelAdapter.getModel();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#setModel(net.sf.mmm.ui.toolkit.api.model.UITreeModel)
   */
  @SuppressWarnings("unchecked")
  public void setModel(UITreeModel<N> newModel) {

    this.modelAdapter.setModel(newModel);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlag#isMultiSelection()
   */
  public boolean isMultiSelection() {

    return this.syncAccess.hasStyle(SWT.MULTI);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getSelection()
   */
  public N getSelection() {

    Object[] selection = this.syncAccess.getSelection();
    if (selection.length > 0) {
      return (N) selection[0];
    }
    return null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UITree#getSelections()
   */
  public N[] getSelections() {

    Object[] selection = this.syncAccess.getSelection();
    N[] result = (N[]) Array.newInstance(getModel().getNodeType(), selection.length);
    System.arraycopy(selection, 0, result, 0, selection.length);
    return result;
  }

}
