/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetTextualInputField input field widget} that represents a text
 * area field. Such field is a multi-line variant of {@link UiWidgetTextField}. The user can enter line breaks
 * by hitting [enter] or [return]. <br>
 * Here you can see an example (with {@link #setLabel(String) field label} "Comment" and
 * {@link #setValue(String) value} set to "some text"):
 * 
 * <pre>
 * Comment: <textarea>some text</textarea>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTextAreaField extends UiWidgetTextualInputField<String>, AttributeWriteKeyboardFilter,
    AttributeWriteHeightInRows, UiWidgetNative {

  // nothing to add...

}
