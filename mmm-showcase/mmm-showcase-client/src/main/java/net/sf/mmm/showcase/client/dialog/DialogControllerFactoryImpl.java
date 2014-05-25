/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.base.dialog.AbstractDialogControllerFactory;
import net.sf.mmm.client.ui.base.dialog.DialogController;
import net.sf.mmm.showcase.client.dialog.editor.EditorDialogController;
import net.sf.mmm.showcase.client.dialog.home.HomeDialogController;
import net.sf.mmm.showcase.client.dialog.list.ListDialogController;
import net.sf.mmm.showcase.client.dialog.masterdetail.MasterDetailDialogController;
import net.sf.mmm.showcase.client.dialog.page.PageDialogController;
import net.sf.mmm.showcase.client.dialog.popup.PopupDialogController;
import net.sf.mmm.showcase.client.dialog.widgets.WidgetsDialogController;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DialogControllerFactoryImpl extends AbstractDialogControllerFactory {

  /**
   * The constructor.
   */
  public DialogControllerFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DialogController<?> createPageDialogController() {

    return new PageDialogController();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DialogController<?> createDialogController(String dialogId) {

    if (dialogId.equals(DialogConstants.DIALOG_ID_HOME)) {
      return new HomeDialogController();
    } else if (dialogId.equals(ShowcaseDialogConstants.DIALOG_ID_WIDGETS)) {
      return new WidgetsDialogController();
    } else if (dialogId.equals(ShowcaseDialogConstants.DIALOG_ID_EDITOR)) {
      return new EditorDialogController();
    } else if (dialogId.equals(ShowcaseDialogConstants.DIALOG_ID_LIST)) {
      return new ListDialogController();
    } else if (dialogId.equals(ShowcaseDialogConstants.DIALOG_ID_POPUP)) {
      return new PopupDialogController();
    } else if (dialogId.equals(ShowcaseDialogConstants.DIALOG_ID_MASTER_DETAIL)) {
      return new MasterDetailDialogController();
    } else {
      return super.createDialogController(dialogId);
    }
  }

}
