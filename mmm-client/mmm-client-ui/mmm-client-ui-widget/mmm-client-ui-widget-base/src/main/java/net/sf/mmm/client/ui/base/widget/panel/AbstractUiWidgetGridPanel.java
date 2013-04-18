/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.aria.role.RolePresentation;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridPanel;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of {@link UiWidgetGridPanel}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetGridPanel<ADAPTER extends UiWidgetAdapterGridPanel> extends
    AbstractUiWidgetDynamicPanel<ADAPTER, UiWidgetGridRow> implements UiWidgetGridPanel {

  /** @see #getColumnCount() */
  private Integer columnCount;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetGridPanel(UiContext context) {

    super(context);
    setPrimaryStyle(PRIMARY_STYLE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RolePresentation getAriaRole() {

    return (RolePresentation) super.getAriaRole();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getColumnCount() {

    return this.columnCount;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumnCount(int columnCount) {

    this.columnCount = Integer.valueOf(columnCount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetGridRow addChildSpanned(UiWidgetRegular child) {

    UiWidgetGridRow row = getContext().getWidgetFactory().create(UiWidgetGridRow.class);
    UiWidgetGridCell cell = row.addChild(child);
    if (this.columnCount == null) {
      cell.setColumnSpan(99);
    } else {
      cell.setColumnSpan(this.columnCount.intValue());
    }
    row.addChild(cell);
    addChild(row);
    return row;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetGridRow addChildren(UiWidgetRegular... children) {

    NlsNullPointerException.checkNotNull("children", children);
    UiWidgetGridRow row = getContext().getWidgetFactory().create(UiWidgetGridRow.class);
    for (UiWidgetRegular child : children) {
      row.addChild(child);
    }
    if (this.columnCount != null) {
      assert (row.getChildCount() == this.columnCount.intValue()) : "Child length (" + children.length
          + ") does not match column count (" + this.columnCount + ")";
    }
    addChild(row);
    return row;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addSeparatorRow() {

    UiWidgetGridRow row = getContext().getWidgetFactory().create(UiWidgetGridRow.class);
    row.addStyle(CssStyles.GRID_ROW_SEPARATOR);
    addChild(row);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<? extends Role> getAriaRoleFixedType() {

    return RolePresentation.class;
  }
}
