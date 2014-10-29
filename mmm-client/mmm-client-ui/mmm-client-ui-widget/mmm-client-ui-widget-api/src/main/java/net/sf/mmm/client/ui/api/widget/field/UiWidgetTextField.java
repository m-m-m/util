/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetTextualInputField input field widget} that represents a text
 * field. Such field allows to enter arbitrary text in a single line. If you want to allow the user to enter
 * text with line breaks (new lines) use {@link UiWidgetTextAreaField} instead. <br>
 * Here you can see an example (with {@link #setLabel(String) field label} "Name"):
 * 
 * <pre>
 * Name: <input type="text"/>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTextField extends UiWidgetTextualInputField<String>, AttributeWriteKeyboardFilter,
    UiWidgetNative {

  // nothing to add...

}
