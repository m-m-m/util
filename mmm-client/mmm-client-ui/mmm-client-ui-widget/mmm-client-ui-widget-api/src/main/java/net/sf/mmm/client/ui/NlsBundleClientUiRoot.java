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

  /** @return the {@link NlsMessage} for the label to prompt for an image URL. */
  @NlsBundleMessage("Enter an image URL")
  NlsMessage labelEnterImageUrl();

  /** @return the {@link NlsMessage} for the label to prompt for a link URL. */
  @NlsBundleMessage("Enter a link URL")
  NlsMessage labelEnterLinkUrl();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#BOLD}.
   */
  @NlsBundleMessage("Bold")
  NlsMessage labelRichTextBold();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#ITALIC}.
   */
  @NlsBundleMessage("Italic")
  NlsMessage labelRichTextItalic();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#UNDERLINE}.
   */
  @NlsBundleMessage("Underline")
  NlsMessage labelRichTextUnderline();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#SUBSCRIPT}.
   */
  @NlsBundleMessage("Subscript")
  NlsMessage labelRichTextSubscript();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#SUPERSCRIPT}.
   */
  @NlsBundleMessage("Superscript")
  NlsMessage labelRichTextSuperscript();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#STRIKETHROUGH}.
   */
  @NlsBundleMessage("Strikethrough")
  NlsMessage labelRichTextStrikethrough();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#ALIGN_LEFT}.
   */
  @NlsBundleMessage("Align left")
  NlsMessage labelRichTextJustifyLeft();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#ALIGN_CENTER}.
   */
  @NlsBundleMessage("Align center")
  NlsMessage labelRichTextJustifyCenter();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#ALIGN_RIGHT}.
   */
  @NlsBundleMessage("Align Right")
  NlsMessage labelRichTextJustifyRight();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#ORDERED_LIST}.
   */
  @NlsBundleMessage("Insert ordered list")
  NlsMessage labelRichTextOrderedList();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#UNORDERED_LIST}.
   */
  @NlsBundleMessage("Insert unordered list")
  NlsMessage labelRichTextUnorderedList();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#INSERT_IMAGE}.
   */
  @NlsBundleMessage("Insert image")
  NlsMessage labelRichTextInsertImage();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#HORIZONTAL_LINE}.
   */
  @NlsBundleMessage("Horizontal line")
  NlsMessage labelRichTextHorizontalLine();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#INSERT_LINK}.
   */
  @NlsBundleMessage("Insert hyperlink")
  NlsMessage labelRichTextInsertLink();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#FONT_FAMILY}.
   */
  @NlsBundleMessage("Font family")
  NlsMessage labelRichTextFontFamily();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#FONT_SIZE}.
   */
  @NlsBundleMessage("Font size")
  NlsMessage labelRichTextFontSize();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#TEXT_COLOR}.
   */
  @NlsBundleMessage("Text color")
  NlsMessage labelRichTextFontColor();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#BACKGROUND_COLOR}.
   */
  @NlsBundleMessage("Background color")
  NlsMessage labelRichTextBackgroundColor();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#INDENT}.
   */
  @NlsBundleMessage("Indent")
  NlsMessage labelRichTextIndent();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#OUTDENT}.
   */
  @NlsBundleMessage("Outdent")
  NlsMessage labelRichTextOutdent();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#REMOVE_FORMAT}.
   */
  @NlsBundleMessage("Remove format")
  NlsMessage labelRichTextRemoveFormat();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#UNDO}.
   */
  @NlsBundleMessage("Undo last change")
  NlsMessage labelRichTextUndo();

  /**
   * @return the {@link NlsMessage} for the label of
   *         {@link net.sf.mmm.client.ui.api.widget.field.RichTextFeature#REDO}.
   */
  @NlsBundleMessage("Redo change")
  NlsMessage labelRichTextRedo();

}
