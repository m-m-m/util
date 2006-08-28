/* $Id: UIButton.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import net.sf.mmm.ui.toolkit.api.UINodeIF;
import net.sf.mmm.ui.toolkit.api.UIPictureIF;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButtonIF;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactory;
import net.sf.mmm.ui.toolkit.impl.swing.UIPicture;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIButtonIF} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIButton extends UIWidget implements UIButtonIF {

    /** the insets of the button */
    //private static final Insets BUTTON_INSETS = new Insets(4, 4, 4, 4);

    /** the actual Swing button */
    private final AbstractButton button;

    /** the style of the button */
    private final ButtonStyle style;

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
    public UIButton(UIFactory uiFactory, UINodeIF parentObject, ButtonStyle buttonStyle) {

        super(uiFactory, parentObject);
        AbstractButton b;
        this.style = buttonStyle;
        switch (this.style) {
            case DEFAULT:
                b = new JButton();
                break;
            case CHECK:
                b = new JCheckBox();
                break;
            case RADIO:
                b = new JRadioButton();
                break;
            case TOGGLE:
                b = new JToggleButton();
                break;
            default :
                throw new IllegalArgumentException("Unknown style: " + this.style);
        }
        this.button = b;
        // this.button.setMargin(BUTTON_INSETS);
        this.icon = null;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.impl.swing.UIComponent#getSwingComponent()
     * {@inheritDoc}
     */
    public JComponent getSwingComponent() {

        return this.button;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF#setText(java.lang.String)
     * {@inheritDoc}
     */
    public void setText(String text) {

        this.button.setText(text);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText()
     * {@inheritDoc}
     */
    public String getText() {

        return this.button.getText();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.base.UIAbstractNode#doInitializeListener()
     * {@inheritDoc}
     */
    @Override
    protected boolean doInitializeListener() {

        this.button.addActionListener(createActionListener());
        return true;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.UIObjectIF#getType()
     * {@inheritDoc}
     */
    public String getType() {

        return TYPE;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF#isSelected()
     * {@inheritDoc}
     */
    public boolean isSelected() {

        return this.button.isSelected();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionFlagIF#setSelected(boolean)
     * {@inheritDoc}
     */
    public void setSelected(boolean selected) {

        this.button.setSelected(selected);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.widget.UIButtonIF#getStyle()
     * {@inheritDoc}
     */
    public ButtonStyle getStyle() {

        return this.style;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIconIF#setIcon(net.sf.mmm.ui.toolkit.api.UIPictureIF)
     * {@inheritDoc}
     */
    public void setIcon(UIPictureIF newIcon) {

        this.icon = (UIPicture) newIcon;
        if (this.icon == null) {
            this.button.setIcon(null);
        } else {
            this.button.setIcon(this.icon.getSwingIcon());            
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