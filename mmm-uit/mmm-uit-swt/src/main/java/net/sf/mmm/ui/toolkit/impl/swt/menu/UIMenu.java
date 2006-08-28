/* $Id: UIMenu.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenu;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMenuAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuIF} interface using SWT as the UI
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenu extends UIAbstractMenu {

    /** the synchron access to the menu */
    private final SyncMenuAccess syncAccess;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param swtMenu
     *        is the SWT menu to wrap.
     * @param text
     *        is the {@link MenuItem#setText(java.lang.String) text} of the
     *        menu.
     */
    public UIMenu(UIFactory uiFactory, UINodeIF parentObject, Menu swtMenu, String text) {

        super(uiFactory, parentObject);
        this.syncAccess = new SyncMenuAccess(uiFactory, SWT.CASCADE, swtMenu, text);
    }

    /**
     * This method gets synchron access on the SWT menu.
     * 
     * @return the synchron access.
     */
    public SyncMenuAccess getSyncAccess() {

        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenu#createMenuItem(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
     * {@inheritDoc}
     */
    protected UIMenuItemIF createMenuItem(String name, ButtonStyle style) {

        MenuItem menuItem = this.syncAccess.createMenuItem(name, style);
        return new UIMenuItem((UIFactory) getFactory(), this, name, style, menuItem);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenu#createSubMenu(java.lang.String)
     * {@inheritDoc}
     */
    protected UIMenuIF createSubMenu(String name) {

        Menu subMenu = this.syncAccess.createSubMenu(name);
        return new UIMenu((UIFactory) getFactory(), this, subMenu, name);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.syncAccess.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addSeparator()
     * {@inheritDoc}
     */
    public void addSeparator() {

        this.syncAccess.addSeparator();
    }

}
