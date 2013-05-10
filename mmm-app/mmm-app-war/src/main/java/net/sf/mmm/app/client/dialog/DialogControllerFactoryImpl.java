/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client.dialog;

import java.util.List;

import net.sf.mmm.app.client.dialog.home.HomeDialogController;
import net.sf.mmm.app.client.dialog.page.PageDialogController;
import net.sf.mmm.app.client.dialog.test.TestDialogController;
import net.sf.mmm.client.ui.base.dialog.AbstractDialogControllerFactory;
import net.sf.mmm.client.ui.base.dialog.DialogController;

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
  protected void createAndAddDialogControllers(List<DialogController<?>> controllerList) {

    controllerList.add(new PageDialogController());
    controllerList.add(new HomeDialogController());
    controllerList.add(new TestDialogController());
  }

}
