/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;

import net.sf.mmm.ui.toolkit.api.menu.UIMenu;
import net.sf.mmm.ui.toolkit.base.menu.AbstractUIMenuBar;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMenuAccess;
import net.sf.mmm.ui.toolkit.impl.swt.window.UIWindowImpl;

/**
 * This class is the implementation of the UIMenuBar interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuBarImpl extends AbstractUIMenuBar {

  /** the synchronous access */
  private final SyncMenuAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwt instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   * @param swtMenuBar is the SWT menu bar to wrap.
   */
  public UIMenuBarImpl(UIFactorySwt uiFactory, UIWindowImpl parentObject, Menu swtMenuBar) {

    super(uiFactory, parentObject);
    this.syncAccess = new SyncMenuAccess(uiFactory, SWT.BAR, swtMenuBar, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UIMenu createMenu(String name) {

    Menu subMenu = this.syncAccess.createSubMenu(name);
    return new UIMenuImpl((UIFactorySwt) getFactory(), this, subMenu, name);
  }

}
