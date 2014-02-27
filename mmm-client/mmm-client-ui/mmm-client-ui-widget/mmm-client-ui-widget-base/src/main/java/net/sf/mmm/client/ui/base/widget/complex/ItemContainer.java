/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelected;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the interface for a container for an {@literal <ITEM>} of a {@link AbstractUiWidgetAbstractDataSet
 * data set widget}. It can be implemented toolkit specific implementation that contains the raw widget to
 * render the item.
 * 
 * @param <ITEM> is the generic type of the {@link #getItem() contained} item.
 * @param <SELF> is the generic type of this container itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ItemContainer<ITEM, SELF extends ItemContainer<ITEM, SELF>> extends AttributeWriteSelected,
    AttributeWriteSelectionMode, AttributeReadValue<ITEM> {

  /**
   * @see #getItemEdited()
   * 
   * @return the original {@literal <ROW>} value provided by the end-user for this row in a data-table.
   */
  ITEM getItemOriginal();

  /**
   * @param row is the {@link #getItemOriginal() original row} to set.
   */
  void setItemOriginal(ITEM row);

  /**
   * @return the copy of the {@link #getItemOriginal() original row} that is lazily created on first editing.
   *         Will be <code>null</code> before the row has been edited.
   */
  ITEM getItemEdited();

  /**
   * @param row is the {@link #getItemEdited() edited row} to set. Should to be a copy of the
   *        {@link #getItemOriginal() original row}. May be <code>null</code> to reset.
   */
  void setItemEdited(ITEM row);

  /**
   * @return the {@link #getItemEdited() edited row} if not <code>null</code> else the
   *         {@link #getItemOriginal() original row}.
   */
  ITEM getItem();

  /**
   * This method does the same as {@link #getItem()}.
   * 
   * {@inheritDoc}
   */
  @Override
  ITEM getValue();

  /**
   * @param row is the {@literal <ROW>} to check.
   * @return <code>true</code> if this is the {@link ItemContainer} responsible for the given <code>row</code>
   *         <code>false</code> otherwise.
   */
  boolean isContainerForItem(ITEM row);

}
