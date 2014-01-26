/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.api.event.UiEventSelectionChange;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventSelection;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.binding.UiDataBinding;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetActive;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractDataTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.lang.api.EqualsChecker;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
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

  /** @see #setColumns(UiWidgetTableColumn...) */
  private final List<UiWidgetTableColumnImpl<ROW, ?>> columns;

  /** @see #getSelectedValues() */
  private final Set<TableRowContainer<ROW>> selectedValues;

  /** @see #isEditable() */
  private boolean editable;

  /** @see #getSelectionMode() */
  private SelectionMode selectionMode;

  /** @see #createColumn(TypedProperty, UiSingleWidgetFactory, Comparator) */
  private UiDataBinding<ROW> rowBinding;

  /** @see #getRowEqualsChecker() */
  private EqualsChecker<ROW> rowEqualsChecker;

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
    this.columns = new ArrayList<UiWidgetTableColumnImpl<ROW, ?>>();
    this.selectionMode = SelectionMode.SINGLE_SELECTION;
    this.selectedValues = new HashSet<TableRowContainer<ROW>>();
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
    adapter.setEditable(this.editable);
    if (!this.columns.isEmpty()) {
      adapter.setColumns(this.columns);
    }
    adapter.setSelectionMode(this.selectionMode);
    // if (!this.selectedValues.isEmpty()) {
    // List<ROW> emptyList = Collections.emptyList();
    // // adapter.updateSelection(emptyList, this.selectedValues);
    // }
    // adapter.setHeight(this.heigthInRows);
  }

  /**
   * @param row is the {@literal <ROW>} to {@link TableRowContainer#setValue(Object) set} in the container.
   * @return a new instance of {@link TableRowContainer}.
   */
  protected TableRowContainer<ROW> createRowContainer(ROW row) {

    TableRowContainer<ROW> container = createRowContainer();
    container.setValue(row);
    return container;
  }

  /**
   * @return a new instance of {@link TableRowContainer}.
   */
  protected abstract TableRowContainer<ROW> createRowContainer();

  /**
   * Searches for an existing {@link TableRowContainer} for the given <code>row</code>.
   * 
   * @param row is the {@literal <ROW>}.
   * @return the existing {@link TableRowContainer} {@link TableRowContainer#getValue() containing} the given
   *         <code>row</code> (for a list table from the list of rows) or <code>null</code>.
   */
  protected abstract TableRowContainer<ROW> getRowContainer(ROW row);

  /**
   * Called from adapter if a row ({@literal <ROW>}) has been (de)selected.
   * 
   * @param row is the {@link TableRowContainer} containing the (de)selected {@literal <ROW>}.
   * @param selected - <code>true</code> if row was just selected, <code>false</code> if just deselected.
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  public void onRowSelection(TableRowContainer<ROW> row, boolean selected, boolean programmatic) {

    boolean fireEvent;
    if (selected) {
      if (this.selectionMode == SelectionMode.SINGLE_SELECTION) {
        this.selectedValues.clear();
      }
      fireEvent = this.selectedValues.add(row);
    } else {
      fireEvent = this.selectedValues.remove(row);
    }
    if (fireEvent) {
      fireEvent(new UiEventSelectionChange<ROW>(this, programmatic));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEditable(boolean editable) {

    this.editable = editable;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setEditable(editable);
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
  public SelectionMode getSelectionMode() {

    return this.selectionMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectionMode(SelectionMode selectionMode) {

    this.selectionMode = selectionMode;
    if ((this.selectionMode == SelectionMode.SINGLE_SELECTION) && (this.selectedValues.size() > 1)) {
      Iterator<TableRowContainer<ROW>> iterator = this.selectedValues.iterator();
      TableRowContainer<ROW> firstSelection = iterator.next();
      while (iterator.hasNext()) {
        TableRowContainer<ROW> rowContainer = iterator.next();
        rowContainer.setSelected(false);
        // iterator.remove();
      }
      this.selectedValues.clear();
      this.selectedValues.add(firstSelection);
    }
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSelectionMode(selectionMode);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedValues(List<ROW> selectionList) {

    switch (this.selectionMode) {
      case SINGLE_SELECTION:
        ROW selectionRow = null;
        TableRowContainer<ROW> selectionRowContainer = null;
        if (!selectionList.isEmpty()) {
          // in single selection mode, we only care for the first selection and ignore anything else...
          selectionRow = selectionList.get(0);
          selectionRowContainer = getRowContainer(selectionRow);
        }
        for (TableRowContainer<ROW> rowContainer : this.selectedValues) {
          if (rowContainer != selectionRowContainer) {
            rowContainer.setSelected(false);
          }
        }
        this.selectedValues.clear();
        if (selectionRowContainer != null) {
          this.selectedValues.add(selectionRowContainer);
        }
        break;
      case MULTIPLE_SELECTION:
        // add and select new rows...
        Set<TableRowContainer<ROW>> newSelectedValues = new HashSet<TableRowContainer<ROW>>();
        for (ROW rowToSelect : selectionList) {
          TableRowContainer<ROW> rowContainer = getRowContainer(rowToSelect);
          if (rowContainer != null) {
            newSelectedValues.add(rowContainer);
            boolean isNew = this.selectedValues.add(rowContainer);
            if (isNew) {
              rowContainer.setSelected(true);
            }
          }
        }
        // remove and deselect rows not selected anymore...
        Iterator<TableRowContainer<ROW>> rowIterator = this.selectedValues.iterator();
        while (rowIterator.hasNext()) {
          TableRowContainer<ROW> rowContainer = rowIterator.next();
          if (!newSelectedValues.contains(rowContainer)) {
            rowContainer.setSelected(false);
            rowIterator.remove();
          }
        }
        break;
      default :
        break;
    }
    boolean programmatic = true;
    fireEvent(new UiEventSelectionChange<ROW>(this, programmatic));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedValue(ROW selectedValue) {

    setSelectedValues(Arrays.asList(selectedValue));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSelectedValue() {

    return !this.selectedValues.isEmpty();
  }

  /**
   * @return the internal representation of the {@link #getSelectedValues()}.
   */
  public Collection<TableRowContainer<ROW>> getSelectedValuesInternal() {

    return this.selectedValues;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ROW getSelectedValue() {

    if (this.selectedValues.isEmpty()) {
      return null;
    }
    TableRowContainer<ROW> first = this.selectedValues.iterator().next();
    return first.getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ROW> getSelectedValues() {

    ArrayList<ROW> result = new ArrayList<ROW>(this.selectedValues.size());
    for (TableRowContainer<ROW> rowContainer : this.selectedValues) {
      result.add(rowContainer.getValue());
    }
    return result;
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
    UiWidgetTableColumn<ROW, CELL> column = createColumn(accessor, widgetFactory, sortComparator);
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

    UiWidgetTableColumnImpl<ROW, CELL> column = new UiWidgetTableColumnImpl<ROW, CELL>(getContext(), this, null);
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

    this.columns.clear();
    // Collections.addAll(this.columns, (UiWidgetTableColumnImpl<ROW, ?>[]) columns);
    for (UiWidgetTableColumn<ROW, ?> column : columns) {
      this.columns.add((UiWidgetTableColumnImpl<ROW, ?>) column);
    }
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
   * @param sortOrder is the {@link SortOrder}.
   */
  abstract void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder);

  /**
   * @param column is the {@link UiWidgetTableColumn} for which to create the widget adapter.
   * @return a new {@link UiWidgetAdapterTableColumn} for the given {@link UiWidgetTableColumn}.
   */
  public UiWidgetAdapterTableColumn createTableColumnAdapter(UiWidgetTableColumn<ROW, ?> column) {

    return getWidgetAdapter().createTableColumnAdapter(column);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EqualsChecker<ROW> getRowEqualsChecker() {

    return this.rowEqualsChecker;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRowEqualsChecker(EqualsChecker<ROW> rowEqualsChecker) {

    this.rowEqualsChecker = rowEqualsChecker;
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
