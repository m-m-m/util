/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;

import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenuBar;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMenuAccess;
import net.sf.mmm.ui.toolkit.impl.swt.window.UIWindow;

/**
 * This class is the implementation of the UIMenuBarIF interface using SWT
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuBar extends UIAbstractMenuBar {

    /** the synchron access */
    private final SyncMenuAccess syncAccess;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *            is the UIFactory instance.
     * @param parentObject
     *            is the parent of this object (may be <code>null</code>).
     * @param swtMenuBar
     *            is the SWT menu bar to wrap.
     */
    public UIMenuBar(UIFactory uiFactory, UIWindow parentObject, Menu swtMenuBar) {
        super(uiFactory, parentObject);
        this.syncAccess = new SyncMenuAccess(uiFactory, SWT.BAR, swtMenuBar, null);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenuBar#createMenu(java.lang.String)
     * 
     */
    protected UIMenuIF createMenu(String name) {
        Menu subMenu = this.syncAccess.createSubMenu(name);
        return new UIMenu((UIFactory) getFactory(), this, subMenu, name);
    }

}
