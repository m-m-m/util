/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog.navigation;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.dialog.DialogController;
import net.sf.mmm.client.ui.base.dialog.DialogSlot;

/**
 * This is the abstract base implementation for the {@link DialogController} of a
 * {@link DialogConstants#TYPE_NAVIGATION navigation dialog}.
 *
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNavigationDialogController<VIEW extends UiWidgetRegular> extends DialogController<VIEW> {

  /**
   * The constructor.
   */
  public AbstractNavigationDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return DialogConstants.DIALOG_ID_NAVIGATION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return DialogConstants.TYPE_NAVIGATION;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DialogSlot doShow(DialogPlace dialogPlace) {

    return DialogConstants.SLOT_PAGE_NAVIGATION;
  }

}
