/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelected;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This class is a simple container for a {@literal <ROW>} of a data table in combination with its widgets to
 * render the row.
 * 
 * @param <ROW> is the generic type of the {@link #getValue() contained} row.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class TableRowContainer<ROW> implements AttributeWriteValue<ROW>, AttributeWriteSelected,
    AttributeWriteSelectionMode {

  /** @see #getDataTable() */
  private final AbstractUiWidgetAbstractDataTable<?, ROW> dataTable;

  /** @see #getValue() */
  private ROW row;

  /**
   * The constructor.
   * 
   * @param dataTable is the {@link AbstractUiWidgetAbstractDataTable} creating and owning this
   *        {@link TableRowContainer}.
   */
  public TableRowContainer(AbstractUiWidgetAbstractDataTable<?, ROW> dataTable) {

    super();
    this.dataTable = dataTable;
  }

  /**
   * @return the {@link AbstractUiWidgetAbstractDataTable} that created this {@link TableRowContainer}.
   */
  protected AbstractUiWidgetAbstractDataTable<?, ROW> getDataTable() {

    return this.dataTable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ROW getValue() {

    return this.row;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(ROW value) {

    this.row = value;
  }

  /**
   * {@inheritDoc}
   */
  public SelectionMode getSelectionMode() {

    return this.dataTable.getSelectionMode();
  }
  //
  // /**
  // * {@inheritDoc}
  // */
  // @SuppressWarnings("unchecked")
  // @Override
  // public boolean equals(Object obj) {
  //
  // if (obj == null) {
  // return false;
  // }
  // // ATTENTION: This implementation of equals is violating the contract of equals
  // // this is done on purpose for performance reasons and flexibility with equals checker
  // // However, this class shall never be passed to API users and is to be used only internal by complex
  // // widgets and their adapters.
  // ROW otherRow;
  // if (obj instanceof TableRowContainer) {
  // otherRow = ((TableRowContainer<ROW>) obj).row;
  // } else {
  // otherRow = (ROW) obj;
  // }
  // return this.dataTable.getRowEqualsChecker().isEqual(this.row, otherRow);
  // }
  //
  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public int hashCode() {
  //
  // return this.row.hashCode();
  // }

}
