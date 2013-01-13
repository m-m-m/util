/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.dialog.base;


/**
 * A {@link DialogSlot} represents a location within the view of a
 * {@link net.sf.mmm.client.ui.dialog.api.Dialog} where a sub-dialog can be embedded.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DialogSlot {

  /** @see #getDialogId() */
  private final String dialogId;

  /** @see #getName() */
  private final String name;

  /**
   * The constructor.
   * 
   * @param dialogId - see #getDialogId()
   * @param slotName - see {@link #getName()}.
   */
  public DialogSlot(String dialogId, String slotName) {

    super();
    this.dialogId = dialogId;
    this.name = slotName;
  }

  /**
   * This method gets the {@link net.sf.mmm.client.ui.dialog.api.Dialog#getId() ID} of the
   * {@link net.sf.mmm.client.ui.dialog.api.Dialog} identified by this {@link DialogSlot}.
   * 
   * @return the {@link net.sf.mmm.client.ui.dialog.api.Dialog#getId() dialog-id}.
   */
  public String getDialogId() {

    return this.dialogId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean equals(Object obj) {

    return super.equals(obj);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int hashCode() {

    return super.hashCode();
  }

  /**
   * This method gets the name of this slot. The name is supposed to be used for debugging purposes and will
   * also be used by {@link #toString()}.
   * 
   * @return the name of this slot.
   */
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Slot:" + this.name + "@" + this.dialogId;
  }
}
