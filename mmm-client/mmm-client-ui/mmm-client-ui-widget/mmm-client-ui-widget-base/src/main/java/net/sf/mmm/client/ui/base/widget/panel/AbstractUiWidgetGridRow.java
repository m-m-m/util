/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridRow;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the abstract base implementation of {@link UiWidgetGridCell}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetGridRow<ADAPTER extends UiWidgetAdapterGridRow> extends
    AbstractUiWidgetDynamicPanel<ADAPTER, UiWidgetGridCell> implements UiWidgetGridRow {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetGridRow(UiContext context) {

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

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetGridCell child) {

    assert verifyColumnCount(child, -1);
    super.addChild(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetGridCell child, int index) {

    assert verifyColumnCount(child, index);
    super.addChild(child, index);
  }

  /**
   * Verifies that the column count is not exceeded after addChild operation.
   * 
   * @param child is the child to add.
   * @param index is the insert position or <code>-1</code> for add at end.
   * @return <code>true</code> if valid, <code>false</code> (or Exception) otherwise.
   */
  private boolean verifyColumnCount(UiWidgetGridCell child, int index) {

    UiWidgetComposite<?> parent = getParent();
    if ((parent != null) && (parent instanceof UiWidgetGridPanel)) {
      UiWidgetGridPanel gridPanel = (UiWidgetGridPanel) parent;
      Integer columnCount = gridPanel.getColumnCount();
      if (columnCount != null) {
        int childCount = getChildCount();
        int newColumnCount = 0;
        for (int i = 0; i < childCount; i++) {
          UiWidgetGridCell cell = getChild(i);
          if ((i != index) && (cell != null)) {
            int columnSpan = cell.getColumnSpan();
            newColumnCount = newColumnCount + columnSpan;
          }
        }
        if (child != null) {
          newColumnCount = child.getColumnSpan();
        }
        if (newColumnCount > columnCount.intValue()) {
          throw new ValueOutOfRangeException(Integer.valueOf(newColumnCount), Integer.valueOf(0), columnCount,
              "columnCount of " + this);
        }
        return (newColumnCount <= columnCount.intValue());
      }
    }
    return true;
  }

}
