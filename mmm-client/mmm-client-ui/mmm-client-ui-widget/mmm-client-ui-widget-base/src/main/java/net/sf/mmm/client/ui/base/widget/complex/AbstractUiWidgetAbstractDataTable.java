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
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractDataTable;
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
public abstract class AbstractUiWidgetAbstractDataTable<ADAPTER extends UiWidgetAdapterAbstractDataTable<ROW>, ROW>
    extends AbstractUiWidgetActive<ADAPTER, List<ROW>> implements UiWidgetAbstractDataTable<ROW> {

  /** @see #sort(AbstractUiWidgetTableColumn) */
  private final ColumnComparator<ROW> columnComparator;

  /** The internal {@link #getValue() value} according to the current sorting, etc. */
  private final List<ROW> value;

  /** @see #setColumns(UiWidgetTableColumn...) */
  private final List<UiWidgetTableColumn<ROW, ?>> columns;

  /** @see #getSelectedValues() */
  private List<ROW> selectedValues;

  /** @see #isEditable() */
  private boolean editable;

  /** @see #getSelectionMode() */
  private SelectionMode selectionMode;

  /** @see #createColumn(TypedProperty, UiSingleWidgetFactory, Comparator) */
  private UiDataBinding<ROW> rowBinding;

  /** @see #getHeightInRows() */
  private int heigthInRows;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetAbstractDataTable(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.editable = false;
    this.value = new ArrayList<ROW>();
    this.columnComparator = new ColumnComparator<ROW>();
    this.columns = new ArrayList<UiWidgetTableColumn<ROW, ?>>();
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
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected Class<List<ROW>> getValueClass() {

    return (Class) List.class;
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

    this.heigthInRows = rows;
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setHeight(height);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInRows() {

    return this.heigthInRows;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setSelectedValue(ROW selectedValue) {

    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setSelectedValue(selectedValue);
    } else {
      this.selectedValues = Arrays.asList(selectedValue);
    }
    return isContained(selectedValue);
  }

  /**
   * @param row is the row to check.
   * @return <code>true</code> if the given <code>row</code> is {@link java.util.Collection#contains(Object)
   *         contained} in this {@link #getValue() value list}.
   */
  private boolean isContained(ROW row) {

    return this.value.contains(row);
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
  public <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(final TypedProperty<CELL> rowProperty,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator) {

    PropertyAccessor<ROW, CELL> accessor;
    if (this.rowBinding == null) {
      accessor = new LazyPropertyAccessor<CELL>(rowProperty);
    } else {
      accessor = this.rowBinding.createPropertyAccessor(rowProperty);
    }
    return createColumn(accessor, widgetFactory, sortComparator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(PropertyAccessor<ROW, CELL> rowAccessor,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator) {

    UiWidgetTableColumnImpl<ROW, CELL> column = new UiWidgetTableColumnImpl<ROW, CELL>(getContext(), this, null);
    column.setSortComparator(sortComparator);
    column.setPropertyAccessor(rowAccessor);
    column.setWidgetFactory(widgetFactory);
    return column;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setColumns(UiWidgetTableColumn<ROW, ?>... columns) {

    this.columns.clear();
    Collections.addAll(this.columns, columns);
    if (hasWidgetAdapter()) {
      ADAPTER widgetAdapter = getWidgetAdapter();
      widgetAdapter.setColumns(this.columns);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumnCount() {

    return this.columns.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTableColumn<ROW, ?> getColumn(int index) {

    return this.columns.get(index);
  }

  /**
   * This method sorts this list according to the given <code>column</code>.
   * 
   * @param column is the {@link AbstractUiWidgetTableColumn} to sort by.
   */
  void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column) {

    if ((this.value != null) && (!this.value.isEmpty())) {
      if (!this.columns.contains(column)) {
        getLogger().warn("Columun " + column.getTitle() + " not set in table!");
        return;
      }
      this.columnComparator.column = column;
      Collections.sort(this.value, this.columnComparator);
      if (hasWidgetAdapter()) {
        // update widget adapter...
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

  /**
   * This class is a lazy implementation of {@link PropertyAccessor}.
   * 
   * @param <CELL> is the generic type of the cell property to access.
   */
  private final class LazyPropertyAccessor<CELL> implements PropertyAccessor<ROW, CELL> {

    /** The {@link TypedProperty} to access. */
    private final TypedProperty<CELL> rowProperty;

    /** The {@link PropertyAccessor} to delegate to. Will be <code>null</code> until first initialization. */
    private PropertyAccessor<ROW, CELL> delegate;

    /**
     * The constructor.
     * 
     * @param rowProperty is the {@link TypedProperty} to access.
     */
    private LazyPropertyAccessor(TypedProperty<CELL> rowProperty) {

      this.rowProperty = rowProperty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CELL getValue(ROW element) {

      return getDelegate(element).getValue(element);
    }

    /**
     * Lazy accessor to get the {@link PropertyAccessor} to delegate to.
     * 
     * @param row is the row value to access.
     * @return the {@link PropertyAccessor} to delegate to.
     */
    private PropertyAccessor<ROW, CELL> getDelegate(ROW row) {

      if (this.delegate == null) {
        if (AbstractUiWidgetAbstractDataTable.this.rowBinding == null) {
          Class<?> rowClass = row.getClass();
          AbstractUiWidgetAbstractDataTable.this.rowBinding = (UiDataBinding<ROW>) getContext().getDataBindingFactory()
              .createDataBinding(AbstractUiWidgetAbstractDataTable.this, rowClass);
        }
        this.delegate = AbstractUiWidgetAbstractDataTable.this.rowBinding.createPropertyAccessor(this.rowProperty);
      }
      return this.delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(ROW element, CELL cellValue) {

      getDelegate(element).setValue(element, cellValue);
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

}
