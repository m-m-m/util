/* $Id: UILabel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.widget.UILabelIF;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swt.UIPicture;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncLabelAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UILabelIF} interface using SWT as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UILabel extends UIWidget implements UILabelIF {

    /** the synchron access to the label */
    private final SyncLabelAccess syncAccess;

    /** the icon */
    private UIPicture icon;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param parentObject
     *        is the parent of this object (may be <code>null</code>).
     */
    public UILabel(UIFactory uiFactory, UISwtNode parentObject) {

        super(uiFactory, parentObject);
        int style = SWT.SHADOW_NONE | SWT.LEFT;
        this.syncAccess = new SyncLabelAccess(uiFactory, style);
        this.icon = null;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swt.UIComponent#getSyncAccess()
     * {@inheritDoc}
     */
    @Override
    public AbstractSyncControlAccess getSyncAccess() {
    
        return this.syncAccess;
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.syncAccess.setText(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.syncAccess.getText();
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

}
