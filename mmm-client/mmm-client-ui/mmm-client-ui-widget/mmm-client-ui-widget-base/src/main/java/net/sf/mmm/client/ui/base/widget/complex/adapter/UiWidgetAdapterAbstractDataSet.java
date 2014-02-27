/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSummary;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteTitleVisible;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetAbstractDataSet}.
 * 
 * @param <ITEM> is the generic type of the nodes of the tree.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterAbstractDataSet<ITEM> extends UiWidgetAdapterActive, AttributeWriteSelectionMode,
    AttributeWriteSummary, AttributeWriteStringTitle, AttributeWriteTitleVisible {

  // nothing to add...

}
