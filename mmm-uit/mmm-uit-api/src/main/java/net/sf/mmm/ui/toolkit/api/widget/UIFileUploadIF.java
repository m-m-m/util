/* $Id: UIFileUploadIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.feature.FileAccessIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF;

/**
 * This is the interface for a widget that allows the user to choose a file for
 * upload. Upload means that the file is send from the user (client) to the
 * application and can be accessed via this widget no matter if running on the
 * client or a webserver.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIFileUploadIF extends UIWidgetIF, UIWriteTextIF {

    /** the type of this object */
    String TYPE = "FileUpload";

    /**
     * This method gets access to the file selected for upload.
     * 
     * @return access to the upload-file.
     */
    FileAccessIF getSelection();

}
