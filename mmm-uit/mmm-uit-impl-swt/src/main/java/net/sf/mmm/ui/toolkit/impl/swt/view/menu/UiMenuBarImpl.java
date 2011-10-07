/* $Id: UIMenuBarImpl.java 978 2011-03-04 20:27:53Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.menu;

import net.sf.mmm.ui.toolkit.api.view.menu.UiMenu;
import net.sf.mmm.ui.toolkit.base.view.menu.AbstractUiMenuBar;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncMenuAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;

/**
 * This class is the implementation of the UIMenuBar interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiMenuBarImpl extends AbstractUiMenuBar<Menu> {

  /** @see #getAdapter() */
  private final SyncMenuAccess adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param swtMenuBar is the SWT {@link Menu} to wrap.
   */
  public UiMenuBarImpl(UiFactorySwt uiFactory, Menu swtMenuBar) {

    super(uiFactory);
    this.adapter = new SyncMenuAccess(uiFactory, this, SWT.BAR, swtMenuBar, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncMenuAccess getAdapter() {

    return this.adapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiMenu createMenu(String name) {

    Menu subMenu = this.adapter.createSubMenu(name);
    return new UiMenuImpl((UiFactorySwt) getFactory(), subMenu, name);
  }

}
