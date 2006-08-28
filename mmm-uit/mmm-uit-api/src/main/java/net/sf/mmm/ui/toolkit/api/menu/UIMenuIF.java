/* $Id$ */
package net.sf.mmm.ui.toolkit.api.menu;

import java.util.Iterator;

import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;
import net.sf.mmm.ui.toolkit.api.feature.ActionIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;

/**
 * This is the interface for a menu.<br>
 * Use {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF#addMenu(String)} or
 * {@link #addSubMenu(String)} to create menus.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIMenuIF extends UIMenuItemIF {

    /** the type of this object */
    String TYPE = "Menu";

    /**
     * This method gets the number of items in this menu.
     * 
     * @return the item count.
     */
    int getItemCount();

    /**
     * This method gets the items of this menu.
     * 
     * @return an iterator of the items in this menu.
     */
    Iterator<UIMenuItemIF> getItems();

    /**
     * This method adds a separator to this menu.
     */
    void addSeparator();

    /**
     * This method creates and adds a new {@link ButtonStyle#DEFAULT regular}
     * item to this menu.
     * 
     * @see #addItem(String, UIActionListenerIF, ButtonStyle)
     * 
     * @param name
     *        is the name of the item to add.
     * @param action
     *        is the action invoked by the item (may be <code>null</code> for
     *        a separator).
     * @return the created menu item.
     */
    UIMenuItemIF addItem(String name, UIActionListenerIF action);

    /**
     * This method creates and adds a new item to this menu.
     * 
     * @see #addItem(String, UIActionListenerIF, ButtonStyle)
     * 
     * @param name
     *        is the name of the item to add.
     * @param style
     *        is the style defining how the item is visualized and behaves.
     * @return the created menu item.
     */
    UIMenuItemIF addItem(String name, ButtonStyle style);

    /**
     * This method creates and adds a new item to this menu.
     * 
     * @param name
     *        is the name of the item to add.
     * @param action
     *        is the action invoked by the item (may be <code>null</code> for
     *        a separator).
     * @param style
     *        is the style defining how the item is visualized and behaves.
     * @return the created menu item.
     */
    UIMenuItemIF addItem(String name, UIActionListenerIF action, ButtonStyle style);

    /**
     * This method creates and adds a new item to this menu.
     * 
     * @param action
     *        is the action to be represented as button.
     * @return the created menu item.
     */
    UIMenuItemIF addItem(ActionIF action);
    
    /**
     * This method creates and adds a new submenu to this menu.
     * 
     * @param name
     *        is the name of the submenu to add.
     * @return the created submenu.
     */
    UIMenuIF addSubMenu(String name);

}
