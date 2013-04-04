/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table.model;

import java.util.List;

/**
 * This is the abstract interface for the model of a table-widget such as
 * {@link net.sf.mmm.client.ui.api.widget.table.UiWidgetListTable}.
 * 
 * @param <ROW> is the generic type of the element representing a row of the table. It should be a java-bean
 *        oriented {@link net.sf.mmm.util.pojo.api.Pojo}. Immutable objects (that have no setters) can also be
 *        used but only for read-only tables.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AbstractUiModelTable<ROW> {

  /**
   * This method gets the "normal" columns of the table represented by this model.
   * 
   * @return the {@link List} with the {@link UiModelTableColumn column models}.
   */
  List<? extends UiModelTableColumn<ROW, ?>> getColumns();

  /**
   * This method creates a new, empty row. This method is only used if the table-widget is editable and
   * supports adding new rows.<br/>
   * <b>NOTE:</b><br/>
   * It is then assumed that &lt;ROW&gt; is a java-bean oriented {@link net.sf.mmm.util.pojo.api.Pojo} that
   * can be modified via the {@link UiModelTableColumn#getPropertyAccessor() property accessors} of the
   * {@link #getColumns() columns}. In other words inline table-editing of immutable objects that require all
   * attributes (cell-values) at construction is (currently) NOT supported.
   * 
   * @return the new row instance.
   */
  ROW createNewRow();

}
