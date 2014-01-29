/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.widget.complex.TableRowContainer;
import net.sf.mmm.client.ui.base.widget.complex.UiWidgetTableColumnImpl;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractListTable;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.TableRow;
import net.sf.mmm.client.ui.impl.gwt.widget.complex.TableRowContainerGwt;
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
  @SuppressWarnings("unchecked")
  @Override
  public void addRow(TableRowContainer<ROW> row, int index) {

    TableRowContainerGwt<ROW> gwtRow = (TableRowContainerGwt<ROW>) row;
    TableRow tableRow = gwtRow.getTableRow();
    if (tableRow == null) {
      tableRow = new TableRow();
      gwtRow.setTableRow(tableRow);
      // add multiselection checkbox
      if (getSelectionMode() == SelectionMode.MULTIPLE_SELECTION) {
        SimpleCheckBox multiSelectCheckbox = gwtRow.getMultiSelectCheckbox();
        tableRow.addCell(multiSelectCheckbox);
      }
      for (UiWidgetTableColumnImpl<ROW, ?> column : getColumns()) {
        UiSingleWidgetFactory<? extends UiWidgetWithValue<?>> widgetFactory = column.getWidgetFactory();
        @SuppressWarnings("rawtypes")
        UiWidgetWithValue cellWidget;
        Object cellValue = column.getPropertyAccessor().getValue(row.getValue());
        if (widgetFactory == null) {
          Class<?> cellType = determineCellType(column, cellValue);
          cellWidget = getContext().getWidgetFactory().createForDatatype(cellType);
        } else {
          cellWidget = widgetFactory.create(getContext());
        }
        if (cellWidget instanceof UiWidgetField) {
          ((UiWidgetField<?>) cellWidget).setViewOnly();
        }
        cellWidget.setMode(UiMode.VIEW);
        cellWidget.setValue(cellValue);
        tableRow.addCell(getToplevelWidget(cellWidget));
      }
    }
    getTableWidget().getTableBody().insert(tableRow, index);
  }

  /**
   * Determines the type of the cell from <code>column</code> and <code>cellValue</code>.
   * 
   * @param column is the {@link UiWidgetTableColumnImpl} specifying the cells in that column.
   * @param cellValue is the current value for the cell. May be <code>null</code>.
   * @return the type of the values in the cell.
   */
  private Class<?> determineCellType(UiWidgetTableColumnImpl<ROW, ?> column, Object cellValue) {

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
