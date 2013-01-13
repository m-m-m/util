/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.dialog.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.client.ui.dialog.api.AbstractDialog;
import net.sf.mmm.client.ui.dialog.api.Dialog;
import net.sf.mmm.client.ui.dialog.api.DialogManager;
import net.sf.mmm.client.ui.dialog.api.DialogPlace;
import net.sf.mmm.client.ui.dialog.api.PopupDialog;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link DialogManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDialogManager extends AbstractLoggableComponent implements DialogManager {

  /** @see #getDialog(String) */
  private final Map<String, DialogController<?>> id2DialogMap;

  /** @see #getCurrentDialog(String) */
  private final Map<String, Dialog> type2currentDialogMap;

  /** @see #setDialogControllerFactory(DialogControllerFactory) */
  private DialogControllerFactory dialogControllerFactory;

  /** @see #initialize(DialogPlace) */
  private DialogPlace defaultPlace;

  /** @see #getCurrentPlace() */
  private DialogPlace currentPlace;

  /**
   * The constructor.
   */
  public AbstractDialogManager() {

    super();
    this.id2DialogMap = new HashMap<String, DialogController<?>>();
    this.type2currentDialogMap = new HashMap<String, Dialog>();
  }

  /**
   * @param dialogControllerFactory is the {@link DialogControllerFactory} to set manually or via
   *        {@link Inject}.
   */
  @Inject
  public void setDialogControllerFactory(DialogControllerFactory dialogControllerFactory) {

    this.dialogControllerFactory = dialogControllerFactory;
    List<DialogController<?>> dialogControllers = this.dialogControllerFactory.createDialogControllers();
    for (DialogController<?> controller : dialogControllers) {
      String dialogId = controller.getId();
      NlsNullPointerException.checkNotNull("dialogId", controller);
      if (!dialogId.matches(AbstractDialog.PATTERN_DIALOG_ID)) {
        throw new ObjectMismatchException(dialogId, AbstractDialog.PATTERN_DIALOG_ID, controller);
      }
      controller.setDialogManager(this);
      this.id2DialogMap.put(dialogId, controller);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(DialogPlace defaultDialogPlace) {

    this.defaultPlace = defaultDialogPlace;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DialogPlace getCurrentPlace() {

    return this.currentPlace;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DialogController<?> getDialog(String dialogId) throws ObjectNotFoundException {

    DialogController<?> dialog = this.id2DialogMap.get(dialogId);
    if (dialog == null) {
      throw new ObjectNotFoundException(Dialog.class, dialogId);
    }
    return dialog;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dialog getCurrentDialog(String type) {

    return this.type2currentDialogMap.get(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Dialog getCurrentMainDialog() {

    return getCurrentDialog(Dialog.TYPE_MAIN);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PopupDialog getCurrentPopupDialog() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * This method should be called whenever a {@link Dialog} is opened or brought to the front.
   * 
   * @param dialog the current {@link Dialog}.
   */
  void onShowDialog(Dialog dialog) {

    this.type2currentDialogMap.put(dialog.getType(), dialog);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void navigateTo(String dialogId) {

    navigateTo(new DialogPlace(dialogId));
  }

  /**
   * @see #navigateTo(DialogPlace)
   * 
   * @param dialogPlace is the {@link DialogPlace} to show.
   */
  protected void show(DialogPlace dialogPlace) {

    String dialogId = dialogPlace.getDialogId();
    DialogController<?> dialogController = getDialog(dialogId);
    dialogController.show(dialogPlace);

    this.currentPlace = dialogPlace;
  }
}
