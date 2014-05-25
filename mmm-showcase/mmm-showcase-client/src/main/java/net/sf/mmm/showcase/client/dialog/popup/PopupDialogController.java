/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.popup;

import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;
import net.sf.mmm.showcase.client.dialog.ShowcaseDialogConstants;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PopupDialogController extends AbstractMainDialogController<PopupViewWidget> {

  /**
   * The constructor.
   */
  public PopupDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Popup";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return ShowcaseDialogConstants.DIALOG_ID_POPUP;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PopupViewWidget createView() {

    return new PopupViewWidget(getUiContext());
  }

}
