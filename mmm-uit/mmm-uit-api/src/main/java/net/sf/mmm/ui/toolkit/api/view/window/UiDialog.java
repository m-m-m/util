/* $Id: UIDialog.java 955 2011-02-16 23:10:16Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.window;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadModal;

/**
 * This is the interface for a dialog. A dialog is a window that is not visible
 * in the taskbar. It usually has the top focus of the application and may be
 * modal what means that all other windows of the application are blocked until
 * the dialog is closed. A dialog is created from a frame that will be his
 * parent.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDialog extends UiWindow, UiReadModal {

  /** the type of this object */
  String TYPE = "Dialog";

}
