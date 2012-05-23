/* $Id: UIDialog.java 955 2011-02-16 23:10:16Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.window;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadModal;

/**
 * This is the interface for a dialog. A dialog is a {@link UiWindow} used for a
 * temporary interaction (e.g. a popup with a warning or additional required
 * input). It is not visible in the task-bar. It usually has the top focus of
 * the application and may be {@link #isModal() modal}. A dialog is created from
 * a frame that will be his {@link #getParent() parent}.
 * 
 * @see UiWindow#createDialog(String, boolean, boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDialog extends UiWindow, AttributeReadModal {

  /** the type of this object */
  String TYPE = "Dialog";

}
