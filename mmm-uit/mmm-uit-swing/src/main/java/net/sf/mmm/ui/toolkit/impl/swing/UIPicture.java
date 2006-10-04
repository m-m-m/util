/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swing;

import java.net.URL;

import javax.swing.ImageIcon;

import net.sf.mmm.ui.toolkit.api.UIFactoryIF;
import net.sf.mmm.ui.toolkit.base.UIAbstractPicture;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIPictureIF} interface using Swing as the
 * underlying implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPicture extends UIAbstractPicture {

    /** the wrapped picture */
    private final ImageIcon picture;

    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param imageUrl
     *        is the URL to the image data.
     */
    public UIPicture(UIFactoryIF uiFactory, URL imageUrl) {

        super(uiFactory);
        this.picture = new ImageIcon(imageUrl);
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredWidth()
     * 
     */
    public int getPreferredWidth() {

        return this.picture.getIconWidth();
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredHeight()
     * 
     */
    public int getPreferredHeight() {

        return this.picture.getIconHeight();
    }
    
    /**
     * This method gets the native Swing picture.
     * 
     * @return the Swing icon.
     */
    public ImageIcon getSwingIcon() {
        
        return this.picture;
    }
    

}
