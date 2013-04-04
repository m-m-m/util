/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table;

import java.util.List;

import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.table.model.UiModelListTable;
import net.sf.mmm.client.ui.api.widget.table.model.UiModelTableColumn;

/**
 * This is the interface for a {@link UiWidgetComposite composite widget} that represents a
 * <em>list table</em>. Such widget represents a table showing rows of data with their attributes in columns.
 * It has the following features:
 * <ul>
 * <li>Configured via {@link #setModel(UiModelListTable)} with a number of
 * {@link UiModelListTable#getColumns() columns}.</li>
 * <li>Each column defines {@link UiModelTableColumn#getId() ID}, {@link UiModelTableColumn#getTitle() title},
 * and {@link UiModelTableColumn#getTooltip() tooltip} that will be used for the header of the column.</li>
 * <li>Scrolling of the table rows as needed while header (and potential footer) remains steady.</li>
 * <li>{@link UiModelTableColumn columns} can be {@link UiModelTableColumn#getSortComparator() sorted} by
 * clicking the column header. Clicking again will swap between ascending and descending order what is also
 * visualized by an arrow icon.</li>
 * <li>{@link UiModelTableColumn columns} can be {@link UiModelTableColumn#isResizable() resized} by clicking
 * to the right of the column header and moving the right column border horizontally.</li>
 * <li>{@link UiModelTableColumn columns} can be {@link UiModelTableColumn#isReorderable() reordered} by
 * dragging them around.</li>
 * <li>Inline {@link UiModelTableColumn#isEditable() editing} of data by double-clicking on cells or
 * programmatically.</li>
 * <li>{@link UiModelListTable}</li>
 * </ul>
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetListTable<ROW> extends UiWidgetComposite<UiWidgetRegular>, UiWidgetListBase<ROW>, UiWidgetReal {

  /**
   * {@inheritDoc}
   */
  @Override
  List<ROW> getValue();

  /**
   * This method will set the {@link UiModelListTable model} of this list-table-widget. If this method is
   * called again, the old model will replaced.<br/>
   * <b>ATTENTION:</b><br/>
   * Changes of your model that happen after this method has been called are NOT automatically reflected by
   * this widget. You can also call this method again with the same model instance to invalidate and update
   * the internal model to reflect all changes of the model. Example for such changes are
   * {@link UiModelListTable#getColumns() columns} that have been added, removed, or changed their
   * {@link net.sf.mmm.client.ui.api.widget.table.model.UiModelTableColumn#isVisible() visible} or
   * {@link net.sf.mmm.client.ui.api.widget.table.model.UiModelTableColumn#isEditable() editable} status.<br/>
   * However, operations like {@link UiModelListTable#createNewRow()},
   * {@link net.sf.mmm.client.ui.api.widget.table.model.UiModelTableColumn#getPropertyAccessor()},
   * {@link net.sf.mmm.client.ui.api.widget.table.model.UiModelTableColumn#createCellWidget()}, or
   * {@link net.sf.mmm.client.ui.api.widget.table.model.UiModelTableColumn#getSortComparator()} are always
   * called again when needed to reflect the current behavior of your model. It is recommended that these
   * operations are stateless though NOT technically required.
   * 
   * @param model is the {@link UiModelListTable model}.
   */
  void setModel(UiModelListTable<ROW> model);

}
