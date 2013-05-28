/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;

/**
 * This interface extends {@link DialogConstants} with the custom constants specific for the dialogs of the
 * MMM application.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface MmmDialogConstants extends DialogConstants {

  /**
   * The {@link net.sf.mmm.client.ui.api.dialog.Dialog#getId() dialog-id} of a {@link #TYPE_MAIN main} dialog
   * for testing.
   */
  String DIALOG_ID_TEST = "test";

}
