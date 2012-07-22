/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;

/**
 * This is the interface that adapts to the native {@link #getWidget() widget} of the underlying toolkit
 * implementation for a {@link AbstractUiWidget}.<br/>
 * It is a design trade-off as java does not have multi-inheritance (we would need scala traits here). This
 * way it is possible to implement an abstract base-implementation for the types of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget}-hierarchy and inherit different implementations (Swing,
 * SWT, GWT, etc.) from that without creating redundant code.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public interface UiWidgetAdapter<WIDGET> extends AttributeWriteHtmlId, AttributeWriteTooltip, AttributeWriteVisible,
    AttributeWriteEnabled {

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#getNativeWidget(net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular)
   * 
   * @return the native widget.
   */
  WIDGET getWidget();

}
