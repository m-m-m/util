/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;

/**
 * This is the abstract interface for a {@link UiWidgetAbstractDataTable data table widget} that represents a
 * <em>list table</em>.<br/>
 * <b>Note:</b><br/>
 * There are the following variants of this abstract list table widget:
 * <ul>
 * <li>{@link UiWidgetListTable}</li>
 * <li>{@link UiWidgetOptionListTable}</li>
 * </ul>
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAbstractListTable<ROW> extends UiWidgetAbstractDataTable<ROW>, UiWidgetListBase<ROW> {

  // nothing to add...

}
