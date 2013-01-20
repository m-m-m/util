/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridRow;

/**
 * This is the abstract base implementation of {@link UiWidgetGridCell}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetGridRow<ADAPTER extends UiWidgetAdapterGridRow<?>> extends
    AbstractUiWidgetDynamicPanel<ADAPTER, UiWidgetGridCell> implements UiWidgetGridRow {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetGridRow(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetGridCell addChild(UiWidgetRegular child) {

    if (child instanceof UiWidgetField) {
      UiWidgetLabel label = ((UiWidgetField<?>) child).getFieldLabelWidget();
      addChildInternal(label);
    }
    return addChildInternal(child);
  }

  /**
   * @see #addChild(UiWidgetRegular)
   * 
   * @param child is the child widget to add.
   * @return the {@link UiWidgetGridCell}.
   */
  private UiWidgetGridCell addChildInternal(UiWidgetRegular child) {

    UiWidgetGridCell cell = getContext().getWidgetFactory().create(UiWidgetGridCell.class);
    cell.setChild(child);
    addChild(cell);
    return cell;
  }

}
