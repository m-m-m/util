/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.dialog.base;

import java.util.HashMap;
import java.util.Map;

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
   * @param dialogPlace is the {@link DialogPlace} identifying this {@link DialogController} and providing
   *        potential {@link DialogPlace#getParameter(String) parameters}.
   * @return the {@link DialogSlot} where this {@link DialogController} should be embedded or
   *         <code>null</code> if this is the root {@link DialogController} that gets embedded into the top of
   *         the {@link net.sf.mmm.client.ui.dialog.api.ApplicationWindow}.
   */
  public final DialogSlot show(DialogPlace dialogPlace) {

    NlsNullPointerException.checkNotNull(DialogPlace.class, dialogPlace);
    if (!getId().equals(dialogPlace.getDialogId())) {
      throw new ObjectMismatchException(dialogPlace.getDialogId(), getId(), DialogPlace.class.getSimpleName()
          + ".dialogId");
    }
    // if (this.place == dialogPlace) {
    // return null;
    // }
    return showInternal(dialogPlace);
  }

  private DialogSlot showInternal(DialogPlace dialogPlace) {

    DialogSlot slot = doShow(dialogPlace);
    DialogController<?> parentDialog = null;
    if (slot == null) {
      // TODO put view into root page...
      throw new IllegalStateException("not yet implemented");
    } else {
      // TODO prevent infinity loop...
      parentDialog = (DialogController<?>) getDialogManager().getDialog(slot.getDialogId());
      parentDialog.setSubDialog(this, slot, dialogPlace);
    }
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

}
