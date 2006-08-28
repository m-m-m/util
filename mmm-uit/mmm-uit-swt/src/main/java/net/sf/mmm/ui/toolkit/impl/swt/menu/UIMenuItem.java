/* $Id: UIMenuItem.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;

import net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncMenuItemAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIMenuItem extends UISwtNode implements UIMenuItemIF {

    /** the SWT menu item */
    private final MenuItem menuItem;

    /** the synchron access to the menu-item */
    private final SyncMenuItemAccess syncAccess;
    
    /** the style */
    private final ButtonStyle style;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param text
     *        is the {@link #getText() text} of the menu item.
     * @param itemStyle
     *        is the style defining how the item is visualized and behaves.
     * @param item
     *        is the actual SWT menu-item.
     */
    public UIMenuItem(UIFactory uiFactory, UIMenu parentObject, String text, ButtonStyle itemStyle,
            MenuItem item) {

        super(uiFactory, parentObject);
        int swtStyle = UIFactory.convertButtonStyleForMenuItem(itemStyle);
        this.syncAccess = new SyncMenuItemAccess(uiFactory, swtStyle, item, text);
        this.style = itemStyle;
        this.menuItem = item;
    }

    /**
     * This method gets the unwrapped menu item.
     * 
     * @return the menu item.
     */
    protected MenuItem getItem() {

        return this.menuItem;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#doInitializeListener()
     * {@inheritDoc}
     */
    protected boolean doInitializeListener() {

        this.syncAccess.addListener(SWT.Selection, createSwtListener());
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.menu.UIMenuItemIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.syncAccess.getText();
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

        return this.syncAccess.isSelected();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF#setSelected(boolean)
     * {@inheritDoc}
     */
    public void setSelected(boolean selected) {

        this.syncAccess.setSelected(selected);
    }

}
