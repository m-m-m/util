/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.menu;

import java.util.Iterator;

import net.sf.mmm.ui.toolkit.api.view.UiNode;

/**
 * This is the interface for a menu-bar. It represents a bar of pull down
 * {@link net.sf.mmm.ui.toolkit.api.view.menu.UiMenu menus} of a
 * {@link net.sf.mmm.ui.toolkit.api.view.window.UiFrame}.<br>
 * Use {@link net.sf.mmm.ui.toolkit.api.view.window.UiFrame#getMenuBar()} to "create" a menu-bar.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiMenuBar extends UiNode {

  /** the type of this object */
  String TYPE = "Menubar";

  /**
   * This method gets the number of menus in this menu bar.
   * 
   * @return the menu bar count.
   */
  int getMenuCount();

  /**
   * This method gets the menus of this menubar.
   * 
   * @return an iterator of the menus in this menubar.
   */
  Iterator<UiMenu> getMenus();

  /**
   * This method gets the menu for the given name.
   * 
   * @param name is the name of the menu.
   * @return the menu with the given name or <code>null</code> if no menu exists for the given name.
   */
  UiMenu getMenu(String name);

  /**
   * This method creates and adds a menu to this menu bar. If the menu with the given name already exists, no
   * new menu will be created but the existing menu is returned.
   * 
   * @param name is the title for the new menu.
   * @return the created (or already existing) menu.
   */
  UiMenu addMenu(String name);

}
