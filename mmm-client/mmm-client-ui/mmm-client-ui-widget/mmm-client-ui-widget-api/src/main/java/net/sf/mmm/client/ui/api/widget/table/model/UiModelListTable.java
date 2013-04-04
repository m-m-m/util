/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.table.model;

/**
 * This is the model for a {@link net.sf.mmm.client.ui.api.widget.table.UiWidgetListTable}.
 * 
 * @see net.sf.mmm.client.ui.api.widget.table.UiWidgetListTable#setModel(UiModelListTable)
 * 
 * @param <ROW> is the generic type of the element representing a row of the table. It should be a java-bean
 *        oriented {@link net.sf.mmm.util.pojo.api.Pojo}. Immutable objects (that have no setters) can also be
 *        used but only for read-only tables.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiModelListTable<ROW> extends AbstractUiModelTable<ROW> {

  // nothing to add...

}
