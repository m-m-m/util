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
  String DIALOG_ID_WIDGETS = "Widgets";

  /** The {@link Dialog#getId() dialog-id} of the {@link #TYPE_MAIN main} dialog showing an editor. */
  String DIALOG_ID_EDITOR = "Editor";

  /** The {@link Dialog#getId() dialog-id} of the {@link #TYPE_MAIN main} dialog showing a list table. */
  String DIALOG_ID_LIST = "List";

  /** The {@link Dialog#getId() dialog-id} of the {@link #TYPE_MAIN main} dialog showing a popup. */
  String DIALOG_ID_POPUP = "Popup";

  /**
   * The {@link Dialog#getId() dialog-id} of the {@link #TYPE_MAIN main} dialog showing a master/detail panel.
   */
  String DIALOG_ID_MASTER_DETAIL = "MasterDetail";

  /** The {@link DialogPlace} for {@link #DIALOG_ID_WIDGETS}. */
  DialogPlace PLACE_WIDGETS = new DialogPlace(DIALOG_ID_WIDGETS);

  /** The {@link DialogPlace} for {@link #DIALOG_ID_EDITOR}. */
  DialogPlace PLACE_EDITOR = new DialogPlace(DIALOG_ID_EDITOR);

  /** The {@link DialogPlace} for {@link #DIALOG_ID_LIST}. */
  DialogPlace PLACE_LIST = new DialogPlace(DIALOG_ID_LIST);

  /** The {@link DialogPlace} for {@link #DIALOG_ID_POPUP}. */
  DialogPlace PLACE_POPUP = new DialogPlace(DIALOG_ID_POPUP);

  /** The {@link DialogPlace} for {@link #DIALOG_ID_MASTER_DETAIL}. */
  DialogPlace PLACE_MASTER_DETAIL = new DialogPlace(DIALOG_ID_MASTER_DETAIL);

}
