/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.SelectionChoice;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.common.SelectionOperation;
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
import net.sf.mmm.client.ui.impl.gwt.widget.complex.ItemContainerGwt;
import net.sf.mmm.util.lang.api.SortOrder;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.SimpleCheckBox;
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

  /** @see #setShowRowNumbers(boolean) */
  private AbstractUiWidgetTableColumn<?, ROW, String> rowNumberColumn;

  /** @see #getStyleElement() */
  private StyleElement styleElement;

  /** @see #setColumns(List) */
  private TableRow headerRow;

  /** @see #setColumns(List) */
  private TableRow bodyWidthRow;

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
    this.headerRow = new TableRow();
    this.bodyWidthRow = new TableRow();
    this.bodyWidthRow.setStyleName(CssStyles.COLUMN_WIDTH_ROW);
    int size = columns.size();
    if (size > 0) {
      // double widthInPercent = 100D / size;
      int columnIndex = 0;
      if (this.rowNumberColumn != null) {
        addColumn(this.rowNumberColumn, columnIndex++);
      }
      if (this.multiSelectionColumn != null) {
        addColumn(this.multiSelectionColumn, columnIndex++);
      }
      for (UiWidgetTableColumn<ROW, ?> column : columns) {
        // column.setWidthInPercent(widthInPercent);
        addColumn((AbstractUiWidgetTableColumn<?, ROW, ?>) column, columnIndex++);
      }
    }
    Widget columnWidget = new TableCellHeaderAtomic();
    columnWidget.setStyleName(CssStyles.SCROLLBAR_HEADER);
    this.headerRow.add(columnWidget);
    tableHeader.add(this.headerRow);
    tableBody.add(this.bodyWidthRow);
  }

  /**
   * Adds the given column to this table.
   * 
   * @param column is the actual column widget.
   * @param columnIndex is the current column index.
   */
  private void addColumn(AbstractUiWidgetTableColumn<?, ROW, ?> column, int columnIndex) {

    UiWidgetAdapterGwtTableColumn columnAdapter = getWidgetAdapterForColumn(column);
    Widget columnWidget = columnAdapter.getToplevelWidget();
    this.headerRow.insert(columnWidget, columnIndex);
    this.bodyWidthRow.insert(columnAdapter.getBodyWidthCell(), columnIndex);
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
  public AbstractUiWidgetAbstractDataTable<?, ROW, ItemContainerGwt<ROW>> getUiWidgetTyped() {

    return (AbstractUiWidgetAbstractDataTable<?, ROW, ItemContainerGwt<ROW>>) getUiWidget();
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
  public void setSummary(String summary) {

    this.tableWidget.setSummary(summary);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSummary() {

    return this.tableWidget.getSummary();
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
  public void setTitle(String title) {

    getTableWidget().setCaption(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitleVisible(boolean titleVisible) {

    getTableWidget().getCaptionWidget().setVisible(titleVisible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTitleVisible() {

    return getTableWidget().getCaptionWidget().isVisible();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SelectionMode getSelectionMode() {

    return getUiWidgetTyped().getSelectionMode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    switch (selectionMode) {
      case MULTIPLE_SELECTION:
        if (this.multiSelectionColumn == null) {
          initializeMultiSelection();
        } else {
          this.multiSelectionColumn.setVisible(true);
        }
        // show multi-selection column
        // getUiWidgetTyped().get
        break;
      case SINGLE_SELECTION:
        // hide multi-selection column
        if (this.multiSelectionColumn != null) {
          this.multiSelectionColumn.setVisible(false);
        }
        break;
      default :
        break;
    }
  }

  /**
   * Method called once to initialize multi-selection mode (add checkbox column).
   */
  protected void initializeMultiSelection() {

    this.multiSelectionColumn = new UiWidgetTableColumnImpl<ROW, Boolean>(getContext(), getUiWidgetTyped(),
        AbstractUiWidgetTableColumn.PROPERTY_SELECTED, null);
    this.multiSelectionColumn.setStyles(CssStyles.MULTI_SELECTION_HEADER);
    this.multiSelectionColumn.setResizable(false);
    this.multiSelectionColumn.setReorderable(false);
    this.multiSelectionColumn.setWidthInPixel(20);
    if (this.headerRow == null) {
      return;
    }
    TableCellHeaderAtomic toplevelWidget = getToplevelWidget(this.multiSelectionColumn, TableCellHeaderAtomic.class);
    final SimpleCheckBox headerCheckbox = new SimpleCheckBox();
    HandlerRegistration registration = headerCheckbox.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        Boolean checked = headerCheckbox.getValue();
        SelectionOperation operation;
        if (Boolean.TRUE.equals(checked)) {
          operation = SelectionOperation.SET;
        } else {
          operation = SelectionOperation.REMOVE;
        }
        getUiWidgetTyped().setSelection(SelectionChoice.ALL, operation);
      }
    });
    addHandlerRegistration(registration);
    toplevelWidget.setChild(headerCheckbox);
    int index = 0;
    if (this.rowNumberColumn != null) {
      index = 1;
    }
    addColumn(this.multiSelectionColumn, index);
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
