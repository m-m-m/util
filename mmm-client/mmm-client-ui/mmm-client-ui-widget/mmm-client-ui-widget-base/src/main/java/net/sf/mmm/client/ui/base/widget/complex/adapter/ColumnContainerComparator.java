/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex.adapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetTableColumn;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * A {@link Comparator} to {@link List} with objects containing a {@literal <ROW>} by a specific
 * {@link AbstractUiWidgetTableColumn column}.
 * 
 * @param <ROW> is the generic type of a row in the list.
 * 
 * @see AbstractUiWidgetTableColumn#sort(SortOrder)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ColumnContainerComparator<ROW> implements Comparator<AttributeReadValue<ROW>> {

  /** The current column to sort by. */
  private AbstractUiWidgetTableColumn<?, ROW, ?> column;

  /** The {@link SortOrder} to sort by. */
  private SortOrder sortOrder;

  /**
   * The constructor.
   */
  public ColumnContainerComparator() {

    super();
  }

  /**
   * Sorts the given <code>rows</code> using the specified <code>column</code> and {@link SortOrder}.
   * 
   * @param rows is the {@link List} of rows to sort.
   * @param sortColumn the {@link AbstractUiWidgetTableColumn column} to sort by.
   * @param order the {@link SortOrder}.
   */
  public void sort(List<? extends AttributeReadValue<ROW>> rows, AbstractUiWidgetTableColumn<?, ROW, ?> sortColumn,
      SortOrder order) {

    this.column = sortColumn;
    this.sortOrder = order;
    Collections.sort(rows, this);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public int compare(AttributeReadValue<ROW> row1, AttributeReadValue<ROW> row2) {

    Comparator sortComparator = this.column.getSortComparator();
    Object cell1 = this.column.getPropertyAccessor().getValue(row1.getValue());
    Object cell2 = this.column.getPropertyAccessor().getValue(row2.getValue());
    int result;
    if (cell1 == cell2) {
      result = 0;
    } else {
      if (sortComparator == null) {
        if (cell1 == null) {
          result = -1;
        } else if (cell2 == null) {
          result = 1;
        } else {
          result = ((Comparable) cell1).compareTo(cell2);
        }
      } else {
        result = sortComparator.compare(cell1, cell2);
      }
    }
    return this.sortOrder.adjustSignum(result);
  }
}
