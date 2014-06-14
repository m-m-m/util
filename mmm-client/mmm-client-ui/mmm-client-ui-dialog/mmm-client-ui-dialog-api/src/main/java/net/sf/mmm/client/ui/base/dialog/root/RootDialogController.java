/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog.root;

import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.dialog.DialogController;
import net.sf.mmm.client.ui.base.dialog.DialogSlot;
import net.sf.mmm.util.exception.api.ObjectMismatchException;

/**
 * This is the {@link DialogController} for the {@link DialogConstants#TYPE_ROOT root} dialog. It simply
 * represents the empty {@link UiWidgetMainWindow main window} of the application and provides a
 * {@link DialogConstants#SLOT_ROOT_PAGE slot} to embed the actual {@link DialogConstants#TYPE_PAGE page}
 * dialog.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RootDialogController extends DialogController<UiWidgetMainWindow> {

  /**
   * The constructor.
   */
  public RootDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getType() {

    return DialogConstants.TYPE_ROOT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    // This title will never be displayed...
    return "root";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return DialogConstants.DIALOG_ID_ROOT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DialogSlot doShow(DialogPlace dialogPlace) {

    // will never be called...
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetMainWindow createView() {

    return getUiContext().getWidgetFactory().getMainWindow();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void embed(DialogController<?> subDialog, DialogSlot slot) {

    if (slot == DialogConstants.SLOT_ROOT_PAGE) {
      UiWidgetMainWindow view = getView();
      if (view.getChildCount() > 0) {
        view.removeChild(0);
      }
      UiWidget childView = subDialog.getView();
      if (childView instanceof UiWidgetRegular) {
        view.addChild((UiWidgetRegular) childView);
      } else {
        throw new ObjectMismatchException(childView, UiWidgetRegular.class, subDialog.getId() + ".view");
      }
    } else {
      super.embed(subDialog, slot);
    }
  }
}
