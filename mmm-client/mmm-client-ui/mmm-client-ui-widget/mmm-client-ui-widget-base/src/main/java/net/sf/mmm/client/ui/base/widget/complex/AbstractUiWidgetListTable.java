/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterListTable;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the base implementation of {@link UiWidgetListTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * @param <ITEM_CONTAINER> is the generic type of the {@link ItemContainer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetListTable<ADAPTER extends UiWidgetAdapterListTable<ROW, ITEM_CONTAINER>, ROW, ITEM_CONTAINER extends ItemContainer<ROW, ITEM_CONTAINER>>
    extends AbstractUiWidgetAbstractListTable<ADAPTER, ROW, ITEM_CONTAINER> implements UiWidgetListTable<ROW> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetListTable(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(List<ROW> newValue, boolean forUser) {

    super.doSetValue(newValue, forUser);
    boolean hasWidgetAdapter = hasWidgetAdapter();
    List<ITEM_CONTAINER> rows = getAllAvailableItems();
    int i = 0;
    int size = rows.size();
    ITEM_CONTAINER container;
    for (ROW newRow : newValue) {
      if (i < size) {
        container = rows.get(i);
        if (forUser) {
          container.setItemEdited(newRow);
        } else {
          container.setItemOriginal(newRow);
          container.setItemEdited(null);
        }
      } else {
        container = createRowContainer(newRow);
        rows.add(container);
        if (hasWidgetAdapter) {
          getWidgetAdapter().addRow(container, i);
        }
      }
      i++;
    }
    for (int j = rows.size() - 1; j >= i; j--) {
      // TODO potentially pool obsolete rows for later reuse...
      ITEM_CONTAINER removedRow = rows.remove(j);
      if (hasWidgetAdapter) {
        getWidgetAdapter().removeRow(removedRow);
      }
    }
    getSelectedValuesInternal().clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<ROW> doGetValue(List<ROW> template, ValidationState state) throws RuntimeException {

    Collection<ITEM_CONTAINER> rows = getAllAvailableItems();
    List<ROW> result = new ArrayList<ROW>(rows.size());
    for (ItemContainer<ROW, ?> container : rows) {
      result.add(container.getItem());
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    int i = 0;
    for (ITEM_CONTAINER row : getAllAvailableItems()) {
      getWidgetAdapter().addRow(row, i++);
    }

  }

}
