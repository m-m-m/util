/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog.page;

import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.dialog.DialogController;
import net.sf.mmm.client.ui.base.dialog.DialogSlot;
import net.sf.mmm.client.ui.base.dialog.root.RootDialogController;

/**
 * This is the abstract base implementation for the {@link DialogController} of the {@link #TYPE_PAGE page}.
 * 
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractPageDialogController<VIEW extends UiWidgetRegular> extends DialogController<VIEW> {

  /** The {@link DialogSlot} for the main dialog. */
  public static final DialogSlot SLOT_MAIN = new DialogSlot(DIALOG_ID_PAGE, TYPE_MAIN);

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

    return TYPE_PAGE;
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
  protected void embed(DialogController<?> subDialog, DialogSlot slot) {

    if (SLOT_MAIN.equals(slot)) {
      embedMainDialog(subDialog);
    } else {
      super.embed(subDialog, slot);
    }
  }

  /**
   * This method {@link #embed(DialogController, DialogSlot) embeds} the given {@link #TYPE_MAIN main dialog}.
   * 
   * @param subDialog is the {@link #TYPE_MAIN main dialog} to embed.
   */
  protected abstract void embedMainDialog(DialogController<?> subDialog);

}
