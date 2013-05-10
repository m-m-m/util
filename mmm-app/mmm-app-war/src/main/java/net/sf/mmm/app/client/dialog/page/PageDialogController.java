/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog.page;

import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.dialog.DialogController;
import net.sf.mmm.client.ui.base.dialog.DialogSlot;
import net.sf.mmm.client.ui.base.dialog.page.AbstractPageDialogController;
import net.sf.mmm.client.ui.base.dialog.root.RootDialogController;

/**
 * This is the implementation of {@link AbstractPageDialogController}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PageDialogController extends AbstractPageDialogController<PageViewWidget> {

  /**
   * The constructor.
   */
  public PageDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return TYPE_PAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Multi-Media-Manager";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return DIALOG_ID_PAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DialogSlot doShow(DialogPlace dialogPlace) {

    return RootDialogController.SLOT_PAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void embedMainDialog(DialogController<?> subDialog) {

    getView().getSlotMainDialog().setChild((UiWidgetRegular) subDialog.getView());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PageViewWidget createView() {

    return new PageViewWidget(getUiContext());
  }

}
