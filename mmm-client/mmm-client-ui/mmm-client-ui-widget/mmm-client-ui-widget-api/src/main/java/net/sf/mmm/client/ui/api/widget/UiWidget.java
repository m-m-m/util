/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeReadAriaRole;
import net.sf.mmm.client.ui.api.attribute.AttributeReadModified;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteMode;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteModeFixed;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSizeAdvanced;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStylesAdvanced;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.client.ui.api.feature.UiFeatureMessages;
import net.sf.mmm.client.ui.api.feature.UiFeatureVisible;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventCollapse;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;
import net.sf.mmm.util.validation.api.AbstractValidatableObject;

/**
 * This is the interface for an adapter to a physical <em>widget</em> (or potentially a composition of such
 * widgets) of the underlying native widget toolkit.<br/>
 * A widget is any object of the UI (user interface) including both {@link UiWidgetAtomic atomic widgets} as
 * well a {@link UiWidgetComposite composite widgets}. <br/>
 * Real {@link UiWidget}s can be {@link UiWidgetFactory#create(Class) created} via {@link UiWidgetFactory}
 * that has implementations for all supported toolkits (technically via
 * {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative}). This way you can decide which
 * native toolkit you like to use just by configuration and classpath (See
 * {@link net.sf.mmm.util.component.api.Cdi CDI}).<br/>
 * If you want to make your UI code portable even for toolkits such as SWT, you need to make proper use of
 * {@link #dispose()} for all {@link UiWidget} that are no longer needed.<br/>
 * <b>ATTENTION:</b><br/>
 * A {@link UiWidget} can only be used once in a client application. Even if you want to have the exact same
 * button twice on the screen you need to create two distinct instances. <br/>
 * Please also note that {@link UiWidget} and all its sub-interfaces are
 * {@link net.sf.mmm.util.component.api.Api#EXTENDABLE_INTERFACE extendable interfaces}.<br/>
 * <br/>
 * For advanced usage see also <code>net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom</code>.
 * 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidget extends UiFeatureVisible, UiFeatureMessages, AttributeWriteHtmlId,
    AttributeWriteTooltip, AttributeWriteEnabled, AttributeWriteStylesAdvanced, AttributeWriteDisposed,
    AttributeWriteSizeAdvanced, AttributeReadModified, AttributeWriteMode, AttributeWriteModeFixed,
    AttributeReadAriaRole, AbstractValidatableObject, UiHandlerEventCollapse {

  /**
   * @return the parent of this widget or <code>null</code> if NOT attached to the UI or if this is a root
   *         widget (e.g. {@link net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow}).
   */
  UiWidgetComposite<?> getParent();

  /**
   * This method gets the {@link UiContext}. This is the container with central components for the UI and
   * client infrastructure. E.g. it contains the {@link UiContext#getWidgetFactory() widget-factory} that
   * created this {@link UiWidget}. It may be used internally in {@link UiWidget} implementations as well as
   * externally to create new {@link UiWidget}s (typically children).
   * 
   * @return the {@link UiContext}.
   */
  UiContext getContext();

}
