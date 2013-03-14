/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table;

import java.util.List;

import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;

/**
 * This is the interface for a {@link UiWidgetComposite composite widget} that represents a
 * <em>list table</em>.
 * 
 * @param <ROW> is the generic type of the {@link #getOptions() options}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetListTable<ROW> extends UiWidgetComposite<UiWidgetRegular>, UiWidgetListBase<ROW>,
    UiWidgetReal, UiWidgetWithValue<List<ROW>> {

  /**
   * 
   * @param columns
   */
  void setColumns(UiModelTableColumn<ROW, ?>... columns);

}
