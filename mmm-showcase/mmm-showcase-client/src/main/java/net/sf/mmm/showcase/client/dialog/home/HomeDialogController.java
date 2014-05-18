/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.home;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class HomeDialogController extends AbstractMainDialogController<HomeViewWidget> {

  /**
   * The constructor.
   */
  public HomeDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Home";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return DialogConstants.DIALOG_ID_HOME;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HomeViewWidget createView() {

    return new HomeViewWidget(getUiContext());
  }

}
