/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import java.util.Comparator;

import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract interface for a {@link UiWidgetAbstractDataTable data table widget} that represents a
 * <em>list table</em>.<br/>
 * <b>Note:</b><br/>
 * There are the following variants of this abstract list table widget:
 * <ul>
 * <li>{@link UiWidgetListTable}</li>
 * <li>{@link UiWidgetOptionListTable}</li>
 * </ul>
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAbstractListTable<ROW> extends UiWidgetAbstractDataTable<ROW>, UiWidgetListBase<ROW> {

  /**
   * This method creates a new {@link UiWidgetTableColumn column} for this table.
   * 
   * @param <CELL> is the generic type of the {@link TypedProperty#getPropertyType() property type}.
   * @param rowProperty is the {@link TypedProperty} identifying which {@link TypedProperty#getPojoPath() property} of
   *        {@literal <ROW>} to show in the column.
   * @param widgetFactory is the factory to {@link UiSingleWidgetFactory#create(net.sf.mmm.client.ui.api.UiContext)
   *        create} widgets for this column. <b>ATTENTION:</b> These widgets might be reused for performance-reasons for
   *        a different row just by {@link UiWidgetWithValue#setValue(Object) setting its value}. They should NOT
   *        contain additional state information (what is generally a bad idea). This parameter may be <code>null</code>
   *        to fall back to {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createForDatatype(Class) datatype
   *        based creation}.
   * @param sortComparator is the {@link UiWidgetTableColumn#setSortComparator(Comparator) sort-comparator to set}.
   * @return a new {@link UiWidgetTableColumn}.
   */
  @Override
  <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(TypedProperty<CELL> rowProperty,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator);

  /**
   * This method creates a new {@link UiWidgetTableColumn column} for this table.
   * 
   * @param <CELL> is the generic type of the {@link PropertyAccessor#getValue(Object) property value}.
   * @param rowAccessor is the {@link PropertyAccessor} to {@link PropertyAccessor#getValue(Object) access} the property
   *        of {@literal <ROW>} to show in the column.
   * @param widgetFactory is the factory to {@link UiSingleWidgetFactory#create(net.sf.mmm.client.ui.api.UiContext)
   *        create} widgets for this column. <b>ATTENTION:</b> These widgets might be reused for performance-reasons for
   *        a different row just by {@link UiWidgetWithValue#setValue(Object) setting its value}. They should NOT
   *        contain additional state information (what is generally a bad idea). This parameter may be <code>null</code>
   *        to fall back to {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createForDatatype(Class) datatype
   *        based creation}.
   * @param sortComparator is the {@link UiWidgetTableColumn#setSortComparator(Comparator) sort-comparator to set}.
   * @return a new {@link UiWidgetTableColumn}.
   */
  @Override
  <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(PropertyAccessor<ROW, CELL> rowAccessor,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator);

  /**
   * This method sets the {@link #getColumn(int) columns} for the table of this model.<br/>
   * <b>ATTENTION:</b><br/>
   * This method should typically be called only once during initialization of this model. Multiple calls of this method
   * for dynamic changes of the UI may NOT be completely supported by all underlying implementations. We recommend to
   * test your code with all relevant implementations before investing in multiple dynamic changes.
   * 
   * @param columns are the {@link UiWidgetTableColumn columns} to set.
   */
  @SuppressWarnings("unchecked")
  @Override
  void setColumns(UiWidgetTableColumn<ROW, ?>... columns);

  /**
   * @see java.util.List#size()
   * 
   * @return the number of {@link #getColumn(int) columns} that are currently
   *         {@link #setColumns(UiWidgetTableColumn...) set}.
   */
  @Override
  int getColumnCount();

  /**
   * This method gets the {@link UiWidgetTableColumn column} at the given <code>index</code>.
   * 
   * @see java.util.List#get(int)
   * 
   * @param index is the index of the requested {@link UiWidgetTableColumn column}. The index corresponds to the index
   *        when the columns have been {@link #setColumns(UiWidgetTableColumn...) set}. Reordering or hiding columns in
   *        the UI has no effect on the index. The value has to be in the range from <code>0</code> to
   *        <code>{@link #getColumnCount()} - 1</code>.
   * @return the requested {@link UiWidgetTableColumn column}.
   */
  @Override
  UiWidgetTableColumn<ROW, ?> getColumn(int index);

}
