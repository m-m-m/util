/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.grid;

import java.util.Comparator;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteEditable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the interface for a descriptor that specifies a column of a grid-widget such as
 * {@link UiWidgetListGrid}.
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface GridColumn<ROW, CELL> extends AttributeWriteStringTitle, AttributeWriteEditable {

  PropertyAccessor<ROW, CELL> getPropertyAccessor();

  Comparator<CELL> getSortComparator();

  UiWidgetWithValue<CELL> createCellWidget(UiMode mode);

}
