/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog.home;

import net.sf.mmm.app.client.DialogConstants;
import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;

/**
 * This is the {@link AbstractMainDialogController controller} for the {@link HomeViewWidget home view}.
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

    HomeViewWidget homeViewWidget = new HomeViewWidget(getUiContext(), getDialogManager());
    return homeViewWidget;
  }

}
