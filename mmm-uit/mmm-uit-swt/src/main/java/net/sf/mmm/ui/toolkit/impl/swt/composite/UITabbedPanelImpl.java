/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.composite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

import net.sf.mmm.ui.toolkit.api.UIComponent;
import net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel;
import net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTabFolderAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncTabItemAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel} interface using
 * SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITabbedPanelImpl extends UIMultiComposite implements UITabbedPanel {

  /** the synchron access to the {@link org.eclipse.swt.widgets.TabFolder} */
  private final SyncTabFolderAccess syncAccess;

  /** the list of the tab-items */
  private final List<SyncTabItemAccess> tabItems;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   */
  public UITabbedPanelImpl(UIFactorySwt uiFactory, AbstractUIComponent parentObject) {

    super(uiFactory, parentObject, null);
    this.syncAccess = new SyncTabFolderAccess(uiFactory, SWT.DEFAULT);
    this.tabItems = new ArrayList<SyncTabItemAccess>();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#addComponent(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      java.lang.String)
   */
  public void addComponent(UIComponent component, String title) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    c.setParent(this);
    SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE);
    itemAccess.setText(title);
    itemAccess.setParentAccess(this.syncAccess);
    Control control = c.getSyncAccess().getSwtObject();
    if (control != null) {
      itemAccess.setControl(control);
    }
    this.tabItems.add(itemAccess);
    this.components.add(c);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#addComponent(net.sf.mmm.ui.toolkit.api.UIComponent,
   *      java.lang.String, int)
   */
  public void addComponent(UIComponent component, String title, int position) {

    AbstractUIComponent c = (AbstractUIComponent) component;
    c.setParent(this);
    SyncTabItemAccess itemAccess = new SyncTabItemAccess(getFactory(), SWT.NONE, position);
    itemAccess.setText(title);
    itemAccess.setParentAccess(this.syncAccess);
    Control control = c.getSyncAccess().getSwtObject();
    if (control != null) {
      itemAccess.setControl(control);
    }
    this.tabItems.add(position, itemAccess);
    this.components.add(position, c);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#removeComponent(net.sf.mmm.ui.toolkit.api.UIComponent)
   */
  public void removeComponent(UIComponent component) {

    int index = this.components.indexOf(component);
    if (index >= 0) {
      removeComponent(index);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.composite.UITabbedPanel#removeComponent(int)
   */
  public void removeComponent(int position) {

    AbstractUIComponent component = this.components.remove(position);
    SyncTabItemAccess itemAccess = this.tabItems.remove(position);
    component.setParent(null);
    itemAccess.dispose();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.composite.AbstractUIComposite#createChildren()
   */
  @Override
  protected void createChildren() {

    super.createChildren();
    int len = this.tabItems.size();
    for (int i = 0; i < len; i++) {
      AbstractUIComponent component = this.components.get(i);
      Control control = component.getSyncAccess().getSwtObject();
      SyncTabItemAccess itemAccess = this.tabItems.get(i);
      itemAccess.setControl(control);
      itemAccess.create();
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getActiveSyncAccess()
   */
  @Override
  public SyncTabFolderAccess getActiveSyncAccess() {

    return this.syncAccess;
  }

}
