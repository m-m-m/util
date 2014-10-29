/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetField field widget} that represents an {@link Integer} slider field. Such
 * field allows to enter a value of the type {@link Integer} using a slider that can be moved. <br>
 * Here you can see an example (with {@link #setLabel(String) field label} "Ranking"):
 * 
 * <pre>
 * Ranking: <input type="range" min="0" max="100"/>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetIntegerSliderField extends UiWidgetRangeField<Integer>, UiWidgetNative {

  // nothing to add...

}
