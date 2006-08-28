/* $Id: UIAbstractMenu.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.base.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF;
import net.sf.mmm.ui.toolkit.api.feature.ActionIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.base.UIAbstractNode;

/**
 * This is the base implementation of the UIMenuIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractMenu extends UIAbstractNode implements UIMenuIF {

    /** the menu entries */
    private final List<UIMenuItemIF> items;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIAbstractMenu(UIFactoryIF uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.items = new ArrayList<UIMenuItemIF>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#getItemCount()
     * {@inheritDoc}
     */
    public int getItemCount() {

        return this.items.size();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#getItems()
     * {@inheritDoc}
     */
    public Iterator<UIMenuItemIF> getItems() {

        return this.items.iterator();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addItem(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF)
     * {@inheritDoc}
     */
    public UIMenuItemIF addItem(String name, UIActionListenerIF action) {

        return addItem(name, action, ButtonStyle.DEFAULT);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addItem(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
     * {@inheritDoc}
     */
    public UIMenuItemIF addItem(String name, ButtonStyle style) {

        return addItem(name, null, style);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addItem(net.sf.mmm.ui.toolkit.api.feature.ActionIF)
     * {@inheritDoc}
     */
    public UIMenuItemIF addItem(ActionIF action) {

        UIMenuItemIF item = addItem(action.getName(), action.getActionListener(), action.getStyle());
        UIPictureIF icon = action.getIcon();
        if (icon != null) {
            // button.setIcon(icon);
        }
        String id = action.getId();
        if (id != null) {
            item.setId(id);
        }
        return item;

    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addItem(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF)
     * {@inheritDoc}
     */
    public UIMenuItemIF addItem(String name, UIActionListenerIF action, ButtonStyle style) {

        UIMenuItemIF menuItem = createMenuItem(name, style);
        this.items.add(menuItem);
        if (action != null) {
            menuItem.addActionListener(action);
        }
        return menuItem;
    }

    /**
     * This method creates a new menu item.
     * 
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addItem(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.event.UIActionListenerIF)
     * 
     * @param name
     *        is the name of the menu item.
     * @param style
     *        is the style defining how the item is visualized and behaves.
     * @return the created menu item.
     */
    protected abstract UIMenuItemIF createMenuItem(String name, ButtonStyle style);

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addSubMenu(java.lang.String)
     * {@inheritDoc}
     */
    public UIMenuIF addSubMenu(String name) {

        UIMenuIF menu = createSubMenu(name);
        this.items.add(menu);
        return menu;
    }

    /**
     * This method creates a new sub menu.
     * 
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addSubMenu(java.lang.String)
     * 
     * @param name
     *        is the name of the sub-menu.
     * @return the created sub-menu.
     */
    protected abstract UIMenuIF createSubMenu(String name);

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF#getStyle()
     * {@inheritDoc}
     */
    public ButtonStyle getStyle() {

        return ButtonStyle.DEFAULT;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF#isSelected()
     * {@inheritDoc}
     */
    public boolean isSelected() {

        return false;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF#setSelected(boolean)
     * {@inheritDoc}
     */
    public void setSelected(boolean selected) {

    // nothing to do here
    }

}