/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog.page;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetSlot;
import net.sf.mmm.client.ui.base.dialog.AttributeReadSlot;
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
public abstract class AbstractPageDialogController<VIEW extends UiWidgetRegular & AttributeReadSlot> extends
    DialogController<VIEW> {

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

    UiWidgetSlot slotWidget = getView().getSlot(slot);
    if (slotWidget == null) {
      super.embed(subDialog, slot);
    } else {
      slotWidget.setChild((UiWidgetRegular) subDialog.getView());
    }
  }

}
