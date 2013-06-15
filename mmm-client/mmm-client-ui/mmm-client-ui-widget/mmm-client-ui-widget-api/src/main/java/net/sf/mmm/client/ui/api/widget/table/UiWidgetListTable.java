/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table;

import java.util.List;

import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.model.UiListTableModel;

/**
 * This is the interface for a {@link UiWidgetComposite composite widget} that represents a
 * <em>list table</em>. Such widget represents a table showing rows of data with their attributes in columns.
 * It has the following features:
 * <ul>
 * <li>Configured via its {@link #getModel() model} with a number of
 * {@link net.sf.mmm.client.ui.api.widget.model.UiModelListTable#setColumns(UiWidgetTableColumn...) columns}.</li>
 * <li>Each column defines attributes like {@link UiWidgetTableColumn#getId() ID},
 * {@link UiWidgetTableColumn#getTitle() title}, and {@link UiWidgetTableColumn#getTooltip() tooltip} that
 * will be used for the header of the column.</li>
 * <li>Scrolling of the table rows as needed while header (and potential footer) remains steady.</li>
 * <li>{@link UiWidgetTableColumn columns} can be {@link UiWidgetTableColumn#getSortComparator() sorted} by
 * clicking the column header. Clicking again will swap between ascending and descending order what is also
 * visualized by an arrow icon.</li>
 * <li>{@link UiWidgetTableColumn columns} can be {@link UiWidgetTableColumn#isResizable() resized} by
 * clicking to the right of the column header and moving the right column border horizontally.</li>
 * <li>{@link UiWidgetTableColumn columns} can be {@link UiWidgetTableColumn#isReorderable() reordered} by
 * dragging them around.</li>
 * <li>{@link UiWidgetTableColumn columns} can be {@link UiWidgetTableColumn#isEditable() edited} by
 * double-clicking on cells or programmatically.</li>
 * <li>...</li>
 * </ul>
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetListTable<ROW> extends UiWidgetComposite<UiWidgetRegular>, UiWidgetListBase<ROW>,
    UiWidgetNative {

  /**
   * {@inheritDoc}
   */
  @Override
  List<ROW> getValue();

  /**
   * This method will set the {@link UiListTableModel model} of this list-table-widget.<br/>
   * 
   * @return the {@link UiListTableModel model}.
   */
  UiListTableModel<ROW> getModel();

}
