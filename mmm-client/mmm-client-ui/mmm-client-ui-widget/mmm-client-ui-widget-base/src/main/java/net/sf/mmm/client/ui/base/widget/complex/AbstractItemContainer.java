/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import net.sf.mmm.client.ui.api.common.SelectionMode;

/**
 * This is the abstract base implementation of {@link ItemContainer}.
 * 
 * @param <ITEM> is the generic type of the {@link #getItem() contained} row.
 * @param <SELF> is the generic type of this container itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractItemContainer<ITEM, SELF extends AbstractItemContainer<ITEM, SELF>> implements
    ItemContainer<ITEM, SELF> {

  /** @see #getUiWidget() */
  private final AbstractUiWidgetAbstractDataSet<?, ?, ITEM, SELF> uiWidget;

  /** @see #getItemOriginal() */
  private ITEM itemOriginal;

  /** @see #getItemEdited() */
  private ITEM itemEdited;

  /**
   * The constructor.
   * 
   * @param widget is the {@link AbstractUiWidgetAbstractDataSet widget} creating and owning this
   *        {@link AbstractItemContainer}.
   */
  public AbstractItemContainer(AbstractUiWidgetAbstractDataSet<?, ?, ITEM, SELF> widget) {

    super();
    this.uiWidget = widget;
  }

  /**
   * @return the {@link AbstractUiWidgetAbstractDataSet} that created this {@link AbstractItemContainer}.
   */
  protected AbstractUiWidgetAbstractDataSet<?, ?, ITEM, SELF> getUiWidget() {

    return this.uiWidget;
  }

  /**
   * {@inheritDoc}
   */
  public ITEM getItemOriginal() {

    return this.itemOriginal;
  }

  /**
   * {@inheritDoc}
   */
  public void setItemOriginal(ITEM row) {

    this.itemOriginal = row;
  }

  /**
   * {@inheritDoc}
   */
  public ITEM getItemEdited() {

    return this.itemEdited;
  }

  /**
   * {@inheritDoc}
   */
  public void setItemEdited(ITEM row) {

    this.itemEdited = row;
  }

  /**
   * {@inheritDoc}
   */
  public ITEM getItem() {

    if (this.itemEdited != null) {
      return this.itemEdited;
    }
    return this.itemOriginal;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ITEM getValue() {

    return getItem();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isContainerForItem(ITEM row) {

    if (row == this.itemOriginal) {
      return true;
    }
    if (row == this.itemEdited) {
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public SelectionMode getSelectionMode() {

    return this.uiWidget.getSelectionMode();
  }

}
