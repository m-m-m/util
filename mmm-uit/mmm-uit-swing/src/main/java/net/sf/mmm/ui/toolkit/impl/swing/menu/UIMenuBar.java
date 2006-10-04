/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenuBar;
import net.sf.mmm.ui.toolkit.base.window.UIAbstractWindow;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the UIMenuBarIF interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuBar extends UIAbstractMenuBar {

    /** the swing menu bar */
    private JMenuBar menuBar;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *            is the UIFactory instance.
     * @param parentObject
     *            is the parent of this object (may be <code>null</code>).
     * @param jMenuBar
     *            is the swing menu bar to wrap.
     */
    public UIMenuBar(UIFactory uiFactory, UIAbstractWindow parentObject, JMenuBar jMenuBar) {
        super(uiFactory, parentObject);
        this.menuBar = jMenuBar;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenuBar#createMenu(java.lang.String)
     * 
     */
    protected UIMenuIF createMenu(String name) {
        JMenu menu = new JMenu(name);
        this.menuBar.add(menu);
        return new UIMenu((UIFactory) getFactory(), this, menu);
    }

}
