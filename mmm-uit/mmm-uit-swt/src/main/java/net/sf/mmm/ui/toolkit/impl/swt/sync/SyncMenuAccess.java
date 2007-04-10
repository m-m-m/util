/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.sync;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;

import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;

/**
 * This class is used for synchronous access on a SWT
 * {@link org.eclipse.swt.widgets.MenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SyncMenuAccess extends AbstractSyncWidgetAccess {

  /**
   * operation to create a {@link org.eclipse.swt.widgets.Menu sub-menu}
   */
  protected static final String OPERATION_CREATE_SUB_MENU = "createSubMenu";

  /**
   * operation to create a {@link org.eclipse.swt.widgets.MenuItem menu-item}
   */
  protected static final String OPERATION_CREATE_MENU_ITEM = "createMenuItem";

  /**
   * operation to add a {@link SWT#SEPARATOR separator} item.
   */
  protected static final String OPERATION_ADD_SEPARATOR = "addSeparator";

  /**
   * operation to set the
   * {@link org.eclipse.swt.widgets.MenuItem#setText(String) text} of the
   * menu-item.
   */
  protected static final String OPERATION_SET_TEXT = "setText";

  /** the menu to access */
  private final Menu menu;

  /** the text of the menu */
  private String text;

  /** the text of the item/sub-menu */
  private String itemText;

  /** a sub-menu */
  private Menu subMenu;

  /** a menu-item */
  private MenuItem menuItem;

  /** the button-style for the menu-item */
  private ButtonStyle buttonStyle;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is used to do the synchronization.
   * @param swtStyle
   *        is the {@link Widget#getStyle() style} of the menu.
   * @param swtMenu
   *        is the menu to access.
   * @param menuText
   *        is the text of the menu.
   */
  public SyncMenuAccess(UIFactorySwt uiFactory, int swtStyle, Menu swtMenu, String menuText) {

    super(uiFactory, swtStyle);
    this.menu = swtMenu;
    this.text = menuText;
    this.buttonStyle = null;
    this.itemText = null;
    this.subMenu = null;
    this.menuItem = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void performSynchron(String operation) {

    if (operation == OPERATION_CREATE_SUB_MENU) {
      this.subMenu = new Menu(this.menu);
      MenuItem subMenuItem = new MenuItem(this.menu, SWT.CASCADE);
      subMenuItem.setText(this.itemText);
      subMenuItem.setMenu(this.subMenu);
    } else if (operation == OPERATION_CREATE_MENU_ITEM) {
      int swtStyle = UIFactorySwt.convertButtonStyleForMenuItem(this.buttonStyle);
      this.menuItem = new MenuItem(this.menu, swtStyle);
      this.menuItem.setText(this.itemText);
    } else if (operation == OPERATION_ADD_SEPARATOR) {
      new MenuItem(this.menu, SWT.SEPARATOR);
    } else if (operation == OPERATION_SET_TEXT) {
      this.menu.getParentItem().setText(this.text);
    } else {
      super.performSynchron(operation);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Menu getSwtObject() {

    return this.menu;
  }

  /**
   * This method creates a sub-menu.
   * 
   * @param menuText
   *        is the {@link MenuItem#setText(String) text} of the sub-menu.
   * @return the sub-menu.
   */
  public Menu createSubMenu(String menuText) {

    assert (checkReady());
    this.itemText = menuText;
    invoke(OPERATION_CREATE_SUB_MENU);
    return this.subMenu;
  }

  /**
   * This method creates a menu-item.
   * 
   * @param menuText
   *        is the {@link MenuItem#setText(String) text} of the menu-item.
   * @param style
   *        is the button-style of the menu-item.
   * @return the new menu-item.
   */
  public MenuItem createMenuItem(String menuText, ButtonStyle style) {

    assert (checkReady());
    this.itemText = menuText;
    this.buttonStyle = style;
    invoke(OPERATION_CREATE_MENU_ITEM);
    return this.menuItem;
  }

  /**
   * This method adds a {@link SWT#SEPARATOR separator} item.
   */
  public void addSeparator() {

    assert (checkReady());
    invoke(OPERATION_ADD_SEPARATOR);
  }

  /**
   * This method sets the {@link MenuItem#setText(String) text} of the menu.
   * 
   * @param newText
   *        is the text to set.
   */
  public void setText(String newText) {

    assert (checkReady());
    this.text = newText;
    invoke(OPERATION_SET_TEXT);
  }

  /**
   * This method gets the {@link MenuItem#getText() text} of the menu.
   * 
   * @return the text.
   */
  public String getText() {

    return this.text;
  }

}
