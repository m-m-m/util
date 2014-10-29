/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;

/**
 * This is the interface for a {@link UiWidget widget} that presents a list of {@literal <ROW>} objects and
 * allows the end-user to select {@link #setSelectionMode(net.sf.mmm.client.ui.api.common.SelectionMode) one
 * or multiple} out of these objects. <br>
 * <b>ATTENTION:</b><br>
 * At this level of abstraction the {@link #getValue() value} can mean totally different things. The available
 * sub-interfaces override {@link #getValue()} to specialize the JavaDoc.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ROW> is the generic type of the object in each row of the list.
 */
public abstract interface UiWidgetListBase<ROW> extends UiWidgetListContainer<ROW>, UiFeatureSelectedValue<ROW> {

  // nothing to add...

}
