/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.ColumnContainerComparator;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractListTable;
import net.sf.mmm.util.collection.base.HashKey;
import net.sf.mmm.util.lang.api.SortOrder;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractListTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * @param <ITEM_CONTAINER> is the generic type of the {@link ItemContainer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractListTable<ADAPTER extends UiWidgetAdapterAbstractListTable<ROW, ITEM_CONTAINER>, ROW, ITEM_CONTAINER extends ItemContainer<ROW, ITEM_CONTAINER>>
    extends AbstractUiWidgetAbstractDataTable<ADAPTER, ROW, ITEM_CONTAINER> implements UiWidgetAbstractListTable<ROW> {

  /** @see #getAllAvailableItems() */
  private final List<ITEM_CONTAINER> rows;

  /** @see #getItemContainer(Object) */
  private final Map<HashKey<ROW>, ITEM_CONTAINER> rowMap;

  /** The {@link ColumnContainerComparator} for this table. */
  private final ColumnContainerComparator<ROW> comparator;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetAbstractListTable(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.rows = new ArrayList<ITEM_CONTAINER>();
    this.rowMap = new HashMap<HashKey<ROW>, ITEM_CONTAINER>();
    this.comparator = new ColumnContainerComparator<ROW>();
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
  public List<ITEM_CONTAINER> getAllAvailableItems() {

    return this.rows;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ITEM_CONTAINER getLastAvailableItem() {

    return this.rows.get(this.rows.size() - 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ITEM_CONTAINER getItemContainer(ROW item) {

    return this.rowMap.get(new HashKey<ROW>(item));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetSelected(ITEM_CONTAINER container, boolean selected) {

    container.setSelected(selected);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row) {

    addRow(row, this.rows.size());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row, int index) {

    ITEM_CONTAINER rowContainer = createRowContainer(row);
    this.rowMap.put(new HashKey<ROW>(row), rowContainer);
    this.rows.add(index, rowContainer);
    if (hasWidgetAdapter()) {
      getWidgetAdapter().addRow(rowContainer, index);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeRow(ROW row) {

    ITEM_CONTAINER rowContainer = removeRowFromMap(row);
    if (rowContainer == null) {
      return false;
    }
    this.rows.remove(rowContainer);
    if (hasWidgetAdapter()) {
      getWidgetAdapter().removeRow(rowContainer);
    }
    return true;
  }

  /**
   * Removes the given row from the internal {@link Map}.
   * 
   * @param row is the {@literal <ROW>} to remove or replace.
   * @return the {@link ItemContainer} for the given <code>row</code> that has been removed or
   *         <code>null</code> if none was found.
   */
  private ITEM_CONTAINER removeRowFromMap(ROW row) {

    HashKey<ROW> key = new HashKey<ROW>(row);
    ITEM_CONTAINER rowContainer = this.rowMap.remove(key);
    if (rowContainer == null) {
      return null;
    }
    ROW duplicate = rowContainer.getItemEdited();
    if (duplicate == row) {
      duplicate = rowContainer.getItemOriginal();
    }
    if (duplicate != null) {
      key = new HashKey<ROW>(duplicate);
      this.rowMap.remove(key);
    }
    return rowContainer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRowIndex(ROW row) {

    for (int i = 0; i < this.rows.size(); i++) {
      if (this.rows.get(i).isContainerForItem(row)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean replaceRow(ROW oldRow, ROW newRow) {

    if (oldRow == newRow) {
      return true;
    }
    ITEM_CONTAINER rowContainer = removeRowFromMap(oldRow);
    if (rowContainer == null) {
      return false;
    }
    rowContainer.setItemOriginal(newRow);
    rowContainer.setItemEdited(null);
    this.rowMap.put(new HashKey<ROW>(newRow), rowContainer);
    // TODO hohwille what about sorting? We need to insert the new row according to the current sort order...
    // or for simplicity trigger the sorting again...

    if (hasWidgetAdapter()) {
      // TODO hohwille update row...
      // getWidgetAdapter().updateRow(rowContainer);
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (!this.rows.isEmpty()) {
      // adapter.setRows(rows);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder) {

    this.comparator.sort(this.rows, column, sortOrder);
    super.sort(column, sortOrder);
    if (hasWidgetAdapter()) {
      getWidgetAdapter().sort(column, sortOrder);
    }
  }

}
