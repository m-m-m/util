/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetListTable;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.complex.TableRowContainer;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractListTable;
import net.sf.mmm.client.ui.gwt.widgets.TableBody;
import net.sf.mmm.client.ui.gwt.widgets.TableCellAtomic;
import net.sf.mmm.client.ui.gwt.widgets.TableRow;
import net.sf.mmm.client.ui.impl.gwt.widget.complex.TableRowContainerGwt;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.SimpleCheckBox;

/**
 * This is the implementation of {@link UiWidgetAdapterAbstractListTable} using GWT.
 * 
 * @param <ROW> is the generic type of a {@link #addRow(TableRowContainer, int) row} in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtAbstractListTable<ROW> extends UiWidgetAdapterGwtAbstractDataTable<ROW> implements
    UiWidgetAdapterAbstractListTable<ROW> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtAbstractListTable() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(TableRowContainer<ROW> row, int index) {

    TableRowContainerGwt<ROW> gwtRowContainer = (TableRowContainerGwt<ROW>) row;
    TableRow tableRow = gwtRowContainer.getTableRow();
    if (tableRow == null) {
      tableRow = new TableRow();
      gwtRowContainer.setTableRow(tableRow);
      // add multiselection checkbox
      if (getSelectionMode() == SelectionMode.MULTIPLE_SELECTION) {
        SimpleCheckBox multiSelectCheckbox = gwtRowContainer.getMultiSelectCheckbox();
        tableRow.addCell(multiSelectCheckbox);
      }
      update(gwtRowContainer, false);
    } else {
      // update(gwtRowContainer, true);
    }
    // "index + 1" because we have an invisible row at the top to set the column width
    getTableWidget().getTableBody().insert(tableRow, index + 1);
  }

  /**
   * @param row is the {@link TableRowContainerGwt} where to add or update the cells.
   * @param update - <code>true</code> to update an already initialized <code>row</code>, <code>false</code>
   *        to initialize a newly created row.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void update(TableRowContainerGwt<ROW> row, boolean update) {

    TableRow tableRow = row.getTableRow();
    for (AbstractUiWidgetTableColumn<?, ROW, ?> column : getColumns()) {
      TableCellAtomic cellWidget;
      Object cellValue = column.getPropertyAccessor().getValue(row.getValue());
      UiWidgetWithValue<?> editWidget = getEditWidget(column, cellValue);
      if (update) {
        cellWidget = (TableCellAtomic) tableRow.getWidget(column.getCurrentIndex());
      } else {
        cellWidget = new TableCellAtomic();
        cellWidget.setStyleName(CssStyles.CELL);
        // cellWidget.setStyleName(editWidget.getStyles());
        // cellWidget.setStyleName(CssStyles.CELL);
        tableRow.add(cellWidget);
      }
      String displayValue = AbstractUiWidget.AccessHelper
          .convertValueToString((AbstractUiWidget) editWidget, cellValue);
      cellWidget.setText(displayValue);
    }
  }

  /**
   * This method gets the {@link UiWidgetAdapterGwtTableColumn#getEditWidget() edit widget}. If it does not
   * yet exist, it is lazily created and then set for further invocations.
   * 
   * @param column is the table column that stores the edit widget.
   * @param cellValue is the current value for a cell in the column. It may be used to interfere the type for
   *        the edit widget.
   * @return the edit widget.
   */
  private UiWidgetWithValue<?> getEditWidget(AbstractUiWidgetTableColumn<?, ROW, ?> column, Object cellValue) {

    UiWidgetAdapterGwtTableColumn columnAdapter = getWidgetAdapterForColumn(column);
    UiWidgetWithValue<?> editWidget = columnAdapter.getEditWidget();
    if (editWidget == null) {
      UiSingleWidgetFactory<? extends UiWidgetWithValue<?>> widgetFactory = column.getWidgetFactory();
      if (widgetFactory == null) {
        Class<?> cellType = determineCellType(column, cellValue);
        editWidget = getContext().getWidgetFactory().createForDatatype(cellType);
      } else {
        editWidget = widgetFactory.create(getContext());
      }
      columnAdapter.setEditWidget(editWidget);
    }
    return editWidget;
  }

  /**
   * Determines the type of the cell from <code>column</code> and <code>cellValue</code>.
   * 
   * @param column is the {@link AbstractUiWidgetTableColumn} specifying the cells in that column.
   * @param cellValue is the current value for the cell. May be <code>null</code>.
   * @return the type of the values in the cell.
   */
  private Class<?> determineCellType(AbstractUiWidgetTableColumn<?, ROW, ?> column, Object cellValue) {

    Class<?> cellType = null;
    TypedProperty<?> typedProperty = column.getTypedProperty();
    if (typedProperty != null) {
      cellType = typedProperty.getPropertyType();
    }
    if (cellType == null) {
      if (cellValue == null) {
        throw new IllegalStateException("Can not handle null values in column " + column.getId() + " of table "
            + getUiWidget().getId()
            + " as neither a TypedProperty nor a WidgetFactory has been provided when the column was created!");
      } else {
        cellType = cellValue.getClass();
      }
    }
    return cellType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeRow(TableRowContainer<ROW> row) {

    TableRowContainerGwt<ROW> gwtRow = (TableRowContainerGwt<ROW>) row;
    TableRow tableRow = gwtRow.getTableRow();
    getTableWidget().getTableBody().remove(tableRow);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder) {

    super.sort(column, sortOrder);
    List<TableRowContainer<ROW>> rows = getUiWidgetTyped().getRowsInternal();
    TableBody tableBody = getTableWidget().getTableBody();
    int index = 1;
    for (TableRowContainer<ROW> row : rows) {
      TableRow tableRow = (TableRow) tableBody.getWidget(index++);
      TableRowContainerGwt<ROW> gwtRow = (TableRowContainerGwt<ROW>) row;
      gwtRow.setTableRow(tableRow);
      update(gwtRow, true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUiWidgetListTable<?, ROW> getUiWidgetTyped() {

    return (AbstractUiWidgetListTable<?, ROW>) getUiWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ROW> getValue() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(List<ROW> value) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    // TODO Auto-generated method stub
    return null; // getTableWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    // TODO Auto-generated method stub
    return null;
  }

}
