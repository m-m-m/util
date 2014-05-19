/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractDataTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractDataTable}.
 *
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Has to be either {@literal ITEM} or
 *        {@literal List<ITEM>}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * @param <ITEM_CONTAINER> is the generic type of the {@link ItemContainer}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractDataTable<ADAPTER extends UiWidgetAdapterAbstractDataTable<ROW>, VALUE, ROW, ITEM_CONTAINER extends ItemContainer<ROW, ITEM_CONTAINER>>
    extends AbstractUiWidgetAbstractDataSet<ADAPTER, VALUE, ROW, ITEM_CONTAINER> implements
    UiWidgetAbstractDataTable<ROW> {

  /** @see #setColumns(List) */
  private final List<AbstractUiWidgetTableColumn<?, ROW, ?>> columns;

  /** @see #createColumn(TypedProperty, UiSingleWidgetFactory, Comparator) */
  private UiDataBinding<ROW> rowBinding;

  /** @see #sort(AbstractUiWidgetTableColumn, SortOrder) */
  private AbstractUiWidgetTableColumn<?, ROW, ?> sortColumn;

  /** @see #getSelectionColumn() */
  private AbstractUiWidgetTableColumn<?, ROW, Boolean> selectionColumn;

  /** @see #getRowNumberColumn() */
  private AbstractUiWidgetTableColumn<?, ROW, Integer> rowNumberColumn;

  /** @see #setDragOverColumn(AbstractUiWidgetTableColumn) */
  private AbstractUiWidgetTableColumn<?, ?, ?> dragOverColumn;

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
   * {@inheritDoc}
   */
  @Override
  public AbstractUiWidgetTableColumn<?, ROW, Boolean> getSelectionColumn() {

    if (this.selectionColumn == null) {
      this.selectionColumn = createSelectionColumn();
    }
    return this.selectionColumn;
  }

  /**
   * @see #getSelectionColumn()
   *
   * @return the newly created selection column.
   */
  protected AbstractUiWidgetTableColumn<?, ROW, Boolean> createSelectionColumn() {

    UiWidgetTableColumnImpl<ROW, Boolean> selectionCol = new UiWidgetTableColumnImpl<>(getContext(), this,
        AbstractUiWidgetTableColumn.PROPERTY_SELECTED, null);
    selectionCol.setResizable(false);
    selectionCol.setReorderable(false);
    selectionCol.setSortable(false);
    selectionCol.getSize().setWidthInPixel(20);
    return selectionCol;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractUiWidgetTableColumn<?, ROW, Integer> getRowNumberColumn() {

    if (this.rowNumberColumn == null) {
      this.rowNumberColumn = createRowNumberColumn();
    }
    return this.rowNumberColumn;
  }

  /**
   * @return <code>true</code> if {@link #getRowNumberColumn()} has already been called, <code>false</code>
   *         otherwise.
   */
  public boolean hasRowNumberColumn() {

    return (this.rowNumberColumn != null);
  }

  /**
   * @see #getRowNumberColumn()
   *
   * @return the newly created row number column.
   */
  private AbstractUiWidgetTableColumn<?, ROW, Integer> createRowNumberColumn() {

    UiWidgetTableColumnImpl<ROW, Integer> rowNumberCol = new UiWidgetTableColumnImpl<>(getContext(), this,
        AbstractUiWidgetTableColumn.PROPERTY_ROW_NUMBER, null);
    rowNumberCol.setTitle("#"); // I18N/Customization ?
    rowNumberCol.setResizable(true);
    rowNumberCol.setReorderable(false); // should always be the first column
    rowNumberCol.setSortable(false); // sorting makes absolutely no sense here...
    rowNumberCol.getSize().setMinimumWidth(Length.valueOfPixel(20));
    rowNumberCol.getSize().setWidthInPixel(40);
    return rowNumberCol;
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
    column.setTitle(getDataBinding().getLabel(rowProperty));
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
  public void setColumns(List<? extends UiWidgetTableColumn<ROW, ?>> columns) {

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
   * {@inheritDoc}
   */
  @Override
  public AbstractUiWidgetTableColumn<?, ROW, ?> getColumnById(String columnId, boolean required)
      throws ObjectNotFoundException {

    for (AbstractUiWidgetTableColumn<?, ROW, ?> col : this.columns) {
      if (columnId.equals(col.getId())) {
        return col;
      }
    }
    if ((this.rowNumberColumn != null) && (columnId.equals(this.rowNumberColumn.getId()))) {
      return this.rowNumberColumn;
    }
    if (columnId.equals(getSelectionColumn().getId())) {
      return getSelectionColumn();
    }
    if (required) {
      throw new ObjectNotFoundException(UiWidgetTableColumn.class, columnId);
    }
    return null;
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
          @SuppressWarnings({ "unchecked", "rawtypes" })
          Class<ROW> rowClass = (Class) row.getClass();
          AbstractUiWidgetAbstractDataTable.this.rowBinding = getContext().getDataBindingFactory().createDataBinding(
              getContext(), rowClass);
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
   * This method updates this table after a column has been dragged and dropped onto another column. It
   * therefore moves the dragged column to the drop column.
   *
   * @param dragId the ID of the dragged column (that is to be moved).
   * @param dropId the ID of the dropped column (where to move to).
   */
  public void dragColumn(String dragId, String dropId) {

    AbstractUiWidgetTableColumn<?, ROW, ?> dragColumn = getColumnById(dragId, true);
    AbstractUiWidgetTableColumn<?, ROW, ?> dropColumn = getColumnById(dropId, true);
    dragColumn(dragColumn, dropColumn);
  }

  /**
   * @see #dragColumn(AbstractUiWidgetTableColumn, AbstractUiWidgetTableColumn)
   *
   * @param dragColumn the dragged column (that is to be moved).
   * @param dropColumn the dropped column (where to move to).
   */
  private void dragColumn(AbstractUiWidgetTableColumn<?, ROW, ?> dragColumn,
      AbstractUiWidgetTableColumn<?, ROW, ?> dropColumn) {

    if (dragColumn == dropColumn) {
      return;
    }
    int dragIndex = dragColumn.getCurrentIndex();
    int dropIndex = dropColumn.getCurrentIndex();
    getWidgetAdapter().dragColumn(dragColumn, dropColumn);
    // update column indices...
    int delta = -1;
    int minIndex = dragIndex;
    int maxIndex = dropIndex;
    if (dropIndex < minIndex) {
      minIndex = dropIndex;
      maxIndex = dragIndex;
      delta = 1;
    }
    dragColumn.setCurrentIndex(dropColumn.getCurrentIndex());
    for (AbstractUiWidgetTableColumn<?, ROW, ?> col : this.columns) {
      int currentIndex = col.getCurrentIndex();
      if ((currentIndex >= minIndex) && (currentIndex <= maxIndex) && (col != dragColumn)) {
        col.setCurrentIndex(currentIndex + delta);
      }
    }
  }

  /**
   * Sets the column that is currently under the dragged column. <br/>
   * <b>Note:</b><br/>
   * For simplicity we ignore the generics here and do not require {@literal <ROW>} what would actually be
   * required for correctness.
   *
   * @param column is the new drag-over column. May be <code>null</code>.
   * @return the previously stored drag-over column that is now replaced. May be <code>null</code>.
   */
  public AbstractUiWidgetTableColumn<?, ?, ?> setDragOverColumn(AbstractUiWidgetTableColumn<?, ?, ?> column) {

    AbstractUiWidgetTableColumn<?, ?, ?> old = this.dragOverColumn;
    this.dragOverColumn = column;
    return old;
  }
}
