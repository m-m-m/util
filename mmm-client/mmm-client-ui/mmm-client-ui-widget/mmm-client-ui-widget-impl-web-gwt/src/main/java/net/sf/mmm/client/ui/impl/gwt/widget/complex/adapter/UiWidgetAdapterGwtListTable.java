/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterListTable;

/**
 * This is the implementation of {@link UiWidgetAdapterListTable} using GWT.
 * 
 * @param <ROW> is the generic type of a
 *        {@link #addRow(net.sf.mmm.client.ui.base.widget.complex.TableRowContainer, int) row} in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtListTable<ROW> extends UiWidgetAdapterGwtAbstractListTable<ROW> implements
    UiWidgetAdapterListTable<ROW> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtListTable() {

    super();
  }

}
