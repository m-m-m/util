/* $Id: UILabel.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.widget.UILabelIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.UIPicture;


/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UILabelIF} interface using Swing as
 * the UI toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UILabel extends UIWidget implements UILabelIF {

    /** the actual Swing label */
    private final JLabel label;

    /** the icon */
    private UIPicture icon;
    
    /**
     * The constructor.
     *
     * @param uiFactory
     * @param parentObject
     */
    public UILabel(UIFactory uiFactory, UINodeIF parentObject) {

        super(uiFactory, parentObject);
        this.label = new JLabel();
        this.icon = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    public @Override JComponent getSwingComponent() {

        return this.label;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.label.setText(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.label.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIconIF#setIcon(net.sf.mmm.ui.toolkit.api.UIPictureIF)
     * {@inheritDoc}
     */
    public void setIcon(UIPictureIF newIcon) {

        this.icon = (UIPicture) newIcon;
        if (this.icon == null) {
            this.label.setIcon(null);
        } else {
            this.label.setIcon(this.icon.getSwingIcon());            
        }        
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadIconIF#getIcon()
     * {@inheritDoc}
     */
    public UIPictureIF getIcon() {

        return this.icon;
    }

}
