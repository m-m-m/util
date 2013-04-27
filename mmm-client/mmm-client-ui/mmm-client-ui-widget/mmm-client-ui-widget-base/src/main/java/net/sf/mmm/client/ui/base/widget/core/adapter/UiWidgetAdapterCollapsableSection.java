/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteCollapsed;
import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterClickable;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterCollapsableSection extends UiWidgetAdapterClickable, AttributeWriteCollapsed {

  /**
   * This method registers the given {@link UiHandlerEventCollapse collapse handler} in the adapted widget.
   * This method will be called only once.
   * 
   * @param eventSource is the
   *        {@link UiHandlerEventCollapse#onCollapseOrExpand(UiFeatureCollapse, boolean, boolean) event
   *        source}.
   * @param eventSender is the {@link UiHandlerEventCollapse}.
   */
  void setCollapseEventSender(UiFeatureCollapse eventSource, UiHandlerEventCollapse eventSender);

}
