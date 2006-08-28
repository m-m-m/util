/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JSeparator;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenu;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the UIMenuIF interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenu extends UIAbstractMenu {

    /** the swing menu */
    private final JMenu menu;

    /** the button group for radio items */
    private ButtonGroup buttonGroup;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param jMenu
     *        is the swing menu to wrap.
     */
    public UIMenu(UIFactory uiFactory, UINodeIF parentObject, JMenu jMenu) {

        super(uiFactory, parentObject);
        this.menu = jMenu;
        this.buttonGroup = null;
    }

    /**
     * This method gets the unwrapped swing menu.
     * 
     * @return the native swing menu.
     */
    protected JMenu getSwingMenu() {
        
        return this.menu;
    }

    /**
     * This method gets the button group for this menu.
     * 
     * @return the button group.
     */
    protected ButtonGroup getButtonGroup() {

        if (this.buttonGroup == null) {
            this.buttonGroup = new ButtonGroup();
        }
        return this.buttonGroup;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenu#createMenuItem(java.lang.String,
     *      net.sf.mmm.ui.toolkit.api.widget.ButtonStyle)
     * {@inheritDoc}
     */
    protected UIMenuItemIF createMenuItem(String name, ButtonStyle style) {

        UIMenuItem item = new UIMenuItem((UIFactory) getFactory(), this, name, style);
        if (style == ButtonStyle.RADIO) {
            getButtonGroup().add(item.getSwingMenuItem());
        }
        this.menu.add(item.getSwingMenuItem());
        return item;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.menu.UIAbstractMenu#createSubMenu(java.lang.String)
     * {@inheritDoc}
     */
    protected UIMenuIF createSubMenu(String name) {

        JMenu subMenu = new JMenu(name);
        this.menu.add(subMenu);
        return new UIMenu((UIFactory) getFactory(), this, subMenu);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.menu.getName();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuIF#addSeparator()
     * {@inheritDoc}
     */
    public void addSeparator() {

        this.menu.add(new JSeparator());
    }

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

    // nothing to do!
    }

}
