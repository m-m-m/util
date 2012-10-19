/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadModified;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadVisibleRecursive;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteAriaRole;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteMode;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteModeFixed;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteOnlySizeInPixel;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSize;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStylesAdvanced;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteDisposed;
import net.sf.mmm.util.validation.api.AbstractValidatableObject;

/**
 * This is the interface for an adapter to a physical <em>widget</em> (or potentially a composition of such
 * widgets) of the underlying native widget toolkit.<br/>
 * A widget is any object of the UI (user interface) including both {@link UiWidgetAtomic atomic widgets} as
 * well a {@link UiWidgetComposite composite widgets}. <br/>
 * Real {@link UiWidget}s can be {@link UiWidgetFactory#create(Class) created} via the {@link UiWidgetFactory}
 * so by choosing the implementation of {@link UiWidgetFactory} (via an IoC container framework like spring or
 * GIN) you can decide which native toolkit you like to use.<br/>
 * If you want to make your UI code portable even for toolkits such as SWT, you need to make proper use of
 * {@link #dispose()} for all {@link UiWidget} that are no longer needed.<br/>
 * <b>ATTENTION:</b><br/>
 * A {@link UiWidget} can only be used once in a client application. Even if you want to have the exact same
 * button twice on the screen you need to create two distinct instances. <br/>
 * <br/>
 * For advanced usage see also <code>net.sf.mmm.ui.toolkit.api.element.UiElement</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidget extends AttributeWriteHtmlId, AttributeReadVisibleRecursive, AttributeWriteVisible,
    AttributeWriteTooltip, AttributeWriteEnabled, AttributeWriteStylesAdvanced, AttributeWriteDisposed,
    AttributeWriteSize, AttributeWriteOnlySizeInPixel, AttributeReadModified, AttributeWriteMode,
    AttributeWriteModeFixed, AttributeWriteAriaRole, AbstractValidatableObject {

  /**
   * @return the parent of this widget or <code>null</code> if NOT attached to the UI or if this is a root
   *         widget (e.g. {@link net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetMainWindow}).
   */
  UiWidgetComposite<?> getParent();

  /**
   * This method gets the {@link UiWidgetFactory} that created this {@link UiWidget}. It may be used
   * internally in {@link UiWidget} implementations as well as externally to create new {@link UiWidget}s
   * (typically children).
   * 
   * @return the {@link UiWidgetFactory}.
   */
  UiWidgetFactory<?> getFactory();

}
