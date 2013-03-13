/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table;

import java.util.Comparator;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.widget.UiWidgetAtomic;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * TODO: this class ...
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetColumn<ROW, CELL> extends UiWidgetAtomic, AttributeWriteStringTitle {

  PropertyAccessor<ROW, CELL> getPropertyAccessor();

  Class<CELL> getValueType();

  // TODO externalize to separate interface...

  void setSortComparator(Comparator<CELL> comparator);

}
