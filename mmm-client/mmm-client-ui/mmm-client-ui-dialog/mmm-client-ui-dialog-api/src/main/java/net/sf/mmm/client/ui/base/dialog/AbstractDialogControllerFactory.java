/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.base.dialog.root.RootDialogController;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link DialogControllerFactory}. It only handles the
 * {@link #createDialogController(String) creation} of the {@link RootDialogController}. You need to override
 * and extend {@link #createDialogController(String)} for your custom dialog controllers.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDialogControllerFactory extends AbstractLoggableComponent implements
    DialogControllerFactory {

  /**
   * The constructor.
   */
  public AbstractDialogControllerFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DialogController<?> createDialogController(String dialogId) {

    if (DialogConstants.DIALOG_ID_ROOT.equals(dialogId)) {
      return new RootDialogController();
    } else if (DialogConstants.DIALOG_ID_PAGE.equals(dialogId)) {
      return createPageDialogController();
    }
    return null;
  }

  /**
   * @return a new instance of the {@link DialogConstants#TYPE_PAGE page} {@link DialogController}.
   */
  protected abstract DialogController<?> createPageDialogController();
}
