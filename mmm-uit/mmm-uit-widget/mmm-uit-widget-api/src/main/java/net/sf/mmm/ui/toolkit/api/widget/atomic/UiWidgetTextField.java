/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteKeyboardFilter;

/**
 * This is the interface for a {@link UiWidgetInputField input field widget} that represents a text field.
 * Such field allows to enter arbitrary text in a single line. If you want to allow the user to enter text
 * with line breaks (new lines) use {@link UiWidgetTextArea} instead.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTextField extends UiWidgetInputField<String>, AttributeWriteKeyboardFilter {

  // nothing to add...

}
