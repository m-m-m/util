/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHeightInTextLines;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteKeyboardFilter;

/**
 * This is the interface for a {@link UiWidgetInputField input field widget} that represents a text area
 * field. Such field is a multi-line variant of {@link UiWidgetTextField}. The user can enter line breaks by
 * hitting [enter] or [return].
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTextArea extends UiWidgetInputField<String>, AttributeWriteKeyboardFilter,
    AttributeWriteHeightInTextLines {

  // nothing to add...

}
