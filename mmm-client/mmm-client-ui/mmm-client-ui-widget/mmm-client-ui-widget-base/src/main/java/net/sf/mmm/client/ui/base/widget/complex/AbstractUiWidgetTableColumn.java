/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.Comparator;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetNative;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.value.api.PropertyAccessor;

/**
 * This is the abstract base implementation of {@link UiWidgetTableColumn}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of the element representing a row of the grid. It should be a java-bean
 *        oriented object. Immutable objects (that have no setters) can also be used but only for read-only
 *        tables.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetTableColumn<ADAPTER extends UiWidgetAdapterTableColumn, ROW, CELL> extends
    AbstractUiWidgetNative<ADAPTER, CELL> implements UiWidgetTableColumn<ROW, CELL> {

  /** @see #getListTable() */
  private final AbstractUiWidgetAbstractListTable<?, ROW> listTable;

  /** @see #setPropertyAccessor(PropertyAccessor) */
  private PropertyAccessor<ROW, CELL> propertyAccessor;

  /** @see #getSortComparator() */
  private Comparator<CELL> sortComparator;

  /** @see #getTitle() */
  private String title;

  /** @see #isReorderable() */
  private boolean reorderable;

  /** @see #isEditable() */
  private boolean editable;

  /** @see #isResizable() */
  private boolean resizable;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param listTable is the {@link AbstractUiWidgetAbstractListTable list table} this column is connected to.
   */
  public AbstractUiWidgetTableColumn(UiContext context, AbstractUiWidgetAbstractListTable<?, ROW> listTable) {

    super(context);
    this.listTable = listTable;
  }

  /**
   * @return the propertyAccessor
   */
  PropertyAccessor<ROW, CELL> getPropertyAccessor() {

    return this.propertyAccessor;
  }

  /**
   * @param propertyAccessor is the propertyAccessor to set
   */
  void setPropertyAccessor(PropertyAccessor<ROW, CELL> propertyAccessor) {

    this.propertyAccessor = propertyAccessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    this.title = title;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setTitle(title);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReorderable() {

    return this.reorderable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setReorderable(boolean reorderable) {

    this.reorderable = reorderable;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setReorderable(reorderable);
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
  public void setEditable(boolean editableFlag) {

    this.editable = editableFlag;
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().setEditable(editableFlag);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    return this.resizable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    this.resizable = resizable;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setResizable(resizable);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSortComparator(Comparator<CELL> sortComparator) {

    NlsNullPointerException.checkNotNull(Comparator.class, sortComparator);
    this.sortComparator = sortComparator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Comparator<CELL> getSortComparator() {

    return this.sortComparator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sort(SortOrder order) {

    this.listTable.sort(this);
  }

  /**
   * @return the {@link AbstractUiWidgetAbstractListTable list table} owning this column.
   */
  public AbstractUiWidgetAbstractListTable<?, ROW> getListTable() {

    return this.listTable;
  }

}
