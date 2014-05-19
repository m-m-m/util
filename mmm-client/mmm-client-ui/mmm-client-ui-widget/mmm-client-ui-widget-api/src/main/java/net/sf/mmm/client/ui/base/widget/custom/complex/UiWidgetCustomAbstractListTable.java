/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.complex;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SelectionChoice;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.common.SelectionOperation;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract base class for a {@link UiWidgetCustom custom widget} implementing
 * {@link UiWidgetAbstractListTable}.
 *
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomAbstractListTable<ROW, DELEGATE extends UiWidgetAbstractListTable<ROW>> extends
    UiWidgetCustom<List<ROW>, DELEGATE> implements UiWidgetAbstractListTable<ROW> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public UiWidgetCustomAbstractListTable(UiContext context, DELEGATE delegate) {

    super(context, delegate, (Class) List.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiDataBinding<List<ROW>> getDataBinding(List<ROW> example) {

    return getDataBindingForWidget(getDelegate(), example);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(TypedProperty<CELL> rowProperty,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator) {

    return getDelegate().createColumn(rowProperty, widgetFactory, sortComparator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(PropertyAccessor<ROW, CELL> rowAccessor,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator) {

    return getDelegate().createColumn(rowAccessor, widgetFactory, sortComparator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumns(List<? extends UiWidgetTableColumn<ROW, ?>> columns) {

    getDelegate().setColumns(columns);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumnCount() {

    return getDelegate().getColumnCount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTableColumn<ROW, ?> getColumn(int index) {

    return getDelegate().getColumn(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTableColumn<?, Boolean> getSelectionColumn() {

    return getDelegate().getSelectionColumn();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTableColumn<?, Integer> getRowNumberColumn() {

    return getDelegate().getRowNumberColumn();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTableColumn<ROW, ?> getColumnById(String columnId, boolean required) throws ObjectNotFoundException {

    return getDelegate().getColumnById(columnId, required);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return getDelegate().getTitle();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    getDelegate().setTitle(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSelectionHandler(UiHandlerEventSelection<ROW> handler) {

    getDelegate().addSelectionHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeSelectionHandler(UiHandlerEventSelection<ROW> handler) {

    return getDelegate().removeSelectionHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelection(SelectionChoice choice, SelectionOperation operation) {

    return getDelegate().setSelection(choice, operation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSelected(SelectionChoice choice) {

    return getDelegate().isSelected(choice);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValue(ROW selectedValue) {

    return getDelegate().setSelectedValue(selectedValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValues(Collection<ROW> selectedValues) {

    return getDelegate().setSelectedValues(selectedValues);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    return getDelegate().hasSelectedValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ROW getSelectedValue() {

    return getDelegate().getSelectedValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<ROW> getSelectedValues() {

    return getDelegate().getSelectedValues();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSelectionCount() {

    return getDelegate().getSelectionCount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    getDelegate().setSelectionMode(selectionMode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SelectionMode getSelectionMode() {

    return getDelegate().getSelectionMode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSummary(String summary) {

    getDelegate().setSummary(summary);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSummary() {

    return getDelegate().getSummary();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitleVisible(boolean titleVisible) {

    getDelegate().setTitleVisible(titleVisible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTitleVisible() {

    return getDelegate().isTitleVisible();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEditable(boolean editableFlag) {

    getDelegate().setEditable(editableFlag);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditable() {

    return getDelegate().isEditable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRowIndex(ROW row) {

    return getDelegate().getRowIndex(row);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row) {

    getDelegate().addRow(row);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row, int index) {

    getDelegate().addRow(row, index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean replaceRow(ROW oldRow, ROW newRow) {

    return getDelegate().replaceRow(oldRow, newRow);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeRow(ROW row) {

    return getDelegate().removeRow(row);
  }

}
