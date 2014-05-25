/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.editor;

import net.sf.mmm.client.ui.api.common.MessageSeverity;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.handler.object.UiHandlerObjectSave;
import net.sf.mmm.client.ui.base.dialog.main.AbstractMainDialogController;
import net.sf.mmm.showcase.client.dialog.ShowcaseDialogConstants;

/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class EditorDialogController extends AbstractMainDialogController<EditorViewWidget> implements
    UiHandlerObjectSave<ContactBean> {

  /**
   * The constructor.
   */
  public EditorDialogController() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return "Editor";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return ShowcaseDialogConstants.DIALOG_ID_EDITOR;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected EditorViewWidget createView() {

    return new EditorViewWidget(getUiContext(), this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onSave(ContactBean object, UiEvent event) {

    getUiContext().getPopupHelper().showPopup("Contact " + object + " saved!", MessageSeverity.INFORMATION);
  }

}
