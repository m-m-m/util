/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import java.util.List;

/**
 * This is the interface for a {@link UiWidgetRegular regular widget} that is or contains a
 * {@link #getValue() list of items} that can be {@link #asFeatureSelectedValue() selected}.
 *
 * @param <ITEM> is the generic type of the selectable
 *        {@link net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue#getSelectedValues() values} that are
 *        the actual rows in the {@link #getValue() list value}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetListContainer<ITEM> extends UiWidgetWithSelectedValue<ITEM>, UiWidgetWithValue<List<ITEM>> {

  // nothing to add...

}
