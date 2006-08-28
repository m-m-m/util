/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt;

import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import net.sf.mmm.ui.toolkit.base.UIAbstractPicture;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.UIPictureIF} interface using SWT as the
 * underlying implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIPicture extends UIAbstractPicture {

    /** the native SWT image object */
    private final Image image;

    /** the image data */
    private final ImageData data;
    
    /**
     * The constructor.
     * 
     * @param uiFactory
     *        is the UIFactory instance.
     * @param imageUrl
     *        is the URL to the image data.
     * @throws IOException
     *         on I/O error opening or reading data from the given URL.
     */
    public UIPicture(UIFactory uiFactory, URL imageUrl) throws IOException {

        super(uiFactory);
        this.data = new ImageData(imageUrl.openStream());
        this.image = new Image(uiFactory.getDisplay().getSwtDisplay(), this.data);        
    }
    
    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredHeight()
     * {@inheritDoc}
     */
    public int getPreferredHeight() {

        return this.data.height;
    }

    /**
     * @see net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF#getPreferredWidth()
     * {@inheritDoc}
     */
    public int getPreferredWidth() {

        return this.data.width;
    }

    /**
     * This method gets the native SWT picture.
     * 
     * @return the SWT image.
     */
    public Image getSwtImage() {
        
        return this.image;
    }
    
}
