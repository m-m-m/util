/* $Id: UIMenuItem.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.menu;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.impl.awt.UIAwtNode;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;

/**
 * This class is the implementation of the UIMenuItemIF interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuItem extends UIAwtNode implements UIMenuItemIF {

    /** the swing menu item */
    private final JMenuItem item;

    /** the style of this item */
    private final ButtonStyle style;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param name
     *        is the {@link #getText() name} of the menu item.
     * @param itemStyle
     *        is the style defining how the item is visualized and behaves.
     */
    public UIMenuItem(UIFactory uiFactory, UINodeIF parentObject, String name, ButtonStyle itemStyle) {

        super(uiFactory, parentObject);
        this.style = itemStyle;
        switch (this.style) {
            case DEFAULT:
                this.item = new JMenuItem(name);
                break;
            case CHECK:
                this.item = new JCheckBoxMenuItem(name);
                break;
            case RADIO:
                this.item = new JRadioButtonMenuItem(name);
                break;
            case TOGGLE:
                // toggle not supported!
                this.item = new JCheckBoxMenuItem(name);
                break;
            default :
                throw new IllegalArgumentException("Unknown style " + itemStyle);
        }
    }

    /**
     * This method gets the unwrapped swing menu-item.
     * 
     * @return the native swing item.
     */
    protected JMenuItem getSwingMenuItem() {
        
        return this.item;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#doInitializeListener()
     * {@inheritDoc}
     */
    protected boolean doInitializeListener() {

        this.item.addActionListener(createActionListener());
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.item.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF#getStyle()
     * {@inheritDoc}
     */
    public ButtonStyle getStyle() {

        return this.style;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF#isSelected()
     * {@inheritDoc}
     */
    public boolean isSelected() {

        return this.item.isSelected();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF#setSelected(boolean)
     * {@inheritDoc}
     */
    public void setSelected(boolean selected) {

        this.item.setSelected(selected);
    }

}
