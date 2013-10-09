/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractListTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractDataTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractDataTable<ADAPTER extends UiWidgetAdapterAbstractListTable, ROW> extends
    AbstractUiWidgetActive<ADAPTER, List<ROW>> implements UiWidgetAbstractDataTable<ROW> {

  /** @see #sort(AbstractUiWidgetTableColumn) */
  private final ColumnComparator<ROW> columnComparator;

  /** The internal {@link #getValue() value} according to the current sorting, etc. */
  private final List<ROW> value;

  /** @see #getSelectedValues() */
  private List<ROW> selectedValues;

  /** @see #isEditable() */
  private boolean editable;

  /** @see #getSelectionMode() */
  private SelectionMode selectionMode;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetAbstractDataTable(UiContext context) {

    super(context);
    this.editable = false;
    this.value = new ArrayList<ROW>();
    this.columnComparator = new ColumnComparator<ROW>();
  }

  /**
   * @return the internal {@link #getValue() value} instance.
   */
  List<ROW> getValueInternal() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEditable(boolean editable) {

    this.editable = editable;
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setEditable(editable);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInRows(int rows) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInRows() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean setSelectedValue(ROW selectedValue) {

    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setSelectedValue(selectedValue);
    } else {
      this.selectedValues = Arrays.asList(selectedValue);
      // return isContained(selectedValue);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValues(List<ROW> selection) {

    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setSelectedValues(selection);
    } else {
      this.selectedValues = selection;
      // return isContained(selection);
    }
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

    this.selectionMode = selectionMode;
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setSelectionMode(selectionMode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSelectionHandler(UiHandlerEventSelection<ROW> handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeSelectionHandler(UiHandlerEventSelection<ROW> handler) {

    return removeEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(List<ROW> newValue, boolean forUser) {

    // TODO Auto-generated method stub
    super.doSetValue(newValue, forUser);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<ROW> doGetValue(List<ROW> template, ValidationState state) throws RuntimeException {

    // TODO Auto-generated method stub
    return super.doGetValue(template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    if (hasWidgetAdapter()) {
      // return getWidgetAdapter().hasSelectedValue();
      return true;
    } else {
      return ((this.selectedValues != null) && (!this.selectedValues.isEmpty()));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ROW getSelectedValue() {

    if (hasWidgetAdapter()) {
      // return getWidgetAdapter().getSelectedValue();
      return null;
    } else {
      if ((this.selectedValues == null) || (this.selectedValues.isEmpty())) {
        return null;
      }
      return this.selectedValues.get(0);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ROW> getSelectedValues() {

    return this.selectedValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(TypedProperty<CELL> rowProperty,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(PropertyAccessor<ROW, CELL> rowAccessor,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumns(UiWidgetTableColumn<ROW, ?>... columns) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumnCount() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTableColumn<ROW, ?> getColumn(int index) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * This method sorts this list according to the given <code>column</code>.
   * 
   * @param column is the {@link AbstractUiWidgetTableColumn} to sort by.
   */
  void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column) {

    if ((this.value != null) && (!this.value.isEmpty())) {
      this.columnComparator.column = column;
      Collections.sort(this.value, this.columnComparator);
      if (hasWidgetAdapter()) {
        // update widget adapter...
      }
    }
  }

  /**
   * This inner class is a {@link Comparator} to sort by a specific column.
   * 
   * @param <ROW> is the generic type of a row in the list.
   * 
   * @see AbstractUiWidgetAbstractDataTable#sort(AbstractUiWidgetTableColumn)
   */
  private static class ColumnComparator<ROW> implements Comparator<ROW> {

    /** The current column to sort by. */
    private AbstractUiWidgetTableColumn<?, ROW, ?> column;

    /**
     * The constructor.
     */
    public ColumnComparator() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public int compare(ROW row1, ROW row2) {

      Comparator sortComparator = this.column.getSortComparator();
      Object cell1 = this.column.getPropertyAccessor().getValue(row1);
      Object cell2 = this.column.getPropertyAccessor().getValue(row2);
      if (cell1 == cell2) {
        return 0;
      } else {
        if (sortComparator == null) {
          if (cell1 == null) {
            return -1;
          } else if (cell2 == null) {
            return 1;
          } else {
            return ((Comparable) cell1).compareTo(cell2);
          }
        } else {
          return sortComparator.compare(cell1, cell2);
        }
      }
    }
  }

  /**
   * @param column is the {@link UiWidgetTableColumn} for which to create the widget adapter.
   * @return a new {@link UiWidgetAdapterTableColumn} for the given {@link UiWidgetTableColumn}.
   */
  public UiWidgetAdapterTableColumn createTableColumnAdapter(UiWidgetTableColumn<ROW, ?> column) {

    return getWidgetAdapter().createTableColumnAdapter(column);
  }

}
