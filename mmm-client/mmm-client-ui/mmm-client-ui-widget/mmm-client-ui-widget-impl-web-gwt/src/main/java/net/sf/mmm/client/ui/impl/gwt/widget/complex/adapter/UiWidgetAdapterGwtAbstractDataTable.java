/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.complex.UiWidgetTableColumnImpl;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractDataTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.Section;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.TableHead;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.TableWidget;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetActive;
import net.sf.mmm.util.lang.api.SortOrder;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterAbstractDataTable} using GWT based on {@link FlowPanel}
 * and {@link net.sf.mmm.client.ui.impl.gwt.gwtwidgets.TableWidget}.
 * 
 * @param <ROW> is the generic type of a row in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtAbstractDataTable<ROW> extends UiWidgetAdapterGwtWidgetActive<FlowPanel> implements
    UiWidgetAdapterAbstractDataTable<ROW> {

  /** @see #getTableWidget() */
  private TableWidget tableWidget;

  /** @see #getSection() */
  private Section section;

  /** @see #setColumns(List) */
  private List<UiWidgetTableColumnImpl<ROW, ?>> columns;

  private SelectionMode selectionMode;

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
  public List<UiWidgetTableColumnImpl<ROW, ?>> getColumns() {

    return this.columns;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumns(List<UiWidgetTableColumnImpl<ROW, ?>> columns) {

    TableHead tableHeader = this.tableWidget.getTableHeader();
    if (this.columns != null) {
      // this approach is totally inefficient but this is currently the simplest approach and
      // dynamic changing of columns is not suggested anyways...
      tableHeader.clear();
      this.tableWidget.getTableBody().clear();
      this.tableWidget.getTableFooter().clear();
    }
    this.columns = columns;
    for (UiWidgetTableColumn<ROW, ?> column : columns) {
      Widget columnWidget = getToplevelWidget(column);
      tableHeader.add(columnWidget);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder) {

    // TODO update UI...
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

    return this.selectionMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    switch (selectionMode) {
      case MULTIPLE_SELECTION:
        // hide multi-selection column
        break;
      case SINGLE_SELECTION:
        // show multi-selection column
        break;
      default :
        break;
    }
    this.selectionMode = selectionMode;
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
