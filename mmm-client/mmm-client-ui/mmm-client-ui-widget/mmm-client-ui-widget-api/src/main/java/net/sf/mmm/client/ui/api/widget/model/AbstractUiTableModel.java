/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.model;

import java.util.Comparator;
import java.util.List;

import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.api.widget.table.UiWidgetListTable;
import net.sf.mmm.client.ui.api.widget.table.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.table.UiWidgetTreeTable;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.HasValueValidators;

/**
 * This is the abstract interface for the {@link UiModel model} of a {@link UiWidgetListTable} or
 * {@link UiWidgetTreeTable}.<br/>
 * Such model allows to {@link #createColumn(TypedProperty, UiSingleWidgetFactory, Comparator) create} and
 * {@link #setColumns(UiWidgetTableColumn...) set} the {@link UiWidgetTableColumn columns} of the table.<br/>
 * <b>Validation:</b><br/>
 * This model logically represents the template of a table-row and therefore allows to
 * {@link #addValidator(net.sf.mmm.util.validation.api.ValueValidator) add validators}, that validate an
 * individual row as an entire instance of {@literal <ROW>} after it has been edited. If such validator fails,
 * that entire row is marked as invalid. To validate a single cell, add validators to the according
 * {@link UiWidgetTableColumn column}. To validate the entire {@link List} or tree with all rows, add the
 * validator to the {@link UiWidgetListTable} or {@link UiWidgetTreeTable}.
 * 
 * @see UiWidgetListTable#getModel()
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid. It should be a java-bean
 *        oriented object. Immutable objects (that have no setters) can also be used but only for read-only
 *        tables.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AbstractUiTableModel<ROW> extends UiModel, HasValueValidators<ROW> {

  /**
   * This method creates a new {@link UiWidgetTableColumn column} for this table.
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
   * This method sets the {@link #getColumn(int) columns} for the table of this model.<br/>
   * <b>ATTENTION:</b><br/>
   * This method should typically be called only once during initialization of this model. Multiple calls of
   * this method for dynamic changes of the UI may NOT be completely supported by all underlying
   * implementations. We recommend to test your code with all relevant implementations before investing in
   * multiple dynamic changes.
   * 
   * @param columns are the {@link UiModelTableColumn columns} to set.
   */
  void setColumns(UiWidgetTableColumn<ROW, ?>... columns);

  /**
   * @see java.util.List#size()
   * 
   * @return the number of {@link #getColumn(int) columns} that are currently
   *         {@link #setColumns(UiWidgetTableColumn...) set}.
   */
  int getColumnCount();

  /**
   * This method gets the {@link UiModelTableColumn column} at the given <code>index</code>.
   * 
   * @see java.util.List#get(int)
   * 
   * @param index is the index of the requested {@link UiModelTableColumn column}. The index corresponds to
   *        the index when the columns have been {@link #setColumns(UiWidgetTableColumn...) set}. Reordering
   *        or hiding columns in the UI has no effect on the index. The value has to be in the range from
   *        <code>0</code> to <code>{@link #getColumnCount()} - 1</code>.
   * @return the requested {@link UiModelTableColumn column}.
   */
  UiModelTableColumn<ROW, ?> getColumn(int index);

}
