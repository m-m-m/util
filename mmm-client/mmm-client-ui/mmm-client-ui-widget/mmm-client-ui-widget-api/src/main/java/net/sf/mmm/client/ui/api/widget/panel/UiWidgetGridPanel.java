/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.aria.role.RolePresentation;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

/**
 * This is the interface for a {@link UiWidgetDynamicPanel dynamic panel} that represents a flexible grid. It
 * consists of {@link UiWidgetGridRow rows} that themselves consists of {@link UiWidgetGridCell cells}. You
 * can think of this widget as an HTML table used for layout (role=presentation).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetGridPanel extends UiWidgetDynamicPanel<UiWidgetGridRow>, UiWidgetReal {

  /**
   * {@inheritDoc}
   * 
   * Here {@link RolePresentation} is used so the implementation can use a table as layout element.
   */
  @Override
  RolePresentation getAriaRole();

  /**
   * This method creates a {@link UiWidgetGridRow}, {@link UiWidgetGridRow#addChild(UiWidgetRegular) adds} the
   * given <code>children</code> to it and then {@link #addChild(UiWidgetGridRow) adds} it to this
   * {@link UiWidgetGridPanel}.
   * 
   * @param children are the child widgets to show in a {@link UiWidgetGridRow row} of this panel.
   * @return the created {@link UiWidgetGridRow}.
   */
  UiWidgetGridRow addChildren(UiWidgetRegular... children);

  /**
   * Adds an empty {@link UiWidgetGridRow row} that acts as a spacer to group blocks of rows with widgets.
   * This row will have the {@link #setStyles(String) style}
   * {@link net.sf.mmm.client.ui.api.common.CssStyles#GRID_ROW_SEPARATOR}.
   */
  void addSeparatorRow();

}
