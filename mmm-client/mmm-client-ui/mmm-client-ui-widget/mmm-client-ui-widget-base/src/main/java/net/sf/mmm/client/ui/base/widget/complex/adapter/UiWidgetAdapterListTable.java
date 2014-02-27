/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex.adapter;

import net.sf.mmm.client.ui.base.widget.complex.ItemContainer;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable}.
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * @param <ITEM_CONTAINER> is the generic type of the {@link ItemContainer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterListTable<ROW, ITEM_CONTAINER extends ItemContainer<ROW, ?>> extends
    UiWidgetAdapterAbstractListTable<ROW, ITEM_CONTAINER> {

  // nothing to add...

}
