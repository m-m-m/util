/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItem;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenu;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMenuAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenu} interface using SWT as the UI
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuImpl extends AbstractUIMenu {

  /** the synchron access to the menu */
  private final SyncMenuAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param swtMenu
   *        is the SWT menu to wrap.
   * @param text
   *        is the {@link MenuItem#setText(java.lang.String) text} of the
   *        menu.
   */
  public UIMenuImpl(UIFactorySwt uiFactory, UINode parentObject, Menu swtMenu, String text) {

    super(uiFactory, parentObject);
    this.syncAccess = new SyncMenuAccess(uiFactory, SWT.CASCADE, swtMenu, text);
  }

  /**
   * This method gets synchron access on the SWT menu.
   * 
   * @return the synchron access.
   */
  public SyncMenuAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenu#createMenuItem(java.lang.String,
   *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
   */
  @Override
  protected UIMenuItem createMenuItem(String name, ButtonStyle style) {

    MenuItem menuItem = this.syncAccess.createMenuItem(name, style);
    return new UIMenuItemImpl((UIFactorySwt) getFactory(), this, name, style, menuItem);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenu#createSubMenu(java.lang.String)
   */
  @Override
  protected UIMenu createSubMenu(String name) {

    Menu subMenu = this.syncAccess.createSubMenu(name);
    return new UIMenuImpl((UIFactorySwt) getFactory(), this, subMenu, name);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItem#getText()
   */
  public String getText() {

    return this.syncAccess.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.menu.UIMenu#addSeparator()
   */
  public void addSeparator() {

    this.syncAccess.addSeparator();
  }

}
