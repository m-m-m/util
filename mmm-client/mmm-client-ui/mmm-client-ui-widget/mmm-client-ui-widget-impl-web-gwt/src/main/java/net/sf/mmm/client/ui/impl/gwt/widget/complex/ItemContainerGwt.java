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
import com.google.gwt.user.client.ui.RadioButton;
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
public class ItemContainerGwt<ROW> extends AbstractItemContainer<ROW, ItemContainerGwt<ROW>> implements ClickHandler {

  /** @see #getTableRow() */
  private TableRow tableRow;

  /** @see #getTableRow() */
  private TableRow originalTableRow;

  /** @see #getRowNumberCell() */
  private TableCellAtomic rowNumberCell;

  /** @see #setSelected(boolean) */
  private SimpleCheckBox multiSelectionCheckbox;

  /** @see #setSelected(boolean) */
  private RadioButton singleSelectionRadioButton;

  /** @see #setSelectionMode(SelectionMode) */
  private TableCellAtomic selectionCell;

  /**
   * The constructor.
   * 
   * @param dataTable is the {@link AbstractUiWidgetAbstractDataTable} creating and owning this
   *        {@link ItemContainerGwt}.
   */
  public ItemContainerGwt(AbstractUiWidgetAbstractDataTable<?, ?, ROW, ItemContainerGwt<ROW>> dataTable) {

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
    if (this.originalTableRow == null) {
      this.originalTableRow = tableRow;
      initTableRow();
    }
  }

  /**
   * Initializes the current table row.
   */
  private void initTableRow() {

    AbstractUiWidgetAbstractDataTable<?, ?, ROW, ItemContainerGwt<ROW>> uiWidget = (AbstractUiWidgetAbstractDataTable<?, ?, ROW, ItemContainerGwt<ROW>>) getUiWidget();
    if (uiWidget.hasRowNumberColumn()) {
      this.tableRow.add(getRowNumberCell());
    }
    this.tableRow.add(getSelectionCell());
    setSelectionMode(uiWidget.getSelectionMode());
  }

  /**
   * @return the multiSelectCheckbox
   */
  public SimpleCheckBox getMultiSelectionCheckbox() {

    if (this.multiSelectionCheckbox == null) {
      this.multiSelectionCheckbox = new SimpleCheckBox();
      this.multiSelectionCheckbox.addClickHandler(this);
    }
    return this.multiSelectionCheckbox;
  }

  /**
   * @return the singleSelectRadioButton
   */
  public RadioButton getSingleSelectionRadioButton() {

    if (this.singleSelectionRadioButton == null) {
      // TODO: id might be changed on the fly by the user
      this.singleSelectionRadioButton = new RadioButton(getUiWidget().getId());
      this.singleSelectionRadioButton.addClickHandler(this);
    }
    return this.singleSelectionRadioButton;
  }

  /**
   * @return the selectionCell
   */
  public TableCellAtomic getSelectionCell() {

    if (this.selectionCell == null) {
      this.selectionCell = new TableCellAtomic();
    }
    return this.selectionCell;
  }

  /**
   * @return the cell showing the row number.
   */
  public TableCellAtomic getRowNumberCell() {

    if (this.rowNumberCell == null) {
      this.rowNumberCell = new TableCellAtomic();
      this.rowNumberCell.setStyleName(CssStyles.ROW_NUMBER);
      getTableRow().insert(this.rowNumberCell, 0);
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
        return this.multiSelectionCheckbox.getValue().booleanValue();
      case SINGLE_SELECTION:
        // return (getUiWidget().getSelectedValue() == getValue());
        return this.singleSelectionRadioButton.getValue().booleanValue();
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
        this.multiSelectionCheckbox.setValue(Boolean.valueOf(selected));
        break;
      case SINGLE_SELECTION:
        this.singleSelectionRadioButton.setValue(Boolean.valueOf(selected));
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
      this.originalTableRow.addStyleName(CssStyles.SELECTED);
    } else {
      this.originalTableRow.removeStyleName(CssStyles.SELECTED);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setSelectionMode(SelectionMode selectionMode) {

    switch (selectionMode) {
      case MULTIPLE_SELECTION:
        getSelectionCell().setChild(getMultiSelectionCheckbox());
        break;
      case SINGLE_SELECTION:
        getSelectionCell().setChild(getSingleSelectionRadioButton());
        break;
      default :
        throw new IllegalCaseException(SelectionMode.class, selectionMode);
    }
  }

}
