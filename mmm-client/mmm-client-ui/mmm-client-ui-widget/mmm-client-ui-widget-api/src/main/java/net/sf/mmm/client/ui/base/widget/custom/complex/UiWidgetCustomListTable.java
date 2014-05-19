/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.complex;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable;

/**
 * This is the abstract base class for a {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom custom
 * widget} implementing {@link UiWidgetListTable}. A subclass should
 * {@link UiWidgetListTable#setColumns(java.util.List) set the columns} and other options to configure a
 * ready-to-use list that can be easily reused.
 *
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomListTable<ROW> extends UiWidgetCustomAbstractListTable<ROW, UiWidgetListTable<ROW>>
    implements UiWidgetListTable<ROW> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCustomListTable(UiContext context) {

    super(context, context.getWidgetFactory().create(UiWidgetListTable.class));
  }

}
