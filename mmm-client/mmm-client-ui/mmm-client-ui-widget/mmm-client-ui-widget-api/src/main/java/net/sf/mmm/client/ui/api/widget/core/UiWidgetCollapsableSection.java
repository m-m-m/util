/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularAtomic;

/**
 * This is the interface for a {@link UiWidgetRegularAtomic regular atomic widget} that represents a
 * <em>collapsable section</em>. It is like {@link UiWidgetSection} but allows to be
 * {@link #setCollapsed(boolean) collapsed and expanded}.<br/>
 * <b>ATTENTION:</b><br/>
 * This widget should only be used directly for specific situations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetCollapsableSection extends UiWidgetRegularAtomic, AttributeWriteLabel, UiFeatureCollapse,
    UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String PRIMARY_STYLE = "Section";

  /**
   * {@link #addCollapseHandler(net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse) Adds} a
   * {@link net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse collapse handler} that triggers the
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteVisibleAdvanced#getVisibleFlag()} visibility} of
   * the given {@link UiWidget}. On collapse the {@link UiWidget} will be hidden, on expand it will be shown
   * (or more precisely its visible state will be restored).
   * 
   * @param widget is the widget to add.
   */
  void addCollapseWidget(UiWidget widget);

}
