/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.widget.complex.TableRowContainer;
import net.sf.mmm.client.ui.base.widget.complex.UiWidgetTableColumnImpl;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractListTable;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.TableRow;
import net.sf.mmm.client.ui.impl.gwt.widget.complex.TableRowContainerGwt;

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
          // TODO cellValue may be null...
          cellWidget = getContext().getWidgetFactory().createForDatatype(cellValue.getClass());
        } else {
          cellWidget = widgetFactory.create(getContext());
        }
        cellWidget.setMode(UiMode.VIEW);
        cellWidget.setValue(cellValue);
        tableRow.addCell(getToplevelWidget(cellWidget));
      }
    }
    getTableWidget().getTableBody().insert(tableRow, index);
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
