/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.common.SelectionMode;
import net.sf.mmm.client.ui.base.widget.complex.AbstractItemContainer;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetAbstractDataTable;
import net.sf.mmm.client.ui.gwt.widgets.TableCellAtomic;
import net.sf.mmm.client.ui.gwt.widgets.TableRow;
import net.sf.mmm.util.nls.api.IllegalCaseException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.SimpleCheckBox;

/**
 * This class is a simple container for a {@literal <ROW>} of a data table in combination with its widgets to
 * render the row.
 * 
 * @param <ROW> is the generic type of the {@link #getValue() contained} row.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ItemContainerGwt<ROW> extends AbstractItemContainer<ROW, ItemContainerGwt<ROW>> implements
    ClickHandler {

  /** @see #getTableRow() */
  private TableRow tableRow;

  /** @see #getRowNumberCell() */
  private TableCellAtomic rowNumberCell;

  /** @see #setSelected(boolean) */
  private SimpleCheckBox multiSelectCheckbox;

  /**
   * The constructor.
   * 
   * @param dataTable is the {@link AbstractUiWidgetAbstractDataTable} creating and owning this
   *        {@link ItemContainerGwt}.
   */
  public ItemContainerGwt(AbstractUiWidgetAbstractDataTable<?, ROW, ItemContainerGwt<ROW>> dataTable) {

    super(dataTable);
  }

  /**
   * @return the {@link TableRow} or <code>null</code> if not set.
   */
  public TableRow getTableRow() {

    return this.tableRow;
  }

  /**
   * @param tableRow is the {@link TableRow} to set.
   */
  public void setTableRow(TableRow tableRow) {

    this.tableRow = tableRow;
  }

  /**
   * @return the multiSelectCheckbox
   */
  public SimpleCheckBox getMultiSelectCheckbox() {

    if (this.multiSelectCheckbox == null) {
      this.multiSelectCheckbox = new SimpleCheckBox();
      TableCellAtomic cell = new TableCellAtomic();
      cell.add(this.multiSelectCheckbox);
      if (this.rowNumberCell == null) {
        getTableRow().insert(cell, 0);
      } else {
        getTableRow().insert(cell, 1);
      }
      this.multiSelectCheckbox.addClickHandler(this);
    }
    return this.multiSelectCheckbox;
  }

  /**
   * @return the cell showing the row number.
   */
  public TableCellAtomic getRowNumberCell() {

    if (this.rowNumberCell == null) {
      this.rowNumberCell = new TableCellAtomic();
      this.rowNumberCell.setStyleName(CssStyles.ROW_NUMBER);
      getTableRow().insert(this.multiSelectCheckbox, 0);
    }
    return this.rowNumberCell;
  }

  /**
   * @param row is the current number of this row (starting with 1).
   */
  public void setRowNumber(int row) {

    getRowNumberCell().setText(Integer.toString(row));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    boolean selected = isSelected();
    updateSelectionStyle(selected);
    getUiWidget().onItemSelection(this, selected, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSelected() {

    switch (getSelectionMode()) {
      case MULTIPLE_SELECTION:
        return this.multiSelectCheckbox.getValue().booleanValue();
      case SINGLE_SELECTION:
        return (getUiWidget().getSelectedValue() == getValue());
      default :
        throw new IllegalCaseException(SelectionMode.class, getSelectionMode());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelected(boolean selected) {

    updateSelectionStyle(selected);

    switch (getSelectionMode()) {
      case MULTIPLE_SELECTION:
        this.multiSelectCheckbox.setValue(Boolean.valueOf(selected));
        break;
      case SINGLE_SELECTION:
        // nothing to do here...
        break;
      default :
        throw new IllegalCaseException(SelectionMode.class, getSelectionMode());
    }
  }

  /**
   * @param selected <code>true</code> for selected, <code>false</code> for de-selected.
   */
  private void updateSelectionStyle(boolean selected) {

    if (selected) {
      getTableRow().addStyleName(CssStyles.SELECTED);
    } else {
      getTableRow().removeStyleName(CssStyles.SELECTED);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectionMode(SelectionMode selectionMode) {

    switch (selectionMode) {
      case MULTIPLE_SELECTION:
        getMultiSelectCheckbox().setVisible(true);
        break;
      case SINGLE_SELECTION:
        if (this.multiSelectCheckbox != null) {
          this.multiSelectCheckbox.setVisible(false);
        }
        break;
      default :
        throw new IllegalCaseException(SelectionMode.class, selectionMode);
    }
  }

}
