/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

/**
 * This is the interface for a {@link UiWidgetRadioButtons radio buttons widget} where the actual options
 * (radios) are aligned vertically from top to the bottom.<br/>
 * <b>ATTENTION:</b><br/>
 * You should only use this type of {@link UiWidgetOptionsField options field widgets} if you have a very
 * limited number of {@link #getOptions() options} (less than ten). Otherwise use {@link UiWidgetComboBox}
 * instead.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiWidgetRadioButtonsVertical<VALUE> extends UiWidgetOptionsField<VALUE> {

  // nothing to add

}
