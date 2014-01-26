/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.TableCellHeader;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;

import com.google.gwt.user.client.ui.InlineLabel;

/**
 * This is the implementation of {@link UiWidgetAdapterTableColumn} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTableColumn extends UiWidgetAdapterGwtWidget<TableCellHeader> implements
    UiWidgetAdapterTableColumn {

  /** @see #getHeaderLabel() */
  private InlineLabel headerLabel;

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
      TableCellHeader toplevelWidget) {

    super(toplevelWidget);
    this.dataTableAdapter = dataTableAdapter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TableCellHeader createToplevelWidget() {

    TableCellHeader tableCellHeader = new TableCellHeader();
    this.headerLabel = new InlineLabel();
    tableCellHeader.add(this.headerLabel);
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
