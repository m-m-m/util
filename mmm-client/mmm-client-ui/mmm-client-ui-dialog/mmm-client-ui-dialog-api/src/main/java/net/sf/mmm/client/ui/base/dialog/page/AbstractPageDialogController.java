/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog.page;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.dialog.DialogController;
import net.sf.mmm.client.ui.base.dialog.DialogSlot;

/**
 * This is the abstract base implementation for the {@link DialogController} of the
 * {@link DialogConstants#TYPE_PAGE page}.
 * 
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractPageDialogController<VIEW extends UiWidgetRegular> extends DialogController<VIEW> {

  /**
   * The constructor.
   */
  public AbstractPageDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return DialogConstants.TYPE_PAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return DialogConstants.DIALOG_ID_PAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DialogSlot doShow(DialogPlace dialogPlace) {

    return DialogConstants.SLOT_ROOT_PAGE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void embed(DialogController<?> subDialog, DialogSlot slot) {

    if (DialogConstants.SLOT_PAGE_MAIN.equals(slot)) {
      embedMainDialog(subDialog);
    } else {
      super.embed(subDialog, slot);
    }
  }

  /**
   * This method {@link #embed(DialogController, DialogSlot) embeds} the given
   * {@link DialogConstants#TYPE_MAIN main dialog}.
   * 
   * @param subDialog is the {@link DialogConstants#TYPE_MAIN main dialog} to embed.
   */
  protected abstract void embedMainDialog(DialogController<?> subDialog);

}
