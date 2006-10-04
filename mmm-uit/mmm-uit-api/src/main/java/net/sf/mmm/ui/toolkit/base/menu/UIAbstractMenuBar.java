/* $Id$ */
package net.sf.mmm.ui.toolkit.base.menu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.base.UIAbstractNode;

/**
 * This is the base implementation of the UIMenuBarIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UIAbstractMenuBar extends UIAbstractNode implements UIMenuBarIF {

    /** mapps the name of a menu (String) to a menu (UIMenuIF) */
    private Map<String, UIMenuIF> menuTable;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UIAbstractMenuBar(UIFactoryIF uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.menuTable = new HashMap<String, UIMenuIF>();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF#addMenu(java.lang.String)
     * 
     */
    public UIMenuIF addMenu(String name) {

        synchronized (this.menuTable) {
            UIMenuIF menu = this.menuTable.get(name);
            if (menu == null) {
                // the menu does not already exist and is created here...
                menu = createMenu(name);
                this.menuTable.put(name, menu);
            }
            return this.menuTable.get(name);
        }
    }

    /**
     * This method creates a menu in this menu bar.
     * 
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF#addMenu(java.lang.String)
     * 
     * @param name
     *        is the name of the menu.
     * @return the created menu.
     */
    protected abstract UIMenuIF createMenu(String name);

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF#getMenu(java.lang.String)
     * 
     */
    public UIMenuIF getMenu(String name) {

        return this.menuTable.get(name);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF#getMenuCount()
     * 
     */
    public int getMenuCount() {

        return this.menuTable.size();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * 
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuBarIF#getMenus()
     * 
     */
    public Iterator<UIMenuIF> getMenus() {

        return this.menuTable.values().iterator();
    }

}
