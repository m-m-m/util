/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table;

import java.util.Comparator;

import net.sf.mmm.client.ui.api.attribute.AttributeReadEditable;
import net.sf.mmm.client.ui.api.attribute.AttributeReadHtmlId;
import net.sf.mmm.client.ui.api.attribute.AttributeReadStringTitle;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the interface for a descriptor that specifies a column of a table-widget such as
 * {@link UiWidgetListTable}.
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid. It should be a java-bean
 *        oriented object. Immutable objects (that have no setters) can also be used but only for read-only
 *        tables.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiModelTableColumn<ROW, CELL> extends AttributeReadHtmlId, AttributeReadStringTitle, AttributeReadEditable {

  /**
   * This method gets the {@link PropertyAccessor} used to {@link PropertyAccessor#getValue(Object) get} and
   * {@link PropertyAccessor#setValue(Object, Object) set} the value.
   * 
   * @return the {@link PropertyAccessor} for the &lt;CELL&gt;.
   */
  PropertyAccessor<ROW, CELL> getPropertyAccessor();

  /**
   * This method gets the {@link Comparator} used to {@link Comparator#compare(Object, Object) compare} and
   * sort the values of this column. If &lt;CELL&gt; implements {@link Comparable} the {@link Comparator}
   * should typically just delegate to {@link Comparable#compareTo(Object)} (unless a more specific sorting is
   * required here).
   * 
   * @return the {@link Comparator} used to sort the values. May be <code>null</code> what will disable the
   *         sorting of this column.
   */
  Comparator<CELL> getSortComparator();

  /**
   * This method creates a new {@link UiWidgetWithValue widget} to render the cell.<br/>
   * <b>ATTENTION:</b><br/>
   * For performance reason these cell widgets might be reused for cells in different rows but the same
   * column. So e.g. if a row gets deleted and a new row is added the widgets of the deleted row may be reused
   * by {@link UiWidgetWithValue#setValue(Object) setting new values}. Also sorting may work this way so that
   * the cell widgets remain in their rows but only the values change. Therefore it is important that you do
   * NOT store additional state information in your widgets (including validators, etc.).
   * 
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetFactoryDatatype#createForDatatype(Class)
   * 
   * @return the new {@link UiWidgetWithValue widget}.
   */
  UiWidgetWithValue<CELL> createCellWidget();

  /**
   * This method creates a new, empty row. This method is only used if the table-widget is editable and
   * supports adding new rows.<br/>
   * <b>NOTE:</b><br/>
   * It is then assumed that &lt;ROW&gt; is a java-bean oriented object that can be modified via the
   * {@link #getPropertyAccessor() property accessor}. In other words inline table-editing of immutable
   * objects that require all attributes (cell-values) at construction are (currently) NOT supported.
   * 
   * @return the new row instance.
   */
  ROW createNewRow();

}
