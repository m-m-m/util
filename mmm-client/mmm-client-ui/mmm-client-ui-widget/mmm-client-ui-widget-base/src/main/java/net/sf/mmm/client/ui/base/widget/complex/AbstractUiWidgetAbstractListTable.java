/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.ColumnContainerComparator;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractListTable;
import net.sf.mmm.util.lang.api.EqualsChecker;
import net.sf.mmm.util.lang.api.SortOrder;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractListTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractListTable<ADAPTER extends UiWidgetAdapterAbstractListTable<ROW>, ROW>
    extends AbstractUiWidgetAbstractDataTable<ADAPTER, ROW> implements UiWidgetAbstractListTable<ROW> {

  /** @see #getRowsInternal() */
  private final List<TableRowContainer<ROW>> rows;

  /** The {@link ColumnContainerComparator} for this table. */
  private final ColumnContainerComparator<ROW> comparator;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetAbstractListTable(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.rows = new ArrayList<TableRowContainer<ROW>>();
    this.comparator = new ColumnContainerComparator<ROW>();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected Class<List<ROW>> getValueClass() {

    return (Class) List.class;
  }

  /**
   * Returns the {@link List} with all rows of this list table. <br/>
   * <b>ATTENTION:</b><br/>
   * The result is an internal reference that shall not be passed to users as result of API methods.
   * 
   * @see net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable#getValue()
   * @see net.sf.mmm.client.ui.api.widget.complex.UiWidgetOptionListTable#getOptions()
   * 
   * @return the {@link List} with all rows of this list table.
   */
  List<TableRowContainer<ROW>> getRowsInternal() {

    return this.rows;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row) {

    TableRowContainer<ROW> rowContainer = createRowContainer(row);
    this.rows.add(rowContainer);
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().addRow(row);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row, int index) {

    TableRowContainer<ROW> rowContainer = createRowContainer(row);
    this.rows.add(index, rowContainer);
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().addRow(row, index);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TableRowContainer<ROW> getRowContainer(ROW row) {

    EqualsChecker<ROW> rowEqualsChecker = getRowEqualsChecker();
    for (TableRowContainer<ROW> rowContainer : this.rows) {
      if (rowEqualsChecker.isEqual(rowContainer.getValue(), row)) {
        return rowContainer;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeRow(ROW row) {

    TableRowContainer<ROW> rowContainer = getRowContainer(row);
    if (rowContainer == null) {
      return false;
    }
    this.rows.remove(rowContainer);
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().removeRow(row);
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRowIndex(ROW row) {

    EqualsChecker<ROW> equalsChecker = getRowEqualsChecker();
    for (int i = 0; i < this.rows.size(); i++) {
      if (equalsChecker.isEqual(row, this.rows.get(i).getValue())) {
        return i;
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean replaceRow(ROW oldRow, ROW newRow) {

    TableRowContainer<ROW> rowContainer = getRowContainer(oldRow);
    if (rowContainer == null) {
      return false;
    }
    Collection<TableRowContainer<ROW>> selection = getSelectedValuesInternal();
    boolean selected = selection.contains(rowContainer);
    if (selected) {
      selection.remove(rowContainer);
    }
    rowContainer.setValue(newRow);
    if (selected) {
      selection.add(rowContainer);
    }
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setRow(index, newRow);
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (!this.rows.isEmpty()) {
      // adapter.setRows(rows);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder) {

    this.comparator.sort(this.rows, column, sortOrder);
    super.sort(column, sortOrder);
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().updateRows();
    }
  }

}
