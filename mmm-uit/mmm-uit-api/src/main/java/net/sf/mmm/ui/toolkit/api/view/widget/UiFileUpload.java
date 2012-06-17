/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteValue;
import net.sf.mmm.ui.toolkit.api.feature.UiFileAccess;

/**
 * This is the interface for a widget that allows the user to choose a file for
 * upload. Upload means that the file is send from the user (client) to the
 * application and can be accessed via this widget no matter if running on the
 * client or a webserver.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFileUpload extends UiWidget, UiWriteValue<String> {

  /** the type of this object */
  String TYPE = "FileUpload";

  /**
   * This method gets access to the file selected for upload.
   * 
   * @return access to the upload-file.
   */
  UiFileAccess getSelection();

}
