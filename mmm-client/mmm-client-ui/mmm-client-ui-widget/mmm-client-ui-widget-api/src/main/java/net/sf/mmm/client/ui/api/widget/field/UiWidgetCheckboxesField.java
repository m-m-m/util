/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import java.util.Set;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetOptionsBase;

/**
 * This is the interface for a {@link UiWidgetOptionsField options field widget} that represents a a set of
 * <em>radio buttons</em>. <br>
 * <b>ATTENTION:</b><br>
 * You should only use this type of {@link UiWidgetOptionsField options field widgets} if you have a very limited number
 * of {@link #getOptions() options} (less than seven). Otherwise use {@link UiWidgetComboboxField} instead. <br>
 * Here you can see an example (with the {@link #setOptions(java.util.List) options} set to {"single", "married",
 * "divorced"}):
 * 
 * <pre>
 * <span>
 * <input type="radio">single</input><input type="radio">married</input><input type="radio">divorced</input>
 * </span>
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiWidgetCheckboxesField<VALUE> extends UiWidgetField<Set<VALUE>>, UiWidgetOptionsBase<VALUE>,
    UiWidgetNative {

  // nothing to add

}
