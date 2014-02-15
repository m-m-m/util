/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.complex.UiWidgetTableColumnImpl;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractDataTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.client.ui.gwt.widgets.Section;
import net.sf.mmm.client.ui.gwt.widgets.TableBody;
import net.sf.mmm.client.ui.gwt.widgets.TableCellHeaderAtomic;
import net.sf.mmm.client.ui.gwt.widgets.TableHead;
import net.sf.mmm.client.ui.gwt.widgets.TableRow;
import net.sf.mmm.client.ui.gwt.widgets.TableWidget;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;
import net.sf.mmm.util.lang.api.SortOrder;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterAbstractDataTable} using GWT based on {@link FlowPanel}
 * and {@link net.sf.mmm.client.ui.gwt.widgets.TableWidget}.
 * 
 * @param <ROW> is the generic type of a row in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtAbstractDataTable<ROW> extends UiWidgetAdapterGwtWidgetActive<FlowPanel>
    implements UiWidgetAdapterAbstractDataTable<ROW> {

  /** @see #getTableWidget() */
  private TableWidget tableWidget;

  /** @see #getSection() */
  private Section section;

  /** @see #setColumns(List) */
  private List<AbstractUiWidgetTableColumn<?, ROW, ?>> columns;

  /** @see #setSelectionMode(SelectionMode) */
  private AbstractUiWidgetTableColumn<?, ROW, Boolean> multiSelectionColumn;

  /** @see #getStyleElement() */
  private StyleElement styleElement;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtAbstractDataTable() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetAdapterTableColumn createTableColumnAdapter(UiWidgetTableColumn<?, ?> column) {

    UiWidgetAdapterGwtTableColumn columnAdapter = new UiWidgetAdapterGwtTableColumn(this);
    return columnAdapter;
  }

  /**
   * @return the columns
   */
  public List<AbstractUiWidgetTableColumn<?, ROW, ?>> getColumns() {

    return this.columns;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setColumns(List<AbstractUiWidgetTableColumn<?, ROW, ?>> columns) {

    TableHead tableHeader = this.tableWidget.getTableHeader();
    TableBody tableBody = this.tableWidget.getTableBody();
    if (this.columns != null) {
      // this approach is totally inefficient but this is currently the simplest approach and
      // dynamic changing of columns is not suggested anyways...
      tableHeader.clear();
      tableBody.clear();
      this.tableWidget.getTableFooter().clear();
    }
    this.columns = columns;
    TableRow headerRow = new TableRow();
    // Hack due to HTML design flaw: we need to add an invisible row to tbody with empty cells that control
    // the width of the column so tbody can be scrollable vertically.
    TableRow bodyWidthRow = new TableRow();
    bodyWidthRow.setStyleName(CssStyles.COLUMN_WIDTH_ROW);
    int size = columns.size();
    if (size > 0) {
      // double widthInPercent = 100D / size;
      int columnIndex = 0;
      if (this.multiSelectionColumn != null) {
        addColumn(headerRow, bodyWidthRow, this.multiSelectionColumn, columnIndex++);
      }
      for (UiWidgetTableColumn<ROW, ?> column : columns) {
        // column.setWidthInPercent(widthInPercent);
        addColumn(headerRow, bodyWidthRow, (AbstractUiWidgetTableColumn<?, ROW, ?>) column, columnIndex++);
      }
    }
    Widget columnWidget = new TableCellHeaderAtomic();
    columnWidget.setStyleName(CssStyles.SCROLLBAR_HEADER);
    headerRow.add(columnWidget);
    tableHeader.add(headerRow);
    tableBody.add(bodyWidthRow);
  }

  /**
   * Adds the given column to this table.
   * 
   * @param headerRow is the {@link TableRow} for the header of the table.
   * @param bodyWidthRow is the invisible row to set the width of the tbody columns.
   * @param column is the actual column widget.
   * @param columnIndex is the current column index.
   */
  private void addColumn(TableRow headerRow, TableRow bodyWidthRow, AbstractUiWidgetTableColumn<?, ROW, ?> column,
      int columnIndex) {

    UiWidgetAdapterGwtTableColumn columnAdapter = getWidgetAdapterForColumn(column);
    Widget columnWidget = columnAdapter.getToplevelWidget();
    headerRow.add(columnWidget);
    bodyWidthRow.add(columnAdapter.getBodyWidthCell());
    column.setCurrentIndex(columnIndex);
  }

  /**
   * @param column is the {@link UiWidgetTableColumn}.
   * @return the widget adapter for the given <code>column</code>.
   */
  protected static UiWidgetAdapterGwtTableColumn getWidgetAdapterForColumn(UiWidgetTableColumn<?, ?> column) {

    UiWidgetAdapterGwtTableColumn columnAdapter = (UiWidgetAdapterGwtTableColumn) AbstractUiWidget
        .getWidgetAdapter(column);
    return columnAdapter;
  }

  /**
   * @return the styleElement
   */
  public StyleElement getStyleElement() {

    if (this.styleElement == null) {
      this.styleElement = Document.get().createStyleElement();
      Document.get().getBody().appendChild(this.styleElement);
    }
    return this.styleElement;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUiWidgetAbstractDataTable<?, ROW> getUiWidgetTyped() {

    return (AbstractUiWidgetAbstractDataTable<?, ROW>) getUiWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder) {

    // the major task of sorting has already happened in the widget before this method has been called
    // all left to do here is update the UI with the new sort order...

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEditable(boolean editableFlag) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditable() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SelectionMode getSelectionMode() {

    return ((UiWidgetAbstractDataTable<?>) getUiWidget()).getSelectionMode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    switch (selectionMode) {
      case MULTIPLE_SELECTION:
        if (this.multiSelectionColumn == null) {
          AbstractUiWidgetAbstractDataTable<?, ROW> listTable = (AbstractUiWidgetAbstractDataTable<?, ROW>) getUiWidget();
          // this.multiSelectionColumn = ((UiWidgetAbstractDataTable) getUiWidget()).createColumn(rowAccessor,
          // widgetFactory, sortComparator)
          this.multiSelectionColumn = new UiWidgetTableColumnImpl<ROW, Boolean>(getContext(), listTable, null, null);
        }
        // hide multi-selection column
        break;
      case SINGLE_SELECTION:
        // show multi-selection column
        break;
      default :
        break;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    // TODO Auto-generated method stub
    return null;
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected FlowPanel createToplevelWidget() {

    FlowPanel flowPanel = new FlowPanel();

    this.section = new Section();
    flowPanel.add(this.section);

    this.tableWidget = new TableWidget();
    this.tableWidget.setStyleName(UiWidgetAbstractDataTable.STYLE_DATA_TABLE);
    // TODO temporary hack...
    DOM.setStyleAttribute(this.tableWidget.getTableBody().getElement(), "maxHeight", "300px");
    flowPanel.add(this.tableWidget);

    return flowPanel;
  }

  /**
   * @return the {@link Section}.
   */
  public Section getSection() {

    return this.section;
  }

  /**
   * @return the {@link TableWidget} for the actual data table.
   */
  protected TableWidget getTableWidget() {

    return this.tableWidget;
  }

}
