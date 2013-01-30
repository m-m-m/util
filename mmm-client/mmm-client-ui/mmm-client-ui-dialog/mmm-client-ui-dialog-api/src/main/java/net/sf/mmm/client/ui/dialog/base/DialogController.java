/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.dialog.base;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.dialog.api.Dialog;
import net.sf.mmm.client.ui.dialog.api.DialogManager;
import net.sf.mmm.client.ui.dialog.api.DialogPlace;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectMismatchException;

/**
 * This is the abstract base class for the {@link AbstractDialogController controller} of a {@link Dialog}.
 * For each {@link net.sf.mmm.client.ui.dialog.api.ApplicationWindow} a single instance of
 * {@link DialogController} exists for each {@link Dialog} holding its state.
 * 
 * <pre>
 * public class PageDialogController extends {@link net.sf.mmm.client.ui.dialog.base.DialogController} {
 *
 *   public static final {@link net.sf.mmm.client.ui.dialog.base.DialogSlot} SLOT_MAIN = new {@link net.sf.mmm.client.ui.dialog.base.DialogSlot}({@link #DIALOG_ID_PAGE}, {@link #TYPE_PAGE});
 *
 *   ...
 *
   *   public String {@link #getId()} {
   *     return {@link #DIALOG_ID_PAGE};
   *   }
   *
   *   protected {@link net.sf.mmm.client.ui.dialog.base.DialogSlot} doShow({@link net.sf.mmm.client.ui.dialog.api.DialogPlace} place) {
   *     return {@link net.sf.mmm.client.ui.dialog.base.root.RootDialogController#SLOT_PAGE};
   *   }
   *
   *   protected void embed({@link net.sf.mmm.client.ui.dialog.base.DialogController}<?> subDialog, {@link net.sf.mmm.client.ui.dialog.base.DialogSlot} slot) {
   *     if (slot == SLOT_MAIN) {
   *       this.widgetMainSlot.setChild(subDialog.getView());
   *     } else if (slot == SLOT_...) {
   *       ...
   *     } else {
   *       super.embed(subDialog, slot);
   *     }
   *   }
   *
   * }
   * </pre>
 * 
 * 
 * @param <VIEW> is the generic type of the {@link #getView() view}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class DialogController<VIEW extends UiWidget> extends AbstractDialogController<VIEW> implements Dialog {

  /** @see #setSubDialog(DialogController, DialogSlot, DialogPlace) */
  private final Map<DialogSlot, DialogController<?>> slot2subDialogMap;

  /** @see #getDialogManager() */
  private AbstractDialogManager dialogManager;

  /** @see #setUiContext(UiContext) */
  private UiContext uiContext;

  /**
   * The parent {@link DialogController} or <code>null</code> for no parent (if this is the root
   * {@link DialogController} or not {@link #isVisible() opened}).
   */
  private DialogController<?> parent;

  /** @see #show(DialogPlace) */
  private DialogPlace place;

  /**
   * The constructor.
   */
  public DialogController() {

    super();
    this.slot2subDialogMap = new HashMap<DialogSlot, DialogController<?>>();
  }

  /**
   * This method sets the parent {@link DialogController} when embedding.
   * 
   * @param parent is the parent {@link DialogController} or <code>null</code> to reset.
   */
  void setParent(DialogController<?> parent) {

    if (this.parent != null) {
      this.parent.onRemoveChild(this);
    }
    this.parent = parent;
  }

  /**
   * This method notifies that the given {@link DialogController} has been removed as a sub-dialog.
   * 
   * @param subDialog is the child {@link DialogController} that has been removed.
   */
  protected void onRemoveChild(DialogController<?> subDialog) {

    // nothing to do by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void setVisible(boolean visible) {

    super.setVisible(visible);
    this.dialogManager.onShowDialog(this);
  }

  /**
   * @return the {@link DialogManager} instance.
   */
  protected DialogManager getDialogManager() {

    return this.dialogManager;
  }

  /**
   * @param dialogManager is the instance of {@link AbstractDialogManager} to use.
   */
  void setDialogManager(AbstractDialogManager dialogManager) {

    this.dialogManager = dialogManager;
  }

  /**
   * This method opens this dialog as triggered by the given {@link DialogPlace}.
   * 
   * @param dialogPlace is the {@link DialogPlace} {@link DialogPlace#getDialogId() identifying} this
   *        {@link DialogController} and providing potential {@link DialogPlace#getParameter(String)
   *        parameters}.
   */
  public final void show(DialogPlace dialogPlace) {

    NlsNullPointerException.checkNotNull(DialogPlace.class, dialogPlace);
    if (!getId().equals(dialogPlace.getDialogId())) {
      throw new ObjectMismatchException(dialogPlace.getDialogId(), getId(), DialogPlace.class.getSimpleName()
          + ".dialogId");
    }
    if (this.place == dialogPlace) {
      return;
    }
    showInternal(dialogPlace);
  }

  /**
   * 
   * @param dialogPlace is the {@link DialogPlace} {@link DialogPlace#getDialogId() pointing} to the
   *        <em>direct</em> dialog to open and containing potential {@link DialogPlace#getParameter(String)
   *        parameters}.
   * @return the {@link DialogSlot} {@link DialogSlot#getDialogId() identifying} the parent
   *         {@link DialogController} and its slot where to embed this {@link DialogController}.
   */
  private DialogSlot showInternal(DialogPlace dialogPlace) {

    DialogSlot slot = doShow(dialogPlace);
    NlsNullPointerException.checkNotNull(DialogSlot.class, slot);
    DialogController<?> parentDialog = this.dialogManager.getDialog(slot.getDialogId());
    // TODO prevent infinity loop...
    parentDialog.setSubDialog(this, slot, dialogPlace);
    setParent(parentDialog);
    setVisible(true);
    this.place = dialogPlace;
    return slot;
  }

  /**
   * This method gets called whenever this {@link DialogController} is to be shown. There are two cases to
   * distinguish:
   * <ul>
   * <li><b>direct</b><br/>
   * This {@link DialogController} is identified by the given {@link DialogPlace}. It is the leaf of the
   * {@link DialogController}-hierarchy that is to be displayed.</li>
   * <li><b>indirect</b><br/>
   * Because of a direct request to {@link #doShow(DialogPlace) show} a dialog, its parent (and ancestors)
   * will be shown that will embed their child dialog in the {@link DialogSlot} returned by this method.</li>
   * </ul>
   * If this is the {@link #TYPE_ROOT root} {@link DialogController} this method will never be called.
   * 
   * @param dialogPlace is the {@link DialogPlace} {@link DialogPlace#getDialogId() pointing} to the
   *        <em>direct</em> dialog to open and containing potential {@link DialogPlace#getParameter(String)
   *        parameters}.
   * @return the {@link DialogSlot} {@link DialogSlot#getDialogId() identifying} the parent
   *         {@link DialogController} and its slot where to embed this {@link DialogController}.
   */
  protected abstract DialogSlot doShow(DialogPlace dialogPlace);

  /**
   * This method embeds the given <code>subDialog</code> inside the given <code>slot</code> of this dialog.
   * The <code>subDialog</code> previously set in that slot is replaced with the new one.
   * 
   * @param subDialog is the {@link DialogController} of the sub-dialog to embed.
   * @param slot is the {@link DialogSlot} identifying the location in the {@link #getView() view} where the
   *        <code>subDialog</code> shall be embedded.
   * @param dialogPlace is the {@link DialogPlace} {@link DialogPlace#getDialogId() pointing} to the child (or
   *        descendant) dialog to embed.
   */
  final void setSubDialog(DialogController<?> subDialog, DialogSlot slot, DialogPlace dialogPlace) {

    NlsNullPointerException.checkNotNull(DialogController.class, subDialog);
    NlsNullPointerException.checkNotNull(DialogSlot.class, slot);
    if (!slot.getDialogId().equals(getId())) {
      throw new ObjectMismatchException(slot.getDialogId(), getId(), DialogSlot.class.getSimpleName() + ".dialogId");
    }

    DialogController<?> existingSubDialog = this.slot2subDialogMap.get(slot);
    if (existingSubDialog != subDialog) {
      if (existingSubDialog != null) {
        existingSubDialog.setParent(null);
        existingSubDialog.setVisible(false);
      }
      embed(subDialog, slot);
      this.slot2subDialogMap.put(slot, subDialog);
    }

    if (!isVisible()) {
      showInternal(dialogPlace);
    }
  }

  /**
   * This method has to be overridden with the custom logic to embed a given {@link DialogController} as
   * sub-dialog in a predefined slot. If this {@link DialogController} represents a leaf-dialog than can not
   * have sub-dialogs this method will be never called.<br/>
   * Here is a typical example:
   * 
   * <pre>
   * protected void embed({@link DialogController} subDialog, {@link DialogSlot} slot) {
   *   if (slot == SLOT_MAIN) {
   *     {@link #getView()}.getMainSlot().{@link net.sf.mmm.client.ui.api.widget.UiWidgetSingleMutableComposite#setChild(UiWidget)
   *     setChild}(subDialog.{@link #getView()});
   *   } else if (slot == SLOT_NAVIGATION) {
   *     ...
   *   } else {
   *     super.embed(subDialog, slot);
   *   }
   * }
   * </pre>
   * 
   * @param subDialog is the {@link DialogController} of the sub-dialog to embed.
   * @param slot is the {@link DialogSlot} identifying the location in the {@link #getView() view} where the
   *        <code>subDialog</code> shall be embedded.
   */
  protected void embed(DialogController<?> subDialog, DialogSlot slot) {

    throw new IllegalCaseException(slot.toString());
  }

  /**
   * @return the uiContext
   */
  public UiContext getUiContext() {

    return this.uiContext;
  }

  /**
   * @param uiContext is the uiContext to set
   */
  @Inject
  public void setUiContext(UiContext uiContext) {

    getInitializationState().requireNotInitilized();
    this.uiContext = uiContext;
  }

}
