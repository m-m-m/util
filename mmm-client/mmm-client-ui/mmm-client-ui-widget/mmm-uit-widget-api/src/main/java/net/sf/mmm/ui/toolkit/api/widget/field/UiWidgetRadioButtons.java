/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;

/**
 * This is the interface for a {@link UiWidgetOptionsField options field widget} that represents a a set of
 * <em>radio buttons</em>.<br/>
 * <b>ATTENTION:</b><br/>
 * You should only use this type of {@link UiWidgetOptionsField options field widgets} if you have a very
 * limited number of {@link #getOptions() options} (less than seven). Otherwise use {@link UiWidgetComboBox}
 * instead.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiWidgetRadioButtons<VALUE> extends UiWidgetOptionsField<VALUE>, UiWidgetReal {

  // nothing to add

}
