/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.grid;

import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;

/**
 * This is the interface for a {@link UiWidgetComposite composite widget} that represents a <em>list grid</em>
 * .
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ROW> is the generic type of the {@link #getOptions() options}.
 */
public interface UiWidgetListGrid<ROW> extends UiWidgetComposite<UiWidgetReal>, UiWidgetListBase<ROW> {

  /**
   * 
   * @param columns
   */
  void setColumns(GridColumn<ROW, ?>... columns);

}
