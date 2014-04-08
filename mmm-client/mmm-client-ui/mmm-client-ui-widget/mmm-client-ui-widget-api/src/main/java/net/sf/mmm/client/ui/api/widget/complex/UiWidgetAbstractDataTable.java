/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import java.util.Comparator;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract interface for a {@link UiWidgetAbstractDataSet data set widget} that represents a
 * <em>data table</em>. That is a table showing rows of data with their attributes in columns. It has the
 * following features:
 * <ul>
 * <li>Configured via its {@link #setColumns(UiWidgetTableColumn...) columns}.</li>
 * <li>Each column defines attributes like {@link UiWidgetTableColumn#getId() ID},
 * {@link UiWidgetTableColumn#getTitle() title}, and {@link UiWidgetTableColumn#getTooltip() tooltip} that
 * will be used for the header of the column.</li>
 * <li>Scrolling of the table rows as needed while header (and potential footer) remains steady.</li>
 * <li>{@link UiWidgetTableColumn columns} can be
 * {@link UiWidgetTableColumn#sort(net.sf.mmm.util.lang.api.SortOrder) sorted} using
 * {@link UiWidgetTableColumn#setSortComparator(Comparator) custom comparators} by clicking the column header.
 * Clicking again will swap between ascending and descending order what is also visualized by an arrow icon.</li>
 * <li>{@link UiWidgetTableColumn columns} can be {@link UiWidgetTableColumn#isResizable() resized} by
 * clicking to the right of the column header and moving the right column border horizontally.</li>
 * <li>{@link UiWidgetTableColumn columns} can be {@link UiWidgetTableColumn#isReorderable() reordered} by
 * dragging them around.</li>
 * <li>{@link UiWidgetTableColumn columns} can be {@link UiWidgetTableColumn#isEditable() edited} by
 * double-clicking on cells or programmatically.</li>
 * <li>...</li>
 * </ul>
 * <br/>
 * <b>Note:</b><br/>
 * There are the following variants of this abstract list table widget:
 * <ul>
 * <li>{@link UiWidgetListTable}</li>
 * <li>{@link UiWidgetOptionListTable}</li>
 * <li>{@link UiWidgetTreeTable}</li>
 * </ul>
 * 
 * @param <ROW> is the generic type of a row in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAbstractDataTable<ROW> extends UiWidgetAbstractDataSet<ROW> {

  /** The default {@link #hasStyle(String) style} of this data table widget. */
  String STYLE_DATA_TABLE = CssStyles.DATA_TABLE;

  /**
   * This method creates a new {@link UiWidgetTableColumn column} for this table. <br/>
   * <b>ATTENTION:</b><br/>
   * The column is not automatically added to the table. You need to call
   * {@link #setColumns(UiWidgetTableColumn...)} for all columns that should appear in the UI.
   * 
   * @param <CELL> is the generic type of the {@link TypedProperty#getPropertyType() property type}.
   * @param rowProperty is the {@link TypedProperty} identifying which {@link TypedProperty#getPojoPath()
   *        property} of {@literal <ROW>} to show in the column.
   * @param widgetFactory is the factory to
   *        {@link UiSingleWidgetFactory#create(net.sf.mmm.client.ui.api.UiContext) create} widgets for this
   *        column. <b>ATTENTION:</b> These widgets might be reused for performance-reasons for a different
   *        row just by {@link UiWidgetWithValue#setValue(Object) setting its value}. They should NOT contain
   *        additional state information (what is generally a bad idea). This parameter may be
   *        <code>null</code> to fall back to
   *        {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createForDatatype(Class) datatype based
   *        creation}.
   * @param sortComparator is the {@link UiWidgetTableColumn#setSortComparator(Comparator) sort-comparator to
   *        set}.
   * @return a new {@link UiWidgetTableColumn}.
   */
  <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(TypedProperty<CELL> rowProperty,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator);

  /**
   * This method creates a new {@link UiWidgetTableColumn column} for this table.<br/>
   * <b>ATTENTION:</b><br/>
   * The column is not automatically added to the table. You need to call
   * {@link #setColumns(UiWidgetTableColumn...)} for all columns that should appear in the UI.
   * 
   * @param <CELL> is the generic type of the {@link PropertyAccessor#getValue(Object) property value}.
   * @param rowAccessor is the {@link PropertyAccessor} to {@link PropertyAccessor#getValue(Object) access}
   *        the property of {@literal <ROW>} to show in the column.
   * @param widgetFactory is the factory to
   *        {@link UiSingleWidgetFactory#create(net.sf.mmm.client.ui.api.UiContext) create} widgets for this
   *        column. <b>ATTENTION:</b> These widgets might be reused for performance-reasons for a different
   *        row just by {@link UiWidgetWithValue#setValue(Object) setting its value}. They should NOT contain
   *        additional state information (what is generally a bad idea). This parameter may be
   *        <code>null</code> to fall back to
   *        {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createForDatatype(Class) datatype based
   *        creation}.
   * @param sortComparator is the {@link UiWidgetTableColumn#setSortComparator(Comparator) sort-comparator to
   *        set}.
   * @return a new {@link UiWidgetTableColumn}.
   */
  <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(PropertyAccessor<ROW, CELL> rowAccessor,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator);

  /**
   * This method sets the {@link #getColumn(int) columns} for the table of this model.<br/>
   * <b>ATTENTION:</b><br/>
   * This method should typically be called only once during initialization of this table. Multiple calls of
   * this method for dynamic changes of the UI may NOT be completely supported by all underlying
   * implementations. We recommend to test your code with all relevant implementations before investing in
   * multiple dynamic changes. Consider {@link UiWidgetTableColumn#setVisible(boolean)} instead if possible.
   * 
   * @see #createColumn(TypedProperty, UiSingleWidgetFactory, Comparator)
   * @see #createColumn(PropertyAccessor, UiSingleWidgetFactory, Comparator)
   * 
   * @param columns are the {@link UiWidgetTableColumn columns} to set. Use <code>createColumn</code> to
   *        create before.
   */
  @SuppressWarnings("unchecked")
  void setColumns(UiWidgetTableColumn<ROW, ?>... columns);

  /**
   * @see java.util.List#size()
   * 
   * @return the number of {@link #getColumn(int) columns} that are currently
   *         {@link #setColumns(UiWidgetTableColumn...) set}.
   */
  int getColumnCount();

  /**
   * This method gets the {@link UiWidgetTableColumn column} at the given <code>index</code>.
   * 
   * @see java.util.List#get(int)
   * 
   * @param index is the index of the requested {@link UiWidgetTableColumn column}. The index corresponds to
   *        the index when the columns have been {@link #setColumns(UiWidgetTableColumn...) set}. Reordering
   *        or hiding columns in the UI has no effect on the index. The value has to be in the range from
   *        <code>0</code> to <code>{@link #getColumnCount()} - 1</code>.
   * @return the requested {@link UiWidgetTableColumn column}.
   */
  UiWidgetTableColumn<ROW, ?> getColumn(int index);

  /**
   * Gets implicit {@link UiWidgetTableColumn column} that shows the checkboxes or radios for the selection of
   * a row.<br/>
   * <b>ATTENTION:</b><br/>
   * This is available for advanced flexibility. Please avoid changing
   * {@link UiWidgetTableColumn#setTitle(String) title}, {@link UiWidgetTableColumn#setReorderable(boolean)
   * reorderable}, {@link UiWidgetTableColumn#setResizable(boolean) resizable}, or
   * {@link UiWidgetTableColumn#setSortable(boolean) sortable}.
   * 
   * @return the implicit {@link UiWidgetTableColumn column} that shows the checkboxes or radios for the
   *         selection of a row.
   */
  UiWidgetTableColumn<?, Boolean> getSelectionColumn();

  /**
   * Gets implicit {@link UiWidgetTableColumn column} that shows the row-numbers.<br/>
   * <b>ATTENTION:</b><br/>
   * This is available for advanced flexibility. Please avoid changing
   * {@link UiWidgetTableColumn#setTitle(String) title}, {@link UiWidgetTableColumn#setReorderable(boolean)
   * reorderable}, {@link UiWidgetTableColumn#setResizable(boolean) resizable}, or
   * {@link UiWidgetTableColumn#setSortable(boolean) sortable}.<br/>
   * This column may be lazily created. Only use if you want to
   * {@link UiWidgetTableColumn#setVisible(boolean) make it visible}.
   * 
   * @return the implicit {@link UiWidgetTableColumn column} that shows the row-numbers.
   */
  UiWidgetTableColumn<?, Integer> getRowNumberColumn();

  /**
   * This method gets the {@link UiWidgetTableColumn column} with the given <code>columnId</code>.
   * 
   * @param columnId is the {@link UiWidgetTableColumn#getId() ID} of the requested column.
   * @param required - if <code>true</code> and the requested column does not exist, an exception is thrown,
   *        <code>false</code> otherwise (<code>null</code> will be returned for non existent columns).
   * @return the requested {@link UiWidgetTableColumn column} or <code>null</code> if no such column exists
   *         and <code>required</code> is <code>false</code>.
   * @throws ObjectNotFoundException if no column exists with the given <code>columnId</code> and
   *         <code>required</code> is <code>true</code>.
   */
  UiWidgetTableColumn<ROW, ?> getColumnById(String columnId, boolean required) throws ObjectNotFoundException;

}
