/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.dialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.dialog.AbstractDialog;
import net.sf.mmm.client.ui.api.dialog.Dialog;
import net.sf.mmm.client.ui.api.dialog.DialogConstants;
import net.sf.mmm.client.ui.api.dialog.DialogManager;
import net.sf.mmm.client.ui.api.dialog.DialogPlace;
import net.sf.mmm.client.ui.api.dialog.PopupDialog;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.binding.UiAccessKeyBinding;
import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
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

  /** @see #getCurrentPopupDialog() */
  private final LinkedList<PopupDialog> popupStack;

  /** @see #setDialogControllerFactory(DialogControllerFactory) */
  private DialogControllerFactory dialogControllerFactory;

  /** @see #setContext(UiContext) */
  private UiContext context;

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
    this.popupStack = new LinkedList<PopupDialog>();
  }

  /**
   * @param dialogControllerFactory is the {@link DialogControllerFactory} to set manually or via
   *        {@link Inject}.
   */
  @Inject
  public void setDialogControllerFactory(DialogControllerFactory dialogControllerFactory) {

    getInitializationState().requireNotInitilized();
    this.dialogControllerFactory = dialogControllerFactory;
  }

  /**
   * @return the {@link UiContext} instance.
   */
  protected UiContext getContext() {

    return this.context;
  }

  /**
   * @param uiContext is the {@link UiContext} to {@link Inject}.
   */
  @Inject
  public void setContext(UiContext uiContext) {

    getInitializationState().requireNotInitilized();
    this.context = uiContext;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.context == null) {
      throw new ResourceMissingException(UiContext.class.getSimpleName());
    }
    if (this.dialogControllerFactory == null) {
      throw new ResourceMissingException(DialogControllerFactory.class.getSimpleName());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(DialogPlace defaultDialogPlace) {

    if (!getInitializationState().isInitialized()) {
      initialize();
    }
    if (this.defaultPlace != null) {
      throw new AlreadyInitializedException();
    }
    this.defaultPlace = defaultDialogPlace;
    DialogPlace startPlace = getStartPlace();
    if (startPlace == null) {
      startPlace = this.defaultPlace;
    }
    navigateTo(startPlace);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DialogPlace getCurrentPlace() {

    return this.currentPlace;
  }

  /**
   * This method determines the start {@link DialogPlace}. If the application is started from a bookmark, this
   * method will create the {@link DialogPlace} from this bookmark (in case of a web-application from the hash
   * of the URL).
   *
   * @see #initialize(DialogPlace)
   *
   * @return the start {@link DialogPlace} or <code>null</code> if not present (and
   *         {@link #initialize(DialogPlace) default} shall be used.
   */
  protected abstract DialogPlace getStartPlace();

  /**
   * {@inheritDoc}
   */
  @Override
  public DialogController<?> getDialog(String dialogId) throws ObjectNotFoundException {

    DialogController<?> dialog = this.id2DialogMap.get(dialogId);
    if (dialog == null) {
      if (!dialogId.matches(AbstractDialog.PATTERN_DIALOG_ID)) {
        throw new ObjectMismatchException(dialogId, AbstractDialog.PATTERN_DIALOG_ID);
      }
      dialog = this.dialogControllerFactory.createDialogController(dialogId);
      if (dialog == null) {
        // throw new ObjectNotFoundException(Dialog.class, dialogId);
        return null;
      }
      if (!dialogId.equals(dialog.getId())) {
        throw new ObjectMismatchException(dialog.getId(), dialogId, "DialogController.id");
      }
      dialog.setDialogManager(this);
      dialog.setUiContext(this.context);
      this.id2DialogMap.put(dialogId, dialog);
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

    return getCurrentDialog(DialogConstants.TYPE_MAIN);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PopupDialog getCurrentPopupDialog() {

    if (this.popupStack.isEmpty()) {
      return null;
    }
    return this.popupStack.getLast();
  }

  /**
   * This method has to be called whenever a {@link Dialog} is opened or brought to the front.
   *
   * @param dialog the current {@link Dialog}.
   */
  void onShowDialog(Dialog dialog) {

    assert dialog.isVisible();
    String type = dialog.getType();
    // show
    Dialog previousDialog = this.type2currentDialogMap.put(type, dialog);
    if (previousDialog != null) {
      if (previousDialog.isVisible() && (previousDialog != dialog)) {
        // there should never be two dialogs visible of the same type...
        throw new DuplicateObjectException(this, type, previousDialog);
      }
    }
  }

  /**
   * This method should be called whenever a {@link Dialog} is hidden.
   *
   * @param dialog the {@link Dialog} to hide.
   */
  void onHideDialog(Dialog dialog) {

    assert (!dialog.isVisible());
    String type = dialog.getType();
    // hide
    Dialog previousDialog = this.type2currentDialogMap.remove(type);
    if (previousDialog != dialog) {
      throw new ObjectMismatchException(previousDialog, dialog, type);
    }
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
    DialogPlace place = dialogPlace;
    if (dialogController == null) {
      dialogController = getDialog(DialogConstants.DIALOG_ID_HOME);
      if (dialogController == null) {
        ObjectNotFoundException cause = new ObjectNotFoundException(Dialog.class, dialogId);
        throw new ObjectNotFoundException(cause, Dialog.class, DialogConstants.DIALOG_ID_HOME);
      }
      place = DialogConstants.PLACE_HOME;
    }
    dialogController.show(place);
    this.currentPlace = place;
    assert verifyUniqueAccessKeys();
  }

  /**
   * @see UiAccessKeyBinding#verifyUniqueAccessKeys()
   * @return always <code>true</code>.
   */
  private boolean verifyUniqueAccessKeys() {

    if (this.context instanceof AbstractUiContext) {
      UiAccessKeyBinding accessKeyBinding = ((AbstractUiContext) this.context).getAccessKeyBinding();
      if (accessKeyBinding != null) {
        accessKeyBinding.verifyUniqueAccessKeys();
      }
    }
    return true;
  }

  /**
   * This method determines the title for the given {@link DialogController}.
   *
   * @param dialogController is the {@link DialogController}.
   * @return the calculated title.
   */
  protected String getTitle(DialogController<?> dialogController) {

    StringBuilder buffer = new StringBuilder(dialogController.getTitle());
    DialogController<?> parentController = dialogController.getParent();
    while (parentController != null) {
      String title = parentController.getTitle();
      if (title != null) {
        buffer.insert(0, " - ");
        buffer.insert(0, title);
      }
      parentController = parentController.getParent();
    }
    return buffer.toString();
  }
}
