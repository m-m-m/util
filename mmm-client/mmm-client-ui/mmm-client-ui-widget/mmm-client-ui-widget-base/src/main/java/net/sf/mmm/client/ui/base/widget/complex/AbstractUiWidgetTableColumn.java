/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.Comparator;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactory;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetNative;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
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
  private final AbstractUiWidgetAbstractDataTable<?, ROW> listTable;

  /** @see #getTypedProperty() */
  private final TypedProperty<CELL> typedProperty;

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

  /** @see #getWidgetFactory() */
  private UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory;

  /** @see #getSortOrder() */
  private SortOrder sortOrder;

  /** @see #isAttached() */
  private boolean attached;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param listTable is the {@link AbstractUiWidgetAbstractListTable list table} this column is connected to.
   * @param typedProperty is the {@link #getTypedProperty() typed property} of the column to create. May be
   *        <code>null</code>.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetTableColumn(UiContext context, AbstractUiWidgetAbstractDataTable<?, ROW> listTable,
      TypedProperty<CELL> typedProperty, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
    this.listTable = listTable;
    this.typedProperty = typedProperty;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.title != null) {
      adapter.setTitle(this.title);
    }
    adapter.setReorderable(this.reorderable);
    adapter.setResizable(this.resizable);
    // adapter.setSortable(this.sortComparator != null);
    if (this.sortOrder != null) {
      adapter.setSortOrder(this.sortOrder);
    }
  }

  /**
   * @return the {@link TypedProperty} identifying the property managed with this column. May be
   *         <code>null</code> if the column was created with an explicit {@link #getPropertyAccessor()
   *         property accessor}. In such case the {@link #getWidgetFactory() widget factory} shall not be
   *         <code>null</code> or cells with <code>null</code> values can not be handled.
   */
  public TypedProperty<CELL> getTypedProperty() {

    return this.typedProperty;
  }

  /**
   * @return the {@link PropertyAccessor}.
   */
  public PropertyAccessor<ROW, CELL> getPropertyAccessor() {

    return this.propertyAccessor;
  }

  /**
   * @param propertyAccessor is the propertyAccessor to set
   */
  void setPropertyAccessor(PropertyAccessor<ROW, CELL> propertyAccessor) {

    this.propertyAccessor = propertyAccessor;
  }

  /**
   * @see net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable#createColumn(PropertyAccessor,
   *      UiSingleWidgetFactory, Comparator)
   * 
   * @return the {@link UiSingleWidgetFactory} used to create {@link UiWidgetWithValue widgets} to view (and
   *         potentially edit) the values of a cell in this column.
   */
  public UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> getWidgetFactory() {

    return this.widgetFactory;
  }

  /**
   * @param widgetFactory is the new {@link #getWidgetFactory() widget factory}.
   */
  void setWidgetFactory(UiSingleWidgetFactory<? extends UiWidgetWithValue<CELL>> widgetFactory) {

    this.widgetFactory = widgetFactory;
  }

  /**
   * @return the attached
   */
  boolean isAttached() {

    return this.attached;
  }

  /**
   * @param attached is the attached to set
   */
  void setAttached(boolean attached) {

    this.attached = attached;
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
  public void sort() {

    if (this.sortOrder == SortOrder.ASCENDING) {
      sort(SortOrder.DESCENDING);
    } else {
      sort(SortOrder.ASCENDING);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sort(SortOrder order) {

    this.listTable.sort(this, order);
  }

  /**
   * @return the current {@link SortOrder} of this column. May be <code>null</code> for no order.
   */
  public SortOrder getSortOrder() {

    return this.sortOrder;
  }

  /**
   * @param order is the new {@link SortOrder}. May be <code>null</code> to remove order.
   */
  public void setSortOrder(SortOrder order) {

    this.sortOrder = order;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setSortOrder(order);
    }
  }

  /**
   * @return the {@link AbstractUiWidgetAbstractListTable list table} owning this column.
   */
  public AbstractUiWidgetAbstractDataTable<?, ROW> getListTable() {

    return this.listTable;
  }

}
