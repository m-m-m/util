/* $Id$ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIWriteTextIF;

/**
 * This is the interface for a widget that allows the user to download a
 * specific file. Download means that the user (client) receives a file from the
 * application. The widget requires
 * {@link net.sf.mmm.ui.toolkit.api.feature.FileAccessIF access} to the
 * download-file on construction.<br>
 * The {@link net.sf.mmm.ui.toolkit.api.state.UIReadTextIF#getText() label-text}
 * is shown to the user and can be clicked (button, link, ...) to start the
 * download (examples are "save", "download foo.xml", etc.). The label-text will
 * be initialized with the
 * {@link net.sf.mmm.ui.toolkit.api.feature.FileAccessIF#getFilename() filename}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIFileDownloadIF extends UIWidgetIF, UIWriteTextIF {

    /** the type of this object */
    String TYPE = "FileDownload";

}
