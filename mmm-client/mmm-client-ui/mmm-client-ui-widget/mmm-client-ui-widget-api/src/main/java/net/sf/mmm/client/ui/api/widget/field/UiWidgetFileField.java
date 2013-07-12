/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.util.io.api.FileItem;

/**
 * This is the interface for a {@link UiWidgetField field widget} that represents a
 * <em>file upload and download</em>. Such widget allows to choose a file on the client and upload it to the
 * server as well as downloading a file from the server.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetFileField extends UiWidgetField<FileItem>, UiWidgetNative {

  // nothing to add...

}
