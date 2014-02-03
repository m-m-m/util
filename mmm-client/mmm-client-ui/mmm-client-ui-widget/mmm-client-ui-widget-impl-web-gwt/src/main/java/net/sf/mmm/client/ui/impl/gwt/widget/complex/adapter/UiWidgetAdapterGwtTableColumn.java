/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.client.ui.gwt.widgets.IconCssWidget;
import net.sf.mmm.client.ui.gwt.widgets.TableCellHeaderComposite;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;
import net.sf.mmm.util.lang.api.SortOrder;

import com.google.gwt.user.client.ui.InlineLabel;

/**
 * This is the implementation of {@link UiWidgetAdapterTableColumn} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTableColumn extends UiWidgetAdapterGwtWidget<TableCellHeaderComposite> implements
    UiWidgetAdapterTableColumn {

  /** @see #getHeaderLabel() */
  private InlineLabel headerLabel;

  /** @see #setSortOrder(SortOrder) */
  private IconCssWidget sortIcon;

  /** @see #getDataTableAdapter() */
  private final UiWidgetAdapterGwtAbstractDataTable<?> dataTableAdapter;

  /**
   * The constructor.
   * 
   * @param dataTableAdapter is the {@link #getDataTableAdapter() data table adapter}.
   */
  public UiWidgetAdapterGwtTableColumn(UiWidgetAdapterGwtAbstractDataTable<?> dataTableAdapter) {

    super();
    this.dataTableAdapter = dataTableAdapter;
  }

  /**
   * The constructor.
   * 
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   * @param dataTableAdapter is the {@link #getDataTableAdapter() data table adapter}.
   */
  public UiWidgetAdapterGwtTableColumn(UiWidgetAdapterGwtAbstractDataTable<?> dataTableAdapter,
      TableCellHeaderComposite toplevelWidget) {

    super(toplevelWidget);
    this.dataTableAdapter = dataTableAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TableCellHeaderComposite createToplevelWidget() {

    TableCellHeaderComposite tableCellHeader = new TableCellHeaderComposite();
    this.headerLabel = new InlineLabel();
    tableCellHeader.add(this.headerLabel);
    this.sortIcon = new IconCssWidget(CssStyles.SORT_ICON_NONE);
    tableCellHeader.add(this.sortIcon);
    return tableCellHeader;
  }

  /**
   * @return the headerLabel
   */
  public InlineLabel getHeaderLabel() {

    return this.headerLabel;
  }

  /**
   * @return the dataTableAdapter
   */
  public UiWidgetAdapterGwtAbstractDataTable<?> getDataTableAdapter() {

    return this.dataTableAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSortOrder(SortOrder sortOrder) {

    String style;
    if (sortOrder == null) {
      style = CssStyles.SORT_ICON_NONE;
    } else if (sortOrder == SortOrder.ASCENDING) {
      style = CssStyles.SORT_ICON_ASCENDING;
    } else {
      style = CssStyles.SORT_ICON_DESCENDING;
    }
    this.sortIcon.setStyleName(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    this.headerLabel.setText(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setReorderable(boolean reorderable) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReorderable() {

    // TODO Auto-generated method stub
    return false;
  }

}
