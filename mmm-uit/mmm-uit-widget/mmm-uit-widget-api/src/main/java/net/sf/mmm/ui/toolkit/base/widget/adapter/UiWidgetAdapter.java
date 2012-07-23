/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSize;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStyles;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;

/**
 * This is the interface that adapts to the native {@link #getWidget() widget} of the underlying toolkit
 * implementation for a {@link net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidget}.<br/>
 * It is a design trade-off as java does not have multi-inheritance (we would need scala traits here). This
 * way it is possible to implement an abstract base-implementation for the types of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget}-hierarchy and inherit different implementations (Swing,
 * SWT, GWT, etc.) from that without creating redundant code.<br/>
 * <b>ATTENTION:</b><br/>
 * The getters (<code>AttributeRead*</code> methods) of attributes that cannot be changed by the end-user are
 * never used and therefore NOT implemented properly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public interface UiWidgetAdapter<WIDGET> extends AttributeWriteHtmlId, AttributeWriteTooltip, AttributeWriteVisible,
    AttributeWriteEnabled, AttributeWriteStyles, AttributeWriteDisposed, AttributeWriteSize {

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#getNativeWidget(net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular)
   * 
   * @return the native widget.
   */
  WIDGET getWidget();

  /**
   * This method removes the {@link #getWidget() widget} from its parent. After this method, the
   * {@link #getWidget() widget} is detached from the UI.
   */
  void removeFromParent();

  /**
   * This method registers the given {@link UiHandlerEventClick click handler} in the {@link #getWidget()
   * widget}. This method will be called only once.
   * 
   * @param eventSource is the {@link UiHandlerEventClick#onClick(UiFeatureClick, boolean) event source}.
   * @param eventSender is the {@link UiHandlerEventClick}.
   */
  void setClickEventSender(UiFeatureClick eventSource, UiHandlerEventClick eventSender);

}