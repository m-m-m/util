/* $Id: UIPictureIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api;

import net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteSizeIF;

/**
 * This is the interface for a picture in the UI toolkit. Think of it as a
 * regular picture-file (jpeg, png, gif).<br>
 * The {@link net.sf.mmm.ui.toolkit.api.state.UIReadPreferredSizeIF preferred-size}
 * is the physical size of the image. The regular
 * {@link net.sf.mmm.ui.toolkit.api.state.UIReadSizeIF size} is initialized with
 * the preferred size but may be changed to scale the image.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIPictureIF extends UIObjectIF, UIWriteSizeIF, UIReadPreferredSizeIF {

    /** the type of this object */
    String TYPE = "Picture";

}
