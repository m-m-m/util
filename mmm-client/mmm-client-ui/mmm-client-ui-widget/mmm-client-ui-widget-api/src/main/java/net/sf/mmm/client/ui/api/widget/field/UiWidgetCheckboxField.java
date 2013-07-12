/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetField field widget} that represents a <em>checkbox</em>. A
 * checkbox allows to edit a boolean flag by showing a box that can be checked or unchecked by clicking on it
 * with a {@link #getLabel() label} that explains the meaning of the flag.<br/>
 * Here you can see an example (with {@link #setLabel(String) label} "check me"):
 * 
 * <pre>
 * <input type="checkbox">check me</input>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetCheckboxField extends UiWidgetField<Boolean>, AttributeWriteLabel, UiWidgetNative {

  // nothing to add

}
