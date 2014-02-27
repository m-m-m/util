/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter;

import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterListTable;
import net.sf.mmm.client.ui.impl.gwt.widget.complex.ItemContainerGwt;

/**
 * This is the implementation of {@link UiWidgetAdapterListTable} using GWT.
 * 
 * @param <ROW> is the generic type of a row in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtListTable<ROW> extends UiWidgetAdapterGwtAbstractListTable<ROW> implements
    UiWidgetAdapterListTable<ROW, ItemContainerGwt<ROW>> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtListTable() {

    super();
  }

}
