/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.attribute.AttributeReadAriaRole;
import net.sf.mmm.client.ui.api.attribute.AttributeReadModified;
import net.sf.mmm.client.ui.api.attribute.AttributeReadSizeInPixel;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteLengthProperty;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSizeAdvanced;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteStylesAdvanced;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteVisibleAdvanced;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.feature.UiFeatureMessages;
import net.sf.mmm.client.ui.api.feature.UiFeatureMode;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;
import net.sf.mmm.util.validation.api.AbstractValidatableObject;

/**
 * This is the interface for an adapter to a physical <em>widget</em> (or potentially a composition of such
 * widgets) of the underlying native widget toolkit.<br/>
 * A widget is any object of the UI (user interface) and may be atomic or {@link UiWidgetComposite composite}. <br/>
 * {@link UiWidgetNative Native widgets} can be {@link UiWidgetFactory#create(Class) created} via
 * {@link UiWidgetFactory} that has implementations for all supported toolkits (technically via
 * {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative}). This way you can decide which
 * native toolkit you like to use just by configuration and classpath (See
 * {@link net.sf.mmm.util.component.api.Cdi CDI}).<br/>
 * If you want to make your UI code portable even for toolkits such as SWT, you need to make proper use of
 * {@link #dispose()} for all {@link UiWidget} that are no longer needed what is generally a good idea.
 * Further for toolkits such as GWT there are strict limitations for the client-side code. The
 * <em>mmm project</em> provides abstraction for various technology specific areas to fill the gaps for you.
 * Use <em>mmm-service</em> for client/server communication and <code>mmm-client-dialog</code> for
 * history-management, embedding, etc.<br/>
 * <b>ATTENTION:</b><br/>
 * A {@link UiWidget} can only be used once in a client application. Even if you want to have the exact same
 * button twice on the screen you need to create two distinct instances. <br/>
 * Please also note that {@link UiWidget} and all its sub-interfaces are
 * {@link net.sf.mmm.util.component.api.Api#EXTENDABLE_INTERFACE extendable interfaces}.<br/>
 * <br/>
 * For advanced usage see also {@link net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidget extends UiFeatureMessages, UiFeatureEvent, UiFeatureMode, AttributeWriteHtmlId,
    AttributeWriteTooltip, AttributeWriteVisibleAdvanced, AttributeWriteEnabled, AttributeWriteStylesAdvanced,
    AttributeWriteDisposed, AttributeWriteSizeAdvanced, AttributeWriteLengthProperty, AttributeReadSizeInPixel,
    AttributeReadModified, AttributeReadAriaRole, AbstractValidatableObject {

  /** The {@link #addStyle(String) style} for a header element. */
  String STYLE_HEADER = "Header";

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

  /**
   * While {@link #getLength(net.sf.mmm.client.ui.api.common.LengthProperty)} or {@link #getWidth()} will only
   * return the {@link net.sf.mmm.client.ui.api.common.Length} value that has been set before (e.g. 50%),
   * {@link #getWidthInPixel()} will determine the actual width in
   * {@link net.sf.mmm.client.ui.api.common.LengthUnit#PIXEL pixels}. Therefore this widget should already be
   * attached (added to a parent visible on the screen).
   * 
   * {@inheritDoc}
   */
  @Override
  double getWidthInPixel();

  /**
   * While {@link #getLength(net.sf.mmm.client.ui.api.common.LengthProperty)} or {@link #getHeight()} will
   * only return the {@link net.sf.mmm.client.ui.api.common.Length} value that has been set before (e.g. 50%),
   * {@link #getHeightInPixel()} will determine the actual height in
   * {@link net.sf.mmm.client.ui.api.common.LengthUnit#PIXEL pixels}. Therefore this widget should already be
   * attached (added to a parent visible on the screen).
   * 
   * {@inheritDoc}
   */
  @Override
  double getHeightInPixel();

}
