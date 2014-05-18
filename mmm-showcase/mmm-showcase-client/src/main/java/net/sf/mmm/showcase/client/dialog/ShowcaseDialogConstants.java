/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog;

import net.sf.mmm.client.ui.api.dialog.Dialog;
import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogPlace;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ShowcaseDialogConstants extends DialogConstants {

  /** The {@link Dialog#getId() dialog-id} of the {@link #TYPE_MAIN main} dialog showing basic widgets. */
  String DIALOG_ID_WIDGETS = "widgets";

  /** The {@link DialogPlace} for the {@link #DIALOG_ID_WIDGETS widgets dialog}. */
  DialogPlace PLACE_WIDGETS = new DialogPlace(DIALOG_ID_WIDGETS);

}
