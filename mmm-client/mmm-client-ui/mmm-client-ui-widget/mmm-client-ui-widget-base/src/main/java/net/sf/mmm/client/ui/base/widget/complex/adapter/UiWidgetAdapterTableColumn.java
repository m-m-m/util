/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteReorderable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteResizable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSortable;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.util.lang.api.SortOrder;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteTitle;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterTableColumn extends UiWidgetAdapter, AttributeWriteTitle<String>,
    AttributeWriteResizable, AttributeWriteReorderable, AttributeWriteSortable {

  /**
   * @param sortOrder is the {@link SortOrder} or <code>null</code> for none.
   */
  void setSortOrder(SortOrder sortOrder);

}
