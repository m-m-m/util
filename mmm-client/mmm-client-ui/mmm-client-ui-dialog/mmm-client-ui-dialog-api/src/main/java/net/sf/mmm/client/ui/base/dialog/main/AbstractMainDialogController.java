/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog.main;

import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.dialog.DialogController;
import net.sf.mmm.client.ui.base.dialog.DialogSlot;
import net.sf.mmm.client.ui.base.dialog.page.AbstractPageDialogController;

/**
 * This is the abstract base implementation for the {@link DialogController} of a {@link #TYPE_MAIN main
 * dialog}.
 * 
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractMainDialogController<VIEW extends UiWidgetRegular> extends DialogController<VIEW> {

  /**
   * The constructor.
   */
  public AbstractMainDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return TYPE_MAIN;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DialogSlot doShow(DialogPlace dialogPlace) {

    return AbstractPageDialogController.SLOT_MAIN;
  }

}
