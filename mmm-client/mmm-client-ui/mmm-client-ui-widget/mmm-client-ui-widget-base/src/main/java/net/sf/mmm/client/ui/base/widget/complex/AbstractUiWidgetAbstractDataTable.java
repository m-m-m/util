/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractDataTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractDataTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * @param <ITEM_CONTAINER> is the generic type of the {@link ItemContainer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractDataTable<ADAPTER extends UiWidgetAdapterAbstractDataTable<ROW>, ROW, ITEM_CONTAINER extends ItemContainer<ROW, ITEM_CONTAINER>>
    extends AbstractUiWidgetAbstractDataSet<ADAPTER, List<ROW>, ROW, ITEM_CONTAINER> implements
    UiWidgetAbstractDataTable<ROW> {

  /** @see #setColumns(UiWidgetTableColumn...) */
  private final List<AbstractUiWidgetTableColumn<?, ROW, ?>> columns;

  /** @see #createColumn(TypedProperty, UiSingleWidgetFactory, Comparator) */
  private UiDataBinding<ROW> rowBinding;

  /** @see #sort(AbstractUiWidgetTableColumn, SortOrder) */
  private AbstractUiWidgetTableColumn<?, ROW, ?> sortColumn;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetAbstractDataTable(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.columns = new ArrayList<AbstractUiWidgetTableColumn<?, ROW, ?>>();
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
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (!this.columns.isEmpty()) {
      adapter.setColumns(this.columns);
    }
    // if (!this.selectedValues.isEmpty()) {
    // List<ROW> emptyList = Collections.emptyList();
    // // adapter.updateSelection(emptyList, this.selectedValues);
    // }
  }

  /**
   * @param row is the {@literal <ROW>} to {@link AbstractItemContainer#setItemOriginal(Object) set} in the
   *        container.
   * @return a new instance of {@link AbstractItemContainer}.
   */
  protected ITEM_CONTAINER createRowContainer(ROW row) {

    ITEM_CONTAINER container = createRowContainer();
    container.setItemOriginal(row);
    return container;
  }

  /**
   * @return a new instance of {@link AbstractItemContainer}.
   */
  protected abstract ITEM_CONTAINER createRowContainer();

  /**
   * {@inheritDoc}
   */
  @Override
  protected ROW getItem(ITEM_CONTAINER container) {

    return container.getItem();
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
    UiWidgetTableColumn<ROW, CELL> column = createColumn(accessor, widgetFactory, sortComparator, rowProperty);
    // TODO reuse label mechanism with I18N...
    column.setTitle(rowProperty.getTitle());
    return column;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(PropertyAccessor<ROW, CELL> rowAccessor,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator) {

    return createColumn(rowAccessor, widgetFactory, sortComparator, null);
  }

  /**
   * @see #createColumn(PropertyAccessor, UiSingleWidgetFactory, Comparator)
   * 
   * @param <CELL> is the generic type of the {@link PropertyAccessor#getValue(Object) property value}.
   * @param rowAccessor is the {@link PropertyAccessor} to {@link PropertyAccessor#getValue(Object) access}
   *        the property of {@literal <ROW>} to show in the column.
   * @param widgetFactory is the factory to
   *        {@link UiSingleWidgetFactory#create(net.sf.mmm.client.ui.api.UiContext) create} widgets for this
   *        column. <b>ATTENTION:</b> These widgets might be reused for performance-reasons for a different
   *        row just by {@link UiWidgetWithValue#setValue(Object) setting its value}. They should NOT contain
   *        additional state information (what is generally a bad idea). This parameter may be
   *        <code>null</code> to fall back to
   *        {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory#createForDatatype(Class) datatype based
   *        creation}.
   * @param sortComparator is the {@link UiWidgetTableColumn#setSortComparator(Comparator) sort-comparator to
   *        set}.
   * @param rowProperty is the {@link TypedProperty}. May be <code>null</code>.
   * @return a new {@link UiWidgetTableColumn}.
   */
  private <CELL> UiWidgetTableColumn<ROW, CELL> createColumn(PropertyAccessor<ROW, CELL> rowAccessor,
      UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory, Comparator<CELL> sortComparator,
      TypedProperty<CELL> rowProperty) {

    UiWidgetTableColumnImpl<ROW, CELL> column = new UiWidgetTableColumnImpl<ROW, CELL>(getContext(), this, rowProperty,
        null);
    column.setPropertyAccessor(rowAccessor);
    if (sortComparator != null) {
      column.setSortComparator(sortComparator);
    }
    if (widgetFactory != null) {
      column.setWidgetFactory(widgetFactory);
    }
    return column;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void setColumns(UiWidgetTableColumn<ROW, ?>... columns) {

    for (AbstractUiWidgetTableColumn<?, ROW, ?> column : this.columns) {
      column.setAttached(false);
    }
    this.columns.clear();
    for (UiWidgetTableColumn<ROW, ?> column : columns) {
      AbstractUiWidgetTableColumn<?, ROW, ?> columnImpl = (AbstractUiWidgetTableColumn<?, ROW, ?>) column;
      columnImpl.setAttached(true);
      this.columns.add(columnImpl);
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setColumns(this.columns);
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
   * @param sortOrder is the {@link SortOrder}.
   */
  void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder) {

    // incomplete implementation, has to be overridden/extended...
    if ((this.sortColumn != null) && (this.sortColumn != column)) {
      this.sortColumn.setSortOrder(null);
    }
    column.setSortOrder(sortOrder);
    this.sortColumn = column;
  }

  /**
   * @return the sortColumn
   */
  AbstractUiWidgetTableColumn<?, ROW, ?> getSortColumn() {

    return this.sortColumn;
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

}
