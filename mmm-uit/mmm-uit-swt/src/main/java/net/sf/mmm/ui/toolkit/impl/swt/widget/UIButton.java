/* $Id: UIButton.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButtonIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UIPicture;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncButtonAccess;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIButtonIF} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIButton extends UIWidget implements UIButtonIF {

    /** the style of the button */
    private final ButtonStyle style;

    /** the synchron access to the button */
    private final SyncButtonAccess syncAccess;

    /** the icon */
    private UIPicture icon;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     * @param buttonStyle
     *        determines the style of the button.
     */
    public UIButton(UIFactory uiFactory, UISwtNode parentObject, ButtonStyle buttonStyle) {

        super(uiFactory, parentObject);
        this.style = buttonStyle;
        int swtStyle = UIFactory.convertButtonStyle(buttonStyle);
        this.syncAccess = new SyncButtonAccess(uiFactory, swtStyle);
        this.icon = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.syncAccess.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.syncAccess.setText(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#doInitializeListener()
     * {@inheritDoc}
     */
    @Override
    protected boolean doInitializeListener() {

        this.syncAccess.addListener(SWT.Selection, createSwtListener());
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIButtonIF#getStyle()
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

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadIconIF#getIcon()
     * {@inheritDoc}
     */
    public UIPicture getIcon() {

        return this.icon;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIconIF#setIcon(net.sf.mmm.ui.toolkit.api.UIPictureIF)
     * {@inheritDoc}
     */
    public void setIcon(UIPictureIF newIcon) {

        this.icon = (UIPicture) newIcon;
        if (this.icon == null) {
            this.syncAccess.setImage(null);
        } else {
            this.syncAccess.setImage(this.icon.getSwtImage());
        }
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public SyncButtonAccess getSyncAccess() {

        return this.syncAccess;
    }

}
