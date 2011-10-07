/* $Id: UIMenuImpl.java 977 2011-03-02 22:10:25Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.menu;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.base.view.menu.AbstractUiMenu;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncMenuAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenu} interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiMenuImpl extends AbstractUiMenu<Menu> {

  /** @see #getAdapter() */
  private final SyncMenuAccess adapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param swtMenu is the SWT menu to wrap.
   * @param text is the {@link MenuItem#setText(java.lang.String) text} of the
   *        menu.
   */
  public UiMenuImpl(UiFactorySwt uiFactory, Menu swtMenu, String text) {

    super(uiFactory);
    this.adapter = new SyncMenuAccess(uiFactory, this, SWT.CASCADE, swtMenu, text);
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
  protected UiMenuItemImpl createMenuItem(String name, ButtonStyle style) {

    MenuItem menuItem = this.adapter.createMenuItem(name, style);
    return new UiMenuItemImpl((UiFactorySwt) getFactory(), name, style, menuItem);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiMenuImpl createSubMenu(String name) {

    Menu subMenu = this.adapter.createSubMenu(name);
    return new UiMenuImpl((UiFactorySwt) getFactory(), subMenu, name);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.adapter.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void addSeparator() {

    this.adapter.addSeparator();
  }

}
