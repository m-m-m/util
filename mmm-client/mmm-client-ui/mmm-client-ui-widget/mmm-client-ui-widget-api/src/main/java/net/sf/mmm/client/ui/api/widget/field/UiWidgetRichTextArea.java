/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;

/**
 * This is the interface for a {@link UiWidgetTextualInputField input field widget} that represents a rich text area
 * field. Such field is like {@link UiWidgetTextArea} but allows to enter rich text with markup. It should
 * support the user with a nice toolbar that allows to apply styles to the selected text (e.g. bold, italic,
 * blockquote, etc.). It should also support unordered and ordered lists as well as headings (at least four
 * levels). Also insertion of hyperlinks and images should be possible.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetRichTextArea extends UiWidgetTextualInputField<String>, AttributeWriteKeyboardFilter,
    AttributeWriteHeightInRows, UiWidgetReal {

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * This method will accept markup (html-fragment). Be careful to prevent XSS attacks by only providing
   * verified markup. This can be achieved in the following ways:
   * <ul>
   * <li>The value was entered using a {@link UiWidgetRichTextArea} (and NOT any other widget) in the same
   * client instance.</li>
   * <li>The value was received from a trusted server on a reliable path (using TLS) and that server strictly
   * validates all markup.</li>
   * <li>The value itself was verified to contain safe HTML or escaped to do so in the client before calling
   * this method (safest way).</li>
   * </ul>
   * It is generally forbidden that the value contains JavasSript or CSS.<br/>
   * TODO: It would be nice to have a way that this method does the latter strategy automatically.
   */
  @Override
  void setValue(String value);
}
