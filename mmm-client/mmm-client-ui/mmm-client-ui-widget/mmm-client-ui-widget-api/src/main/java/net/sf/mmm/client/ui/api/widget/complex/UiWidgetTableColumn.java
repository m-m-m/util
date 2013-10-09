/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteEditable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteReorderable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteResizable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSortComparator;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.validation.api.HasValueValidators;

/**
 * This is the interface for a virtual {@link UiWidget} that represents the
 * {@link UiWidgetAbstractDataTable#getColumn(int) column} of a {@link UiWidgetAbstractDataTable data table}.<br/>
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid. It should be a java-bean
 *        oriented object. Immutable objects (that have no setters) can also be used but only for read-only
 *        tables.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTableColumn<ROW, CELL> extends UiWidget, HasValueValidators<CELL>, AttributeWriteStringTitle,
    AttributeWriteEditable, AttributeWriteResizable, AttributeWriteReorderable, AttributeWriteSortComparator<CELL> {

  /**
   * This method sorts the {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable#getValue()
   * table-data} according to this column using the {@link #getSortComparator() sort-comparator} and the given
   * <code>order</code>.
   * 
   * @param order is the {@link SortOrder}.
   */
  void sort(SortOrder order);

}
