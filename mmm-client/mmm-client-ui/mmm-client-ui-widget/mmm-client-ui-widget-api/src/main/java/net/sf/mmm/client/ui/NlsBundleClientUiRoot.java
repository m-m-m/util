/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleLocation;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This class holds the {@link NlsBundle internationalized messages} for this module.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@NlsBundleLocation(bundleName = "NlsBundleClientUi")
public interface NlsBundleClientUiRoot extends NlsBundle {

  /**
   * @return the {@link NlsMessage} for the label "Save".
   */
  @NlsBundleMessage("Save")
  NlsMessage labelSave();

  /**
   * @return the {@link NlsMessage} for the label "Cancel".
   */
  @NlsBundleMessage("Cancel")
  NlsMessage labelCancel();

  /**
   * @return the {@link NlsMessage} for the label "Delete".
   */
  @NlsBundleMessage("Delete")
  NlsMessage labelDelete();

  /**
   * @return the {@link NlsMessage} for the label "Remove".
   */
  @NlsBundleMessage("Remove")
  NlsMessage labelRemove();

  /**
   * @return the {@link NlsMessage} for the label "Approve".
   */
  @NlsBundleMessage("Approve")
  NlsMessage labelApprove();

  /**
   * @return the {@link NlsMessage} for the label "Confirm".
   */
  @NlsBundleMessage("Confirm")
  NlsMessage labelConfirm();

  /**
   * @return the {@link NlsMessage} for the label "Submit".
   */
  @NlsBundleMessage("Submit")
  NlsMessage labelSubmit();

  /**
   * @return the {@link NlsMessage} for the label "Open".
   */
  @NlsBundleMessage("Open")
  NlsMessage labelOpen();

  /**
   * @return the {@link NlsMessage} for the label to start editing ("Edit").
   */
  @NlsBundleMessage("Edit")
  NlsMessage labelStartEdit();

  /**
   * @return the {@link NlsMessage} for the label to stop editing ("Revert").
   */
  @NlsBundleMessage("Revert")
  NlsMessage labelStopEdit();

  /**
   * @return the {@link NlsMessage} for the tooltip "Next".
   */
  @NlsBundleMessage("Step to next item")
  NlsMessage tooltipNext();

  /**
   * @return the {@link NlsMessage} for the tooltip "Previous".
   */
  @NlsBundleMessage("Step to previous item")
  NlsMessage tooltipPrevious();

  /**
   * @return the {@link NlsMessage} for the label "Up".
   */
  @NlsBundleMessage("Up")
  NlsMessage labelUp();

  /**
   * @return the {@link NlsMessage} for the label "Down".
   */
  @NlsBundleMessage("Down")
  NlsMessage labelDown();

  /**
   * @return the {@link NlsMessage} for the message of the popup to confirm a deletion.
   */
  @NlsBundleMessage("Do you really want to delete?")
  NlsMessage messageConfirmDelete();

  /**
   * @return the {@link NlsMessage} for the message of the popup to confirm a deletion.
   */
  @NlsBundleMessage("There are unsaved changes. Do you really want to cancel and discard all changes?")
  NlsMessage messageConfirmStopEdit();

  /**
   * @return the {@link NlsMessage} for the title of the popup to confirm the discard of changes.
   */
  @NlsBundleMessage("Discard?")
  NlsMessage titleDiscardPopup();

  /**
   * @return the {@link NlsMessage} for the title of the popup to confirm a deletion.
   */
  @NlsBundleMessage("Delete?")
  NlsMessage titleDeletePopup();

  /**
   * @return the {@link NlsMessage} for the message of the popup to confirm a deletion.
   */
  @NlsBundleMessage("Do you really want to remove?")
  NlsMessage messageConfirmRemove();

  /**
   * @return the {@link NlsMessage} for the title of the popup to confirm a deletion.
   */
  @NlsBundleMessage("Remove?")
  NlsMessage titleRemovePopup();

  /**
   * @return the {@link NlsMessage} for the tooltip of a widget to
   *         {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed#setCollapsed(boolean) collapse}
   *         content.
   */
  @NlsBundleMessage("Collapse content")
  NlsMessage tooltipCollapse();

  /**
   * @return the {@link NlsMessage} for the tooltip of a widget to
   *         {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed#setCollapsed(boolean) expand}
   *         content.
   */
  @NlsBundleMessage("Expand content")
  NlsMessage tooltipExpand();

}
